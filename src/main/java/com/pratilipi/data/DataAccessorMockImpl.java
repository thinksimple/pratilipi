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
		hiPratilipi_1.setPublicationYear( 20L);
		hiPratilipi_1.setReviewCount( 10L );
		hiPratilipi_1.setRatingCount( 10L );
		hiPratilipi_1.setTotalRating( 35L );
		
		
		Pratilipi hiPratilipi_2 = new PratilipiEntity( 1L );
		hiPratilipi_2.setTitle( "बुक टाइटल २ " );
		hiPratilipi_2.setTitleEn( "Book title 2" );
		hiPratilipi_2.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_2.setPublicationYear( 20L);
		hiPratilipi_2.setReviewCount( 10L );
		hiPratilipi_2.setRatingCount( 10L );
		hiPratilipi_2.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_3 = new PratilipiEntity( 1L );
		hiPratilipi_3.setTitle( "बुक टाइटल ३   " );
		hiPratilipi_3.setTitleEn( "Book title 3" );
		hiPratilipi_3.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_3.setPublicationYear( 20L);
		hiPratilipi_3.setReviewCount( 10L );
		hiPratilipi_3.setRatingCount( 10L );
		hiPratilipi_3.setTotalRating( 35L );
		
		Pratilipi guPratilipi_101 = new PratilipiEntity( 2L );
		guPratilipi_101.setTitle( "બૂક તીટલે 1 " );
		guPratilipi_101.setTitleEn( "Book title 1" );
		guPratilipi_101.setAuthorId( guAuthor_1.getId() );
		guPratilipi_101.setPublicationYear( 20L);
		guPratilipi_101.setReviewCount( 10L );
		guPratilipi_101.setRatingCount( 10L );
		guPratilipi_101.setTotalRating( 35L );
		
		Pratilipi guPratilipi_102 = new PratilipiEntity( 2L );
		guPratilipi_102.setTitle( "બૂક તીટલે 2 " );
		guPratilipi_102.setTitleEn( "Book title 2" );
		guPratilipi_102.setAuthorId( guAuthor_1.getId() );
		guPratilipi_102.setPublicationYear( 20L);
		guPratilipi_102.setReviewCount( 10L );
		guPratilipi_102.setRatingCount( 10L );
		guPratilipi_102.setTotalRating( 35L );
		
		Pratilipi guPratilipi_103 = new PratilipiEntity( 2L );
		guPratilipi_103.setTitle( "બૂક તીટલે 3 " );
		guPratilipi_103.setTitleEn( "Book title 3" );
		guPratilipi_103.setAuthorId( guAuthor_1.getId() );
		guPratilipi_103.setPublicationYear( 20L);
		guPratilipi_103.setReviewCount( 10L );
		guPratilipi_103.setRatingCount( 10L );
		guPratilipi_103.setTotalRating( 35L );
		
		Pratilipi taPratilipi_201 = new PratilipiEntity( 3L );
		taPratilipi_201.setTitle( "புக் டைட்டில் 1 " );
		taPratilipi_201.setTitleEn( "Book title 1" );
		taPratilipi_201.setAuthorId( taAuthor_1.getId() );
		taPratilipi_201.setPublicationYear( 20L);
		taPratilipi_201.setReviewCount( 10L );
		taPratilipi_201.setRatingCount( 10L );
		taPratilipi_201.setTotalRating( 35L );
		
		Pratilipi taPratilipi_202 = new PratilipiEntity( 3L );
		taPratilipi_202.setTitle( "புக் டைட்டில் 2" );
		taPratilipi_202.setTitleEn( "Book title 2" );
		taPratilipi_202.setAuthorId( taAuthor_1.getId() );
		taPratilipi_202.setPublicationYear( 20L);
		taPratilipi_202.setReviewCount( 10L );
		taPratilipi_202.setRatingCount( 10L );
		taPratilipi_202.setTotalRating( 35L );
		
		Pratilipi taPratilipi_203 = new PratilipiEntity( 3L );
		taPratilipi_203.setTitle( "புக் டைட்டில் 3" );
		taPratilipi_203.setTitleEn( "Book title 3" );
		taPratilipi_203.setAuthorId( taAuthor_1.getId() );
		taPratilipi_203.setPublicationYear( 20L);
		taPratilipi_203.setReviewCount( 10L );
		taPratilipi_203.setRatingCount( 10L );
		taPratilipi_203.setTotalRating( 35L );
		
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
