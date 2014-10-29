package com.pratilipi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
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
	
	private static final IndexSpec indexSpec = IndexSpec.newBuilder().setName( "PRATILIPI" ).build(); 
	private static final Index index = SearchServiceFactory.getSearchService().getIndex( indexSpec );

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
			
			//Comma separated genre name list.
			String commaSeparatedGenreList = null;
			if( genreList.size() > 1 ){
				for( int i = 1; i < genreList.size(); i++ )
					commaSeparatedGenreList +=  ", " + genreList.get( i );
			}
			else if( genreList.size() == 1 )
				commaSeparatedGenreList = genreList.get( 0 ).toString();
			else
				commaSeparatedGenreList = "";

			Document document = Document.newBuilder()
					.setId( pratilipiData.getId().toString() )
					.addField( Field.newBuilder().setName( "Pratilipi-Type" ).setAtom( pratilipiData.getType().getName() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Title" ).setText( pratilipiData.getTitle() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Title-En" ).setText( pratilipiData.getTitleEn() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Language-Id" ).setAtom( pratilipiData.getLanguageId().toString() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Language-Name" ).setText( pratilipiData.getLanguageData().getName() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Language-Name-En" ).setText( pratilipiData.getLanguageData().getNameEn() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Author-Id" ).setAtom( pratilipiData.getAuthorId().toString() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Author-Name" ).setText( pratilipiData.getAuthorData().getFullName() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Author-Name-En" ).setText( pratilipiData.getAuthorData().getFullNameEn() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Genre-List" ).setText( commaSeparatedGenreList ) )
					.addField( Field.newBuilder().setName( "Pratilipi-Summary" ).setHTML( pratilipiData.getSummary() ) )
					.addField( Field.newBuilder().setName( "Pratilipi-State" ).setAtom( pratilipiData.getState().toString().toLowerCase() ) )
					.build();
			
			documentList.add( document );
		}
		
		for( int i = 0; i < documentList.size(); i = i + 200 ) {
			try {
				index.put( documentList.subList( i, i + 200 > documentList.size() ? documentList.size() : i + 200 ) );

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
