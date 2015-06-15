package com.pratilipi.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;

public class DataAccessorMockImpl implements DataAccessor {

	private static final List<Page> pageTable = new LinkedList<>();
	private static final List<Pratilipi> pratilipiTable = new LinkedList<>();
	
	
	static {
		
		// AUTHOR Table
		
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
		
		
		// PRATILIPI Table
		
		Pratilipi hiPratilipi_1 = new PratilipiEntity( 1L );
		hiPratilipi_1.setTitle( "बुक टाइटल १" );
		hiPratilipi_1.setTitleEn( "Book Title 1" );
		hiPratilipi_1.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_1.setPublicationYear( 2009L);
		hiPratilipi_1.setReviewCount( 10L );
		hiPratilipi_1.setRatingCount( 10L );
		hiPratilipi_1.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_2 = new PratilipiEntity( 2L );
		hiPratilipi_2.setTitle( "बुक टाइटल २" );
		hiPratilipi_2.setTitleEn( "Book Title 2" );
		hiPratilipi_2.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_2.setPublicationYear( 2010L);
		hiPratilipi_2.setReviewCount( 10L );
		hiPratilipi_2.setRatingCount( 10L );
		hiPratilipi_2.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_3 = new PratilipiEntity( 3L );
		hiPratilipi_3.setTitle( "बुक टाइटल ३" );
		hiPratilipi_3.setTitleEn( "Book Title 3" );
		hiPratilipi_3.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_3.setPublicationYear( 2011L);
		hiPratilipi_3.setReviewCount( 10L );
		hiPratilipi_3.setRatingCount( 10L );
		hiPratilipi_3.setTotalRating( 35L );
		
		Pratilipi guPratilipi_1 = new PratilipiEntity( 4L );
		guPratilipi_1.setTitle( "બૂક તીટલે 1" );
		guPratilipi_1.setTitleEn( "Book Title 1" );
		guPratilipi_1.setAuthorId( guAuthor_1.getId() );
		guPratilipi_1.setPublicationYear( 2012L);
		guPratilipi_1.setReviewCount( 10L );
		guPratilipi_1.setRatingCount( 10L );
		guPratilipi_1.setTotalRating( 35L );
		
		Pratilipi guPratilipi_2 = new PratilipiEntity( 5L );
		guPratilipi_2.setTitle( "બૂક તીટલે 2" );
		guPratilipi_2.setTitleEn( "Book Title 2" );
		guPratilipi_2.setAuthorId( guAuthor_1.getId() );
		guPratilipi_2.setPublicationYear( 2013L);
		guPratilipi_2.setReviewCount( 10L );
		guPratilipi_2.setRatingCount( 10L );
		guPratilipi_2.setTotalRating( 35L );
		
		Pratilipi guPratilipi_3 = new PratilipiEntity( 6L );
		guPratilipi_3.setTitle( "બૂક તીટલે 3" );
		guPratilipi_3.setTitleEn( "Book Title 3" );
		guPratilipi_3.setAuthorId( guAuthor_1.getId() );
		guPratilipi_3.setPublicationYear( 2014L);
		guPratilipi_3.setReviewCount( 10L );
		guPratilipi_3.setRatingCount( 10L );
		guPratilipi_3.setTotalRating( 35L );
		
		Pratilipi taPratilipi_1 = new PratilipiEntity( 7L );
		taPratilipi_1.setTitle( "புக் டைட்டில் 1" );
		taPratilipi_1.setTitleEn( "Book Title 1" );
		taPratilipi_1.setAuthorId( taAuthor_1.getId() );
		taPratilipi_1.setPublicationYear( 2015L);
		taPratilipi_1.setReviewCount( 10L );
		taPratilipi_1.setRatingCount( 10L );
		taPratilipi_1.setTotalRating( 35L );
		
		Pratilipi taPratilipi_2 = new PratilipiEntity( 8L );
		taPratilipi_2.setTitle( "புக் டைட்டில் 2" );
		taPratilipi_2.setTitleEn( "Book Title 2" );
		taPratilipi_2.setAuthorId( taAuthor_1.getId() );
		taPratilipi_2.setPublicationYear( 2009L);
		taPratilipi_2.setReviewCount( 10L );
		taPratilipi_2.setRatingCount( 10L );
		taPratilipi_2.setTotalRating( 35L );
		
		Pratilipi taPratilipi_3 = new PratilipiEntity( 9L );
		taPratilipi_3.setTitle( "புக் டைட்டில் 3" );
		taPratilipi_3.setTitleEn( "Book Title 3" );
		taPratilipi_3.setAuthorId( taAuthor_1.getId() );
		taPratilipi_3.setPublicationYear( 2010L);
		taPratilipi_3.setReviewCount( 10L );
		taPratilipi_3.setRatingCount( 10L );
		taPratilipi_3.setTotalRating( 35L );
		
		pratilipiTable.add( hiPratilipi_1 );
		pratilipiTable.add( hiPratilipi_2 );
		pratilipiTable.add( hiPratilipi_3 );
		
		pratilipiTable.add( guPratilipi_1 );
		pratilipiTable.add( guPratilipi_2 );
		pratilipiTable.add( guPratilipi_3 );
		
		pratilipiTable.add( taPratilipi_1 );
		pratilipiTable.add( taPratilipi_2 );
		pratilipiTable.add( taPratilipi_3 );


		// PAGE Table
		
		Page hiPratilipi_1_Page = new PageEntity( 1L );
		hiPratilipi_1_Page.setType( PageType.PRATILIPI );
		hiPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_1.getId() );
		hiPratilipi_1_Page.setUriAlias( "/hindi-author/book-title-1" );
		hiPratilipi_1_Page.setPrimaryContentId( hiPratilipi_1.getId() );

		Page hiPratilipi_2_Page = new PageEntity( 2L );
		hiPratilipi_2_Page.setType( PageType.PRATILIPI );
		hiPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_2.getId() );
		hiPratilipi_2_Page.setUriAlias( "/hindi-author/book-title-2" );
		hiPratilipi_2_Page.setPrimaryContentId( hiPratilipi_2.getId() );

		Page hiPratilipi_3_Page = new PageEntity( 3L );
		hiPratilipi_3_Page.setType( PageType.PRATILIPI );
		hiPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_3.getId() );
		hiPratilipi_3_Page.setUriAlias( "/hindi-author/book-title-3" );
		hiPratilipi_3_Page.setPrimaryContentId( hiPratilipi_1.getId() );

		Page guPratilipi_1_Page = new PageEntity( 4L );
		guPratilipi_1_Page.setType( PageType.PRATILIPI );
		guPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_1.getId() );
		guPratilipi_1_Page.setUriAlias( "/gujarati-author/book-title-1" );
		guPratilipi_1_Page.setPrimaryContentId( guPratilipi_1.getId() );

		Page guPratilipi_2_Page = new PageEntity( 5L );
		guPratilipi_2_Page.setType( PageType.PRATILIPI );
		guPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_2.getId() );
		guPratilipi_2_Page.setUriAlias( "/gujarati-author/book-title-2" );
		guPratilipi_2_Page.setPrimaryContentId( guPratilipi_2.getId() );

		Page guPratilipi_3_Page = new PageEntity( 6L );
		guPratilipi_3_Page.setType( PageType.PRATILIPI );
		guPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_3.getId() );
		guPratilipi_3_Page.setUriAlias( "/gujarati-author/book-title-3" );
		guPratilipi_3_Page.setPrimaryContentId( guPratilipi_3.getId() );

		Page taPratilipi_1_Page = new PageEntity( 7L );
		taPratilipi_1_Page.setType( PageType.PRATILIPI );
		taPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_1.getId() );
		taPratilipi_1_Page.setUriAlias( "/tamil-author/book-title-1" );
		taPratilipi_1_Page.setPrimaryContentId( taPratilipi_1.getId() );
	
		Page taPratilipi_2_Page = new PageEntity( 8L );
		taPratilipi_2_Page.setType( PageType.PRATILIPI );
		taPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_2.getId() );
		taPratilipi_2_Page.setUriAlias( "/tamil-author/book-title-2" );
		taPratilipi_2_Page.setPrimaryContentId( taPratilipi_2.getId() );

		Page taPratilipi_3_Page = new PageEntity( 9L );
		taPratilipi_3_Page.setType( PageType.PRATILIPI );
		taPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_3.getId() );
		taPratilipi_3_Page.setUriAlias( "/tamil-author/book-title-3" );
		taPratilipi_3_Page.setPrimaryContentId( taPratilipi_3.getId() );
		
		pageTable.add( hiPratilipi_1_Page );
		pageTable.add( hiPratilipi_2_Page );
		pageTable.add( hiPratilipi_3_Page );

		pageTable.add( guPratilipi_1_Page );
		pageTable.add( guPratilipi_2_Page );
		pageTable.add( guPratilipi_3_Page );

		pageTable.add( taPratilipi_1_Page );
		pageTable.add( taPratilipi_2_Page );
		pageTable.add( taPratilipi_3_Page );
	}
	
	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		for( Page page : pageTable )
			if( page.getId().equals( id ) )
				return page;

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getPage( String uri ) {
		for( Page page : pageTable )
			if( page.getUri().equals( uri ) )
				return page;

		for( Page page : pageTable )
			if( page.getUriAlias().equals( uri ) )
				return page;

		return null;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		for( Page page : pageTable )
			if( page.getType().equals( pageType ) && page.getPrimaryContentId().equals( primaryContentId ) )
				return page;

		return null;
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		pageTable.add( page );
		return page;
	}
	
	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		for( Pratilipi pratilipi : pratilipiTable )
			if( pratilipi.getId().equals( id) )
				return pratilipi;
		
		return null;
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
		pratilipiTable.add( pratilipi );
		return pratilipi;
	}
	
	
	// Destroy

	@Override
	public void destroy() {}

}
