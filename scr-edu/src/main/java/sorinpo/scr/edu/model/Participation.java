package sorinpo.scr.edu.model;

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
@RooJpaActiveRecord(finders = { "findParticipationsByPupilIdAndYear", "findParticipationsByYear" })
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
