package com.pratilipi.api.impl.mailinglist;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.mailinglist.shared.PostMailingListSubscribeRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.util.MailingListSubscriptionDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/mailinglist/subscribe" )
public class MailingListSubscribeApi extends GenericApi {
	
	@Post
	public GenericResponse get( PostMailingListSubscribeRequest request )
			throws InvalidArgumentException {
		
		MailingListSubscriptionDataUtil.subscribe(
				request.getMailingList(),
				request.getEmail().toString().trim() );
		
		return new GenericResponse();
		
	}
	
}
