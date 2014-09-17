package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PratilipiType implements IsSerializable {
	
	BOOK( "Book",
			PratilipiHelper.URL_BOOK_PAGE,
			PratilipiHelper.RESOURCE_BOOK_COVER,
			PratilipiHelper.URL_BOOK_COVER,
			PratilipiHelper.URL_BOOK_READER,
			PratilipiHelper.RESOURCE_BOOK_CONTENT,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_HTML,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_WORD,
			PratilipiHelper.RESOURCE_BOOK_CONTENT_IMAGE,
			PratilipiHelper.URL_BOOK_CONTENT,
			PratilipiHelper.URL_BOOK_CONTENT_HTML,
			PratilipiHelper.URL_BOOK_CONTENT_WORD,
			PratilipiHelper.URL_BOOK_CONTENT_IMAGE ),
	POEM( "Poem",
			PratilipiHelper.URL_POEM_PAGE,
			PratilipiHelper.RESOURCE_POEM_COVER,
			PratilipiHelper.URL_POEM_COVER,
			PratilipiHelper.URL_POEM_READER,
			PratilipiHelper.RESOURCE_POEM_CONTENT,
			PratilipiHelper.RESOURCE_POEM_CONTENT_HTML,
			PratilipiHelper.RESOURCE_POEM_CONTENT_WORD,
			PratilipiHelper.RESOURCE_POEM_CONTENT_IMAGE,
			PratilipiHelper.URL_POEM_CONTENT,
			PratilipiHelper.URL_POEM_CONTENT_HTML,
			PratilipiHelper.URL_POEM_CONTENT_WORD,
			PratilipiHelper.URL_POEM_CONTENT_IMAGE ),
	STORY( "Story",
			PratilipiHelper.URL_STORY_PAGE,
			PratilipiHelper.RESOURCE_STORY_COVER,
			PratilipiHelper.URL_STORY_COVER,
			PratilipiHelper.URL_STORY_READER,
			PratilipiHelper.RESOURCE_STORY_CONTENT,
			PratilipiHelper.RESOURCE_STORY_CONTENT_HTML,
			PratilipiHelper.RESOURCE_STORY_CONTENT_WORD,
			PratilipiHelper.RESOURCE_STORY_CONTENT_IMAGE,
			PratilipiHelper.URL_STORY_CONTENT,
			PratilipiHelper.URL_STORY_CONTENT_HTML,
			PratilipiHelper.URL_STORY_CONTENT_WORD,
			PratilipiHelper.URL_STORY_CONTENT_IMAGE ),
	ARTICLE( "Article",
			PratilipiHelper.URL_ARTICLE_PAGE,
			PratilipiHelper.RESOURCE_ARTICLE_COVER,
			PratilipiHelper.URL_ARTICLE_COVER,
			PratilipiHelper.URL_ARTICLE_READER,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_HTML,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_WORD,
			PratilipiHelper.RESOURCE_ARTICLE_CONTENT_IMAGE,
			PratilipiHelper.URL_ARTICLE_CONTENT,
			PratilipiHelper.URL_ARTICLE_CONTENT_HTML,
			PratilipiHelper.URL_ARTICLE_CONTENT_WORD,
			PratilipiHelper.URL_ARTICLE_CONTENT_IMAGE );
	
	
	private String name;
	private String pageUrl;
	private String coverImageResource;
	private String coverImageUrl;
	private String readerPageUrl;
	
	private String contentResource;
	private String contentHtmlResource;
	private String contentWordResource;
	private String contentImageResource;
	
	private String contentUrl;
	private String contentHtmlUrl;
	private String contentWordUrl;
	private String contentImageUrl;
	
	
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
		this.contentImageResource = contentJpegResource;
		this.contentUrl = contentUrl;
		this.contentHtmlUrl = contentHtmlUrl;
		this.contentWordUrl = contentWordUrl;
		this.contentImageUrl = contentJpegUrl;
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
	
	public String getContentImageResource() {
		return contentImageResource;
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
	
	public String getContentImageUrl() {
		return contentImageUrl;
	}
	
}
