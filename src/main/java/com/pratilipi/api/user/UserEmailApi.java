package com.pratilipi.api.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PostUserEmailRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/email" )
public class UserEmailApi extends GenericApi {
	
	@Post
	public static GenericResponse putUserEmail( PostUserEmailRequest request )
					throws InvalidArgumentException, UnexpectedServerException {
	
		if( request.sendWelcomeMail() )
			UserDataUtil.sendWelcomeMail( request.getUserId() );
		if( request.sendEmailVerificationMail() )
			UserDataUtil.sendEmailVerificationMail( request.getUserId() );
		else
			throw new InvalidArgumentException( "Feature not yet supported !" );
		
		return new GenericResponse();
		
	}
	
}
