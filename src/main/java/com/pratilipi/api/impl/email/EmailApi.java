package com.pratilipi.api.impl.email;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserAuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/email" )
public class EmailApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long emailId;

	}

	@Post
	public GenericResponse post( PostRequest request )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Email email = dataAccessor.getEmail( request.emailId );


		if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL ) {
			AuthorDataUtil.sendContentPublishedMail( email.getPrimaryContentId(), 
														email.getUserId(), 
														email.getLanguage(), 
														email.getType() );

		} else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL ) {
			UserAuthorDataUtil.sendContentPublishedMail( email.getPrimaryContentId(),
															email.getUserId(),
															email.getLanguage(),
															email.getType() );
		}
		

		email.setState( EmailState.SENT );
		dataAccessor.createOrUpdateEmail( email );

		return new GenericResponse();

	}		

}
