package com.claymus.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.EmailUtil;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;

@SuppressWarnings("serial")
public class PasswordResetServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( PasswordResetServlet.class.getName() );


	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		Long userId = Long.parseLong( request.getParameter( "userId" ) );

		// New password
		String newPassword = generatePassword();
		
		// Updating password in the DataStore
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );
		user.setPassword( EncryptPassword.getSaltedHash( newPassword ) );
		dataAccessor.createOrUpdateUser( user );
		dataAccessor.destroy();
		
		// Sending email to the user
		try {
			EmailUtil.sendPasswordResetMail( user, newPassword );
		} catch( MessagingException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			logger.log( Level.SEVERE, "Failed to send the email !", e );
		}
	}
	
	private String generatePassword() {
		char[] charRange = new char[ 10 + 26 + 26];
		
		int i = 0;
		for( char ch = '0'; ch <= '9'; ch++ )
			charRange[ i++ ] = ch;
		for( char ch = 'a'; ch <= 'z'; ch++ )
			charRange[ i++ ] = ch;
		for( char ch = 'A'; ch <= 'Z'; ch++ )
			charRange[ i++ ] = ch;
		
		char[] password = new char[ 10 ];
		for( int j = 0; j < 10; j++ )
			password[ j ] = charRange[ (int)( Math.random() * password.length ) ];

		return new String( password );
	}
	
}
