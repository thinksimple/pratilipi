package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.data.transfer.shared.PratilipiData;

public class SearchAccessorGaeImpl
		extends com.claymus.data.access.SearchAccessorGaeImpl
		implements SearchAccessor {

	
	private static final Logger logger =
			Logger.getLogger( SearchAccessorGaeImpl.class.getName() );

	
	public SearchAccessorGaeImpl( String indexName ) {
		super( indexName );
	}

	
	@Override
	public DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		SortOptions.Builder sortOptionsBuilder = SortOptions.newBuilder();

		if( pratilipiFilter.getOrderByReadCount() != null ) {
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( "readCount" )
					.setDirection( pratilipiFilter.getOrderByReadCount()
							? SortExpression.SortDirection.ASCENDING
							: SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0 ) );

		} else {
			sortOptionsBuilder.addSortExpression( SortExpression.newBuilder()
					.setExpression( "relevance" )
					.setDirection( SortExpression.SortDirection.DESCENDING )
					.setDefaultValueNumeric( 0 ) );
		}
		
		SortOptions sortOptions = sortOptionsBuilder.setLimit( 10000 ).build();

		
		String searchQuery = pratilipiFilter.getType() == null
				? "docType:Pratilipi"
				: "docType:Pratilipi-" + pratilipiFilter.getType().getName();

		if( pratilipiFilter.getLanguageId() != null )
			searchQuery = searchQuery + " AND language:" + pratilipiFilter.getLanguageId();

		if( pratilipiFilter.getAuthorId() != null )
			searchQuery = searchQuery + " AND author:" + pratilipiFilter.getAuthorId();


		logger.log( Level.INFO, searchQuery );

		
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

	@Override
	public void indexAuthorData( AuthorData authorData ) throws UnexpectedServerException {
		indexDocument( createDocument( authorData ) );
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
				.addField( Field.newBuilder().setName( "language" ).setAtom( pratilipiData.getLanguageId().toString() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getName() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				.addField( Field.newBuilder().setName( "language" ).setText( pratilipiData.getLanguage().getNameEn() ) )
				
				.addField( Field.newBuilder().setName( "summary" ).setHTML( pratilipiData.getSummary() ) )
				
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
/* TODO: Index Pratilipi genres
		for( Long genreId : pratilipiData.getGenreIdList() )
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreId.toString() ) );

		for( String genreName : pratilipiData.getGenreNameList() ) {
			// 4x weightage to Genre
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreName ) );
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreName ) );
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreName ) );
			docBuilder.addField( Field.newBuilder().setName( "genre" ).setAtom( genreName ) );
		}
*/		
		return docBuilder.build();
	}
	
	private Document createDocument( AuthorData authorData ) {
		
		String docId = "AuthorData:" + authorData.getId();
		
		return Document.newBuilder()
				.setId( docId )
				.addField( Field.newBuilder().setName( "docId" ).setAtom( authorData.getId().toString() ) )
				.addField( Field.newBuilder().setName( "docType" ).setAtom( "Author" ) )

				 // 4x weightage to Language
				.addField( Field.newBuilder().setName( "language" ).setAtom( authorData.getLanguageId().toString() ) )
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
