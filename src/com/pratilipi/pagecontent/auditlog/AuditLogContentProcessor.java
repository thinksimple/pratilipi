package com.pratilipi.pagecontent.auditlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.gae.PratilipiEntity;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.service.shared.data.PratilipiData;

public class AuditLogContentProcessor extends PageContentProcessor<AuditLogContent> {

	private static final Gson gson = new GsonBuilder().create();
	
	@Override
	public String generateTitle( AuditLogContent auditLogContent, HttpServletRequest request ) {
		return null;
	}
	
	@Override
	public String generateHtml(
			AuditLogContent auditLogContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		com.pratilipi.data.access.DataAccessor pratilipiDataAccessor = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( request );
		
		DataListCursorTuple<AuditLog> auditLogListCursorTuple = 
				dataAccessor.getAuditLogList( null, 100 );
		List<AuditLog> auditLogList = auditLogListCursorTuple.getDataList();
		
		 Map<String, PratilipiData> newEventData = new HashMap<>();
		 Map<String, PratilipiData> oldEventData = new HashMap<>();
		 Map<String, User> userList = new HashMap<>();
		 for( AuditLog auditLog : auditLogList ){
			 AccessToken accessToken = dataAccessor.getAccessTokenById( auditLog.getAccessId() );
			 User user = dataAccessor.getUser( accessToken.getUserId() );
			 userList.put( auditLog.getId().toString(), user );
			 
			 Pratilipi newPratilipi = gson.fromJson( auditLog.getEventDataNew(), PratilipiEntity.class );
			 Pratilipi oldPratilipi = gson.fromJson( auditLog.getEventDataOld(), PratilipiEntity.class );
			 
			 Author newAuthor = pratilipiDataAccessor.getAuthor( newPratilipi.getAuthorId() );
			 Author oldAuthor = null;
			 if( oldPratilipi != null && newPratilipi.getAuthorId().equals( oldPratilipi.getAuthorId() ))
				 oldAuthor = newAuthor;
			 else if( auditLog.getEventDataOld().length() > 2 )
				 oldAuthor = pratilipiDataAccessor.getAuthor( oldPratilipi.getAuthorId() );
			 
			 Language newLanguage = pratilipiDataAccessor.getLanguage( newPratilipi.getLanguageId() );
			 Language oldLanguage = null;
			 if( oldPratilipi != null && newPratilipi.getLanguageId().equals( oldPratilipi.getLanguageId() ))
				 oldLanguage = newLanguage;
			 else if( auditLog.getEventDataOld().length() > 2 )
				 oldLanguage = pratilipiDataAccessor.getLanguage( oldPratilipi.getLanguageId() );
			 
			 List<PratilipiGenre> newnPratilipiGenreList = pratilipiDataAccessor.getPratilipiGenreList( newPratilipi.getId() );
			 List<Genre> newGenreList = new ArrayList<>( newnPratilipiGenreList.size() );
			 for( PratilipiGenre pratilipiGenre : newnPratilipiGenreList )
				 newGenreList.add( pratilipiDataAccessor.getGenre( pratilipiGenre.getGenreId() ));
			 
			 PratilipiData newPratilipiData = pratilipiHelper.createPratilipiData( newPratilipi, newLanguage, newAuthor, newGenreList );
			 newEventData.put( auditLog.getId().toString(), newPratilipiData );
			 
			 if( auditLog.getEventDataOld().length() > 2 ){
				 List<Genre> oldGenreList = null;
				 List<PratilipiGenre> oldPratilipiGenreList = pratilipiDataAccessor.getPratilipiGenreList( oldPratilipi.getId() );
				 oldGenreList = new ArrayList<>( oldPratilipiGenreList.size() );
				 for( PratilipiGenre pratilipiGenre : oldPratilipiGenreList )
					 oldGenreList.add( pratilipiDataAccessor.getGenre( pratilipiGenre.getGenreId() ));

				 PratilipiData oldPratilipiData = pratilipiHelper.createPratilipiData( oldPratilipi, oldLanguage, oldAuthor, oldGenreList );
				 oldEventData.put( auditLog.getId().toString(), oldPratilipiData );
			 }
			 
			 
		 }
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "auditLogList", auditLogList );
		dataModel.put( "userList", userList );
		dataModel.put( "newEventData", newEventData );
		dataModel.put( "oldEventData", oldEventData );
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
