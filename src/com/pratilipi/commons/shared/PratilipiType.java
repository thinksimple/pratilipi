package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.ArticleData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.PoemData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.StoryData;

public enum PratilipiType implements IsSerializable {
	
	BOOK	( "Book",		PratilipiHelper.URL_BOOK_PAGE,		PratilipiHelper.URL_BOOK_COVER ),
	POEM	( "Poem",		PratilipiHelper.URL_POEM_PAGE,		PratilipiHelper.URL_POEM_COVER ),
	STORY	( "Story",		PratilipiHelper.URL_STORY_PAGE,		PratilipiHelper.URL_STORY_COVER ),
	ARTICLE	( "Article",	PratilipiHelper.URL_ARTICLE_PAGE,	PratilipiHelper.URL_ARTICLE_COVER );
	
	
	private String name;
	private String pageUrl;
	private String coverImageUrl;
	
	
	private PratilipiType( String name, String pageUrl, String coverImageUrl ) {
		this.name = name;
		this.pageUrl = pageUrl;
		this.coverImageUrl = coverImageUrl;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}
	
	public PratilipiData newPratilipiData() {
		PratilipiData pratilipiData = null;
		
		switch( this ) {
			case BOOK:
				pratilipiData = new BookData();
				pratilipiData.setType( this );
				break;
			case POEM:
				pratilipiData = new PoemData();
				pratilipiData.setType( this );
				break;
			case STORY:
				pratilipiData = new StoryData();
				pratilipiData.setType( this );
				break;
			case ARTICLE:
				pratilipiData = new ArticleData();
				pratilipiData.setType( this );
				break;
		}
		
		return pratilipiData;
	}

}
