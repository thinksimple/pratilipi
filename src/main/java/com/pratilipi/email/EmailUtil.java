package com.pratilipi.email;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.FreeMarkerUtil;


public class EmailUtil {
	
	private static final Logger logger = Logger.getLogger( EmailUtil.class.getName() );

	private final static Properties properties = new Properties();
	private final static Session session = Session.getDefaultInstance( properties, null );
	private final static String filePath = 
			EmailUtil.class.getName().substring( 0, EmailUtil.class.getName().lastIndexOf(".") ).replace( ".", "/" ) + "/template/";


	@Deprecated
	public static void sendMail( String name, String email, String templateName,
			Language language ) throws UnexpectedServerException {
		
		sendMail( name, email, templateName, language, new HashMap<String, String>() );
	}
	
	@Deprecated
	public static void sendMail( String name, String email, String templateName,
			Language language, Map<String, String> dataModel ) throws UnexpectedServerException {
		
		dataModel.put( "user_display_name" , name );
		
		String senderName = null;
		Pattern senderNamePattern = Pattern.compile( "<#-- SENDER_NAME:(.+?)-->" );
		String senderEmail = null;
		Pattern senderEmailPattern = Pattern.compile( "<#-- SENDER_EMAIL:(.+?)-->" );
		String subject = null;
		Pattern subjectPattern = Pattern.compile( "<#-- SUBJECT:(.+?)-->" );

		String body = FreeMarkerUtil.processTemplate( dataModel, filePath + templateName + "." + language.getCode() + ".ftl" );

		try {
			File file = new File( EmailUtil.class.getResource( "template/" + templateName + "." + language.getCode() + ".ftl" ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			Matcher m = null;
			String line = null;
			while( it.hasNext() ) {
				line = it.nextLine().trim();
				
				if( line.isEmpty() )
					continue;
				else if( senderName == null && (m = senderNamePattern.matcher( line )).find() )
					senderName = m.group(1).trim();
				else if( senderEmail == null && (m = senderEmailPattern.matcher( line )).find() )
					senderEmail = m.group(1).trim();
				else if( subject == null && (m = subjectPattern.matcher( line )).find() )
					subject = m.group(1).trim();
				else if( senderName != null && senderEmail != null && subject != null )
					break;
			}

			_sendMail( senderName, senderEmail, name, email, subject, body );

		} catch ( IOException | URISyntaxException e1 ) {
			logger.log( Level.SEVERE, "Failed to process \"" + templateName + "." + language.getCode() + "\" email template.", e1 );
			throw new UnexpectedServerException();
		}
		
	}
	
	public static void sendMail(
			String recipientName, String recipientEmail, EmailType emailType, Map<String, String> dataModel )
			throws UnexpectedServerException {

		String body = FreeMarkerUtil.processTemplate( dataModel, filePath + emailType.getTemplateName() );

		Pattern senderNamePattern = Pattern.compile( "<!-- SENDER_NAME:(.+?) -->" );
		Pattern senderEmailPattern = Pattern.compile( "<!-- SENDER_EMAIL:(.+?) -->" );
		Pattern subjectPattern = Pattern.compile( "<!-- SUBJECT:(.+?) -->" );

		String senderName = null;
		String senderEmail = null;
		String subject = null;

		Matcher m = null;
		if( ( m = senderNamePattern.matcher( body ) ).find() )
			senderName = m.group( 1 ).trim();
		if( ( m = senderEmailPattern.matcher( body ) ).find() )
			senderEmail = m.group( 1 ).trim();
		if( ( m = subjectPattern.matcher( body ) ).find() )
			subject = m.group( 1 ).trim();

		_sendMail( senderName, senderEmail, recipientName, recipientEmail, subject, body );

	}

	private static void _sendMail(
			String senderName, String senderEmail, 
			String recipientName, String recipientEmail,
			String subject, String body ) throws UnexpectedServerException {
		
		try {
			MimeMessage msg = new MimeMessage( session );
			msg.setFrom( new InternetAddress( senderEmail, senderName ) );
			msg.addRecipient( Message.RecipientType.TO, new InternetAddress( recipientEmail, recipientName ) );
			msg.addRecipient( Message.RecipientType.BCC, new InternetAddress( "mail-archive@pratilipi.com", "Mail Archive" ) );
			msg.setSubject( subject, "UTF-8" );
			msg.setContent( body, "text/html" );
			Transport.send( msg );
			logger.log( Level.INFO, "Successfully sent mail to " + recipientEmail + "." );
		} catch ( UnsupportedEncodingException | MessagingException e ) {
			logger.log( Level.SEVERE, "Failed to send mail to " + recipientEmail + ".", e );
			throw new UnexpectedServerException();
		}
		
	}

}
