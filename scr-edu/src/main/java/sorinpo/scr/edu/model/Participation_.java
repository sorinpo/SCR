/*package sorinpo.scr.edu.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

	//@ManyToOne
	//Pupil pupil;
	
	@Column
	private Long pupilId;
	
    @Enumerated(EnumType.STRING)
    private Activity activity;

    @Column
    private int year;
    
    @ElementCollection
	@Enumerated(EnumType.STRING)
	Set<Month> activeMonths;
    
    public static enum Activity {
    	SCHOOL, EXTRA, GROUP, INDIVIDUAL, ONLINE, DISCUSSION
    }
}
*/