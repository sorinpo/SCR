package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.service.ReportService.ReportException;
import sorinpo.scr.edu.util.TestHelpers;


public class ReportServiceTest {

ReportService exportService = null;
	
	@Before
	public void setup(){
		exportService = new ReportService();
	}


	@Test
	public void writePupilsEmptySuccess() throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		exportService.writePupilParticipations(Collections.<PupilParticipation>emptyList(), out);
		
		byte[] buff = out.toByteArray();
		
		assertTrue( buff.length > 0 );
	}
	
	@Test
	public void writePupilsSuccess() throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		exportService.writePupilParticipations(TestHelpers.createTestPupilParticipation(), out);
		
		byte[] buff = out.toByteArray();
		
		assertTrue( buff.length > 0 );
		
		Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(buff));
		
		Sheet sheet = wb.getSheetAt(0);
		
		//AG
		Row row = sheet.getRow(2);
		assertNotNull(row);
		
		assertEquals("AG",row.getCell(0).getStringCellValue());
		assertEquals(2,(int)row.getCell(1).getNumericCellValue());
		
		assertEquals(1,(int)row.getCell(2).getNumericCellValue());
		assertEquals(0,(int)row.getCell(3).getNumericCellValue());
		assertEquals(1,(int)row.getCell(4).getNumericCellValue());
		
		//school jan
		assertEquals(1,(int)row.getCell(5).getNumericCellValue());
		//school feb
		assertEquals(0,(int)row.getCell(6).getNumericCellValue());
		
		//free time feb
		assertEquals(1,(int)row.getCell(18).getNumericCellValue());
		//free time mar
		assertEquals(0,(int)row.getCell(19).getNumericCellValue());
		
		//extra mar
		assertEquals(1,(int)row.getCell(31).getNumericCellValue());
		//extra apr
		assertEquals(0,(int)row.getCell(32).getNumericCellValue());
		
		//group apr
		assertEquals(1,(int)row.getCell(44).getNumericCellValue());
		//group may
		assertEquals(0,(int)row.getCell(45).getNumericCellValue());
		
		//individual may
		assertEquals(1,(int)row.getCell(57).getNumericCellValue());
		//individual jun
		assertEquals(0,(int)row.getCell(58).getNumericCellValue());
		
		//communication jun
		assertEquals(1,(int)row.getCell(70).getNumericCellValue());
		//communication jul
		assertEquals(0,(int)row.getCell(51).getNumericCellValue());
		
		//local dec
		assertEquals(1,(int)row.getCell(88).getNumericCellValue());
		//local nov
		assertEquals(0,(int)row.getCell(87).getNumericCellValue());
				
		//IS
		row = sheet.getRow(3);
		assertNotNull(row);
		
		assertEquals("IS",row.getCell(0).getStringCellValue());
		assertEquals(1,(int)row.getCell(1).getNumericCellValue());
		
		//TOTAL
		row = sheet.getRow(4);
		assertNotNull(row);
		assertEquals("Total",row.getCell(0).getStringCellValue());
		
		assertEquals(Cell.CELL_TYPE_FORMULA, row.getCell(1).getCellType());
		
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		assertEquals(3,  (int)evaluator.evaluate(row.getCell(1)).getNumberValue() );		
		
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullPP() throws ReportException {
		
		exportService.writePupilParticipations(null, System.out);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullOut() throws ReportException {
		
		exportService.writePupilParticipations(Collections.<PupilParticipation>emptyList(), null);
		
	}
	
}
