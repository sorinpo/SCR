package sorinpo.scr.edu.model;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findCentruInfoesByUserId" })
public class CentruInfo {
	
	public CentruInfo(Long userId){
		this.userId = userId;
	}
	
	Long userId;
	
	String locality;
	String address;
	
	@Column(length = 1024)
	String team;

	public static void fromJsonToCentruInfo(String json, CentruInfo existing) {
        new JSONDeserializer<CentruInfo>().use(null, CentruInfo.class).deserializeInto(json, existing);
    }
	
}
