package com.pratilipi.data.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.type.Website;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.PasswordUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.User;
import com.pratilipi.email.EmailUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;

public class UserDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserDataUtil.class.getName() );

	private static final Long USER_ID_SYSTEM = 5636632866717696L;

	
	public static UserSignUpSource getUserSignUpSource( boolean isFbLogin, boolean isGpLogin ) {

		if( UxModeFilter.isAndroidApp() ) {

			if( isFbLogin )
				return UserSignUpSource.ANDROID_APP_FACEBOOK;
			else if( isGpLogin )
				return UserSignUpSource.ANDROID_APP_GOOGLE;
			else
				return UserSignUpSource.ANDROID_APP;
		
		} else {
			
			if( isFbLogin ) {
				
				switch( UxModeFilter.getFilterLanguage() ) {
					case TAMIL:
						return UserSignUpSource.WEBSITE_M6_TAMIL_FACEBOOK;
					default:
						return UserSignUpSource.WEBSITE_M6_FACEBOOK;
				}
				
			} else {

				switch( UxModeFilter.getFilterLanguage() ) {
					case TAMIL:
						return UserSignUpSource.WEBSITE_M6_TAMIL;
					default:
						return UserSignUpSource.WEBSITE_M6;
				}
				
			}
			
		}
		
	}
	
	private static String getNextToken( String verificationToken ) {
		if( verificationToken != null ) {
			Long expiryDateMillis = Long.parseLong( verificationToken.substring( verificationToken.indexOf( "|" ) + 1 ) );
			if( ( expiryDateMillis - new Date().getTime() ) > TimeUnit.MILLISECONDS.convert( 4, TimeUnit.DAYS ) )
				return verificationToken;
		}
		
		return UUID.randomUUID().toString() + "|" + ( new Date().getTime() + TimeUnit.MILLISECONDS.convert( 7, TimeUnit.DAYS ) ); // Valid for 7 days.
	}
	
	private static boolean verifyToken( User user, String verificationToken ) {
		if( user.getVerificationToken() == null )
			return false;
		
		String userVerificationToken = user.getVerificationToken().substring( 0, user.getVerificationToken().indexOf( "|" ) );
		Long expiryDateMillis = Long.parseLong( user.getVerificationToken().substring( user.getVerificationToken().indexOf( "|" ) + 1 ) );
		return userVerificationToken.equals( verificationToken ) && expiryDateMillis > new Date().getTime();
	}

	
	public static UserData createUserData( User user ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthorByUserId( user.getId() );
		Page authorPage = dataAccessor.getPage( PageType.AUTHOR, author.getId() );
		
		UserData userData = new UserData( user.getId() );
		userData.setFacebookId( user.getFacebookId() );
		userData.setEmail( user.getEmail() );
		userData.setState( user.getState() );
		userData.setSignUpDate( user.getSignUpDate() );
		
		userData.setFirstName( author.getFirstName() != null ? author.getFirstName() : author.getFirstNameEn() );
		userData.setLastName( author.getLastName() != null ? author.getLastName() : author.getLastNameEn() );
		userData.setDisplayName( userData.getFirstName() != null ? userData.getFirstName() : userData.getLastName() );
		userData.setGender( author.getGender() );
		userData.setDateOfBirth( author.getDateOfBirth() );
		userData.setProfilePageUrl( authorPage.getUriAlias() == null ? authorPage.getUri() : authorPage.getUriAlias() );
		userData.setProfileImageUrl( AuthorDataUtil.createAuthorImageUrl( author ) );
		
		return userData;
		
	}

	
	public static UserData getGuestUser() {
		UserData userData = new UserData( 0L );
		userData.setFirstName( "Guest" );
		userData.setLastName( "User" );
		userData.setState( UserState.GUEST );
		return userData;
	}

	public static UserData getCurrentUser() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		Long userId = accessToken.getUserId();
		if( userId.equals( 0L ) )
			return getGuestUser();
		else
			return createUserData( DataAccessorFactory.getDataAccessor().getUser( accessToken.getUserId() ) );
	}

	
	public static UserData registerUser( String firstName, String lastName,
			String email, String password, UserSignUpSource signUpSource )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email.toLowerCase() );
		
		if( user == null || user.getState() == UserState.DELETED ) {
			user = dataAccessor.newUser();
		} else if( user.getState() != UserState.REFERRAL ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REGISTERED_ALREADY );
			throw new InvalidArgumentException( errorMessages );
		}

		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( AccessType.USER_ADD );
		auditLog.setEventDataOld( gson.toJson( user ) );
		
		
		user.setPassword( PasswordUtil.getSaltedHash( password ) );
		user.setEmail( email.toLowerCase() );
		user.setState( UserState.REGISTERED );
		user.setSignUpDate( new Date() );
		user.setSignUpSource( signUpSource );

		
		auditLog.setEventDataNew( gson.toJson( user ) );

		
		user = dataAccessor.createOrUpdateUser( user, auditLog );
		
		return createUserData( user );

	}

	public static UserData loginUser( String email, String password )
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email.toLowerCase() );

		if( user == null || user.getState() == UserState.REFERRAL || user.getState() == UserState.DELETED ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_NOT_REGISTERED );
			throw new InvalidArgumentException( errorMessages );
		} else if( user.getState() == UserState.BLOCKED ) {
			throw new InsufficientAccessException( GenericRequest.ERR_ACCOUNT_BLOCKED );
		}
		
		if( user.getPassword() == null && user.getFacebookId() != null )
			throw new InvalidArgumentException( GenericRequest.ERR_EMAIL_REGISTERED_WITH_FB );
		
		if( PasswordUtil.check( password, user.getPassword() ) ) {
			_loginUser( AccessTokenFilter.getAccessToken(), user );
			return createUserData( user );
		}
		
		if( verifyToken( user, password ) ) {
			user.setVerificationToken( null );
			dataAccessor.createOrUpdateUser( user );
			_loginUser( AccessTokenFilter.getAccessToken(), user );
			return createUserData( user );
		}
		
		throw new InvalidArgumentException( GenericRequest.ERR_INVALID_CREDENTIALS );

	}

	public static UserData loginUser( String fbUserAccessToken, UserSignUpSource signUpSource )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		UserData fbUserData = FacebookApi.getUserData( fbUserAccessToken );

		boolean isNew = false;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByFacebookId( fbUserData.getFacebookId() );

		// * Users having Facebook Id can never be in GUEST or REFERRAL state.
		// * No action required for REGISTERED or ACTIVE users.
		
		if( user == null || user.getState() == UserState.DELETED ) {
			
			if( fbUserData.getEmail() != null )
				user = dataAccessor.getUserByEmail( fbUserData.getEmail() );
			
			Gson gson = new Gson();
			
			AuditLog auditLog = dataAccessor.newAuditLog();
			auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );

			if( user == null || user.getState() == UserState.DELETED ) {
				
				user = dataAccessor.newUser();
				
				auditLog.setAccessType( AccessType.USER_ADD );
				auditLog.setEventDataOld( gson.toJson( user ) );
				
				user.setEmail( fbUserData.getEmail() );
				user.setState( UserState.ACTIVE ); // Counting on Facebook for e-mail/user verification
				user.setSignUpDate( new Date() );
				user.setSignUpSource( signUpSource );
				
				isNew = true;
				
			} else if( user.getState() == UserState.REFERRAL ) {
				
				auditLog.setAccessType( AccessType.USER_ADD );
				auditLog.setEventDataOld( gson.toJson( user ) );
				
				user.setState( UserState.ACTIVE ); // Counting on Facebook for e-mail/user verification
				user.setSignUpDate( new Date() );
				user.setSignUpSource( signUpSource );
				
				isNew = true;

			} else if( user.getState() == UserState.REGISTERED ) {

				auditLog.setAccessType( AccessType.USER_UPDATE );
				auditLog.setEventDataOld( gson.toJson( user ) );

				user.setState( UserState.ACTIVE ); // Counting on Facebook for e-mail/user verification

			} else { // user.getState() == UserState.ACTIVE || user.getState() == UserState.BLOCKED
				
				auditLog.setAccessType( AccessType.USER_UPDATE );
				auditLog.setEventDataOld( gson.toJson( user ) );

			}
			
			user.setFacebookId( fbUserData.getFacebookId() );
			
			auditLog.setEventDataNew( gson.toJson( user ) );

			user = dataAccessor.createOrUpdateUser( user, auditLog );
		
		}
		
		
		if( user.getState() == UserState.BLOCKED )
			throw new InsufficientAccessException( GenericRequest.ERR_ACCOUNT_BLOCKED );

		
		_loginUser( AccessTokenFilter.getAccessToken(), user );
		
		
		if( isNew ) {
			fbUserData.setId( user.getId() );
			return fbUserData;
		} else {
			return createUserData( user );
		}
		
	}
	
	private static void _loginUser( AccessToken accessToken, User user ) {
		
		if( ! accessToken.getUserId().equals( 0L ) )
			return;
		
		accessToken.setUserId( user.getId() );
		accessToken.setLogInDate( new Date() );
		accessToken = DataAccessorFactory.getDataAccessor().createOrUpdateAccessToken( accessToken );

		/*
		while( true ) { 
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( ! accessToken.getUserId().equals( 0L ) )
					return createUserData( user );
				accessToken.setUserId( user.getId() );
				accessToken.setLogInDate( new Date() );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				dataAccessor.commitTx();
				return createUserData( user );
			} catch( JDODataStoreException ex ) {
				logger.log( Level.INFO, "Transaction failed. Retrying in 100 ms...", ex );
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			try {
				Thread.sleep( 1000 );
			} catch( InterruptedException e ) { } // Do nothing
		}
		*/
		
	}

	public static UserData logoutUser() {
		return logoutUser( AccessTokenFilter.getAccessToken() );
	}

	public static UserData logoutUser( AccessToken accessToken ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( accessToken.getUserId().equals( 0L ) )
			return getGuestUser();

		accessToken.setLogOutDate( new Date() );
		accessToken.setExpiry( new Date() );
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return getGuestUser();

		/*
		while( true ) {
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( accessToken.getUserId().equals( 0L ) )
					return getGuestUser();
				accessToken.setLogOutDate( new Date() );
				accessToken.setExpiry( new Date() );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				dataAccessor.commitTx();
				return getGuestUser();
			} catch( JDODataStoreException ex ) {
				logger.log( Level.INFO, "Transaction failed. Retrying in 100 ms...", ex );
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			try {
				Thread.sleep( 1000 );
			} catch( InterruptedException e ) { } // Do nothing
		}
 		*/
	}
	
	
	public static void createAuthorProfile( UserData userData, Language language ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Author author = dataAccessor.getAuthorByUserId( userData.getId() );
		if( author != null && author.getState() != AuthorState.DELETED )
			return;
		else
			author = dataAccessor.newAuthor();
		
		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( AccessType.AUTHOR_ADD );
		auditLog.setEventDataOld( gson.toJson( author ) );
			
		author.setUserId( userData.getId() );
		author.setFirstName( userData.getFirstName() );
		author.setLastName( userData.getLastName() );
		author.setGender( userData.getGender() );
		author.setDateOfBirth( userData.getDateOfBirth() );
		author.setEmail( userData.getEmail() ); // For backward compatibility with Mark-4
		author.setLanguage( language );
		author.setState( AuthorState.ACTIVE );
		author.setRegistrationDate( userData.getSignUpDate() );
		author.setLastUpdated( new Date() );
		
		auditLog.setEventDataNew( gson.toJson( author ) );

		author = dataAccessor.createOrUpdateAuthor( author, auditLog );
		
	}

	
	public static void sendWelcomeMail( Long userId )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "welcome", Language.ENGLISH );
		
	}
	
	public static void sendEmailVerificationMail( Long userId )
			throws InvalidArgumentException, UnexpectedServerException {
		
		_sendEmailVerificationMail( DataAccessorFactory.getDataAccessor().getUser( userId ) );
		
	}

	public static void sendEmailVerificationMail( String emailId )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		if( userId.equals( 0L ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( emailId.trim().toLowerCase() );
		if( user == null || ! user.getId().equals( userId ) )
			throw new InvalidArgumentException( GenericRequest.ERR_EMAIL_NOT_REGISTERED );
		
		_sendEmailVerificationMail( user );
		
	}

	private static void _sendEmailVerificationMail( User user )
			throws InvalidArgumentException, UnexpectedServerException {
		
		String verificationToken = getNextToken( user.getVerificationToken() );
		if( ! verificationToken.equals( user.getVerificationToken() ) ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			user.setVerificationToken( verificationToken );
			user = dataAccessor.createOrUpdateUser( user );
		}
		
		Map<String, String> dataModel = new HashMap<>();
		String verificationLink = "http://" + Website.ALL_LANGUAGE.getHostName()
				+ "/" + "?" + "email=" + user.getEmail()
				+ "&" + "token=" + verificationToken.substring( 0, verificationToken.indexOf( "|" ) )
				+ "&" + "verifyUser=" + Boolean.TRUE;
		dataModel.put( "emailVerificationUrl", verificationLink );
		
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "verification", Language.ENGLISH, dataModel );
		
	}
	
	public static void sendPasswordResetMail( String email )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email );
		String verificationToken = getNextToken( user.getVerificationToken() );
		if( ! verificationToken.equals( user.getVerificationToken() ) ) {
			user.setVerificationToken( verificationToken );
			user = dataAccessor.createOrUpdateUser( user );
		}
		
		Map<String, String> dataModel = new HashMap<>();
		String passwordResetUrl = "http://" + Website.ALL_LANGUAGE.getHostName()
				+ "/" + "?" + "email=" + user.getEmail()
				+ "&" + "token=" + verificationToken.substring( 0, verificationToken.indexOf( "|" ) )
				+ "&" + "passwordReset=" + Boolean.TRUE;
		dataModel.put( "passwordResetUrl", passwordResetUrl );
		
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "password-reset", Language.ENGLISH, dataModel );
		
	}

	
	public static void verifyUserEmail( String email, String verificationToken )
			throws InvalidArgumentException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email );
		
		if( user == null || user.getState() != UserState.REGISTERED )
			return;
		
		if( ! verifyToken( user, verificationToken ) )
			throw new InvalidArgumentException( GenericRequest.ERR_VERIFICATION_TOKEN_INVALID_OR_EXPIRED );
		
		user.setState( UserState.ACTIVE );
		dataAccessor.createOrUpdateUser( user );
		
	}
	
	public static void updateUserPassword( String password, String newPassword )
			throws InvalidArgumentException, InsufficientAccessException {
		
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		if( userId.equals( 0L ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );
		if( user.getState() != UserState.REGISTERED && user.getState() != UserState.ACTIVE  )
			throw new InsufficientAccessException();
		
		if( PasswordUtil.check( password, user.getPassword() ) ) {
			if( ! password.equals( newPassword ) ) {
				user.setPassword( PasswordUtil.getSaltedHash( newPassword ) );
				user = dataAccessor.createOrUpdateUser( user );
			}
		} else {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "password", GenericRequest.ERR_PASSWORD_INCORRECT );
			throw new InvalidArgumentException( errorMessages );
		}
		
	}
	
	public static void updateUserPassword( String email, String verificationToken, String newPassword )
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email.trim().toLowerCase() );
		
		JsonObject errorMessages = new JsonObject();
		if( user == null || user.getState() == UserState.REFERRAL || user.getState() == UserState.DELETED ) {
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_NOT_REGISTERED );
			throw new InvalidArgumentException( errorMessages );
		} else if( user.getState() == UserState.BLOCKED ) {
			throw new InvalidArgumentException( GenericRequest.ERR_ACCOUNT_BLOCKED );
		}
		
		if( ! verifyToken( user, verificationToken ) )
			throw new InvalidArgumentException( GenericRequest.ERR_VERIFICATION_TOKEN_INVALID_OR_EXPIRED );
			
		user.setPassword( PasswordUtil.getSaltedHash( newPassword ) );
		dataAccessor.createOrUpdateUser( user );

	}
	
}