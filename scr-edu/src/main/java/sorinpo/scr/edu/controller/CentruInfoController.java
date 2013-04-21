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

		if (user == null) {
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}

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
			@RequestBody String json) {

		CentruInfo centruInfo = CentruInfo.fromJsonToCentruInfo(json);

		User user = User.findUser(centruInfo.getUserId());
		if (user == null) {
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}

		if (!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(user.getUsername())) {
			return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
		}

		if (null != centruInfo.getId()) {

			if ((centruInfo = centruInfo.merge()) == null) {
				return new ResponseEntity<String>(headers(),
						HttpStatus.NOT_FOUND);
			}

		} else {
			// TODO check that the current user has the right to modify the existing pupil
			centruInfo.persist();
		}

		return new ResponseEntity<String>(centruInfo.toJson(), headers(),
				HttpStatus.OK);
	}

}
