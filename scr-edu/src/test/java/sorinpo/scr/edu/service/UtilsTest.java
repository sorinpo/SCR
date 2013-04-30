package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Info;
import sorinpo.scr.edu.model.Participation;

public class UtilsTest {

	@Test
	public void replaceCedillesSuccess(){
		assertEquals("ABCșțȘȚtest",Utils.replaceCedilles("ABCșțȘȚtest"));
	}
	
	@Test
	public void replaceCedillesUnchanged(){
		assertEquals("ABCșțȘȚtest",Utils.replaceCedilles("ABCşţŞŢtest"));
	}
	
	@Test
	public void replaceCedillesNulll(){
		assertNull(Utils.replaceCedilles(null));
	}
	
	@Test
	public void updateInfoSuccess(){
		
		Info newInfo = new Info();
		newInfo.initializeMonthlyNumbers();
		newInfo.getAparitiiPresa().setJan(1);
		newInfo.getAparitiiPresa().setMar(1);
		newInfo.getBeneficiariIndirecti().setApr(1);
		
		Info oldInfo = new Info();
		oldInfo.initializeMonthlyNumbers();
		oldInfo.getAparitiiPresa().setFeb(1);
		oldInfo.getBeneficiariIndirecti().setApr(2);
		
		ActivityData activeMonths = new ActivityData();
		activeMonths.setJan(true);
		activeMonths.setApr(true);
		
		Info result = Utils.updateInfo(newInfo, oldInfo, activeMonths);
		
		assertEquals(1, result.getAparitiiPresa().getJan());
		assertEquals(1, result.getAparitiiPresa().getFeb());
		assertEquals(0, result.getAparitiiPresa().getMar());
		assertEquals(1, result.getBeneficiariIndirecti().getApr());
		assertEquals(0, result.getBeneficiariIndirecti().getMay());
	}
	
	@Test
	public void updateParticipationSuccess(){
		
		Participation newP = new Participation();
		newP.initializeActivityData();
		newP.getSchool().setJan(true);
		newP.getSchool().setMar(true);
		newP.getExtraSchool().setApr(true);
		
		Participation oldP = new Participation();
		oldP.initializeActivityData();
		oldP.getSchool().setFeb(true);
		oldP.getExtraSchool().setApr(true);
		
		ActivityData activeMonths = new ActivityData();
		activeMonths.setJan(true);
		activeMonths.setApr(true);
		
		Participation result = Utils.updateParticipation(newP, oldP, activeMonths);
		
		assertEquals(true, result.getSchool().isJan());
		assertEquals(true, result.getSchool().isFeb());
		assertEquals(false, result.getSchool().isMar());
		assertEquals(true, result.getExtraSchool().isApr());
		assertEquals(false, result.getExtraSchool().isMay());
	}
}
