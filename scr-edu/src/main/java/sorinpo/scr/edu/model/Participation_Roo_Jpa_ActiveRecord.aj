// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import sorinpo.scr.edu.model.Participation;

privileged aspect Participation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Participation.entityManager;
    
    public static final EntityManager Participation.entityManager() {
        EntityManager em = new Participation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Participation.countParticipations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Participation o", Long.class).getSingleResult();
    }
    
    public static List<Participation> Participation.findAllParticipations() {
        return entityManager().createQuery("SELECT o FROM Participation o", Participation.class).getResultList();
    }
    
    public static Participation Participation.findParticipation(Long id) {
        if (id == null) return null;
        return entityManager().find(Participation.class, id);
    }
    
    public static List<Participation> Participation.findParticipationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Participation o", Participation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Participation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Participation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Participation attached = Participation.findParticipation(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Participation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Participation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Participation Participation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Participation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}