package com.pratilipi.pagecontent.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.MatchScorer;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortOptions;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class SearchContentProcessor extends PageContentProcessor<SearchContent> {

	@Override
	public String generateHtml( SearchContent searchContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		
		String queryParameter = "";
		boolean isSearchQueryPresent = true ;
		
		if( request.getParameter( "query" ) != null )
			queryParameter = request.getParameter( "query" );
		else
			isSearchQueryPresent = false;
		
		String modifiedSearchQuery = "";
		String[] queryArray = queryParameter.toLowerCase().split( " " );
		
		for( int i=0; i< queryArray.length; i++ ) {
			if( ( queryArray[i].toUpperCase().equals( "AND" ) ||
							queryArray[i].toUpperCase().equals( "NOT" ) ) && i != ( queryArray.length - 1 ) ) {
				modifiedSearchQuery += ( " " + queryArray[i].toUpperCase() + " " + queryArray[++i] );
				continue;
			}else if( queryArray[i].toUpperCase().equals( "OR" ))
				continue;
			else {
				if( i == 0 )
					modifiedSearchQuery += queryArray[i];
				else
					modifiedSearchQuery += ( " OR " + queryArray[i] );
			}
		}
		
		if( request.getParameter( "section" ) != null &&  !request.getParameter( "section" ).equals( "All" ) )
			modifiedSearchQuery = request.getParameter( "section" ) + " AND " + modifiedSearchQuery;
		
		String serverMsg;
		
		Results<ScoredDocument> result = null;
		
		SortOptions sortOptions = SortOptions.newBuilder()
		        .setMatchScorer( MatchScorer.newBuilder() )
		        .build();
		
	    // Build the QueryOptions
	    QueryOptions options = QueryOptions.newBuilder()
	        .setLimit( 20 )
	        .setReturningIdsOnly( true )
	        .setSortOptions( sortOptions )
	        .build();

	    //  Build the Query and run the search
	    Query query = Query.newBuilder().setOptions(options).build( modifiedSearchQuery );
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName( "PRATILIPI" ).build();
	    Index index = SearchServiceFactory.getSearchService().getIndex( indexSpec );
	    result =  index.search( query );
	    int numberReturned = result.getNumberReturned();
	    
	    List<Long> pratilipiIdList = new ArrayList<>(); 
	    List<PratilipiData> pratilipiDataList = new ArrayList<>();
	    
	    if( numberReturned > 0 ){
	    	
			for( ScoredDocument document : result ){
				pratilipiIdList.add( Long.parseLong( document.getId() ));
			}
			
			pratilipiDataList = pratilipiHelper.createPratilipiDataListFromIdList( pratilipiIdList, false, true, false );
			serverMsg = "<p style=\"width: 100%;text-align: center; border: 1px solid black;"
						+ " padding: 5px;\">No more records are found</p>" ;
	    }
	    else{
	    	serverMsg = "<h5>We can't find what you are looking for."
					+ "Please <a href='/contact'>mail us</a> if you are looking for something special. </h5>" ;
	    }
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		dataModel.put( "serverMsg", serverMsg );
		dataModel.put( "isSearchQueryPresent", isSearchQueryPresent );
		dataModel.put( "endOfSearchReached", numberReturned < 20 ? true : false );

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
