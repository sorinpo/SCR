package sorinpo.scr.edu.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findPupilsByOwner" })
public class Pupil {

    private String name;

    @Enumerated(EnumType.STRING)
    private ParentState parentState;

    private String leftToCountry;
    
    private String comment;
    
    //the username that owns the data
    private String owner;
    
    //meta
    private boolean deleteRequested;
    private boolean unlockRequested;
    private boolean locked;
    
    public static enum ParentState {
    	MOTHER, FATHER, BOTH
    }
}
