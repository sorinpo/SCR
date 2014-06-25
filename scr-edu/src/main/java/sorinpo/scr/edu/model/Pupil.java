package sorinpo.scr.edu.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import sorinpo.scr.edu.util.JsonUtils;

@RooJavaBean
@RooJpaActiveRecord(finders = { "findPupilsByOwner" })
public class Pupil {
    private String name;
    
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private ParentState parentState;

    private String leftToCountry;
    
    private String comment;
    
    @Temporal(TemporalType.DATE)
    private Date recruitmentDate;
    
    @Enumerated(EnumType.STRING)
    private RecruimentMethod recruitmentMethod;
    
    //the username that owns the data
    private String owner;
    
    //meta
    private boolean deleteRequested;
    private boolean unlockRequested;
    private boolean locked;
   
    public static enum ParentState {
    	MOTHER("Mama", 1), FATHER("Tatăl", 1), BOTH("Ambii", 2), NONE("-", 0);
    	
    	private int num;
    	private String text;
    	
    	ParentState(String text, int num){
    		this.text = text;
    		this.num = num;
    	}
    	
    	public String getText() {
    		return text;
    	}
    	
    	public int getNum() {
    		return num;
    	}
    }
    
    public static enum RecruimentMethod {
    	PROJECT_TEAM("Echipa de proiect"),
    	PARTNERS("Instituții partenere"),
    	COMMUNITY("Comunitate");
    	
    	private String text;
    	
    	RecruimentMethod(String text) {
    		this.text = text;
    	}
    	
    	public String getText() {
    		return text;
    	}
    }
    
    public static TypedQuery<Pupil> findPupilsByOwnerIn(Collection<String> owners) {
        if (owners == null || owners.size() == 0) throw new IllegalArgumentException("The owners argument is required");
        EntityManager em = Pupil.entityManager();
        TypedQuery<Pupil> q = em.createQuery("SELECT o FROM Pupil AS o WHERE o.owner IN :owners", Pupil.class);
        q.setParameter("owners", owners);
        return q;
    }
    
    public static TypedQuery<Pupil> findPupilsByNameInAndOwner(Collection<String> names, String owner) {
        if (names == null || names.size() == 0) throw new IllegalArgumentException("The names argument is required");
        if (owner == null) throw new IllegalArgumentException("The owner argument is required");
        EntityManager em = Pupil.entityManager();
        TypedQuery<Pupil> q = em.createQuery("SELECT o FROM Pupil AS o WHERE o.name IN :names AND o.owner = :owner", Pupil.class);
        q.setParameter("names", names);
        q.setParameter("owner", owner);
        return q;
    }

	public String toJson() {
        return JsonUtils.newSerializer().serialize(this);
    }

	public static Pupil fromJsonToPupil(String json) {
        return JsonUtils.newDeserializer(Pupil.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Pupil> collection) {
        return JsonUtils.newSerializer().serialize(collection);
    }	
}
