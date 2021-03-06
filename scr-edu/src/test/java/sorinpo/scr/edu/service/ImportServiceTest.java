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
		
		Collection<PupilParticipation> pupilInfo = importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-success-ian-mar_merged.xls"), 3);
		
		assertEquals(50, pupilInfo.size());
		
		checkFirstEntry(pupilInfo);

	}
	
	@Test
	public void readPupilsSuccessNotMergedHeaderWithCedilles() throws ImportException{
		
		Collection<PupilParticipation> pupilInfo = importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-success-ian-mar_not_merged_cedilles.xls"), 3);
		
		assertEquals(50, pupilInfo.size());
		
		checkFirstEntry(pupilInfo);

	}
	
	@Test //#SCR2
	public void readPupilsSuccessCarasEmptyCellComment() throws ImportException{
		
		Collection<PupilParticipation> pupilInfo = importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-success-caras.xls"), 3);
		
		assertEquals(42, pupilInfo.size());
		
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
				
				assertTrue(p.getSchool().getJan());
				assertTrue(p.getSchool().getFeb());
				assertTrue(p.getSchool().getMar());
				assertTrue(!p.getSchool().getApr());
				
				assertTrue(p.getFreeTime().getJan());
				assertTrue(p.getFreeTime().getFeb());
				assertTrue(p.getFreeTime().getMar());
				assertTrue(!p.getFreeTime().getApr());
				
				assertTrue(p.getExtraSchool().getJan());
				assertTrue(p.getExtraSchool().getFeb());
				assertTrue(p.getExtraSchool().getMar());
				assertTrue(!p.getExtraSchool().getApr());
				
				assertTrue(p.getGroupCounseling().getJan());
				assertTrue(p.getGroupCounseling().getFeb());
				assertTrue(p.getGroupCounseling().getMar());
				assertTrue(!p.getGroupCounseling().getApr());
				
				assertTrue(!p.getIndividualCounseling().getJan());
				assertTrue(!p.getIndividualCounseling().getFeb());
				assertTrue(!p.getIndividualCounseling().getMar());
				assertTrue(!p.getIndividualCounseling().getApr());
				
				assertTrue(p.getParentalCommunication().getJan());
				assertTrue(p.getParentalCommunication().getFeb());
				assertTrue(p.getParentalCommunication().getMar());
				assertTrue(!p.getParentalCommunication().getApr());
				
				assertTrue(p.getLocalMeetings().getJan());
				assertTrue(p.getLocalMeetings().getFeb());
				assertTrue(p.getLocalMeetings().getMar());
				assertTrue(!p.getLocalMeetings().getApr());
			}
		}
		assertTrue(found);
	}
	
	@Test(expected=BadHeaderException.class)
	public void readPupilsHeaderFailure() throws ImportException{
		
		importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-fail-header.xls"), 3);
		
	}
	
	@Test(expected=BadHeaderException.class)
	public void readPupilsFailSuceavaHiddenSheets() throws ImportException{
		
		importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-fail-suceava.xls"), 3);
		
	}
	
	@Test(expected=EmptyNameException.class)
	public void readPupilsEmptyNameFailure() throws ImportException{
		
		importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-fail-empty-name.xls"), 3);
		
	}
	
	@Test(expected=FormatException.class)
	public void readPupilsBadFormatFailure() throws ImportException{
		
		importService.readPupilParticipations( new ByteArrayInputStream("test".getBytes()), 3);
		
	}
	
	@Test(expected=InvalidParentalStatusException.class)
	public void readPupilsBadMamaTata() throws ImportException{
		
		importService.readPupilParticipations( ImportServiceTest.class.getResourceAsStream("/import/test-fail-mama-tata.xls"), 3);
		
	}

}
