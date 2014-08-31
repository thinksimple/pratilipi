package com.claymus.commons.server;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.claymus.data.transfer.User;

public class EmailUtil {

	private final static Properties properties = new Properties();
	private final static Session session = Session.getDefaultInstance( properties, null );


	public static void sendPasswordResetMail( User user, String newPassword )
			throws UnsupportedEncodingException, MessagingException {

		Message msg = new MimeMessage( session );
	
		msg.setFrom( new InternetAddress( "no-reply@pratilipi.com", "Pratilipi" ) );
		msg.addRecipient( Message.RecipientType.TO, createInternetAddress( user ) );
		msg.setReplyTo( new Address[]{ new InternetAddress( "contact@pratilipi.com", "Pratilipi" ) } );

		msg.setSubject( "Your Pratilipi password" );
		msg.setContent( newPassword, "text/html" );

		Transport.send( msg );
	}
	
	private static InternetAddress createInternetAddress( User user )
			throws AddressException, UnsupportedEncodingException {
		
		String name = null;
		if( user.getFirstName() != null && user.getLastName() != null )
			name = user.getFirstName() + " " + user.getLastName();
		else if( user.getFirstName() != null )
			name = user.getFirstName();
		else if( user.getNickName() != null )
			name = user.getNickName();
		
		return name == null ?
				new InternetAddress( user.getEmail() ) :
				new InternetAddress( user.getEmail(), name );
	}
	
}
