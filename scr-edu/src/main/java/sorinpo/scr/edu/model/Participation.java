package sorinpo.scr.edu.model;

import javax.persistence.Column;

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

    private ActivityData school;
    private ActivityData freeTime;
    private ActivityData extraSchool;
    private ActivityData groupCounseling;
    private ActivityData individualCounseling;
    private ActivityData parentalCommunication;
    private ActivityData localMeetings;
    
	public Participation(long pupilId, int year) {
		// TODO Auto-generated constructor stub
	}

	public void initializeActivityData() {
		school = new ActivityData();
		freeTime = new ActivityData();
		extraSchool = new ActivityData();
		groupCounseling = new ActivityData();
		individualCounseling = new ActivityData();
		parentalCommunication = new ActivityData();
		localMeetings = new ActivityData();
	}
}
