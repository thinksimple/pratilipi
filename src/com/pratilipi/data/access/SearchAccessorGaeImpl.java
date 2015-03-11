package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.List;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.service.shared.data.PratilipiData;

public class SearchAccessorGaeImpl
		extends com.claymus.data.access.SearchAccessorGaeImpl
		implements SearchAccessor {

	
	public SearchAccessorGaeImpl( String indexName ) {
		super( indexName );
	}

	
	@Override
	public DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		SortOptions sortOptions = SortOptions.newBuilder()
				.addSortExpression( SortExpression.newBuilder()
						.setExpression( "relevance" )
						.setDirection( SortExpression.SortDirection.DESCENDING )
						.setDefaultValueNumeric( -999999999 ) )
				.setLimit( 10000 )
				.build();

		
		String searchQuery = pratilipiFilter.getType() == null
				? "docType:Pratilipi"
				: "docType:Pratilipi-" + pratilipiFilter.getType();

		if( pratilipiFilter.getLanguageId() != null )
			searchQuery = searchQuery + " AND language:" + pratilipiFilter.getLanguageId();

		if( pratilipiFilter.getAuthorId() != null )
			searchQuery = searchQuery + " AND author:" + pratilipiFilter.getAuthorId();

		
		Results<ScoredDocument> result = search( searchQuery, sortOptions, cursorStr, resultCount, "docId" );
		
		List<Long> pratilipiIdList = new ArrayList<>( result.getNumberReturned() ); 
		for( ScoredDocument document : result )
			pratilipiIdList.add( Long.parseLong( document.getFields( "docId" ).iterator().next().getAtom() ) );
		
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

				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitle() ) )
				.addField( Field.newBuilder().setName( "title" ).setText( pratilipiData.getTitleEn() ) )

				.addField( Field.newBuilder().setName( "language" ).setAtom( pratilipiData.getLanguageId().toString() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguageData().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguageData().getNameEn() ) )
				
				.addField( Field.newBuilder().setName( "summary" ).setHTML( pratilipiData.getSummary() ) )
				
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getName() ) )
				.addField( Field.newBuilder().setName( "keyword" ).setAtom( pratilipiData.getType().getNamePlural() ) )
				
				.addField( Field.newBuilder().setName( "relevance" ).setNumber( pratilipiData.getRelevance() ) );
		
		if( pratilipiData.getAuthorId() != null )
			docBuilder.addField( Field.newBuilder().setName( "author" ).setAtom( pratilipiData.getAuthorId().toString() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthorData().getFullName() ) )
					.addField( Field.newBuilder().setName( "author" ).setText( pratilipiData.getAuthorData().getFullNameEn() ) );

		for( Long genreId : pratilipiData.getGenreIdList() )
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreId.toString() ) );

		for( String genreName : pratilipiData.getGenreNameList() )
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreName ) );

		return docBuilder.build();
	}
	
}