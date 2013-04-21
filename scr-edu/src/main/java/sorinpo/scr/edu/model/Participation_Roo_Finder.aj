// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sorinpo.scr.edu.model.Participation;

privileged aspect Participation_Roo_Finder {
    
    public static TypedQuery<Participation> Participation.findParticipationsByPupilIdAndYear(Long pupilId, int year) {
        if (pupilId == null) throw new IllegalArgumentException("The pupilId argument is required");
        EntityManager em = Participation.entityManager();
        TypedQuery<Participation> q = em.createQuery("SELECT o FROM Participation AS o WHERE o.pupilId = :pupilId AND o.year = :year", Participation.class);
        q.setParameter("pupilId", pupilId);
        q.setParameter("year", year);
        return q;
    }
    
}
