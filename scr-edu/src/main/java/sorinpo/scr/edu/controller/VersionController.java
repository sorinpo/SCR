package sorinpo.scr.edu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VersionController {

    private String version;

    @ResponseBody
    @RequestMapping(value = "/version", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getVersion() {
        return version;
    }

    @Value("${project.version}")
    public void setVersion(String version) {
        this.version = version;
    }
}
