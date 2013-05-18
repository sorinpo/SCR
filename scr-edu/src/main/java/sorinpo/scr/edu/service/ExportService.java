package sorinpo.scr.edu.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.Pupil.ParentState;

@Service
public class ExportService {

	private static final int TEMPLATE_SHEET_INDEX = 0;
	private static final int START_ROW = 2;
	
	public void writePupils(Collection<PupilParticipation> pps, OutputStream out) throws ExportException {

		if(pps == null || out == null){
			throw new IllegalArgumentException("pps and out cannot be null. Aceasta este o eroare interna. Contactati un administrator.");
		}
			
		Workbook wb =  null;
		
		try {
			wb = WorkbookFactory.create(ExportService.class.getResourceAsStream("/poi_templates/export.tmpl.xlsx"));
		} catch (IllegalArgumentException e){
			throw new ExportException("Nu s-a putut incarca templata de export. Aceasta este o eroare interna. Contactati un administrator.", e);
		} catch (InvalidFormatException e){
			throw new ExportException("Nu s-a putut incarca templata de export. Aceasta este o eroare interna. Contactati un administrator.", e);
		} catch (IOException e){
			throw new ExportException("Nu s-a putut incarca templata de export. Aceasta este o eroare interna. Contactati un administrator.", e);
		}
		
		Map<String, Collection<PupilParticipation>> gpps = groupOnOwner(pps);
		
		if(gpps.size()>0){
			
			cloneSheets(wb, gpps);
			
			int i=0;
			Sheet sheet = null;
			for(Entry<String, Collection<PupilParticipation>> entry: gpps.entrySet()){
				sheet = wb.getSheetAt(TEMPLATE_SHEET_INDEX + i);
				wb.setSheetName(TEMPLATE_SHEET_INDEX+i, entry.getKey());
				writeSheet(sheet, entry.getValue());
				i++;
			}
			
		}

		try {
			wb.write(out);
		} catch (IOException e) {
			throw new ExportException("Nu s-a scris exportul. Aceasta este o eroare interna. Contactati un administrator.", e);
		}
			
	}
	
	private void cloneSheets(Workbook wb,
			Map<String, Collection<PupilParticipation>> gpps)
			throws ExportException {
		Sheet sheet = wb.getSheetAt(TEMPLATE_SHEET_INDEX);
		if(sheet==null){
			throw new ExportException("Nu s-a gasit Sheet-ul templata.  Aceasta este o eroare interna. Contactati un administrator.");
		}
		for(int i=1; i < gpps.size(); i++){
			
			sheet = wb.cloneSheet(TEMPLATE_SHEET_INDEX);
			wb.setSheetOrder(sheet.getSheetName(), TEMPLATE_SHEET_INDEX	+ i);
		}
	}
	
	private Map<String, Collection<PupilParticipation>> groupOnOwner(Collection<PupilParticipation> pps){
		
		Map<String, Collection<PupilParticipation>> result = new TreeMap<String, Collection<PupilParticipation>>();
		
		for(PupilParticipation pp: pps){
			
			String owner = pp.getPupil().getOwner();
			Collection<PupilParticipation> pps2 = result.get(owner);
			if(pps2==null){
				pps2 = new ArrayList<PupilParticipation>();
				result.put(owner, pps2);
			}
			
			pps2.add(pp);
		}
		
		return result;
	}
	
	private void writeSheet(Sheet sheet, Collection<PupilParticipation> pps){
		
		int rowIdx = 0;
		for(PupilParticipation pp: pps){
		
			Pupil pupil = pp.getPupil();
			Participation p = pp.getParticipation();
			
			Row row = sheet.createRow(START_ROW + rowIdx++);
			
			int cellIdx = 0;
			cellIdx = writePupilData(pupil, row, cellIdx);
			
			cellIdx = writeActivityData(row, p.getSchool(), cellIdx);
			cellIdx = writeActivityData(row, p.getFreeTime(), cellIdx);
			cellIdx = writeActivityData(row, p.getExtraSchool(), cellIdx);
			cellIdx = writeActivityData(row, p.getGroupCounseling(), cellIdx);
			cellIdx = writeActivityData(row, p.getIndividualCounseling(), cellIdx);
			cellIdx = writeActivityData(row, p.getParentalCommunication(), cellIdx);
			cellIdx = writeActivityData(row, p.getLocalMeetings(), cellIdx);
						
		}
		
	}
	
	private static int writePupilData(Pupil pupil, Row row, int cellIdx){
		
		Cell cell = row.createCell(cellIdx++);
		cell.setCellValue(pupil.getName());
		
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, pupil.getParentState() == ParentState.MOTHER);
		
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, pupil.getParentState() == ParentState.FATHER);
		
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, pupil.getParentState() == ParentState.BOTH);
		
		cell = row.createCell(cellIdx++);
		cell.setCellValue(pupil.getLeftToCountry());
		
		if(pupil.getComment()!=null){
			POIUtils.setCellComment(cell, pupil.getComment(), pupil.getOwner());
		}
		
		return cellIdx;
	}
	
	private static int writeActivityData(Row row, ActivityData data, int cellIdx){
		Cell cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getJan());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getFeb());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getMar());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getApr());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getMay());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getJun());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getJul());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getAug());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getSep());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getOct());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getNov());
		cell = row.createCell(cellIdx++);
		POIUtils.setCellValueAs1Blank(cell, data.getDec());
		
		return cellIdx;
	}
	
	@SuppressWarnings("serial")
	public static class ExportException extends Exception {

		public ExportException(String message, Throwable cause) {
			super(message, cause);
		}

		public ExportException(String message) {
			super(message);
		}
		
	}
	
}