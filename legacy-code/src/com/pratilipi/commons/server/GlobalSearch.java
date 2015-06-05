package com.pratilipi.commons.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.MatchScorer;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.QueryOptions.Builder;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortOptions;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.server.PratilipiServiceImpl;

@Deprecated
public class GlobalSearch {

	private static final Logger logger =
			Logger.getLogger( PratilipiServiceImpl.class.getName() );

	private static final Index searchIndex = SearchServiceFactory
			.getSearchService()
			.getIndex( IndexSpec.newBuilder().setName( ClaymusHelper.SEARCH_INDEX_NAME ) );

	
	private final PratilipiHelper pratilipiHelper;
	private final Builder optionsBuilder;
	private Cursor cursor;
	private long numberFound;
	
	
	public GlobalSearch( HttpServletRequest request ) {
		pratilipiHelper = PratilipiHelper.get( request );
		optionsBuilder = QueryOptions.newBuilder()
				.setSortOptions( SortOptions.newBuilder().setMatchScorer( MatchScorer.newBuilder() ) )
				.setFieldsToReturn( "docType" );
		setCursor( Cursor.newBuilder().build() );
	}
	
	public String getCursor() {
		return cursor == null ? null : cursor.toWebSafeString();
	}
	
	public void setCursor( Cursor cursor ) {
		this.cursor = cursor;
		optionsBuilder.setCursor( cursor );
	}
	
	public void setCursor( String cursorStr ) {
		setCursor( cursorStr == null ? null : Cursor.newBuilder().build( cursorStr ) );
	}
	
	public void setResultCount( int resultCount ) {
		optionsBuilder.setLimit( resultCount );
	}
	
	public List<IsSerializable> search( String queryStr ) {
		return search( queryStr, null );
	}
	
	public List<IsSerializable> search( String queryStr, String filterStr ) {
		
		String searchQuery  = null;
		if( queryStr != null && !queryStr.isEmpty() )
			searchQuery = queryStr.toLowerCase().trim().replaceAll( "[\\s]+", " OR " );
		
		if( filterStr != null && !filterStr.isEmpty() )
			searchQuery = ( searchQuery == null ? "" : "( " + queryStr + " ) AND " ) + filterStr;
		
		logger.log( Level.INFO, "Search Query: " + searchQuery );
		
		Query query = Query.newBuilder()
				.setOptions( optionsBuilder )
				.build( searchQuery );

	    Results<ScoredDocument> result = searchIndex.search( query );

	    List<IsSerializable> dataList = new ArrayList<>( result.getNumberReturned() ); 
		for( ScoredDocument document : result ) {
			String docType = document.getFields( "docType" ).iterator().next().getAtom();
			if( docType.equals( "Pratilipi" ) ) {
				Long pratilipiId = Long.parseLong( document.getId().substring( "PratilipiData:".length() ) );
				dataList.add( pratilipiHelper.createPratilipiData( pratilipiId ) );
				
			} else if( docType.equals( "Author" ) ) {
				Long authorId = Long.parseLong( document.getId().substring( "AuthorData:".length() ) );
				dataList.add( pratilipiHelper.createAuthorData( authorId ) );
				
			} else {
				logger.log( Level.SEVERE, "\"" + docType + "\" docType is not yet supported !");
			}
		}

		setCursor( result.getCursor() );
		numberFound = result.getNumberFound();
		return dataList;
	}
	
	public long getResultCout() {
		return numberFound;
	}
	
}
