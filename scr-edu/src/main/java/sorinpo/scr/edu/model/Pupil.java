package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findPupilsByCountyEquals" })
public class Pupil {

    private String name;

    @Enumerated(EnumType.STRING)
    private ParentState parentState;

    private String leftToCountry;
    
    private String comment;
    
    private String county;
    
    @Embedded
    private Participation school;
    
    @Embedded
    private Participation extra;
    
    @Embedded
    private Participation group;
    
    @Embedded
    private Participation individual;
    
    @Embedded
    private Participation online;
    
    @Embedded
    private Participation discussion;
    
    //meta
    private boolean deleteRequested;
    private boolean unlockRequested;
    private boolean locked;
    
    public static enum ParentState {
    	MOTHER, FATHER, BOTH
    }
}
