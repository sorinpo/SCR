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

import sorinpo.scr.edu.model.CentruInfo;
import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/centru_infos.json")
public class CentruInfoController {

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getJson(@RequestParam String username) {

		User user = User.findUsersByUsernameEquals(username).getSingleResult();
		
		if (!user.getUsername().equalsIgnoreCase(
				SecurityUtil.getCurrenUsername())
				&& !SecurityUtil.isAdmin()) {
			return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
		}

		CentruInfo result = null;

		try {
			result = CentruInfo.findCentruInfoesByUserId(user.getId())
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			result = new CentruInfo(user.getId());
		}

		return new ResponseEntity<String>(result.toJson(), headers(),
				HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, headers = "Accept=application/json")
	public ResponseEntity<String> createOrUpdateFromJson(
			@RequestParam String username, @RequestBody String json) {

		User user = User.findUsersByUsernameEquals(username).getSingleResult();

		if (!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(user.getUsername())) {
			return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
		}

		CentruInfo.findCentruInfoesByUserId(user.getId()).getSingleResult();
		
		CentruInfo centruInfo = null;

		try {
			centruInfo = CentruInfo.findCentruInfoesByUserId(user.getId())
					.getSingleResult();
			CentruInfo.fromJsonToCentruInfo(json, centruInfo);
			
			if (!SecurityUtil.isAdmin() && !user.getId().equals(centruInfo.getUserId())) {
				return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
			}
			
			centruInfo = centruInfo.merge();
		} catch (EmptyResultDataAccessException e) {
			centruInfo = CentruInfo.fromJsonToCentruInfo(json);
			centruInfo.persist();
		}

		return new ResponseEntity<String>(centruInfo.toJson(), headers(),
				HttpStatus.OK);
	}

}
