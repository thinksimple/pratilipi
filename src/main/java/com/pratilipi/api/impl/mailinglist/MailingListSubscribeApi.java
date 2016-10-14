package com.pratilipi.api.impl.mailinglist;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.data.util.MailingListSubscriptionDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/mailinglist/subscribe" )
public class MailingListSubscribeApi extends GenericApi {

	public class PostRequest extends GenericRequest {

		@Validate( required = true )
		private MailingList mailingList;

		@Validate( regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
		private String email;

		@Validate( regEx = REGEX_PHONE, regExErrMsg = ERR_PHONE_INVALID )
		private String phone;

		private Language language;

		private String comment;

	}

	@Post
	public GenericResponse post( PostRequest request )
			throws InvalidArgumentException {

		if( request.mailingList != MailingList.LAUNCH_ANNOUNCEMENT_ANDROID_APP && request.email == null ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );
			throw new InvalidArgumentException( errorMessages );
		}
			

		MailingListSubscriptionDataUtil.subscribe(
				request.mailingList,
				AccessTokenFilter.getAccessToken().getUserId(),
				request.email.trim(),
				request.phone,
				request.comment,
				request.language );
		
		return new GenericResponse();
		
	}
	
}
