package sorinpo.scr.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.util.SecurityUtil;
import static sorinpo.scr.edu.util.HeaderUtils.headers;

@Controller
@RequestMapping(value = "/pupils.json")
//@PreAuthorize("isAuthenticated()") //already verified this condition in security xml
public class PupilController {

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(@RequestParam String owner) {
        
        if(!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(owner)){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        } 
        	
        List<Pupil> result = Pupil.findPupilsByOwner(owner).getResultList();
        return new ResponseEntity<String>(Pupil.toJsonArray(result), headers(), HttpStatus.OK);
    }
    
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, headers = "Accept=application/json")
    public ResponseEntity<String> createOrUpdateFromJson(@RequestParam String owner, @RequestBody String json) {
        Pupil pupil = Pupil.fromJsonToPupil(json);
        
        boolean userIsHimselfAndOwnsThePupil = SecurityUtil.getCurrenUsername().equals(owner) && owner.equals(pupil.getOwner());
        
        if(!SecurityUtil.isAdmin() && !userIsHimselfAndOwnsThePupil){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        }
        
        if(User.findUsersByUsernameEquals(owner).getSingleResult() == null){
        	return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
        }
        
        if(null != pupil.getId()) {
        	
        	if ((pupil = pupil.merge()) == null) {
                return new ResponseEntity<String>(headers(), HttpStatus.NOT_FOUND);
            }
        	
        } else {
        	//TODO check that the current user has the right to modify the existing pupil
        	pupil.persist();
        }
       
        return new ResponseEntity<String>(pupil.toJson(), headers(), HttpStatus.OK);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping( method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@RequestParam String owner, @RequestBody String json) {
        
    	if(!SecurityUtil.isAdmin()){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        }
    	
    	Pupil pupil = Pupil.fromJsonToPupil(json);        
    	
        if (pupil == null) {
            return new ResponseEntity<String>(headers(), HttpStatus.NOT_FOUND);
        }
        
        //removing participation data
        for(Participation p : Participation.findParticipationsByPupilId(pupil.getId()).getResultList()){
        	p.remove();
        }
        
        pupil.remove();
        
        return new ResponseEntity<String>(headers(), HttpStatus.OK);
    }
}
