package sorinpo.scr.edu.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import sorinpo.scr.edu.model.ParentalCommunicationData;
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
			
			int cellIdx = writeNrCrt(rowIdx, row);
			
			cellIdx = writePupilData(pupil, row, cellIdx);
			
			cellIdx = writeParentalCommunicationData(row, p.getParentalCommunicationDetailed(), cellIdx);
			
			//minimum one activity
			cellIdx = writeMinimumActivityData(row, p, cellIdx);
			
			//inactivity
			cellIdx = writeActivityData(row, p.getInactivity(), cellIdx);
			
			cellIdx = writeActivityData(row, p.getSchool(), cellIdx);
			cellIdx = writeActivityData(row, p.getFreeTime(), cellIdx);
			cellIdx = writeActivityData(row, p.getExtraSchool(), cellIdx);
			cellIdx = writeActivityData(row, p.getGroupCounseling(), cellIdx);
			cellIdx = writeActivityData(row, p.getIndividualCounseling(), cellIdx);
			cellIdx = writeActivityData(row, p.getParentalCommunication(), cellIdx);
			cellIdx = writeActivityData(row, p.getLocalMeetings(), cellIdx);
		}
		
	}
	
	private static int writeNrCrt(int rowIdx, Row row){
		Cell cell = row.createCell(0);
		cell.setCellValue(rowIdx);
		return 1;
	}
	
	private static int writePupilData(Pupil pupil, Row row, int cellIdx){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Cell cell = row.createCell(cellIdx++);
		cell.setCellValue(pupil.getName());
		
		cell = row.createCell(cellIdx++);
		if(pupil.getBirthDate() != null){
			cell.setCellValue( dateFormat.format(pupil.getBirthDate() ));
		}
		
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
		
		cell = row.createCell(cellIdx++);
		if(pupil.getRecruitmentDate() != null){
			cell.setCellValue(dateFormat.format( pupil.getRecruitmentDate() ));
		}
		
		cell = row.createCell(cellIdx++);
		if(pupil.getRecruitmentMethod() != null){
			cell.setCellValue(pupil.getRecruitmentMethod().getText());
		}
		
		return cellIdx;
	}
	
	private static int writeParentalCommunicationData(Row row, ParentalCommunicationData data, int cellIdx){
		
		for(int month=0; month < 12; month++){
			Cell cell = row.createCell(cellIdx++);
			
			ParentState parentState = data.get(month);
			
			if(parentState != null){
				cell.setCellValue(parentState.getText());
			}			
		}
		
		return cellIdx;
	}
	
	private static int writeMinimumActivityData(Row row, Participation p, int cellIdx) {
		
		for(int month=0; month < 12; month++){
			
			boolean val = false;
			
			for(ActivityData ad: p.getActivityData()) {
				if(ad.get(month)) {
					val = true;
					break;
				}
			}
			
			Cell cell = row.createCell(cellIdx++);
			POIUtils.setCellValueAs1Blank(cell, val);
		}
		
		return cellIdx;
		
	}
	
	private static int writeActivityData(Row row, ActivityData data, int cellIdx){
		
		for(int month=0; month < 12; month++){
			Cell cell = row.createCell(cellIdx++);
			POIUtils.setCellValueAs1Blank(cell, data.get(month));
		}
		
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