package sorinpo.scr.edu.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import sorinpo.scr.edu.model.User;

public class SecurityUtil {

	public static String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static String getCurrenUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return null;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return null;

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		if (principal == null)
			return null;

		String username = principal.getUsername();
		if (username == null)
			return null;

		return username;
	}

	public static User getCurrenUser(){
		 
        String username = getCurrenUsername();
        
        if(username == null)
        	return null;
        
        return User.findUsersByUsernameEquals(username).getSingleResult();
	}
	
	public static boolean isAdmin(){
		return hasRole(ROLE_ADMIN);
	}
		
	public static boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

	
}
