// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sorinpo.scr.edu.model.Participation;

privileged aspect Participation_Roo_Json {
    
    public String Participation.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Participation Participation.fromJsonToParticipation(String json) {
        return new JSONDeserializer<Participation>().use(null, Participation.class).deserialize(json);
    }
    
    public static String Participation.toJsonArray(Collection<Participation> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Participation> Participation.fromJsonArrayToParticipations(String json) {
        return new JSONDeserializer<List<Participation>>().use(null, ArrayList.class).use("values", Participation.class).deserialize(json);
    }
    
}