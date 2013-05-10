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
		newInfo.setLinks("link1");
		
		Info oldInfo = new Info();
		oldInfo.initializeMonthlyNumbers();
		oldInfo.getAparitiiPresa().setFeb(1);
		oldInfo.getBeneficiariIndirecti().setApr(2);
		oldInfo.setLinks("link0");
		
		ActivityData activeMonths = new ActivityData(true);
		activeMonths.setJan(true);
		activeMonths.setApr(true);
		
		Info result = Utils.updateInfo(newInfo, oldInfo, activeMonths);
		
		assertEquals(1, result.getAparitiiPresa().getJan());
		assertEquals(1, result.getAparitiiPresa().getFeb());
		assertEquals(0, result.getAparitiiPresa().getMar());
		assertEquals(1, result.getBeneficiariIndirecti().getApr());
		assertEquals(0, result.getBeneficiariIndirecti().getMay());
		
		//XXX
		assertEquals("link0", result.getLinks());
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
		
		ActivityData activeMonths = new ActivityData(true);
		activeMonths.setJan(true);
		activeMonths.setApr(true);
		
		Participation result = Utils.updateParticipation(newP, oldP, activeMonths);
		
		assertEquals(true, result.getSchool().getJan());
		assertEquals(true, result.getSchool().getFeb());
		assertEquals(false, result.getSchool().getMar());
		assertEquals(true, result.getExtraSchool().getApr());
		assertEquals(false, result.getExtraSchool().getMay());
	}
	
	@Test
	public void updateParticipationSuccessWithIgnoredNulls(){
		
		Participation newP = new Participation();
		newP.initializeActivityData();
		newP.getSchool().setJan(true);
		newP.getSchool().setFeb(null);
		newP.setExtraSchool(null);
		
		Participation oldP = new Participation();
		oldP.initializeActivityData();
		oldP.getSchool().setJan(false);
		oldP.getSchool().setFeb(true);
		oldP.getExtraSchool().setJan(true);
		
		ActivityData activeMonths = new ActivityData(true);
		activeMonths.setJan(true);
		activeMonths.setFeb(true);
		
		Participation result = Utils.updateParticipation(newP, oldP, activeMonths);
		
		assertEquals(true, result.getSchool().getJan());
		assertEquals(true, result.getSchool().getFeb());
		assertEquals(true, result.getExtraSchool().getJan());
		
	}
}
