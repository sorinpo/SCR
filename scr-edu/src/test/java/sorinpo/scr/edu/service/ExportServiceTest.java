package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.service.ExportService.ExportException;
import sorinpo.scr.edu.util.TestHelpers;


public class ExportServiceTest {

ExportService exportService = null;
	
	@Before
	public void setup(){
		exportService = new ExportService();
	}

	@Test
	public void writePupilsEmptySuccess() throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		exportService.writePupils(Collections.<PupilParticipation>emptyList(), out);
		
		byte[] buff = out.toByteArray();
		
		assertTrue( buff.length > 0 );
	}
	
	@Test
	public void writePupilsSuccess() throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		exportService.writePupils(TestHelpers.createTestPupilParticipation(), out);
		
		byte[] buff = out.toByteArray();
		
		assertTrue( buff.length > 0 );
		
		Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(buff));
		
		//AG
		Sheet sheet = wb.getSheetAt(0);
		assertEquals("AG", sheet.getSheetName());
		
		//AG test 1
		Row row = sheet.getRow(2);
		
		assertEquals("test 1",row.getCell(0).getStringCellValue());
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(1)));
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(2)));
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(3)));
		
		assertEquals("country 1",row.getCell(4).getStringCellValue());
		assertEquals("comment 1",row.getCell(4).getCellComment().getString().getString());

		//school jan
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(5)));
		//school feb		
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(6)));
		
		//free time feb
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(18)));
		//free time mar
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(19)));
		
		//extra mar
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(31)));
		//extra apr
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(32)));
		
		//group apr
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(44)));
		//group may
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(45)));
		
		//individual may
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(57)));
		//individual jun
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(58)));
		
		//communication jun
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(70)));
		//communication jul
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(51)));
		
		//local dec
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(88)));
		//local nov
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(87)));
				
		//AG test 2
		row = sheet.getRow(3);
		assertEquals("test 2",row.getCell(0).getStringCellValue());
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(1)));
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(2)));
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(3)));
		
		assertEquals("country 2",row.getCell(4).getStringCellValue());
		assertEquals(null, row.getCell(4).getCellComment());

		//school jan
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(5)));
		
		//IS
		sheet = wb.getSheetAt(1);
		assertEquals("IS", sheet.getSheetName());
		
		//IS test 3
		row = sheet.getRow(2);
		
		assertEquals("test 3",row.getCell(0).getStringCellValue());
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(1)));
		assertEquals(true, POIUtils.getCellValueAs1Blank(row.getCell(2)));
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(3)));
		
		assertEquals("country 3",row.getCell(4).getStringCellValue());
		assertEquals("comment 3",row.getCell(4).getCellComment().getString().getString());

		//school jan
		assertEquals(false, POIUtils.getCellValueAs1Blank(row.getCell(5)));		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullPP() throws ExportException {
		
		exportService.writePupils(null, System.out);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullOut() throws ExportException {
		
		exportService.writePupils(Collections.<PupilParticipation>emptyList(), null);
		
	}
	
}
