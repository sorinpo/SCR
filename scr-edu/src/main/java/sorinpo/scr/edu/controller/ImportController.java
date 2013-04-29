package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;
import static sorinpo.scr.edu.util.HeaderUtils.htmlHeaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import sorinpo.scr.edu.dto.PupilParticipation;
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
			@RequestParam("file") MultipartFile file, @RequestParam("owner") String username, @RequestParam("year") int year, @RequestParam("strategy") Strategy strategy) {

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
		
		Collection<PupilParticipation> ppList;
		try {
			ppList = importService.readPupilParticipations(file.getInputStream(), 3);
		} catch (ImportException e){
			log.warn(e.getMessage(), e);
			return new ResponseEntity<String>(new ActionResponse(false,e.getMessage()).toJson(), htmlHeaders(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(new ActionResponse(false, "A aparut o eroare necunoscuta: " + e.getMessage()).toJson(), htmlHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Collection<String> names = new ArrayList<String>();
		for(PupilParticipation pp: ppList){
			names.add(pp.getPupil().getName());
		}
		List<Pupil> oldPupils = Pupil.findPupilsByNameInAndOwner(names, username).getResultList();
		
		if(strategy == Strategy.ABORT && oldPupils.size()>0){
			return new ResponseEntity<String>(new ActionResponse(false, "Importul a fost abandonat deoarece s-a detectat existenta unor intrari anterioare.").toJson(), HttpStatus.OK);
		}
		
		Map<String, Pupil> oldPupilsMap = new HashMap<String, Pupil>();
		for(Pupil pupil: oldPupils){
			oldPupilsMap.put(pupil.getName(), pupil);
		}
		
		int newEntries = 0;
		for(PupilParticipation pp: ppList){
			Pupil pupil = pp.getPupil();
			Participation part = pp.getParticipation();
			
			Pupil existingPupil = oldPupilsMap.get(pupil.getName());
			
			if(existingPupil!=null){
				if(strategy == Strategy.KEEP){
					continue;
				} else {
					pupil = existingPupil;
					Participation existingParticipation = Participation.getParticipationsByPupilIdAndYear(pupil.getId(), year);
					if(existingParticipation!=null){
						part = existingParticipation;
					}
				}
			} else {
				newEntries++;
			}
			
			pupil.setOwner(username);
			
			if(pupil.getId() != null) {
				pupil.merge();
			} else {
				pupil.persist();
			}
			
			part.setPupilId(pupil.getId());
			part.setYear(year);
			part.persist();
			
			if(part.getId() != null){
				part.merge();
			} else {
				part.persist();
			}
		}
		
		return new ResponseEntity<String>(new ActionResponse(true, "Au fost importate " + ppList.size() + " intrari din care " + newEntries + " noi.").toJson(), htmlHeaders(),
				HttpStatus.OK);
	}

	public ImportService getImportService() {
		return importService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public static enum Strategy {
		KEEP,
		OVERWRITE,
		ABORT
	}
}
