package sorinpo.scr.edu.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;

import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Participation.Activity;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RooJavaBean
public class ParticipationsDTO {

	public ParticipationsDTO(){
	}
	
	public ParticipationsDTO(Long pupilId, int year, List<Participation> ps) {
		this.year = year;
		this.id = pupilId;
		
		for(Participation p: ps){
			
			switch(p.getActivity()){
				case SCHOOL: 
					school = p; break;
				case DISCUSSION:
					discussion = p; break;
				case EXTRA:
					extra = p; break;
				case GROUP:
					group = p; break;
				case INDIVIDUAL:
					individual = p; break;
				case ONLINE:
					online = p; break;
			}
		}
	}

	private int year;
	private Long id;
	
	private Participation school;
	private Participation discussion;
	private Participation extra;
	private Participation group;
	private Participation individual;
	private Participation online;
	
	public Map<Activity, Participation> toMap(){
		Map<Activity, Participation> map = new HashMap<Activity, Participation>();
		
		addParticipation(map, Activity.SCHOOL, school);
		addParticipation(map, Activity.DISCUSSION, discussion);
		addParticipation(map, Activity.EXTRA, extra);
		addParticipation(map, Activity.GROUP, group);
		addParticipation(map, Activity.INDIVIDUAL, individual);
		addParticipation(map, Activity.ONLINE, online);
			
		return map;
	}
	
	private static void addParticipation( Map<Activity, Participation> map, Activity a, Participation p){
		if(p!=null){
			p.setActivity(a);
			map.put(a, p);
		}
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ParticipationsDTO fromJsonToParticipationDTO(String json) {
        return new JSONDeserializer<ParticipationsDTO>().use(null, ParticipationsDTO.class).deserialize(json);
    }
}
