package sorinpo.scr.edu.util;

import java.lang.reflect.Type;
import java.util.Date;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

public class TimestampObjectFactory implements ObjectFactory {

	@Override
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        return new Date( Long.parseLong(value.toString()) );
    }

}
