package org.create.bankingApplication.user_service.utils;

import java.util.Arrays;

public class FieldChecker {
	
	/*
	 Check if an object has empty fields or not 
	 check for nested objects within current object also
	 */
	public static boolean hasEmptyFields(Object object) {
		
		if(object == null) {
			return true;
		}
		
		return Arrays.stream(object.getClass().getDeclaredFields()) //loop over all declared fields from object's class
					.peek(field -> field.setAccessible(true))       //set accessibily of private fields too to access them also via reflection
					.anyMatch(field -> {
						try {
							Object value = field.get(object);       //check value of current field
							
							//check if value is null or not null
							if(value != null) {
								//if not null then check is it enum
								if(field.getType().isEnum()) {
									return false;      //enum values are always constant so it always have value so skip
								}else if(!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
									//check if given fields type is primitive and standard java library type{always starts with .java}
									//if not its definitely nested object
									//then again check its empty fields 
									return hasEmptyFields(value);
								}
							}
							return value == null;
						}catch(IllegalAccessException e) {
							throw new RuntimeException(e);
						}	
					});
	}
}
