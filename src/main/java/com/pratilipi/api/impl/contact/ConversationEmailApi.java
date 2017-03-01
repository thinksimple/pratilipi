package com.pratilipi.api.impl.contact;

import javax.mail.internet.InternetAddress;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.EmailUtil;

@SuppressWarnings("serial")
@Bind( uri= "/contact/email" )
public class ConversationEmailApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {
		
		private String body;
		private String subject;
		private InternetAddress[] receivers;
		private InternetAddress[] cc;

		
		public String getBody() {
			return body;
		}
		
		public String getSubject() {
			return subject;
		}
		
		public InternetAddress[] getReceievers() {
			return receivers;
		}
		
		public InternetAddress[] getCc() {
			return cc;
		}
	}

	
	@Post
	public GenericResponse post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		EmailUtil.sendMail(
				"Team Android", 
				"android@pratilipi.com", 
				request.getReceievers(), 
				request.getCc(), 
				request.getSubject(), 
				request.getBody()
		);
		
		return new GenericResponse();
		
	}

}
