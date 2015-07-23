package com.pratilipi.api.shared;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.common.exception.UnexpectedServerException;

public class GenericRequest {

	private static final Logger logger =
			Logger.getLogger( GenericRequest.class.getName() );

	
	protected static final String REGEX_NUMBER = "[0-9]+";
	protected static final String REGEX_PASSWORD = "\\S{6,128}";
	protected static final String REGEX_URI = "(/[A-Za-z0-9-]+)+";
	protected static final String REGEX_EMAIL = "[A-Za-z0-9]+([._+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,4}";

	
	public GenericRequest() {}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final JsonObject validate() throws UnexpectedServerException {
		JsonObject errorMessages = new JsonObject();
		for( Field field : this.getClass().getDeclaredFields() ) {
			Validate validate = field.getAnnotation( Validate.class );
			if( validate != null ) {
				
				field.setAccessible( true );
				try {

					Object value = field.get( this );
					if( value == null || ( field.getType() == String.class && ( (String) value ).trim().isEmpty() ) ) {

						if( validate.required() ) {
							errorMessages.addProperty( field.getName(), validate.requiredErrMsg().isEmpty()
									? "'" + field.getName() + "' is missing."
									: validate.requiredErrMsg() );
						}
					
					} else if( value != null ) {
						
						if( field.getType() == String.class ) {
							if( ! validate.regEx().isEmpty() && ! ( (String) value ).matches( validate.regEx() ) )
								errorMessages.addProperty( field.getName(), validate.regExErrMsg().isEmpty()
										? "'" + field.getName() + "' value is invalid."
										: validate.regExErrMsg() );
	
						} else if( field.getType() == Long.class ) {
							if( (Long) value < validate.minLong() )
								errorMessages.addProperty( field.getName(), "'" + field.getName() + "' must be greater than or equal to " + validate.minLong() );
						
						} else if( field.getType() == List.class && GenericRequest.class.isAssignableFrom( (Class) ( (ParameterizedType) field.getGenericType() ).getActualTypeArguments()[0] ) ) {
							JsonArray errorMessagesArray = new JsonArray();
							for( GenericRequest request : (List<GenericRequest>) value ) {
								JsonObject nestedErrorMessages = request.validate();
								if( nestedErrorMessages.entrySet().size() > 0 )
									errorMessagesArray.add( nestedErrorMessages );
							}
							if( errorMessagesArray.size() > 0 )
								errorMessages.add( field.getName(), errorMessagesArray );
						}
						
					}
					
				} catch( IllegalAccessException e ) {
					logger.log( Level.SEVERE, "Unexpected exception occured !", e );
					throw new UnexpectedServerException();
				}
				
			}
		}
		return errorMessages;
	}

}
