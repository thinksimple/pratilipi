package com.pratilipi.data.mock;

import static com.pratilipi.data.mock.AuthorMock.*;
import static com.pratilipi.data.mock.PratilipiMock.*;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.gae.PageEntity;

public class PageMock {

	public static final List<Page> PAGE_TABLE = new LinkedList<>();

	public static final Page home_Page = new PageEntity( 1L );

	public static final Page hiAuthor_1_Page = new PageEntity( 101L );
	public static final Page guAuthor_1_Page = new PageEntity( 102L );
	public static final Page taAuthor_1_Page = new PageEntity( 103L );
	
	public static final Page hiPratilipi_1_Page = new PageEntity( 10101L );
	public static final Page hiPratilipi_2_Page = new PageEntity( 10102L );
	public static final Page hiPratilipi_3_Page = new PageEntity( 10103L );
	
	public static final Page guPratilipi_1_Page = new PageEntity( 10201L );
	public static final Page guPratilipi_2_Page = new PageEntity( 10202L );
	public static final Page guPratilipi_3_Page = new PageEntity( 10203L );
	
	public static final Page taPratilipi_1_Page = new PageEntity( 10301L );
	public static final Page taPratilipi_2_Page = new PageEntity( 10302L );
	public static final Page taPratilipi_3_Page = new PageEntity( 10303L );

	
	static {
		PAGE_TABLE.add( home_Page );

		PAGE_TABLE.add( hiAuthor_1_Page );
		PAGE_TABLE.add( guAuthor_1_Page );
		PAGE_TABLE.add( taAuthor_1_Page );

		PAGE_TABLE.add( hiPratilipi_1_Page );
		PAGE_TABLE.add( hiPratilipi_2_Page );
		PAGE_TABLE.add( hiPratilipi_3_Page );

		PAGE_TABLE.add( guPratilipi_1_Page );
		PAGE_TABLE.add( guPratilipi_2_Page );
		PAGE_TABLE.add( guPratilipi_3_Page );

		PAGE_TABLE.add( taPratilipi_1_Page );
		PAGE_TABLE.add( taPratilipi_2_Page );
		PAGE_TABLE.add( taPratilipi_3_Page );

		
		home_Page.setType( PageType.GENERIC );
		home_Page.setUri( null );
		home_Page.setUriAlias( "/" );
		home_Page.setPrimaryContentId( null );

		
		hiAuthor_1_Page.setType( PageType.AUTHOR );
		hiAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + hiAuthor_1.getId() );
		hiAuthor_1_Page.setUriAlias( "/hindi-author-1" );
		hiAuthor_1_Page.setPrimaryContentId( hiAuthor_1.getId() );

		guAuthor_1_Page.setType( PageType.AUTHOR );
		guAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + guAuthor_1.getId() );
		guAuthor_1_Page.setUriAlias( "/gujarati-author-1" );
		guAuthor_1_Page.setPrimaryContentId( guAuthor_1.getId() );

		taAuthor_1_Page.setType( PageType.AUTHOR );
		taAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + taAuthor_1.getId() );
		taAuthor_1_Page.setUriAlias( "/tamil-author-1" );
		taAuthor_1_Page.setPrimaryContentId( taAuthor_1.getId() );

		
		hiPratilipi_1_Page.setType( PageType.PRATILIPI );
		hiPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_1.getId() );
		hiPratilipi_1_Page.setUriAlias( "/hindi-author-1/book-title-1" );
		hiPratilipi_1_Page.setPrimaryContentId( hiPratilipi_1.getId() );

		hiPratilipi_2_Page.setType( PageType.PRATILIPI );
		hiPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_2.getId() );
		hiPratilipi_2_Page.setUriAlias( "/hindi-author-1/book-title-2" );
		hiPratilipi_2_Page.setPrimaryContentId( hiPratilipi_2.getId() );

		hiPratilipi_3_Page.setType( PageType.PRATILIPI );
		hiPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_3.getId() );
		hiPratilipi_3_Page.setUriAlias( "/hindi-author-1/book-title-3" );
		hiPratilipi_3_Page.setPrimaryContentId( hiPratilipi_3.getId() );

		guPratilipi_1_Page.setType( PageType.PRATILIPI );
		guPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_1.getId() );
		guPratilipi_1_Page.setUriAlias( "/gujarati-author-1/book-title-1" );
		guPratilipi_1_Page.setPrimaryContentId( guPratilipi_1.getId() );

		guPratilipi_2_Page.setType( PageType.PRATILIPI );
		guPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_2.getId() );
		guPratilipi_2_Page.setUriAlias( "/gujarati-author-1/book-title-2" );
		guPratilipi_2_Page.setPrimaryContentId( guPratilipi_2.getId() );

		guPratilipi_3_Page.setType( PageType.PRATILIPI );
		guPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_3.getId() );
		guPratilipi_3_Page.setUriAlias( "/gujarati-author-1/book-title-3" );
		guPratilipi_3_Page.setPrimaryContentId( guPratilipi_3.getId() );

		taPratilipi_1_Page.setType( PageType.PRATILIPI );
		taPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_1.getId() );
		taPratilipi_1_Page.setUriAlias( "/tamil-author-1/book-title-1" );
		taPratilipi_1_Page.setPrimaryContentId( taPratilipi_1.getId() );
	
		taPratilipi_2_Page.setType( PageType.PRATILIPI );
		taPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_2.getId() );
		taPratilipi_2_Page.setUriAlias( "/tamil-author-1/book-title-2" );
		taPratilipi_2_Page.setPrimaryContentId( taPratilipi_2.getId() );

		taPratilipi_3_Page.setType( PageType.PRATILIPI );
		taPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_3.getId() );
		taPratilipi_3_Page.setUriAlias( "/tamil-author-1/book-title-3" );
		taPratilipi_3_Page.setPrimaryContentId( taPratilipi_3.getId() );
	}
	
}
