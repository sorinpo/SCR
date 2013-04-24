package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/participations.json")
//@PreAuthorize("isAuthenticated()") //already verified this condition in security xml
public class ParticipationController {

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getJson(@RequestParam("id") Long pupilId, @RequestParam int year) {
        
		Pupil pupil = Pupil.findPupil(pupilId);
		
		if(pupil==null){
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}
		
        if(!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(pupil.getOwner()) ){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        } 
        
        Participation p;
		try {
			p = Participation.findParticipationsByPupilIdAndYear(pupil.getId(), year)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			p = new Participation(pupil.getId(), year);
			p.initializeActivityData();
		}
        		
        return new ResponseEntity<String>(p.toJson(), headers(), HttpStatus.OK);
    }
    
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT} , headers = "Accept=application/json")
    public ResponseEntity<String> createOrUpdateFromJson(@RequestBody String json) {
    
    	Participation p = Participation.fromJsonToParticipation(json);
    	
    	Long pupilId = p.getPupilId();
    	int year = p.getYear();
    	if(pupilId==null || year == 0){
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}
    	
		Pupil pupil = Pupil.findPupil(pupilId);
		
		if(pupil==null){
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}
		
        if(!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(pupil.getOwner()) ){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        } 
    	
    	
		if (null != p.getId()) {

			if ((p = p.merge()) == null) {
				return new ResponseEntity<String>(headers(),
						HttpStatus.NOT_FOUND);
			}

		} else {
			// TODO check that the current user has the right to modify the existing participation data
			p.persist();
		}
    	
    	return new ResponseEntity<String>(p.toJson(), headers(), HttpStatus.OK);
    	
    }
	
}
