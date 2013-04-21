package sorinpo.scr.edu.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import sorinpo.scr.edu.model.User;

public class DbUserService implements UserDetailsService {
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return User.findUsersByUsernameEquals(username).getSingleResult();
	}

}
