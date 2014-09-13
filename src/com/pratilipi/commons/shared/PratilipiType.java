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
			PratilipiHelper.RESOURCE_BOOK_COVER,
			PratilipiHelper.URL_BOOK_COVER,
			PratilipiHelper.URL_BOOK_READER,
			PratilipiHelper.RESOURCE_BOOK_CONTENT,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_HTML,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_WORD,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_JPEG,
			PratilipiHelper.URL_BOOK_CONTENT,
			PratilipiHelper.URL_BOOK_CONTENT_HTML,
			PratilipiHelper.URL_BOOK_CONTENT_WORD,
			PratilipiHelper.URL_BOOK_CONTENT_JPEG ),
	POEM( "Poem",
			PratilipiHelper.URL_POEM_PAGE,
			PratilipiHelper.RESOURCE_POEM_COVER,
			PratilipiHelper.URL_POEM_COVER,
			PratilipiHelper.URL_POEM_READER,
			PratilipiHelper.RESOURCE_POEM_CONTENT,
			PratilipiHelper.RESOURCE_POEM_CONTENT_HTML,
			PratilipiHelper.RESOURCE_POEM_CONTENT_WORD,
			PratilipiHelper.RESOURCE_POEM_CONTENT_JPEG,
			PratilipiHelper.URL_POEM_CONTENT,
			PratilipiHelper.URL_POEM_CONTENT_HTML,
			PratilipiHelper.URL_POEM_CONTENT_WORD,
			PratilipiHelper.URL_POEM_CONTENT_JPEG ),
	STORY( "Story",
			PratilipiHelper.URL_STORY_PAGE,
			PratilipiHelper.RESOURCE_STORY_COVER,
			PratilipiHelper.URL_STORY_COVER,
			PratilipiHelper.URL_STORY_READER,
			PratilipiHelper.RESOURCE_STORY_CONTENT,
			PratilipiHelper.RESOURCE_STORY_CONTENT_HTML,
			PratilipiHelper.RESOURCE_STORY_CONTENT_WORD,
			PratilipiHelper.RESOURCE_STORY_CONTENT_JPEG,
			PratilipiHelper.URL_STORY_CONTENT,
			PratilipiHelper.URL_STORY_CONTENT_HTML,
			PratilipiHelper.URL_STORY_CONTENT_WORD,
			PratilipiHelper.URL_STORY_CONTENT_JPEG ),
	ARTICLE( "Article",
			PratilipiHelper.URL_ARTICLE_PAGE,
			PratilipiHelper.RESOURCE_ARTICLE_COVER,
			PratilipiHelper.URL_ARTICLE_COVER,
			PratilipiHelper.URL_ARTICLE_READER,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_HTML,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_WORD,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_JPEG,
			PratilipiHelper.URL_ARTICLE_CONTENT,
			PratilipiHelper.URL_ARTICLE_CONTENT_HTML,
			PratilipiHelper.URL_ARTICLE_CONTENT_WORD,
			PratilipiHelper.URL_ARTICLE_CONTENT_JPEG );
	
	
	private String name;
	private String pageUrl;
	private String coverImageResource;
	private String coverImageUrl;
	private String readerPageUrl;
	
	private String contentResource;
	private String contentHtmlResource;
	private String contentWordResource;
	private String contentJpegResource;
	
	private String contentUrl;
	private String contentHtmlUrl;
	private String contentWordUrl;
	private String contentJpegUrl;
	
	
	private PratilipiType(
			String name, String pageUrl,
			String coverImageResource, String coverImageUrl, String readerPageUrl,
			String contentResource, String contentHtmlResource,
			String contentWordResource, String contentJpegResource,
			String contentUrl, String contentHtmlUrl,
			String contentWordUrl, String contentJpegUrl ) {
		this.name = name;
		this.pageUrl = pageUrl;
		this.coverImageResource = coverImageResource;
		this.coverImageUrl = coverImageUrl;
		this.readerPageUrl = readerPageUrl;
		this.contentResource = contentResource;
		this.contentHtmlResource = contentHtmlResource;
		this.contentWordResource = contentWordResource;
		this.contentJpegResource = contentJpegResource;
		this.contentUrl = contentUrl;
		this.contentHtmlUrl = contentHtmlUrl;
		this.contentWordUrl = contentWordUrl;
		this.contentJpegUrl = contentJpegUrl;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public String getCoverImageResource() {
		return coverImageResource;
	}
	
	public String getCoverImageUrl() {
		return coverImageUrl;
	}
	
	public String getReaderPageUrl() {
		return readerPageUrl;
	}
	
	
	public String getContentResource() {
		return contentResource;
	}
	
	public String getContentHtmlResource() {
		return contentHtmlResource;
	}
	
	public String getContentWordResource() {
		return contentWordResource;
	}
	
	public String getContentJpegResource() {
		return contentJpegResource;
	}
	
	
	public String getContentUrl() {
		return contentUrl;
	}
	
	public String getContentHtmlUrl() {
		return contentHtmlUrl;
	}
	
	public String getContentWordUrl() {
		return contentWordUrl;
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
