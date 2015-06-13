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
		hiPratilipi_1.setTitle( "बुक टाइटल १ " );
		hiPratilipi_1.setTitleEn( "Book title 1" );
		hiPratilipi_1.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_1.setPublicationYear( 2009L);
		hiPratilipi_1.setReviewCount( 10L );
		hiPratilipi_1.setRatingCount( 10L );
		hiPratilipi_1.setTotalRating( 35L );
		
		
		Pratilipi hiPratilipi_2 = new PratilipiEntity( 2L );
		hiPratilipi_2.setTitle( "बुक टाइटल २ " );
		hiPratilipi_2.setTitleEn( "Book title 2" );
		hiPratilipi_2.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_2.setPublicationYear( 2010L);
		hiPratilipi_2.setReviewCount( 10L );
		hiPratilipi_2.setRatingCount( 10L );
		hiPratilipi_2.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_3 = new PratilipiEntity( 3L );
		hiPratilipi_3.setTitle( "बुक टाइटल ३   " );
		hiPratilipi_3.setTitleEn( "Book title 3" );
		hiPratilipi_3.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_3.setPublicationYear( 2011L);
		hiPratilipi_3.setReviewCount( 10L );
		hiPratilipi_3.setRatingCount( 10L );
		hiPratilipi_3.setTotalRating( 35L );
		
		Pratilipi guPratilipi_1 = new PratilipiEntity( 4L );
		guPratilipi_1.setTitle( "બૂક તીટલે 1 " );
		guPratilipi_1.setTitleEn( "Book title 1" );
		guPratilipi_1.setAuthorId( guAuthor_1.getId() );
		guPratilipi_1.setPublicationYear( 2012L);
		guPratilipi_1.setReviewCount( 10L );
		guPratilipi_1.setRatingCount( 10L );
		guPratilipi_1.setTotalRating( 35L );
		
		Pratilipi guPratilipi_2 = new PratilipiEntity( 5L );
		guPratilipi_2.setTitle( "બૂક તીટલે 2 " );
		guPratilipi_2.setTitleEn( "Book title 2" );
		guPratilipi_2.setAuthorId( guAuthor_1.getId() );
		guPratilipi_2.setPublicationYear( 2013L);
		guPratilipi_2.setReviewCount( 10L );
		guPratilipi_2.setRatingCount( 10L );
		guPratilipi_2.setTotalRating( 35L );
		
		Pratilipi guPratilipi_3 = new PratilipiEntity( 6L );
		guPratilipi_3.setTitle( "બૂક તીટલે 3 " );
		guPratilipi_3.setTitleEn( "Book title 3" );
		guPratilipi_3.setAuthorId( guAuthor_1.getId() );
		guPratilipi_3.setPublicationYear( 2014L);
		guPratilipi_3.setReviewCount( 10L );
		guPratilipi_3.setRatingCount( 10L );
		guPratilipi_3.setTotalRating( 35L );
		
		Pratilipi taPratilipi_1 = new PratilipiEntity( 7L );
		taPratilipi_1.setTitle( "புக் டைட்டில் 1 " );
		taPratilipi_1.setTitleEn( "Book title 1" );
		taPratilipi_1.setAuthorId( taAuthor_1.getId() );
		taPratilipi_1.setPublicationYear( 2015L);
		taPratilipi_1.setReviewCount( 10L );
		taPratilipi_1.setRatingCount( 10L );
		taPratilipi_1.setTotalRating( 35L );
		
		Pratilipi taPratilipi_2 = new PratilipiEntity( 8L );
		taPratilipi_2.setTitle( "புக் டைட்டில் 2" );
		taPratilipi_2.setTitleEn( "Book title 2" );
		taPratilipi_2.setAuthorId( taAuthor_1.getId() );
		taPratilipi_2.setPublicationYear( 2009L);
		taPratilipi_2.setReviewCount( 10L );
		taPratilipi_2.setRatingCount( 10L );
		taPratilipi_2.setTotalRating( 35L );
		
		Pratilipi taPratilipi_3 = new PratilipiEntity( 9L );
		taPratilipi_3.setTitle( "புக் டைட்டில் 3" );
		taPratilipi_3.setTitleEn( "Book title 3" );
		taPratilipi_3.setAuthorId( taAuthor_1.getId() );
		taPratilipi_3.setPublicationYear( 2010L);
		taPratilipi_3.setReviewCount( 10L );
		taPratilipi_3.setRatingCount( 10L );
		taPratilipi_3.setTotalRating( 35L );
		
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
