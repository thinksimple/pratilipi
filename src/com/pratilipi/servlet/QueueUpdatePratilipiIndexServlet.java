package com.pratilipi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.service.shared.data.PratilipiData;

@SuppressWarnings("serial")
public class QueueUpdatePratilipiIndexServlet extends HttpServlet {
	
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
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		String authorIdStr = request.getParameter( "authorId" );
		String pratilipiIdStr = request.getParameter( "pratilipiId" );

		List<Pratilipi> pratilipiList = null;
		
		if( authorIdStr != null ) {
			Long authorId = Long.parseLong( authorIdStr );
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setAuthorId( authorId );
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
			pratilipiList = dataAccessor
					.getPratilipiList( pratilipiFilter, null, 1000 )
					.getDataList();
			
		} else if( pratilipiIdStr != null ) {
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			pratilipiList = new ArrayList<>( 0 );
			pratilipiList.add( dataAccessor.getPratilipi( pratilipiId ) );
			
		} else {
			logger.log( Level.SEVERE, "Neither AuthorId, nor PratilipiId is provided !" );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}
		
		List<Document> documentList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			List<PratilipiGenre> pratilipiGenreList = dataAccessor.getPratilipiGenreList( pratilipi.getId() );
			
			List<Genre> genreList = new ArrayList<>( pratilipiGenreList.size() );
			for( PratilipiGenre pratilipiGenre : pratilipiGenreList )
				genreList.add( dataAccessor.getGenre( pratilipiGenre.getGenreId() ) );
			
			PratilipiData pratilipiData =
					pratilipiHelper.createPratilipiData( pratilipi, language, author, genreList );
			String docId = "PratilipiData:" + pratilipiData.getId().toString();
			
			if( pratilipiData.getState() == PratilipiState.PUBLISHED ) {
			
				Builder docBuilder = Document.newBuilder()
						.setId( docId )
						.addField( Field.newBuilder().setName( "docType" ).setAtom( "Pratilipi" ) )
						.addField( Field.newBuilder().setName( "docType" ).setAtom( "Pratilipi-" + pratilipiData.getType().getName() ) )

						.addField( Field.newBuilder().setName( "pageUrl" ).setAtom( pratilipiData.getPageUrlAlias() ) )
						.addField( Field.newBuilder().setName( "imageUrl" ).setAtom( pratilipiData.getCoverImageUrl() ) )

						.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitle() ) )
						.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitleEn() ) )
	
						.addField( Field.newBuilder().setName( "language" ).setAtom( pratilipiData.getLanguageId().toString() ) )
						.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguageData().getName() ) )
						.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguageData().getNameEn() ) )
						
						.addField( Field.newBuilder().setName( "summary" ).setHTML( pratilipiData.getSummary() ) );
				
				if( author != null )
					docBuilder.addField( Field.newBuilder().setName( "author" ).setAtom( pratilipiData.getAuthorId().toString() ) )
							.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthorData().getFullName() ) )
							.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthorData().getFullNameEn() ) );

				for( Genre genre : genreList )
					docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genre.getId().toString() ) )
							.addField( Field.newBuilder().setName( "genre" ).setText( genre.getName() ) );
				
				Document document = docBuilder.build();
				
				documentList.add( document );
				
			} else {
				searchIndex.delete( docId );
			}
			
		}
		
		
		for( int i = 0; i < documentList.size(); i = i + 200 ) {
			try {
				searchIndex.put( documentList.subList( i, i + 200 > documentList.size() ? documentList.size() : i + 200 ) );

			} catch( PutException e ) {
				if( StatusCode.TRANSIENT_ERROR.equals( e.getOperationResult().getCode() ) ) {
					i = i - 200;
				} else {
					logger.log( Level.SEVERE, "Exception while updating PRATILIPI index.", e );
					response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
					break;
				}
			}
		}

	}
	
}
