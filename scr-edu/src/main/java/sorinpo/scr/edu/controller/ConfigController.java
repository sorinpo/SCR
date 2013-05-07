package sorinpo.scr.edu.controller;

import static sorinpo.scr.edu.util.HeaderUtils.headers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sorinpo.scr.edu.model.Config;

@Controller
@RequestMapping(headers = "Accept=application/json", value = "/config")
public class ConfigController {
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getConfig() {
        
        return new ResponseEntity<String>(Config.getConfig().toJson(), headers(), HttpStatus.OK);
    }
    
	@Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> updateConfig(@RequestBody String json) {
        
        return new ResponseEntity<String>(Config.updateFromJson(json).toJson(), headers(), HttpStatus.OK);
    }
	
}
