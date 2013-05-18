package sorinpo.scr.edu.service;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;

@Service
public class ReportService {

	private static final int SHEET_INDEX = 0;
	private static final int START_ROW = 2;
		
	@SuppressWarnings("incomplete-switch")
	public void writePupilParticipations(Collection<PupilParticipation> pps, OutputStream out) throws ReportException {

		if(pps == null || out == null){
			throw new IllegalArgumentException("PP and out cannot be null. Aceasta este o eroare interna. Contactati un administrator.");
		}
			
		Workbook wb =  null;
		
		try {
			wb = WorkbookFactory.create(ReportService.class.getResourceAsStream("/poi_templates/raportare.tmpl.xlsx"));
		} catch (IllegalArgumentException e){
			throw new ReportException("Nu s-a putut incarca templata de raport. Aceasta este o eroare interna. Contactati un administrator.", e);
		} catch (InvalidFormatException e){
			throw new ReportException("Nu s-a putut incarca templata de raport. Aceasta este o eroare interna. Contactati un administrator.", e);
		} catch (IOException e){
			throw new ReportException("Nu s-a putut incarca templata de raport. Aceasta este o eroare interna. Contactati un administrator.", e);
		}
		
		Sheet sheet = wb.getSheetAt(SHEET_INDEX);
		if(sheet==null){
			throw new ReportException("Sheet-ul cu indice " + (SHEET_INDEX+1) + " nu există.  Aceasta este o eroare interna. Contactati un administrator.");
		}
		
		Map<String, ReportRow> rep = new TreeMap<String, ReportRow>();
		
		for(PupilParticipation pp: pps){
			
			Pupil pupil = pp.getPupil();
			Participation p = pp.getParticipation();
			
			ReportRow row = rep.get(pupil.getOwner());
			if(row==null){
				row = new ReportRow();
				row.county = pupil.getOwner();
				rep.put(row.county, row);
			}
			row.children++;
			
			switch(pupil.getParentState()){
				case MOTHER: row.motherLeft++;break;
				case FATHER: row.fatherLeft++;break;
				case BOTH: row.bothLeft++;break;
			}
			
			updateActivityData(p, row, "school");
			updateActivityData(p, row, "freeTime");
			updateActivityData(p, row, "extraSchool");
			updateActivityData(p, row, "groupCounseling");
			updateActivityData(p, row, "individualCounseling");
			updateActivityData(p, row, "parentalCommunication");
			updateActivityData(p, row, "localMeetings");
		}
		
		int rowIdx = 0;
		
		for(ReportRow repRow: rep.values()){
			
			insertReportRow(repRow, sheet, rowIdx++);
			
		}
		
		insertTotalRow(sheet, rowIdx++, POIUtils.boldCellStyle(wb));
		
		try {
			wb.write(out);
		} catch (IOException e) {
			throw new ReportException("Nu s-a scris raportul. Aceasta este o eroare interna. Contactati un administrator.", e);
		}
			
	}
	
	private static void insertReportRow(ReportRow repRow, Sheet sheet, int rowIdx){
		Row row = sheet.createRow(START_ROW + rowIdx);
		int cellIdx = -1;
		Cell cell = row.createCell(++cellIdx);
		cell.setCellValue(repRow.county);
		
		cell = row.createCell(++cellIdx);
		cell.setCellValue(repRow.children);
		
		cell = row.createCell(++cellIdx);
		cell.setCellValue(repRow.motherLeft);
		
		cell = row.createCell(++cellIdx);
		cell.setCellValue(repRow.fatherLeft);
		
		cell = row.createCell(++cellIdx);
		cell.setCellValue(repRow.bothLeft);
		
		writeActivityReport(row, repRow.school, ++cellIdx);
		writeActivityReport(row, repRow.freeTime, cellIdx+=12);
		writeActivityReport(row, repRow.extraSchool, cellIdx+=12);
		writeActivityReport(row, repRow.groupCounseling, cellIdx+=12);
		writeActivityReport(row, repRow.individualCounseling, cellIdx+=12);
		writeActivityReport(row, repRow.parentalCommunication, cellIdx+=12);
		writeActivityReport(row, repRow.localMeetings, cellIdx+=12);
	}
	
	private static void insertTotalRow(Sheet sheet, int rowIdx, CellStyle style){
		Row row = sheet.createRow(START_ROW + rowIdx);
		Cell cell = row.createCell(0);
		cell.setCellValue("Total");
		cell.setCellStyle(style);
		
		for(int cellIdx = 1; cellIdx<89; cellIdx++){
			cell = row.createCell(cellIdx);
			CellReference top = new CellReference(START_ROW, cellIdx);
			CellReference bottom = new CellReference(START_ROW + rowIdx - 1, cellIdx);
			
			cell.setCellFormula("SUM(" + top.formatAsString() + ":" + bottom.formatAsString() + ")");
			cell.setCellStyle(style);
		}
	}
	
	private static void updateActivityData(Participation p, ReportRow row, String propName){
		
		try {
			ActivityData ad = (ActivityData) new PropertyDescriptor(propName, Participation.class).getReadMethod().invoke(p);
			ActivityReport ar = (ActivityReport) ReportRow.class.getField(propName).get(row);
			
			if(ad.getJan()){
				ar.jan++;
			}
			
			if(ad.getFeb()){
				ar.feb++;
			}	
			
			if(ad.getMar()){
				ar.mar++;
			}
			
			if(ad.getApr()){
				ar.apr++;
			}
			
			if(ad.getMay()){
				ar.may++;
			}
			
			if(ad.getJun()){
				ar.jun++;
			}
			
			if(ad.getJul()){
				ar.jul++;
			}
			
			if(ad.getAug()){
				ar.aug++;
			}
			
			if(ad.getSep()){
				ar.sep++;
			}
			
			if(ad.getOct()){
				ar.oct++;
			}
			
			if(ad.getNov()){
				ar.nov++;
			}
			
			if(ad.getDec()){
				ar.dec++;
			}
		} catch (Exception e) {
			//XXX
			throw new RuntimeException(e);
		}
		
	}
	
	private static void writeActivityReport(Row row, ActivityReport rep, int idx){
		Cell cell = row.createCell(idx++);
		cell.setCellValue(rep.jan);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.feb);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.mar);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.apr);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.may);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.jun);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.jul);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.aug);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.sep);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.oct);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.nov);
		cell = row.createCell(idx++);
		cell.setCellValue(rep.dec);
	}
	
	
	private static class ReportRow {
		
		public String county;
		public int children;
		public int motherLeft;
		public int fatherLeft;
		public int bothLeft;
				
		public ActivityReport school = new ActivityReport();
	    public ActivityReport freeTime = new ActivityReport();
	    public ActivityReport extraSchool = new ActivityReport();
	    public ActivityReport groupCounseling = new ActivityReport();
	    public ActivityReport individualCounseling = new ActivityReport();
	    public ActivityReport parentalCommunication = new ActivityReport();
	    public ActivityReport localMeetings = new ActivityReport();
	}
	
	private static class ActivityReport {
		
	    public int jan;
	    public int feb;
	    public int mar;
	    public int apr;
	    public int may;
	    public int jun;
	    public int jul;
	    public int aug;
	    public int sep;
	    public int oct;
	    public int nov;
	    public int dec;
	   
	}
	
	@SuppressWarnings("serial")
	public static class ReportException extends Exception {

		public ReportException(String message, Throwable cause) {
			super(message, cause);
		}

		public ReportException(String message) {
			super(message);
		}
		
	}
	
}
