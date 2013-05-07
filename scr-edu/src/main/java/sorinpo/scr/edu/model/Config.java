package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RooJavaBean
@RooJpaEntity
public class Config {

	private static final long ID = 1;
	
	@Column
	int activeYear;
	
	@Embedded
	ActivityData activeMonths;
	
	public static Config getConfig() {

		return entityManager().find(Config.class, ID);
		
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Config updateFromJson(String json) {
    	
    	Config config = getConfig();
    	
        new JSONDeserializer<Config>().use(null, Config.class).deserializeInto(json, config);
        config.getActiveMonths().nullToFalse();
         
        config = config.merge();
        return config;
    }
	
	@PersistenceContext
    transient EntityManager entityManager;
	
	private static final EntityManager entityManager() {
        EntityManager em = new Info().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	    
    @Transactional
    private Config merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Config merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
