package com.pratilipi.email;

import java.io.IOException;
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

import com.pratilipi.email.template.EmailTemplate;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FreeMarkerUtil;

import freemarker.template.TemplateException;

public class EmailUtil {

	private final static Properties properties = new Properties();
	private final static Session session =
			Session.getDefaultInstance( properties, null );
	
	private static final Logger logger =
			Logger.getLogger( EmailUtil.class.getName() );
	private static Map<String, Object> dataModel;
	
	public static void sendMail( EmailTemplate emailTemplate )
					throws MessagingException, IOException, TemplateException {

		//FreeMarker template
		String templateName = emailTemplate.getTemplateName();
		if( emailTemplate.getLanguage() != null )
			templateName = templateName + "." + emailTemplate.getLanguage() + ".ftl";
		else
			templateName = templateName + "." + "en" + ".ftl";
		
		dataModel = null;
		dataModel.put( "Name" , emailTemplate.getRecipientName() );
		
		// The magic
		String body = "";
		try {
			body = FreeMarkerUtil.processTemplate( dataModel, templateName );
		} catch (UnexpectedServerException e) {
			logger.log( Level.SEVERE, "Neither AuthorId, nor PratilipiId is provided !" );
			e.printStackTrace();
		}
		
		// Send E-mail
		Message msg = new MimeMessage( session );
		
		msg.setFrom( new InternetAddress( emailTemplate.getSenderEmail(), emailTemplate.getSenderName() ) );
		msg.addRecipient( Message.RecipientType.TO, new InternetAddress( emailTemplate.getRecipientEmail(), emailTemplate.getRecipientName() ) );
		msg.setReplyTo( new Address[]{ new InternetAddress( emailTemplate.getRecipientEmail(), emailTemplate.getRecipientName() ) } );
		msg.setSubject( emailTemplate.getSubject() );
		msg.setContent( body, "text/html" );

		Transport.send( msg );
	}
	
}
