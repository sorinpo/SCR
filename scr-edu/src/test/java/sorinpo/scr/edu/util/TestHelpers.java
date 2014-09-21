package sorinpo.scr.edu.util;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.Pupil.ParentState;
import sorinpo.scr.edu.model.User;

public class TestHelpers {

	@SuppressWarnings("unchecked")
	public static void setAuthentication(String username){
		User user = User.findUsersByUsernameEquals(username).getSingleResult();
		 ((SecurityContextImpl)SecurityContextHolder.getContext())
		 	.setAuthentication( new TestingAuthenticationToken( 
		 			user, null, (List<GrantedAuthority>)user.getAuthorities() ) );
	}
	
	public static class ValueHolder<T> {
		T value;
		
		public T getValue(){
			return value;
		}
		
		public void setValue(T value){
			this.value = value; 
		}
	}
	
	public static class StealerMatcher<T> extends BaseMatcher<T>{

		ValueHolder<T> holder;
		public StealerMatcher(ValueHolder<T> holder){
			this.holder = holder;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean matches(Object item) {
			
			holder.setValue((T) item);
			
			return true;
		}

		@Override
		public void describeTo(Description description) {
			//i steal values
		}
		
	}
	
	public static <T> Matcher<T> steal(final ValueHolder<T> holder){
		return new StealerMatcher<T>(holder);
	}
	
	public static Collection<PupilParticipation> createTestPupilParticipation() {
		Pupil pu1 = new Pupil();
		pu1.setOwner("AG");
		pu1.setName("test 1");
		pu1.setComment("comment 1");
		pu1.setLeftToCountry("country 1");
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
		pu2.setName("test 2");
		pu2.setLeftToCountry("country 2");
		pu2.setParentState(ParentState.MOTHER);
		Participation pa2 = new Participation();
		pa2.initializeActivityData();
		
		Pupil pu3 = new Pupil();
		pu3.setOwner("IS");
		pu3.setName("test 3");
		pu3.setComment("comment 3");
		pu3.setLeftToCountry("country 3");
		pu3.setParentState(ParentState.FATHER);
		Participation pa3 = new Participation();
		pa3.initializeActivityData();
		
		Collection<PupilParticipation> pps = Arrays.asList(
			new PupilParticipation(pu1, pa1),
			new PupilParticipation(pu2, pa2),
			new PupilParticipation(pu3, pa3)
		);
		return pps;
	}
	
	public static void openInSystemEditor(String fileSuffix, byte[] bytes){
		
		if(!Desktop.isDesktopSupported()){
			throw new IllegalArgumentException("Desktop integration is not supported. Cannot open files."); 
		}
		
		try {
			
			Path file = Files.createTempFile("", fileSuffix);
			Files.write(file, bytes);
			
			Desktop.getDesktop().open(file.toFile());
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		
	}
}
