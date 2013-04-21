package sorinpo.scr.edu.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import flexjson.JSONSerializer;

@SuppressWarnings("serial")
@RooJavaBean
@RooJpaActiveRecord(finders = { "findUsersByUsernameEquals", "findUsersByRole" })
public class User implements UserDetails {

	@Column(unique=true, nullable=false)
    private String username;
    
	@Column
    private String description;
	
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
    
	public static enum Role implements GrantedAuthority {
		
		ROLE_ADMIN, ROLE_USER;

		@Override
		public String getAuthority() {
			return this.toString();
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(role);
	}
	
	//merged aj
    public String toJson() {
        return new JSONSerializer().include("id", "username", "description", "role").exclude("*").serialize(this);
    }
    
    public static String toJsonArray(Collection<User> collection) {
        return new JSONSerializer().include("id", "username", "description", "role").exclude("*").serialize(collection);
    }
	
}
