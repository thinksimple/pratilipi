package com.pratilipi.api.impl.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.gae.AuditLogEntity;

@SuppressWarnings("serial")
@Bind( uri = "/test" )
public class TestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( TestApi.class.getName() );
	
	
	public static class GetRequest extends GenericRequest {
		Long pratilipiId;
		Integer pageNo;
		String url;
		Long blogPostId;
		Language language;
		String str;
	}
	
	public static class PostRequest extends GenericRequest { 
		String str;
	}

	public static class Response extends GenericResponse {
		
		String msg;
		
		Response( String msg ) {
			this.msg = msg;
		}
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request ) throws InsufficientAccessException, InvalidArgumentException, UnexpectedServerException, IOException, ParseException {
		
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
		
		
		/*DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String appPropertyId = "Api.Test.4802785708081152";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date() );
		}
		
		QueryResultIterator<AuditLogEntity> itr = ObjectifyService.ofy().load().type( AuditLogEntity.class )
				.filter( "CREATION_DATE <=", appProperty.getValue() )
				.order( "-CREATION_DATE" )
				.limit( 2500 )
				.iterator();
		
		while( itr.hasNext() ) {
			AuditLog auditLog = itr.next();
			if( auditLog.getAccessType() != AccessType.PRATILIPI_UPDATE )
				continue;
			if( auditLog.getEventDataNew().contains( "4802785708081152" ) )
				logger.log( Level.INFO, auditLog.getId() + "" );
			appProperty.setValue( auditLog.getCreationDate() );
		}
		
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );*/
		
		/*DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
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
		i18n_3.setI18nString( Language.HINDI, "आपको फॉलो कर रहे हैं।" );
		i18n_4.setI18nString( Language.HINDI, "लोग आपको फॉलो कर रहे हैं।" );
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
		i18n_3.setI18nString( Language.TAMIL, "உங்களைப் பின் தொடர்கிறார்கள்" );
		i18n_4.setI18nString( Language.TAMIL, "பேர் உங்களைப் பின் தொடர்கிறார்கள்" );
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
		dataAccessor.createOrUpdateI18n( i18n_5 );*/
		
/*		List<Key<PratilipiEntity>> keys = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "CONTENT_TYPE", PratilipiContentType.PRATILIPI )
				.keys()
				.list();
		
		List<Task> taskList = new ArrayList<>( keys.size() );
		for( Key<PratilipiEntity> key : keys )
			taskList.add( TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", key.getId() + "" )
					.addParam( "processContent", "true" ) );
		TaskQueueFactory.getPratilipiOfflineTaskQueue().addAll( taskList );*/

		

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.TestApi";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( 1453534631495L );
		}

		
		
		QueryResultIterator<AuditLogEntity> itr = ObjectifyService.ofy().load()
				.type( AuditLogEntity.class )
				.filter( "CREATION_DATE >=", new Date( (Long) appProperty.getValue() ) )
				.order( "CREATION_DATE" )
				.chunk( 100 )
				.iterator();
		
		Gson gson = new Gson();
		
		int count = 0;
		while( itr.hasNext() ) {
			
			count++;
			
			AuditLog auditLog = itr.next();
			JsonObject jsonObject = gson.fromJson( auditLog.getEventDataNew(), JsonElement.class ).getAsJsonObject();
		
			if( auditLog.getUserId() != null
					&& auditLog.getPrimaryContentId() != null
					&& auditLog.getEventDataOld() != null )
				continue;
		
			
			if( auditLog.getUserId() == null )
				auditLog.setUserId( dataAccessor.getAccessToken( auditLog.getAccessId() ).getUserId() );
			
		
			if( auditLog.getAccessType() == AccessType.PRATILIPI_ADD || auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE ) {
			
				if( jsonObject.get( "id" ) == null && jsonObject.get( "PRATILIPI_ID" ) == null ) {
					ObjectifyService.ofy().delete().entity( auditLog );
					System.out.println( "\nDeleting " + auditLog.getId() + " ..." );
					continue;
				} else if( jsonObject.get( "id" ) != null )
					auditLog.setPrimaryContentId( jsonObject.get( "id" ).getAsLong() );
				else
					auditLog.setPrimaryContentId( jsonObject.get( "PRATILIPI_ID" ).getAsLong() );
			
			} else if( auditLog.getAccessType() == AccessType.AUTHOR_ADD || auditLog.getAccessType() == AccessType.AUTHOR_UPDATE ) {
				
				if( jsonObject.get( "id" ) != null )
					auditLog.setPrimaryContentId( jsonObject.get( "id" ).getAsLong() );
				else
					auditLog.setPrimaryContentId( jsonObject.get( "AUTHOR_ID" ).getAsLong() );
				
			} else {
				
				System.out.println( "\n" + auditLog.getAccessType() );
				System.out.println( auditLog.getCreationDate().getTime() );
				break;
		
			}
			
			
			if( auditLog.getEventDataOld() == null )
				auditLog.setEventDataOld( "{}" );
		
			
			ObjectifyService.ofy().save().entity( auditLog );
			
			
			System.out.print( "." );
			if( count % 100 == 0 ) {
				appProperty.setValue( auditLog.getCreationDate().getTime() );
				dataAccessor.createOrUpdateAppProperty( appProperty );
				System.out.print( "\n" + auditLog.getCreationDate().getTime() + " : " + count + " " );
			}
		
		}

		
		return new GenericResponse();
		
	}
	
}
