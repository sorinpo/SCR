package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.model.User.Role;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/users.json")
public class UserController {

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getJson() {
        
        
        List<User> result = null; 
        		
        if(SecurityUtil.isAdmin()){
        	result = User.findUsersByRole(Role.ROLE_USER).getResultList();
        } else {
        	result = Arrays.asList( SecurityUtil.getCurrenUser() );
        }
        
        return new ResponseEntity<String>(User.toJsonArray(result), headers(), HttpStatus.OK);
    }
    
}
