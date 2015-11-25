package com.pratilipi.api.impl.user;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserEmailRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/email" )
public class UserEmailApi extends GenericApi {
	
	@Post
	public static GenericResponse putUserEmail( PostUserEmailRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		JsonObject errorMessages = new JsonObject();

		if( request.sendBirthdayMail() )
			throw new InvalidArgumentException( "Feature not yet supported !" );

		if( request.getUserId() == null && ( request.sendWelcomeMail() || request.sendEmailVerificationMail() || request.sendBirthdayMail() ) )
			errorMessages.addProperty( "userId", GenericRequest.ERR_MISSING_USER_ID );
			
		if( request.sendPasswordResetMail() && ( request.getEmail() == null || request.getEmail().trim().isEmpty() ) )
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );

		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );


		if( request.sendWelcomeMail() )
			UserDataUtil.sendWelcomeMail( request.getUserId() );
		
		if( request.sendEmailVerificationMail() )
			UserDataUtil.sendEmailVerificationMail( request.getUserId() );

		if( request.sendPasswordResetMail() ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			User user = dataAccessor.getUserByEmail( request.getEmail() );
			if( user == null ) {
				errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_NOT_REGISTERED );
				throw new InvalidArgumentException( errorMessages );
			}
			UserDataUtil.sendPasswordResetMail( user.getId() );
		}
		
		return new GenericResponse();
		
	}
	
}
