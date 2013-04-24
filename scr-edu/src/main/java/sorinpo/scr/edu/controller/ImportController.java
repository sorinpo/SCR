package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;
import static sorinpo.scr.edu.util.HeaderUtils.htmlHeaders;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sorinpo.scr.edu.dto.ActionResponse;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.service.ImportService;
import sorinpo.scr.edu.service.ImportService.ImportException;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/import")
public class ImportController {

	private static final Logger log = LoggerFactory.getLogger(ImportController.class);
	
	@Autowired
	private ImportService importService;
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createOrUpdateFromJson(
			@RequestParam("file") MultipartFile file, @RequestParam("owner") String username, @RequestParam("year") int year) {

		if (!SecurityUtil.isAdmin()) {
			return new ResponseEntity<String>(new ActionResponse(false).toJson(), htmlHeaders(), HttpStatus.FORBIDDEN);
		}
		
		User user = User.findUsersByUsernameEquals(username).getSingleResult();
		if (user == null || year == 0) {
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}
		
		if(file.isEmpty()){
			return new ResponseEntity<String>(new ActionResponse(false, "Fișierul este gol").toJson(), htmlHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		Map<Pupil, Participation> pupilMap;
		try {
			pupilMap = importService.readPupils(file.getInputStream(), 3);
		} catch (ImportException e){
			log.warn(e.getMessage(), e);
			return new ResponseEntity<String>(new ActionResponse(false,e.getMessage()).toJson(), htmlHeaders(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(new ActionResponse(false, "A aparut o eroare necunoscuta: " + e.getMessage()).toJson(), htmlHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for(Entry<Pupil, Participation> entry: pupilMap.entrySet()){
			Pupil pupil = entry.getKey();
			pupil.setOwner(username);
			pupil.persist();
			
			Participation p = entry.getValue();
			p.setPupilId(pupil.getId());
			p.setYear(year);
			p.persist();
		}
		
		return new ResponseEntity<String>(new ActionResponse(true).toJson(), htmlHeaders(),
				HttpStatus.OK);
	}

	public ImportService getImportService() {
		return importService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

}
