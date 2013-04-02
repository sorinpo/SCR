package sorinpo.scr.edu.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.util.SecurityUtil;


@Controller
@RequestMapping(value = "/pupils.json")
//@PreAuthorize("isAuthenticated()") //already verified this condition in security xml
public class PupilController {

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Pupil> result = null;
        
        if(SecurityUtil.isAdmin()){
        	result = Pupil.findAllPupils();
        } else {
        	result = Pupil.findPupilsByCountyEquals(SecurityUtil.getCurrenUsername().toUpperCase()).getResultList();
        }
        
        return new ResponseEntity<String>(Pupil.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createOrUpdateFromJson(@RequestBody String json) {
        Pupil pupil = Pupil.fromJsonToPupil(json);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        if(null != pupil.getId()) {
        	
        	if ((pupil = pupil.merge()) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        	
        } else {
        	pupil.persist();
        }
       
        return new ResponseEntity<String>(pupil.toJson(), headers, HttpStatus.CREATED);
    }
	
//    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
//    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        Pupil pupil = Pupil.fromJsonToPupil(json);
//        if (pupil.merge() == null) {
//            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<String>(pupil.toJson(), headers, HttpStatus.OK);
//    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Pupil pupil = Pupil.findPupil(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (pupil == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        pupil.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
