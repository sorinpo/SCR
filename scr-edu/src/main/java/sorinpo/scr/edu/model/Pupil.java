package sorinpo.scr.edu.model;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
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
    	MOTHER, FATHER, BOTH, NONE
    }
    
    public static TypedQuery<Pupil> findPupilsByOwnerIn(Collection<String> owners) {
        if (owners == null || owners.size() == 0) throw new IllegalArgumentException("The owners argument is required");
        EntityManager em = Pupil.entityManager();
        TypedQuery<Pupil> q = em.createQuery("SELECT o FROM Pupil AS o WHERE o.owner IN :owners", Pupil.class);
        q.setParameter("owners", owners);
        return q;
    }
}
