package com.pratilipi.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.client.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class NewUserQueueServlet extends HttpServlet {

	private static final Logger logger = 
			Logger.getLogger( NewUserQueueServlet.class.getName() );


	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
	
		Long userId = Long.parseLong( request.getParameter( "userId" ) );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );

		User referer = null;
		if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL )
			referer = dataAccessor.getUser( Long.parseLong( user.getReferer() ) );
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance( props, null );

		try {
			Writer writer = new StringWriter();

			String msgSubject = null;
			if( user.getStatus() == UserStatus.PRELAUNCH_SIGNUP )
				msgSubject = "Thank you for subscribing at Pratilipi !";
			else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL )
				msgSubject = 
						"Invitaiton from "
						+ ( referer.getFirstName() == null
								? referer.getEmail()
								: referer.getLastName() == null
										? referer.getFirstName()
										: referer.getFirstName() + " " + referer.getLastName() )
						+ " | Pratilipi";
			
			String emailTemplate = null;
			if( user.getStatus() == UserStatus.PRELAUNCH_SIGNUP )
				emailTemplate = "com/pratilipi/servlet/content/WelcomeEmailContent.ftl";
			else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL )
				emailTemplate = "com/pratilipi/servlet/content/ReferralEmailContent.ftl";
			Map<String, Object> input = new HashMap<>();
			input.put( "user", user );
			input.put( "referer", referer );
			ClaymusMain.FREEMARKER_CONFIGURATION
					.getTemplate( emailTemplate )
					.process( input, writer );
			String msgBody = writer.toString();
			
			Message msg = new MimeMessage( session );
		
			msg.setFrom(
					new InternetAddress(
							"no-reply@pratilipi.com",
							"Ranjeet (Pratilipi)" ) );
		
			msg.addRecipient(
					Message.RecipientType.TO,
					new InternetAddress(
							user.getEmail(),
							user.getFirstName() == null
									? null
									:  user.getLastName() == null
											? user.getFirstName()
											: user.getFirstName() + " " + user.getLastName() ) );
			
//			msg.addRecipient(
//					Message.RecipientType.BCC,
//					new InternetAddress( "contact@pratilipi.com" ) );

			msg.setReplyTo(
					new Address[]{
							new InternetAddress(
									"contact@pratilipi.com",
									"Pratilipi" ) } );

			msg.setSubject( msgSubject );
			msg.setContent( msgBody, "text/html" );
			Transport.send( msg );

		} catch( MessagingException | TemplateException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			logger.log( Level.SEVERE, "Failed to send the email !", e );
        }		
		
	}
	
}
