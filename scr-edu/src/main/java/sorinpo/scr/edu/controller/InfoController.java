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

import sorinpo.scr.edu.model.Info;
import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/infos.json")
public class InfoController {

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getJson(@RequestParam String username,
			@RequestParam int year) {

		User user = User.findUsersByUsernameEquals(username).getSingleResult();

		if (user == null || year == 0) {
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}

		if (!user.getUsername().equalsIgnoreCase(
				SecurityUtil.getCurrenUsername())
				&& !SecurityUtil.isAdmin()) {
			return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
		}

		Info result = null;

		try {
			result = Info.findInfoesByUserIdAndYear(user.getId(), year)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			result = new Info(user.getId(), year);
			result.initializeMonthlyNumbers();
		}

		return new ResponseEntity<String>(result.toJson(), headers(),
				HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, headers = "Accept=application/json")
	public ResponseEntity<String> createOrUpdateFromJson(
			@RequestBody String json) {

		Info info = Info.fromJsonToInfo(json);

		User user = User.findUser(info.getUserId());
		if (user == null || info.getYear() == 0) {
			return new ResponseEntity<String>(headers(), HttpStatus.BAD_REQUEST);
		}

		if (!SecurityUtil.isAdmin() && !SecurityUtil.getCurrenUsername().equals(user.getUsername())) {
			return new ResponseEntity<String>(headers(), HttpStatus.FORBIDDEN);
		}

		if (null != info.getId()) {

			if ((info = info.merge()) == null) {
				return new ResponseEntity<String>(headers(),
						HttpStatus.NOT_FOUND);
			}

		} else {
			// TODO check that the current user has the right to modify the existing pupil
			info.persist();
		}

		return new ResponseEntity<String>(info.toJson(), headers(),
				HttpStatus.OK);
	}

}
