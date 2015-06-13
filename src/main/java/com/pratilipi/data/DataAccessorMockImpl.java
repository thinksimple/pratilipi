package com.pratilipi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.Language;
import com.pratilipi.data.gae.AuthorEntity;
import com.pratilipi.data.gae.PratilipiEntity;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;

public class DataAccessorMockImpl implements DataAccessor {

	private static final Map<Long, Pratilipi> pratilipiTable = new HashMap<>();
	
	static {
		Author hiAuthor_1 = new AuthorEntity( 1L );
		hiAuthor_1.setFirstName( "Hindi" );
		hiAuthor_1.setLastName( "Author" );
		hiAuthor_1.setLanguage( Language.HINDI );

		Author guAuthor_1 = new AuthorEntity( 2L );
		guAuthor_1.setFirstName( "Gujarati" );
		guAuthor_1.setLastName( "Author" );
		guAuthor_1.setLanguage( Language.GUJARATI );

		Author taAuthor_1 = new AuthorEntity( 3L );
		taAuthor_1.setFirstName( "Tamil" );
		taAuthor_1.setLastName( "Author" );
		taAuthor_1.setLanguage( Language.TAMIL );


		Pratilipi hiPratilipi_1 = new PratilipiEntity( 1L );
		hiPratilipi_1.setTitle( "Book Title" );
		hiPratilipi_1.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_1.setRatingCount( 10L );
		hiPratilipi_1.setTotalRating( 35L );
		
		
		pratilipiTable.put( hiPratilipi_1.getId(), hiPratilipi_1 );
	}
	
	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		return pratilipiTable.get( id );
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			pratilipiList.add( getPratilipi( id ) );
		return pratilipiList;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		pratilipiTable.put( pratilipi.getId(), pratilipi );
		return pratilipi;
	}
	
	
	// Destroy

	@Override
	public void destroy() {}

}
