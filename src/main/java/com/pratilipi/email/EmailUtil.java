package com.pratilipi.email;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
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
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.I18n;


public class EmailUtil {

	private static final Logger logger = Logger.getLogger( EmailUtil.class.getName() );

	private final static Properties properties = new Properties();
	private final static Session session = Session.getDefaultInstance( properties, null );
	private final static String filePath = 
			EmailUtil.class.getName().substring( 0, EmailUtil.class.getName().lastIndexOf(".") ).replace( ".", "/" ) + "/template/";

	private static final Map<String, I18n> i18ns;

	static {
		List<I18n> i18nList = DataAccessorFactory.getDataAccessor().getI18nList( I18nGroup.EMAIL );
		i18ns = new HashMap<>( i18nList.size() );
		for( I18n i18n : i18nList )
			i18ns.put( i18n.getId(), i18n );
	}


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

	public static String getEmailTemplate( EmailType emailType, Language language ) 
			throws UnexpectedServerException {

		return FreeMarkerUtil.processTemplate( i18ns, filePath + emailType.getTemplateName() );
	}

	public static void sendUserEmail( String senderName, String senderEmail,
			String recipientName, String recipientEmail, 
			String subject, String body, Map<String, Object> dataModel ) 
			throws UnexpectedServerException {

		dataModel.put( "emailBody", body );
		dataModel.put( "user_name", recipientName );
		body = FreeMarkerUtil.processTemplate( dataModel, filePath + "MainEmailTemplate.ftl" );

		_sendMail( senderName, senderEmail, recipientName, recipientEmail, subject, body );

	}

	private static void _sendMail(
			String senderName, String senderEmail, 
			String recipientName, String recipientEmail,
			String subject, String body ) throws UnexpectedServerException {
		
		try {
			Message msg = new MimeMessage( session );
			msg.setFrom( new InternetAddress( senderEmail, senderName ) );
			msg.addRecipient( Message.RecipientType.TO, new InternetAddress( recipientEmail, MimeUtility.encodeText( recipientName, "UTF-8", "B" ) ) );
			msg.addRecipient( Message.RecipientType.BCC, new InternetAddress( "mail-archive@pratilipi.com", "Mail Archive" ) );
			msg.setSubject( MimeUtility.encodeText( subject, "UTF-8", "B" ) );
			msg.setContent( body, "text/html" );
			Transport.send( msg );
			logger.log( Level.INFO, "Successfully sent mail to " + recipientEmail + "." );
		} catch ( UnsupportedEncodingException | MessagingException e ) {
			logger.log( Level.SEVERE, "Failed to send mail to " + recipientEmail + ".", e );
			throw new UnexpectedServerException();
		}
		
	}

}
