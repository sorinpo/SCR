package sorinpo.scr.edu.util;

import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.BasicDateTransformer;

public class JsonUtils {

	public static <T> JSONDeserializer<T> newDeserializer(Class<T> root) {
		return new JSONDeserializer<T>()				
				.use(Date.class, new TimestampObjectFactory())
				.use(null, root);
	}
	
	public static JSONSerializer newSerializer() {
		return new JSONSerializer()
        	.transform(new BasicDateTransformer(), Date.class)
			.exclude("*.class");
	}
	
}
