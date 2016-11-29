package com.pratilipi.data.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.FirebaseApi;
import com.pratilipi.common.util.GoogleApi;
import com.pratilipi.common.util.PasswordUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.User;
import com.pratilipi.email.EmailUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;

public class UserDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserDataUtil.class.getName() );

	
	public static boolean hasAccessToAddUserData( UserData userData ) {
		return UserAccessUtil.hasUserAccess(
				AccessTokenFilter.getAccessToken().getUserId(),
				null,
				AccessType.USER_ADD );
	}

	public static boolean hasAccessToUpdateUserData( User user, UserData userData ) {

		// Case 1: Only REFERRAL/REGISTERED/ACTIVE User Profiles can be updated.
		if( user.getState() != UserState.REFERRAL
				&& user.getState() != UserState.REGISTERED
				&& user.getState() != UserState.ACTIVE )
			return false;

		// Case 2: User with USER_UPDATE access can update any User profile.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, AccessType.USER_UPDATE ) )
			return true;
		
		// Case 3: User can update his/her own User profile.
		if( accessToken.getUserId().equals( user.getId() ) )
			return true;
		
		return false;
		
	}
	
	
	public static UserSignUpSource getUserSignUpSource( boolean isFbLogin, boolean isGpLogin ) {

		if( UxModeFilter.isAndroidApp() ) {

			if( isFbLogin )
				return UserSignUpSource.ANDROID_APP_FACEBOOK;
			else if( isGpLogin )
				return UserSignUpSource.ANDROID_APP_GOOGLE;
			else
				return UserSignUpSource.ANDROID_APP;
		
		} else {
			
			if( isFbLogin )
				return UserSignUpSource.WEBSITE_M6_FACEBOOK;
			else if( isGpLogin )
				return UserSignUpSource.WEBSITE_M6_GOOGLE;
			else
				return UserSignUpSource.WEBSITE_M6;
			
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
		
		if( user == null )
			return null;
		
		return createUserData(
				user,
				DataAccessorFactory.getDataAccessor().getAuthorByUserId( user.getId() ) );
		
	}
	
	public static UserData createUserData( User user, Author author ) {
		
		if( user == null )
			return null;
		
		UserData userData = new UserData( user.getId() );
		userData.setFacebookId( user.getFacebookId() );
		userData.setEmail( user.getEmail() );
		userData.setPhone( user.getPhone() );
		userData.setState( user.getState() );
		userData.setSignUpDate( user.getSignUpDate() );
		userData.setFollowCount( user.getFollowCount() );
		
		if( author != null ) {
			
			AuthorData authorData = AuthorDataUtil.createAuthorData( author );
			userData.setAuthor( authorData );
			
			userData.setFirstName( authorData.getFirstName() != null ? authorData.getFirstName() : authorData.getFirstNameEn() );
			userData.setLastName( authorData.getLastName() != null ? authorData.getLastName() : authorData.getLastNameEn() );
			userData.setDisplayName( userData.getFirstName() != null ? userData.getFirstName() : userData.getLastName() );
			userData.setGender( authorData.getGender() );
			userData.setDateOfBirth( authorData.getDateOfBirth() );
			userData.setProfilePageUrl( authorData.getPageUrl() );
			userData.setProfileImageUrl( authorData.getImageUrl() );
			userData.setFirebaseToken( FirebaseApi.getCustomTokenForUser( userData.getId() ) );
		}
		
		return userData;
		
	}
	
	public static Map<Long, UserData> createUserDataList( List<Long> userIdList, boolean includeAuthorData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<User> userList = dataAccessor.getUserList( userIdList );
		
		Map<Long, UserData> userDataList = new HashMap<>( userIdList.size() );
		if( includeAuthorData ) {
			List<Author> authorList = dataAccessor.getAuthorListByUserIdList( userIdList );
			for( int i = 0; i < userIdList.size(); i++ )
				userDataList.put( userIdList.get( i ), createUserData( userList.get( i ), authorList.get( i ) ) );
		} else {
			for( User user : userList )
				userDataList.put( user.getId(), createUserData( user, null ) );
		}
		
		return userDataList;
		
	}

	
	public static UserData getGuestUser() {
		UserData userData = new UserData( 0L );
		userData.setAuthor( new AuthorData() );
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

	
	public static UserData saveUserData( UserData userData )
			throws InvalidArgumentException, InsufficientAccessException {
		
		_validateUserDataForSave( userData );
		
		boolean isNew = userData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = isNew ? dataAccessor.newUser() : dataAccessor.getUser( userData.getId() );

		if ( isNew && ! hasAccessToAddUserData( userData ) )
			throw new InsufficientAccessException();
		if( ! isNew && ! hasAccessToUpdateUserData( user, userData ) )
			throw new InsufficientAccessException();

		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLog(
				AccessTokenFilter.getAccessToken(),
				isNew ? AccessType.USER_ADD : AccessType.USER_UPDATE,
				user
				);

		
		if( userData.hasEmail() && ! userData.getEmail().equals( user.getEmail() ) ) {
			user.setEmail( userData.getEmail() );
			if( user.getState() == UserState.ACTIVE )
				user.setState( UserState.REGISTERED );
			user.setVerificationToken( null );
		}
		if( userData.hasPhone() )
			user.setPhone( userData.getPhone() );

		if( isNew ) { // Assuming only AEEs have USER_ADD access.
			user.setState( UserState.REFERRAL );
			user.setCampaign( UserCampaign.AEE_TEAM );
			user.setReferrer( AccessTokenFilter.getAccessToken().getUserId().toString() );
			user.setSignUpDate( new Date() );
			user.setSignUpSource( getUserSignUpSource( false, false ) );
		}
		
		user.setLastUpdated( new Date() );

		
		auditLog.setEventDataNew( gson.toJson( user ) );
		
		
		user = dataAccessor.createOrUpdateUser( user, auditLog );
		
		return createUserData( user );
		
	}
	
	private static void _validateUserDataForSave( UserData userData )
			throws InvalidArgumentException {
		
		boolean isNew = userData.getId() == null;
		
		JsonObject errorMessages = new JsonObject();

		// New user profile must have email.
		if( isNew && ( ! userData.hasEmail() || userData.getEmail() == null ) )
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );
		// Email can not be un-set or set to null.
		else if( ! isNew && userData.hasEmail() && userData.getEmail() == null )
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );

		// For new user, user email should be not registered already.
		if( isNew && DataAccessorFactory.getDataAccessor().getUserByEmail( userData.getEmail() ) != null )
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REGISTERED_ALREADY );
		// Email, if provided, must not be registered with some other user.
		else if( ! isNew && userData.hasEmail() && userData.getEmail() != null ) {
			User user = DataAccessorFactory.getDataAccessor().getUserByEmail( userData.getEmail() );
			if( user != null && ! user.getId().equals( userData.getId() ) )
				errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REGISTERED_ALREADY );
		}
		
		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );

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

		
		AuditLog auditLog = dataAccessor.newAuditLog(
				AccessTokenFilter.getAccessToken(),
				AccessType.USER_ADD,
				user );
		
		
		user.setPassword( PasswordUtil.getSaltedHash( password ) );
		user.setEmail( email.toLowerCase() );
		user.setState( UserState.REGISTERED );
		user.setSignUpDate( new Date() );
		user.setSignUpSource( signUpSource );

		
		user = dataAccessor.createOrUpdateUser( user, auditLog );
		
		UserData userData = createUserData( user );
		userData.setFirstName( firstName );
		userData.setLastName( lastName );
		
		return userData;

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
		
		if( user.getPassword() == null && user.getGoogleId() != null )
			throw new InvalidArgumentException( GenericRequest.ERR_EMAIL_REGISTERED_WITH_GOOGLE );

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

	private static UserData processFederatedLoginData( UserData apiUserData, User user, UserSignUpSource signUpSource ) 
			throws InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		boolean isNew = false;

		// * Users having Facebook or google Id can never be in GUEST or REFERRAL state.
		// * No action required for REGISTERED or ACTIVE users.

		if( user == null || user.getState() == UserState.DELETED ) {

			if( apiUserData.getEmail() != null )
				user = dataAccessor.getUserByEmail( apiUserData.getEmail() );

			AuditLog auditLog = dataAccessor.newAuditLog(
					AccessTokenFilter.getAccessToken(),
					null,
					null );

			if( user == null || user.getState() == UserState.DELETED ) {

				user = dataAccessor.newUser();

				auditLog.setAccessType( AccessType.USER_ADD );
				auditLog.setEventDataOld( user );

				user.setEmail( apiUserData.getEmail() );
				user.setState( UserState.ACTIVE ); // Counting on Facebook / google for e-mail/user verification
				user.setSignUpDate( new Date() );
				user.setSignUpSource( signUpSource );

				isNew = true;

			} else if( user.getState() == UserState.REFERRAL ) {

				auditLog.setAccessType( AccessType.USER_ADD );
				auditLog.setEventDataOld( user );

				user.setState( UserState.ACTIVE ); // Counting on Facebook / google for e-mail/user verification
				user.setSignUpDate( new Date() );
				user.setSignUpSource( signUpSource );

				isNew = true;

			} else if( user.getState() == UserState.REGISTERED ) {

				auditLog.setAccessType( AccessType.USER_UPDATE );
				auditLog.setEventDataOld( user );

				user.setState( UserState.ACTIVE ); // Counting on Facebook / google for e-mail/user verification

			} else { // user.getState() == UserState.ACTIVE || user.getState() == UserState.BLOCKED

				auditLog.setAccessType( AccessType.USER_UPDATE );
				auditLog.setEventDataOld( user );

			}

			if( apiUserData.getFacebookId() != null )
				user.setFacebookId( apiUserData.getFacebookId() );

			if( apiUserData.getGoogleId() != null )
				user.setGoogleId( apiUserData.getGoogleId() );

			auditLog.setEventDataNew( user );

			user.setLastUpdated( new Date() );
			
			user = dataAccessor.createOrUpdateUser( user, auditLog );

		}


		if( user.getState() == UserState.BLOCKED )
			throw new InsufficientAccessException( GenericRequest.ERR_ACCOUNT_BLOCKED );


		_loginUser( AccessTokenFilter.getAccessToken(), user );


		UserData userData = createUserData( user );
		if( isNew ) {
			userData.setFirstName( apiUserData.getFirstName() );
			userData.setLastName( apiUserData.getLastName() );
			userData.setGender( apiUserData.getGender() );
			userData.setDateOfBirth( apiUserData.getDateOfBirth() );
		}

		return userData;

	}

	public static UserData loginUserWithFacebook( String fbUserAccessToken, UserSignUpSource signUpSource )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		UserData fbUserData = FacebookApi.getUserData( fbUserAccessToken );

		return processFederatedLoginData( fbUserData, 
						DataAccessorFactory.getDataAccessor().getUserByFacebookId( fbUserData.getFacebookId() ), 
						signUpSource );

	}
	
	public static UserData loginUserWithGoogle( String googleIdToken, UserSignUpSource signUpSource )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		UserData googleUserData = GoogleApi.getUserData( googleIdToken );

		return processFederatedLoginData( googleUserData, 
						DataAccessorFactory.getDataAccessor().getUserByGoogleId( googleUserData.getGoogleId() ), 
						signUpSource );

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
	
	
	public static void sendWelcomeMail( Long userId, Language language )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "welcome", language );
		
	}
	
	public static void sendEmailVerificationMail( Long userId, Language language )
			throws InvalidArgumentException, UnexpectedServerException {
		
		_sendEmailVerificationMail( DataAccessorFactory.getDataAccessor().getUser( userId ), language );
		
	}

	public static void sendEmailVerificationMail( String emailId, Language language )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		if( userId.equals( 0L ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( emailId.trim().toLowerCase() );
		if( user == null || ! user.getId().equals( userId ) )
			throw new InvalidArgumentException( GenericRequest.ERR_EMAIL_NOT_REGISTERED );
		
		_sendEmailVerificationMail( user, language );
		
	}

	private static void _sendEmailVerificationMail( User user, Language language )
			throws InvalidArgumentException, UnexpectedServerException {
		
		String verificationToken = getNextToken( user.getVerificationToken() );
		if( ! verificationToken.equals( user.getVerificationToken() ) ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			user.setVerificationToken( verificationToken );
			user = dataAccessor.createOrUpdateUser( user );
		}
		
		Map<String, String> dataModel = new HashMap<>();
		String verificationLink = "/" + "?" + "email=" + user.getEmail()
				+ "&" + "token=" + verificationToken.substring( 0, verificationToken.indexOf( "|" ) )
				+ "&" + "verifyUser=" + Boolean.TRUE;
		dataModel.put( "emailVerificationUrl", verificationLink );
		
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "verification", language, dataModel );
		
	}
	
	public static void sendPasswordResetMail( String email, Language language )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email.toLowerCase() );
		if( user == null )
			throw new InvalidArgumentException( GenericRequest.ERR_EMAIL_NOT_REGISTERED );
		
		String verificationToken = getNextToken( user.getVerificationToken() );
		if( ! verificationToken.equals( user.getVerificationToken() ) ) {
			user.setVerificationToken( verificationToken );
			user = dataAccessor.createOrUpdateUser( user );
		}
		
		Map<String, String> dataModel = new HashMap<>();
		String passwordResetUrl = "/" + "?" + "email=" + user.getEmail()
				+ "&" + "token=" + verificationToken.substring( 0, verificationToken.indexOf( "|" ) )
				+ "&" + "passwordReset=" + Boolean.TRUE;
		dataModel.put( "passwordResetUrl", passwordResetUrl );
		
		EmailUtil.sendMail( createUserData( user ).getDisplayName(), user.getEmail(), "password-reset", language, dataModel );
		
	}

	
	public static void verifyUserEmail( String email, String verificationToken )
			throws InvalidArgumentException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email.toLowerCase() );
		
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
		User user = dataAccessor.getUserByEmail( email.toLowerCase() );
		
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
	
	
	public static void updateUserAuthorStats( Long userId ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		User user = dataAccessor.getUser( userId );
		if( user.getState() != UserState.REGISTERED && user.getState() != UserState.ACTIVE )
			return;

		
		AuditLog auditLog = dataAccessor.newAuditLog(
				AccessTokenFilter.getAccessToken(),
				AccessType.USER_UPDATE,
				user );

		long followCount = dataAccessor.getUserAuthorFollowList( userId, null, null, null, null )
				.getDataList()
				.size();
		user.setFollowCount( followCount );
		
		auditLog.setEventDataNew( user );

		
		user = dataAccessor.createOrUpdateUser( user, auditLog );
		
	}

}
