package com.pratilipi.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.service.shared.data.AuthorData;

@SuppressWarnings("serial")
public class QueueUpdateAuthorIndexServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( QueueWordToPratilipiServlet.class.getName() );
	
	private static final Index searchIndex = SearchServiceFactory
			.getSearchService()
			.getIndex( IndexSpec.newBuilder().setName( ClaymusHelper.SEARCH_INDEX_NAME ) );

	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		
		String authorIdStr = request.getParameter( "authorId" );
		if( authorIdStr == null ) {
			logger.log( Level.SEVERE, "AuthorId is not provided !" );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}
		
		Long authorId = Long.parseLong( authorIdStr );
		AuthorData authorData = pratilipiHelper.createAuthorData( authorId );
		String docId = "AuthorData:" + authorId;
		Document document = Document.newBuilder()
				.setId( docId )
				.addField( Field.newBuilder().setName( "docType" ).setAtom( "Author" ) )

				.addField( Field.newBuilder().setName( "pageUrl" ).setAtom( authorData.getPageUrlAlias() ) )
				.addField( Field.newBuilder().setName( "imageUrl" ).setAtom( authorData.getAuthorImageUploadUrl() ) )
				
				.addField( Field.newBuilder().setName( "language" ).setAtom( authorData.getLanguageId().toString() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguageData().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguageData().getNameEn() ) )
				
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullName() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullNameEn() ) )

				.addField( Field.newBuilder().setName( "email" ).setText( authorData.getEmail() ) )

				.addField( Field.newBuilder().setName( "summary" ).setHTML( authorData.getSummary() ) )

				.build();

		searchIndex.put( document );
		
	}
	
}
