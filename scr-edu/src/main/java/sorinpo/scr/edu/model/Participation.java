package sorinpo.scr.edu.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findParticipationsByPupilId", "findParticipationsByPupilIdAndYear", "findParticipationsByYear" })
public class Participation {

	@Column
	private Long pupilId;
	
    @Column
    private int year;

    @Embedded
    private ActivityData inactivity;
    
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
    @Embedded
    private ParentalCommunicationData parentalCommunicationDetailed;
    
	public Participation(long pupilId, int year) {
		this.pupilId = pupilId;
		this.year = year;
	}

	public void initializeActivityData() {
		
		inactivity = new ActivityData(true);
		
		school = new ActivityData(true);
		freeTime = new ActivityData(true);
		extraSchool = new ActivityData(true);
		groupCounseling = new ActivityData(true);
		individualCounseling = new ActivityData(true);
		parentalCommunication = new ActivityData(true);
		localMeetings = new ActivityData(true);
		parentalCommunicationDetailed = new ParentalCommunicationData(true);
	}
	
	public Iterable<ActivityData> getActivityData() {
		return Arrays.asList(school, freeTime, extraSchool, groupCounseling, individualCounseling, parentalCommunication, localMeetings);
	}
	
	public static TypedQuery<Participation> findParticipationsByPupilIdsAndYear(Collection<Long> pupilIds, int year) {
        if (pupilIds == null || pupilIds.size()==0) throw new IllegalArgumentException("The pupilIds argument is required");
        EntityManager em = Participation.entityManager();
        TypedQuery<Participation> q = em.createQuery("SELECT o FROM Participation AS o WHERE o.pupilId IN :pupilIds AND o.year = :year", Participation.class);
        q.setParameter("pupilIds", pupilIds);
        q.setParameter("year", year);
        return q;
    }
	
	public static Participation getParticipationsByPupilIdAndYear(Long pupilId, int year) {
		try {
			return findParticipationsByPupilIdAndYear(pupilId, year)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
    }
	
}
