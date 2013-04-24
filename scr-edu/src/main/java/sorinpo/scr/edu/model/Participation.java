package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.Embedded;

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

    @Embedded
    private ActivityData school;
    @Embedded
    private ActivityData freeTime;
    @Embedded
    private ActivityData extraSchool;
    @Embedded
    private ActivityData groupCounseling;
    @Embedded
    private ActivityData individualCounseling;
    @Embedded
    private ActivityData parentalCommunication;
    @Embedded
    private ActivityData localMeetings;
    
	public Participation(long pupilId, int year) {
		this.pupilId = pupilId;
		this.year = year;
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
