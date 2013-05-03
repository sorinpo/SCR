package sorinpo.scr.edu.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
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
public class ImportService {

	private static final int SHEET_INDEX = 0;
	private static final int SKIP_ROWS = 2;
	private static final int SKIP_COLLS = 1;
	
	public Collection<PupilParticipation> readPupilParticipations(InputStream in, int numMonths) throws ImportException {
		
		List<PupilParticipation> result = new ArrayList<PupilParticipation>();
		
		try {
			
			Workbook wb =  null;
			
			try {
				wb = WorkbookFactory.create(in);
			} catch (IllegalArgumentException e){
				throw new FormatException("Formatul fisierului este invalid", e);
			}
			
			Sheet sheet = wb.getSheetAt(SHEET_INDEX);
			if(sheet==null){
				throw new ImportException("Sheet-ul cu indice " + (SHEET_INDEX+1) + " nu există");
			}
			
			boolean foundHeader = false;
			boolean foundEmptyNumeSiPrenume = false;
			for(Row row : sheet){
				int rowIndex = row.getRowNum(); 
				if(rowIndex < SKIP_ROWS){
					if(!foundHeader){
						Cell cell = row.getCell(SKIP_COLLS, Row.RETURN_BLANK_AS_NULL);
						if(cell!=null && cell.getCellType()==Cell.CELL_TYPE_STRING && 
								"Nume și prenume".equalsIgnoreCase(getStringNorm(cell))){
							foundHeader = true;
						}
					}
					continue;
				}
				
				if(!foundHeader){
					break;
				}
				
				Cell cell = row.getCell(SKIP_COLLS, Row.RETURN_BLANK_AS_NULL);
				
				if(cell==null || cell.getCellType()!=Cell.CELL_TYPE_STRING ){
					foundEmptyNumeSiPrenume = true;
					continue;
				} else if(foundEmptyNumeSiPrenume) {
					throw new EmptyNameException("In coloana \"Nume și prenume\" este intercalat un rand cu nume gol");
				}
				
				Pupil pupil = new Pupil();
				pupil.setName(getStringNorm(cell));
				
				boolean mama = readBoolean( row.getCell(SKIP_COLLS+1 , Row.RETURN_BLANK_AS_NULL));
				boolean tata = readBoolean( row.getCell(SKIP_COLLS+2 , Row.RETURN_BLANK_AS_NULL));
				
				cell = row.getCell(SKIP_COLLS+3 , Row.RETURN_BLANK_AS_NULL);
				boolean ambii = readBoolean( cell );
				
				if((ambii && mama) || (mama && tata)  || (ambii && tata))
					throw new InvalidParentalStatusException("Randul cu numarul " + (rowIndex+1) + " nu are bifata doar una dintre celulele cu parintii plecati");
				
				pupil.setParentState(ambii?ParentState.BOTH: mama?ParentState.MOTHER: tata?ParentState.FATHER : ParentState.NONE);
				
				Comment comment = cell!=null?cell.getCellComment():null;
				if(comment!=null){
					pupil.setComment(comment.getString().getString().substring(comment.getAuthor().length()+2));
				}
				
				cell = row.getCell(SKIP_COLLS+4, Row.RETURN_BLANK_AS_NULL);
				pupil.setLeftToCountry(getStringNorm(cell));
				
				//participations
				
				Participation p = new Participation();
				
				p.setSchool( readParticipation(SKIP_COLLS+5+numMonths*0, row, numMonths));
				
				p.setFreeTime( readParticipation(SKIP_COLLS+5+numMonths*1, row, numMonths));
				
				p.setExtraSchool( readParticipation(SKIP_COLLS+5+numMonths*2, row, numMonths));
				
				p.setGroupCounseling( readParticipation(SKIP_COLLS+5+numMonths*3, row, numMonths));
				
				p.setIndividualCounseling( readParticipation(SKIP_COLLS+5+numMonths*4, row, numMonths));
				
				p.setParentalCommunication( readParticipation(SKIP_COLLS+5+numMonths*5, row, numMonths));
				
				p.setLocalMeetings( readParticipation(SKIP_COLLS+5+numMonths*6, row, numMonths));
				
				result.add(new PupilParticipation(pupil, p));
			}
			
			if(!foundHeader) {
				throw new BadHeaderException("headerul \"Nume și prenume\" nu a fost gasit pe coloana " + (SKIP_COLLS+1));
			}
			
		} catch (InvalidFormatException e) {
			throw new FormatException("Formatul fisierului este invalid",e);
		} catch (IOException e) {
			throw new ImportException("A aparut o eroare la citirea datelor",e);
		}
		
		return result;
	}
	
	private static ActivityData readParticipation(int index, Row row, int numMonths) throws ImportException{
		
		if(numMonths<0)
			throw new IllegalArgumentException("Numarul de luni trebuie sa fie un intreg pozitiv");
		
		ActivityData result = new ActivityData(true);
		
		int i = index;
		numMonths += index;
		
		if(i==numMonths) return result;
		result.setJan( readBoolean( row.getCell(i++, Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setFeb( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setMar( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setApr( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setMay( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setJun( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setJul( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setAug( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setSep( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setOct( readBoolean( row.getCell(i++,  Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setNov( readBoolean( row.getCell(i++, Row.RETURN_BLANK_AS_NULL)));
		if(i==numMonths) return result;
		result.setDec( readBoolean( row.getCell(i++, Row.RETURN_BLANK_AS_NULL)));
		
		return result;
		
	}
	
	private static boolean readBoolean(Cell cell) throws ImportException{
		if(cell == null)
			return false;
		
		if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
			return cell.getBooleanCellValue();
		
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if(cell.getNumericCellValue() == 0) return false;
			if(cell.getNumericCellValue() == 1) return true;
		}
		
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			String val = cell.getStringCellValue().trim();
			
			if(val.equals("1") || val.equalsIgnoreCase("DA"))
				return true;
			if(val.equals("0") || val.equalsIgnoreCase("NU") || val.equalsIgnoreCase("-"))
				return false;
		}
		
		throw new ImportException("N-am putut interpeta valoare de adevar a celulei de pe randul " + (cell.getRowIndex() + 1) + " si coloana " + (cell.getColumnIndex() + 1) );
	}
	
	private static String getStringNorm(Cell cell){
		
		if(cell==null)
			return null;
		
		if(cell.getCellType() != Cell.CELL_TYPE_STRING){
			throw new IllegalArgumentException("The cell is not a string type cell");
		}
		
		return Utils.replaceCedilles(cell.getStringCellValue().trim());
		
	}
	
	
	@SuppressWarnings("serial")
	public static class ImportException extends Exception {

		public ImportException(String message, Throwable cause) {
			super(message, cause);
		}

		public ImportException(String message) {
			super(message);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class FormatException extends ImportException {
		
		public FormatException(String message, Throwable cause) {
			super(message, cause);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class BadHeaderException extends ImportException {
		
		public BadHeaderException(String message) {
			super(message);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class EmptyNameException extends ImportException {
		
		public EmptyNameException(String message) {
			super(message);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class InvalidParentalStatusException extends ImportException {
		
		public InvalidParentalStatusException(String message) {
			super(message);
		}
		
	}
}
