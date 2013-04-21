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
import sorinpo.scr.edu.model.Month;
import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

@Controller
public class ConfigController {
	@RequestMapping(headers = "Accept=application/json", value = "/config.json")
    @ResponseBody
    public ResponseEntity<String> getConfig() {
        
        
        
        return new ResponseEntity<String>(Config.getConfig().toJson(), headers(), HttpStatus.OK);
    }
    
	@Secured("ROLE_ADMIN")
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, headers = "Accept=application/json", value = "/config.json")
    public ResponseEntity<String> updateConfig(@RequestBody String json) {
          
        
       
       
        return new ResponseEntity<String>(Config.updateFromJson(json).toJson(), headers(), HttpStatus.CREATED);
    }
	
	@RequestMapping(headers = "Accept=application/json", value = "/months.json")
    @ResponseBody
    public ResponseEntity<String> getMonths() {
        
        
        
        return new ResponseEntity<String>(
        		new JSONSerializer().include("value","display").exclude("*").transform(new AbstractTransformer(){

        			public void transform(Object object) {
        		        Month month = (Month) object;
        		        
        		        getContext().writeOpenObject();
        		        getContext().writeName("id");
        		        getContext().writeQuoted( month.name() );
        		        getContext().writeComma();
        		        getContext().writeName("ord");
        		        getContext().write( String.valueOf( month.getOrd() ) ); 
        		        getContext().writeComma();
        		        getContext().writeName("name");
        		        getContext().writeQuoted( month.getName());
        		        getContext().writeComma();
        		        getContext().writeName("abbr");
        		        getContext().writeQuoted( month.getAbbr());
        		        getContext().writeCloseObject();
        		        
        		    }}, Month.class).serialize(Month.values()), 
        		headers(), HttpStatus.OK);
    }
}
