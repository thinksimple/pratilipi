package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.ArticleData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.PoemData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.StoryData;

public enum PratilipiType implements IsSerializable {
	
	BOOK( "Book",
			PratilipiHelper.URL_BOOK_PAGE,
			PratilipiHelper.URL_BOOK_COVER,
			PratilipiHelper.URL_BOOK_READER,
			PratilipiHelper.URL_BOOK_CONTENT_JPEG ),
	POEM( "Poem",
			PratilipiHelper.URL_POEM_PAGE,
			PratilipiHelper.URL_POEM_COVER,
			PratilipiHelper.URL_POEM_READER,
			PratilipiHelper.URL_POEM_CONTENT_JPEG ),
	STORY( "Story",
			PratilipiHelper.URL_STORY_PAGE,
			PratilipiHelper.URL_STORY_COVER,
			PratilipiHelper.URL_STORY_READER,
			PratilipiHelper.URL_STORY_CONTENT_JPEG ),
	ARTICLE( "Article",
			PratilipiHelper.URL_ARTICLE_PAGE,
			PratilipiHelper.URL_ARTICLE_COVER,
			PratilipiHelper.URL_ARTICLE_READER,
			PratilipiHelper.URL_ARTICLE_CONTENT_JPEG );
	
	
	private String name;
	private String pageUrl;
	private String coverImageUrl;
	private String readerPageUrl;
	private String contentJpegUrl;
	
	private PratilipiType(
			String name, String pageUrl,
			String coverImageUrl, String readerPageUrl,
			String contentJpegUrl ) {
		this.name = name;
		this.pageUrl = pageUrl;
		this.coverImageUrl = coverImageUrl;
		this.readerPageUrl = readerPageUrl;
		this.contentJpegUrl = contentJpegUrl;
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
	
	public String getReaderPageUrl() {
		return readerPageUrl;
	}
	
	public String getContentJpegUrl() {
		return contentJpegUrl;
	}
	
	public PratilipiData newPratilipiData() {
		PratilipiData pratilipiData = null;
		
		switch( this ) {
			case BOOK:
				pratilipiData = new BookData();
				pratilipiData.setType( BOOK );
				break;
			case POEM:
				pratilipiData = new PoemData();
				pratilipiData.setType( POEM );
				break;
			case STORY:
				pratilipiData = new StoryData();
				pratilipiData.setType( STORY );
				break;
			case ARTICLE:
				pratilipiData = new ArticleData();
				pratilipiData.setType( ARTICLE );
				break;
		}
		
		return pratilipiData;
	}

}
