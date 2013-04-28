package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.Pupil.ParentState;
import sorinpo.scr.edu.service.ImportService.BadHeaderException;
import sorinpo.scr.edu.service.ImportService.EmptyNameException;
import sorinpo.scr.edu.service.ImportService.FormatException;
import sorinpo.scr.edu.service.ImportService.ImportException;
import sorinpo.scr.edu.service.ImportService.InvalidParentalStatusException;


public class ImportServiceTest {

ImportService importService = null;
	
	@Before
	public void setup(){
		importService = new ImportService();
	}

	@Test
	public void readPupilsSuccessMergedHeader() throws ImportException{
		
		Collection<PupilParticipation> pupilInfo = importService.readPupilParticipations( ExportServiceTest.class.getResourceAsStream("/import/test-success-ian-mar_merged.xls"), 3);
		
		assertTrue(pupilInfo.size()==50);
		
		checkFirstEntry(pupilInfo);

	}
	
	@Test
	public void readPupilsSuccessNotMergedHeaderWithCedilles() throws ImportException{
		
		Collection<PupilParticipation> pupilInfo = importService.readPupilParticipations( ExportServiceTest.class.getResourceAsStream("/import/test-success-ian-mar_not_merged_cedilles.xls"), 3);
		
		assertTrue(pupilInfo.size()==50);
		
		checkFirstEntry(pupilInfo);

	}
	
	private void checkFirstEntry(Collection<PupilParticipation> pupilInfo){
		boolean found=false;
		
		for(PupilParticipation entry : pupilInfo){
			Pupil pupil = entry.getPupil();
			if("Augustin Roxana Ioana".equals(pupil.getName())){
				found = true;
				//Augustin Roxana Ioana	1	0	0	Franta	1	1	1	1	1	1	1	1	1	1	1	1				1	1	1	1	1	1
				assertEquals(ParentState.MOTHER, pupil.getParentState());
				assertEquals("Franța", pupil.getLeftToCountry());
				
				assertEquals("test comment 1", pupil.getComment());
				
				Participation p = entry.getParticipation();
				
				assertTrue(p.getSchool().isJan());
				assertTrue(p.getSchool().isFeb());
				assertTrue(p.getSchool().isMar());
				assertTrue(!p.getSchool().isApr());
				
				assertTrue(p.getFreeTime().isJan());
				assertTrue(p.getFreeTime().isFeb());
				assertTrue(p.getFreeTime().isMar());
				assertTrue(!p.getFreeTime().isApr());
				
				assertTrue(p.getExtraSchool().isJan());
				assertTrue(p.getExtraSchool().isFeb());
				assertTrue(p.getExtraSchool().isMar());
				assertTrue(!p.getExtraSchool().isApr());
				
				assertTrue(p.getGroupCounseling().isJan());
				assertTrue(p.getGroupCounseling().isFeb());
				assertTrue(p.getGroupCounseling().isMar());
				assertTrue(!p.getGroupCounseling().isApr());
				
				assertTrue(!p.getIndividualCounseling().isJan());
				assertTrue(!p.getIndividualCounseling().isFeb());
				assertTrue(!p.getIndividualCounseling().isMar());
				assertTrue(!p.getIndividualCounseling().isApr());
				
				assertTrue(p.getParentalCommunication().isJan());
				assertTrue(p.getParentalCommunication().isFeb());
				assertTrue(p.getParentalCommunication().isMar());
				assertTrue(!p.getParentalCommunication().isApr());
				
				assertTrue(p.getLocalMeetings().isJan());
				assertTrue(p.getLocalMeetings().isFeb());
				assertTrue(p.getLocalMeetings().isMar());
				assertTrue(!p.getLocalMeetings().isApr());
			}
		}
		assertTrue(found);
	}
	
	@Test(expected=BadHeaderException.class)
	public void readPupilsHeaderFailure() throws ImportException{
		
		importService.readPupilParticipations( ExportServiceTest.class.getResourceAsStream("/import/test-fail-header.xls"), 3);
		
	}
	
	@Test(expected=EmptyNameException.class)
	public void readPupilsEmptyNameFailure() throws ImportException{
		
		importService.readPupilParticipations( ExportServiceTest.class.getResourceAsStream("/import/test-fail-empty-name.xls"), 3);
		
	}
	
	@Test(expected=FormatException.class)
	public void readPupilsBadFormatFailure() throws ImportException{
		
		importService.readPupilParticipations( new ByteArrayInputStream("test".getBytes()), 3);
		
	}
	
	@Test(expected=InvalidParentalStatusException.class)
	public void readPupilsBadMamaTata() throws ImportException{
		
		importService.readPupilParticipations( ExportServiceTest.class.getResourceAsStream("/import/test-fail-mama-tata.xls"), 3);
		
	}

}
