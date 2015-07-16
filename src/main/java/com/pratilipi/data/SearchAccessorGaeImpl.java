package com.pratilipi.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.MatchScorer;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.StatusCode;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

public class SearchAccessorGaeImpl implements SearchAccessor {

	private static final Logger logger =
			Logger.getLogger( SearchAccessorGaeImpl.class.getName() );

	private static final SearchService searchService =
			SearchServiceFactory.getSearchService();

	private final Index searchIndex;

	
	// Constructor
	
	public SearchAccessorGaeImpl( String indexName ) {
		searchIndex = searchService.getIndex( IndexSpec.newBuilder().setName( indexName ) );
	}


	// Helper Methods
	
	public Results<ScoredDocument> search(
			String searchQuery, SortOptions sortOptions, String cursorStr,
			Integer resultCount, String... fieldsToReturn ) {
		
		if( sortOptions == null ) {
			SortOptions.Builder sortOptionsBuilder = SortOptions.newBuilder();
			sortOptionsBuilder.setMatchScorer( MatchScorer.newBuilder() );
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( SortExpression.SCORE_FIELD_NAME )
					.setDirection( SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0.0 ) );
			sortOptions = sortOptionsBuilder.setLimit( 10000 ).build();
		}
		
		QueryOptions queryOptions = QueryOptions.newBuilder()
				.setSortOptions( sortOptions )
				.setCursor( cursorStr == null || cursorStr.isEmpty() ? Cursor.newBuilder().build() : Cursor.newBuilder().build( cursorStr ) )
				.setLimit( resultCount == null ? 1000 : resultCount )
				.setNumberFoundAccuracy( 10000 )
				.setFieldsToReturn( fieldsToReturn )
				.build();
		
		Query query = Query.newBuilder()
				.setOptions( queryOptions )
				.build( searchQuery );

		logger.log( Level.INFO, "Search Query: " + query.toString() );
		
	    return searchIndex.search( query );
	}

	protected void indexDocument( Document document ) throws UnexpectedServerException {
		List<Document> documentList = new ArrayList<>( 1 );
		documentList.add( document );
		indexDocumentList( documentList );
	}
	
	protected void indexDocumentList( List<Document> documentList ) throws UnexpectedServerException {
		for( int i = 0; i < documentList.size(); i = i + 200 ) {
			try {
				searchIndex.put( documentList.subList( i, i + 200 > documentList.size() ? documentList.size() : i + 200 ) );
			} catch( PutException e ) {
				if( StatusCode.TRANSIENT_ERROR.equals( e.getOperationResult().getCode() ) ) {
					logger.log( Level.WARNING, "Failed to update search index. Retrying ...", e );
					try {
						Thread.sleep( 100 );
						i = i - 200;
						continue;
					} catch( InterruptedException ex ) {
						// Do nothing
					}
				} else {
					logger.log( Level.SEVERE, "Failed to update search index.", e );
					throw new UnexpectedServerException();
				}
			}
		}
	}

	protected void deleteIndex( String docId ) {
		searchIndex.delete( docId );
	}

	
	// PRATILIPI Data
	
	@Override
	public DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		return searchPratilipi( null, pratilipiFilter, cursorStr, resultCount );
	}
	
	@Override
	public DataListCursorTuple<Long> searchPratilipi( String query, PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		SortOptions.Builder sortOptionsBuilder = SortOptions.newBuilder();

		if( pratilipiFilter.getOrderByReadCount() != null ) {
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( "readCount" )
					.setDirection( pratilipiFilter.getOrderByReadCount()
							? SortExpression.SortDirection.ASCENDING
							: SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0.0 ) );

		} else if( query != null && ! query.isEmpty() ) {
			sortOptionsBuilder.setMatchScorer( MatchScorer.newBuilder() );
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( SortExpression.SCORE_FIELD_NAME )
					.setDirection( SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0.0 ) );
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( "relevance" )
					.setDirection( SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0.0 ) );
			
		} else {
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( "relevance" )
					.setDirection( SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0.0 ) );
		}
		
		SortOptions sortOptions = sortOptionsBuilder.setLimit( 10000 ).build();

		
		String searchQuery = pratilipiFilter.getType() == null
				? "docType:Pratilipi"
				: "docType:Pratilipi-" + pratilipiFilter.getType().getName();

		if( pratilipiFilter.getLanguage() != null )
			searchQuery = searchQuery + " AND language:" + pratilipiFilter.getLanguage().getNameEn();

		if( pratilipiFilter.getAuthorId() != null )
			searchQuery = searchQuery + " AND author:" + pratilipiFilter.getAuthorId();

		if( query != null && ! query.isEmpty() )
			searchQuery = "( " + query + " ) AND " + searchQuery;

		
		Results<ScoredDocument> result = search( searchQuery, sortOptions, cursorStr, resultCount, "docId" );
		
		List<Long> pratilipiIdList = new ArrayList<>( result.getNumberReturned() ); 
		for( ScoredDocument document : result )
			pratilipiIdList.add( Long.parseLong( document.getOnlyField( "docId" ).getAtom() ) );
		
		Cursor cursor = result.getCursor();
		
		return new DataListCursorTuple<Long>( pratilipiIdList, cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public void indexPratilipiData( PratilipiData pratilipiData ) throws UnexpectedServerException {
		indexDocument( createDocument( pratilipiData ) );
	}

	@Override
	public void indexPratilipiDataList( List<PratilipiData> pratilipiDataList ) throws UnexpectedServerException {
		List<Document> documentList = new ArrayList<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			documentList.add( createDocument( pratilipiData ) );
		indexDocumentList( documentList );
	}

	@Override
	public void deletePratilipiDataIndex( Long pratilipiId ) {
		deleteIndex( "PratilipiData:" + pratilipiId );
	}

	private Document createDocument( PratilipiData pratilipiData ) {

		String docId = "PratilipiData:" + pratilipiData.getId();
		
		Builder docBuilder = Document.newBuilder()
				.setId( docId )
				.addField( Field.newBuilder().setName( "docId" ).setAtom( pratilipiData.getId().toString() ) )
				.addField( Field.newBuilder().setName( "docType" ).setAtom( "Pratilipi" ) )
				.addField( Field.newBuilder().setName( "docType" ).setAtom( "Pratilipi-" + pratilipiData.getType().getName() ) )

				// 2x weightage to Title
				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitle() ) )
				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitle() ) )
				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitleEn() ) )
				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitleEn() ) )

				 // 4x weightage to Language
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				
				.addField( Field.newBuilder().setName( "summary" ).setHTML( pratilipiData.getSummary() ) );
				// Adding keywords to the document.
		if( pratilipiData.getKeywords() != null )
			docBuilder.addField( Field.newBuilder().setName( "keywords" ).setText( pratilipiData.getKeywords() ) )
				
				// 4x weightage to PratilipiType
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getName() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getName() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getName() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getName() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getNamePlural() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getNamePlural() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getNamePlural() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getNamePlural() ) )
				
				.addField( Field.newBuilder().setName( "readCount" ).setNumber( pratilipiData.getReadCount() ) )
				.addField( Field.newBuilder().setName( "relevance" ).setNumber( pratilipiData.getRelevance() ) );
		
		if( pratilipiData.getAuthorId() != null )
			docBuilder.addField( Field.newBuilder().setName( "author" ).setAtom( pratilipiData.getAuthorId().toString() ) )
					// 3x weightage to Author Name
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullName() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullName() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullName() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullNameEn() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullNameEn() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthor().getFullNameEn() ) );

		return docBuilder.build();
	}

	
	// AUTHOR Data
	
	@Override
	public void indexAuthorData( AuthorData authorData ) throws UnexpectedServerException {
		indexDocument( createDocument( authorData ) );
	}

	private Document createDocument( AuthorData authorData ) {
		
		String docId = "AuthorData:" + authorData.getId();
		
		return Document.newBuilder()
				.setId( docId )
				.addField( Field.newBuilder().setName( "docId" ).setAtom( authorData.getId().toString() ) )
				.addField( Field.newBuilder().setName( "docType" ).setAtom( "Author" ) )

				 // 4x weightage to Language
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( authorData.getLanguage().getNameEn() ) )
				
				// 3x weightage to Author Name
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullName() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullName() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullName() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullNameEn() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullNameEn() ) )
				.addField( Field.newBuilder().setName( "name" ).setText( authorData.getFullNameEn() ) )

				.addField( Field.newBuilder().setName( "email" ).setText( authorData.getEmail() ) )

				.addField( Field.newBuilder().setName( "summary" ).setHTML( authorData.getSummary() ) )

				.build();
	}
	
}
