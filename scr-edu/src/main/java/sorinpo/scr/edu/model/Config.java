package sorinpo.scr.edu.model;

import java.util.EnumSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
	private static volatile Config INSTANCE;	
    
	@Id
    @Column(name = "id")
    private Long id;
	
	@Column
	int year;
	
	@ElementCollection
	@Enumerated(EnumType.STRING)
	Set<Month> activeMonths;
	
	public static Config getConfig() {
         
		if (INSTANCE == null) {
			synchronized (Config.class) {
				if (INSTANCE == null) {

					Config config = entityManager().find(Config.class, ID);
					
					if(config == null){
						config = new Config();
						config.persist();
					}
					INSTANCE = config;
				}
			}
		}
         
        return INSTANCE;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Config updateFromJson(String json) {
         Config config = new JSONDeserializer<Config>().use(null, Config.class).deserializeInto(json, getConfig());
         config.id = ID;
         config.merge();
         return config;
    }
	
	@PersistenceContext
    transient EntityManager entityManager;
	
	private static final EntityManager entityManager() {
        EntityManager em = new Info().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
		
	private Config(){
		id = ID;
		activeMonths = EnumSet.noneOf(Month.class);
	}
	    
    @Transactional
    private Config merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Config merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    @Transactional
    private void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
}
