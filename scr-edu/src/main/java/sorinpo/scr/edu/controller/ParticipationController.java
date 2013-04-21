package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.dto.ParticipationsDTO;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Participation.Activity;
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
			return new ResponseEntity<String>(headers(), HttpStatus.NOT_FOUND);
		}
		
        if(!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(pupil.getOwner()) ){
        	return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
        } 
        
        List<Participation> ps = Participation.findParticipationsByPupilIdAndYear(pupilId, year).getResultList();
        		
        return new ResponseEntity<String>(new ParticipationsDTO(pupilId, year, ps).toJson(), headers(), HttpStatus.OK);
    }
    
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT} , headers = "Accept=application/json")
    public ResponseEntity<String> createOrUpdateFromJson(@RequestBody String json) {
    
    	ParticipationsDTO pdto = ParticipationsDTO.fromJsonToParticipationDTO(json);
    	Map<Activity, Participation> map = pdto.toMap();
    	
    	Long pupilId = pdto.getId();
    	int year = pdto.getYear();
    	
    	if(pupilId==null || year==0){
    		return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
    	}
    		
    	
    	List<Participation> ps = Participation.findParticipationsByPupilIdAndYear(pupilId, year).getResultList();
    	
    	for(Participation oldP: ps){
    		
    		if(map.containsKey(oldP.getActivity())){
    			
    			Participation newP = map.get(oldP.getActivity());
    			newP.setId(oldP.getId());
    			newP.setVersion(oldP.getVersion());
    			
    		}
    		
    	}
    	
    	ps = new ArrayList<Participation>();
    	
    	for(Participation newP: map.values()){
    		
    		newP.setPupilId(pupilId);
    		newP.setYear(year);
    		
    		if(newP.getId()!=null){
    			newP = newP.merge();
    		} else {
    			newP.persist();
    		}
    		
    		ps.add(newP);
    	}
    	
    	return new ResponseEntity<String>(new ParticipationsDTO(pupilId, year, ps).toJson(), headers(), HttpStatus.OK);
    	
    }
	
}
