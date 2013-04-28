package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.Pupil.ParentState;
import sorinpo.scr.edu.service.ExportService.ExportException;


public class ExportServiceTest {

ExportService exportService = null;
	
	@Before
	public void setup(){
		exportService = new ExportService();
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
		
		Pupil pu1 = new Pupil();
		pu1.setOwner("AG");
		pu1.setParentState(ParentState.BOTH);
		
		Participation pa1 = new Participation();
		pa1.initializeActivityData();
		pa1.getSchool().setJan(true);
		pa1.getFreeTime().setFeb(true);
		pa1.getExtraSchool().setMar(true);
		pa1.getGroupCounseling().setApr(true);
		pa1.getIndividualCounseling().setMay(true);
		pa1.getParentalCommunication().setJun(true);
		pa1.getLocalMeetings().setDec(true);
		
		Pupil pu2 = new Pupil();
		pu2.setOwner("AG");
		pu2.setParentState(ParentState.MOTHER);
		Participation pa2 = new Participation();
		pa2.initializeActivityData();
		
		Pupil pu3 = new Pupil();
		pu3.setOwner("IS");
		pu3.setParentState(ParentState.FATHER);
		Participation pa3 = new Participation();
		pa3.initializeActivityData();
		
		Collection<PupilParticipation> pps = Arrays.asList(
			new PupilParticipation(pu1, pa1),
			new PupilParticipation(pu2, pa2),
			new PupilParticipation(pu3, pa3)
		);
		
		exportService.writePupilParticipations(pps, out);
		
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
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullPP() throws ExportException {
		
		exportService.writePupilParticipations(null, System.out);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void writePupilsNullOut() throws ExportException {
		
		exportService.writePupilParticipations(Collections.<PupilParticipation>emptyList(), null);
		
	}
	
}
