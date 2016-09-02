package com.pratilipi.api.impl.contact;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.data.util.ConversationDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind(uri = "/contact")
public class ContactApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate(required = true)
		private ContactTeam team;

		@Validate(required = true)
		private String message;

	}

	
	@Post
	public GenericResponse post( PostRequest request ) throws InsufficientAccessException {

		ConversationDataUtil.saveMessage(
				request.team,
				AccessTokenFilter.getAccessToken().getUserId(),
				request.message );
		
		return new GenericResponse();

	}

}
