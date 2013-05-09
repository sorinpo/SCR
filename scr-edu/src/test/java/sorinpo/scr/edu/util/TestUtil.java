package sorinpo.scr.edu.util;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import sorinpo.scr.edu.model.User;

public class TestUtil {

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
	
}
