package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findParticipationsByPupilIdAndYear" })
public class Participation {

	@Column
	private Long pupilId;
	
    @Column
    private int year;
    
    @Enumerated(EnumType.STRING)
    private Activity activity;
    
    private boolean jan;
    private boolean feb;
    private boolean mar;
    private boolean apr;
    private boolean may;
    private boolean jun;
    private boolean jul;
    private boolean aug;
    private boolean sep;
    private boolean oct;
    private boolean nov;
    @Column(name="dcm")
    private boolean dec;
    
    public static enum Activity {
    	SCHOOL, EXTRA, GROUP, INDIVIDUAL, ONLINE, DISCUSSION
    }
}
