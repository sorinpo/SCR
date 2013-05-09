package sorinpo.scr.edu.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Info;
import sorinpo.scr.edu.model.MonthlyNumbers;
import sorinpo.scr.edu.model.Participation;


public class Utils {

	public static String replaceCedilles(String s) {

		if(s==null){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			switch(ch){
				case '\u015F': sb.append('\u0219');break;//s
				case '\u015E': sb.append('\u0218');break;//S
				case '\u0163': sb.append('\u021B');break;//t
				case '\u0162': sb.append('\u021A');break;//T
				default: sb.append(ch);
			}
		}
		
		return sb.toString();
	}
	
	public static Info updateInfo(final Info newI, final Info oldI, final ActivityData activeMonths){
		return updatePair(newI, oldI, activeMonths, MonthlyNumbers.class);
	}
	
	public static Participation updateParticipation(final Participation newP, final Participation oldP, final ActivityData activeMonths){
		return updatePair(newP, oldP, activeMonths, ActivityData.class);
	}
	
	
	//overwrites the old with the new if the activeMonth is true for that month
	private static <T> T updatePair(final T newT, final T oldT, final ActivityData activeMonths, final Class<?> innerType ){
		
		ReflectionUtils.doWithFields(ActivityData.class, new FieldCallback() {
			
			@Override
			public void doWith(final Field field) throws IllegalArgumentException,
					IllegalAccessException {
								
				if(field.getType().isAssignableFrom(Boolean.class)){
						
					if((boolean)getField(activeMonths, field.getName())){
						
						ReflectionUtils.doWithFields(newT.getClass(), new FieldCallback() {
	
							@Override
							public void doWith(Field field2)
									throws IllegalArgumentException, IllegalAccessException {
								
								if(field2.getType().isAssignableFrom(innerType)){
	
									Object newO = getField(newT, field2.getName());
									
									if(newO == null){
										return;
									}
									
									Object oldO = getField(oldT, field2.getName());
																		
									Object newVal = getField(newO, field.getName());
									
									if(newVal == null){
										return;
									}
									
									setField(oldO, field.getName(), newVal);
									
								}
								
							}
							
						});
						
						
					}

				}
				
			}
		});
		
		return oldT;
	}
		
	private static Object getField(Object obj, String fieldName){
		try {
			PropertyDescriptor desc = new PropertyDescriptor(fieldName, obj.getClass());
			return desc.getReadMethod().invoke(obj);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private static void setField(Object obj, String fieldName, Object value){
		try {
			PropertyDescriptor desc = new PropertyDescriptor(fieldName, obj.getClass());
			desc.getWriteMethod().invoke(obj, value);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
