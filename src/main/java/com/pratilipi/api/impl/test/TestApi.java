package com.pratilipi.api.impl.test;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.I18n;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

@SuppressWarnings("serial")
@Bind( uri = "/test" )
public class TestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( TestApi.class.getName() );
	
	
	public static class GetRequest extends GenericRequest {
		Long deleteUserId;
		String email;
		String facebookId;
		
		Long pratilipiId;
		Integer pageNo;
		String url;
		Long blogPostId;
		Language language;
	}
	
	public static class Response extends GenericResponse {
		
		String msg;
		
		Response( String msg ) {
			this.msg = msg;
		}
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request ) throws InsufficientAccessException, InvalidArgumentException, UnexpectedServerException {
		
/*		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( 5830554839678976L );
		pratilipi.setContentType( PratilipiContentType.IMAGE );
		pratilipi.setPageCount( 180 );
		ObjectifyService.ofy().save().entity( pratilipi ).now();
*/		

/*		String appPropertyId = "Api.PratilipiProcess.ValidateData";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );

		appPropertyId = "Api.AuthorProcess.ValidateData";
		appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
		appPropertyId = "Api.UserProcess.ValidateData";
		appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty ); */
		
		
/*		if( request.email != null || request.facebookId != null ) {
			
			List<UserEntity> userList = null;
			
			if( request.email != null ) {
				userList = ObjectifyService.ofy().load()
						.type( UserEntity.class )
						.filter( "EMAIL", request.email )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" )
						.list();
			
			} else if( request.facebookId != null ) {
				userList = ObjectifyService.ofy().load()
						.type( UserEntity.class )
						.filter( "FACEBOOK_ID", request.facebookId )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" )
						.list();
			}
		
			if( userList.size() == 0 )
				return new Response( "Could not find user for given email/facebook id !" );
			
			if( userList.size() == 1 )
				return new Response( "Everythig looks well !" );
			
			User xUser = null;
			for( User user : userList )
				if( _canDelete( user ) )
					xUser = user;
			
			if( xUser == null )
				return new Response( "Its complicated !" );
			
			return new Response( "User (" + xUser.getId() + ") of " + userList.size() + " users can be deleted !" );
			
		} else if( request.deleteUserId != null ) {
			
			List<AuthorEntity> authorList = ObjectifyService.ofy().load()
					.type( AuthorEntity.class )
					.filter( "USER_ID", request.deleteUserId )
					.filter( "STATE !=", AuthorState.DELETED )
					.order( "STATE" )
					.order( "REGISTRATION_DATE" )
					.list();
			
			for( Author author : authorList ) {
				List<PageEntity> pageList = ObjectifyService.ofy().load()
						.type( PageEntity.class )
						.filter( "PAGE_TYPE", "AUTHOR" )
						.filter( "PRIMARY_CONTENT_ID", author.getId() )
						.list();
				ObjectifyService.ofy().delete().entities( pageList );
				author.setState( AuthorState.DELETED );
				ObjectifyService.ofy().save().entity( author );
			}
			
			User user = ObjectifyService.ofy().load()
					.type( UserEntity.class )
					.id( request.deleteUserId )
					.now();
			user.setState( UserState.DELETED );
			ObjectifyService.ofy().save().entity( user );
			
			return new Response( "User (" + request.deleteUserId + ") deleted !" );

		} else {
			
			return new GenericResponse();
			
		}*/

		
/*		QueryResultIterator<AuditLogEntity> itr = ObjectifyService.ofy().load().type( AuditLogEntity.class )
				.order( "-CREATION_DATE" )
				.iterator();
		
		while( itr.hasNext() ) {
			AuditLog auditLog = itr.next();
			if( auditLog.getAccessType() != AccessType.PRATILIPI_UPDATE )
				continue;
			if( auditLog.getEventDataNew().contains( "6627432686682112" ) )
				logger.log( Level.INFO, auditLog.getId() + "" );
		}*/
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		I18n i18n_1 = dataAccessor.newI18n( "notification_and" );
		I18n i18n_2 = dataAccessor.newI18n( "notification_has_followed" );
		I18n i18n_3 = dataAccessor.newI18n( "notification_have_followed" );
		I18n i18n_4 = dataAccessor.newI18n( "notification_others_have_followed" );
		I18n i18n_5 = dataAccessor.newI18n( "notification_has_published" );
		
		i18n_1.setGroup( I18nGroup.NOTIFICATION );
		i18n_2.setGroup( I18nGroup.NOTIFICATION );
		i18n_3.setGroup( I18nGroup.NOTIFICATION );
		i18n_4.setGroup( I18nGroup.NOTIFICATION );
		i18n_5.setGroup( I18nGroup.NOTIFICATION );
		
		i18n_1.setI18nString( Language.ENGLISH, "and" );
		i18n_2.setI18nString( Language.ENGLISH, "has followed you." );
		i18n_3.setI18nString( Language.ENGLISH, "have followed you." );
		i18n_4.setI18nString( Language.ENGLISH, "others have followed you." );
		i18n_5.setI18nString( Language.ENGLISH, "has published a new content :" );

		i18n_1.setI18nString( Language.HINDI, "और" );
		i18n_2.setI18nString( Language.HINDI, "आपको फॉलो कर रहे है।" );
		i18n_3.setI18nString( Language.HINDI, "आपको फॉलो कर रहे है।" );
		i18n_4.setI18nString( Language.HINDI, "लोग आपको फॉलो कर रहे है।" );
		i18n_5.setI18nString( Language.HINDI, "ने एक रचना प्रकाशित करी है : " );

		i18n_1.setI18nString( Language.GUJARATI, "અને" );
		i18n_2.setI18nString( Language.GUJARATI, "આપને ફોલો કરી રહ્યા છે." );
		i18n_3.setI18nString( Language.GUJARATI, "આપને ફોલો કરી રહ્યા છે." );
		i18n_4.setI18nString( Language.GUJARATI, "લોકો આપને ફોલો કરી રહ્યા છે." );
		i18n_5.setI18nString( Language.GUJARATI, "એ એક રચના પ્રકાશિત કરી છે : " );

		i18n_1.setI18nString( Language.BENGALI, "এবং" );
		i18n_2.setI18nString( Language.BENGALI, "আপনাকে অনুসরণ করছে।" );
		i18n_3.setI18nString( Language.BENGALI, "আপনাকে অনুসরণ করছে।" );
		i18n_4.setI18nString( Language.BENGALI, "জন আপনাকে অনুসরণ করছে।" );
		i18n_5.setI18nString( Language.BENGALI, "একটি রচনা প্রকাশিত করেছেন : " );

		i18n_1.setI18nString( Language.KANNADA, "ಮತ್ತು" );
		i18n_2.setI18nString( Language.KANNADA, "ನಿಮ್ಮನ್ನು ಹಿಂಬಾಲಿಸುತ್ತಿದ್ದಾರೆ" );
		i18n_3.setI18nString( Language.KANNADA, "ನಿಮ್ಮನ್ನು ಹಿಂಬಾಲಿಸುತ್ತಿದ್ದಾರೆ" );
		i18n_4.setI18nString( Language.KANNADA, "ಜನರು ನಿಮ್ಮನ್ನು ಹಿಂಬಾಲಿಸುತ್ತಿದ್ದಾರೆ" );
		i18n_5.setI18nString( Language.KANNADA, "ಹೊಸ ಬರಹವೊಂದನ್ನು ಪ್ರಕಟಿಸಿದ್ದಾರೆ : " );

		i18n_1.setI18nString( Language.TELUGU, "మరియు" );
		i18n_2.setI18nString( Language.TELUGU, "మిమ్మల్ని అనుసరిస్తున్నారు" );
		i18n_3.setI18nString( Language.TELUGU, "మిమ్మల్ని అనుసరిస్తున్నారు" );
		i18n_4.setI18nString( Language.TELUGU, "మంది మిమ్మల్ని అనుసరిస్తున్నారు" );
		i18n_5.setI18nString( Language.TELUGU, "కొత్త రచనను ప్రచురించారు : " );

		i18n_1.setI18nString( Language.MARATHI, "आणि" );
		i18n_2.setI18nString( Language.MARATHI, "माझे अनुसरण करत आहेत" );
		i18n_3.setI18nString( Language.MARATHI, "माझे अनुसरण करत आहेत" );
		i18n_4.setI18nString( Language.MARATHI, "लोक माझे अनुसरण करत आहेत" );
		i18n_5.setI18nString( Language.MARATHI, "ने एक रचना प्रकाशित केली आहे : " );

		i18n_1.setI18nString( Language.TAMIL, "மற்றும்" );
		i18n_2.setI18nString( Language.TAMIL, "உங்களைப் பின் தொடர்கிறார்" );
		i18n_3.setI18nString( Language.TAMIL, "உங்களைப் பின் தொடர்கிறார்" );
		i18n_4.setI18nString( Language.TAMIL, "பேர் உங்களைப் பின் தொடர்கிறார்" );
		i18n_5.setI18nString( Language.TAMIL, "ஒரு படைப்பைப் பதிப்பித்துள்ளார் : " );

		i18n_1.setI18nString( Language.MALAYALAM, "എന്നിവരും പിന്നെ" );
		i18n_2.setI18nString( Language.MALAYALAM, "താങ്കളെ ഫോളോ ചെയ്യുന്നു" );
		i18n_3.setI18nString( Language.MALAYALAM, "താങ്കളെ ഫോളോ ചെയ്യുന്നു" );
		i18n_4.setI18nString( Language.MALAYALAM, "പേരും താങ്കളെ ഫോളോ ചെയ്യുന്നു" );
		i18n_5.setI18nString( Language.MALAYALAM, "ഒരു പുതിയ രചന പ്രസിദ്ധീകരിച്ചിരിക്കുന്നു : " );

		dataAccessor.createOrUpdateI18n( i18n_1 );
		dataAccessor.createOrUpdateI18n( i18n_2 );
		dataAccessor.createOrUpdateI18n( i18n_3 );
		dataAccessor.createOrUpdateI18n( i18n_4 );
		dataAccessor.createOrUpdateI18n( i18n_5 );
		
		return new GenericResponse();
		
	}
	
	private boolean _canDelete( User user ) {
		
		List<AuthorEntity> authorList = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.filter( "STATE !=", AuthorState.DELETED )
				.order( "STATE" )
				.order( "REGISTRATION_DATE" )
				.list();
		
		List<UserPratilipiEntity> userPratilipiList = ObjectifyService.ofy().load()
				.type( UserPratilipiEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		List<UserAuthorEntity> userAuthorList = ObjectifyService.ofy().load()
				.type( UserAuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		List<AccessTokenEntity> accessTokenList = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		return ( authorList.size() == 0 || ( authorList.size() == 1 && _canDelete( authorList.get( 0 ) ) ) )
				&& userPratilipiList.size() == 0
				&& userAuthorList.size() == 0
				&& accessTokenList.size() == 0;
		
	}
	
	private boolean _canDelete( Author author ) {
		
		List<PratilipiEntity> pratilipiList = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "AUTHOR_ID", author.getId() )
				.list();
		
		List<PageEntity> pageList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", "AUTHOR" )
				.filter( "PRIMARY_CONTENT_ID", author.getId() )
				.list();
		
		return pratilipiList.size() == 0 && pageList.size() <= 1;
		
	}
	
}
