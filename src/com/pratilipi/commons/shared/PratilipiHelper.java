package com.pratilipi.commons.shared;

import java.util.regex.Pattern;

import com.claymus.commons.server.ClaymusHelper;

public class PratilipiHelper {

	public static final Pattern REGEX_PAGE_BREAK = Pattern.compile(
			"(<hr\\s+style=\"page-break-(before|after).+?>)"
			+ "|"
			+ "(<div\\s+style=\"page-break-(before|after).+?>(.+?)</div>)" );
	
	
	public static final String URL_RESOURCE = ClaymusHelper.URL_RESOURCE;

	
	public static final String URL_AUTHOR_PAGE = "/author/";

	public static final String URL_LANGUAGE_PAGE = "/language/";

	public static final String URL_GENRE_PAGE = "/genre/";

	
	// URL to Pratilipi pages
	public static final String URL_BOOK_PAGE    = "/book/";
	public static final String URL_POEM_PAGE    = "/poem/";
	public static final String URL_STORY_PAGE   = "/story/";
	public static final String URL_ARTICLE_PAGE = "/article/";

	// URL to Pratilipi covers
	public static final String URL_BOOK_COVER    = "/resource.book-cover/original/";
	public static final String URL_POEM_COVER    = "/resource.poem-cover/original/";
	public static final String URL_STORY_COVER   = "/resource.story-cover/original/";
	public static final String URL_ARTICLE_COVER = "/resource.article-cover/original/";
	
	// URL to Pratilipi readers
	public static final String URL_BOOK_READER    = "/read/book/";
	public static final String URL_POEM_READER    = "/read/poem/";
	public static final String URL_STORY_READER   = "/read/story/";
	public static final String URL_ARTICLE_READER = "/read/article/";

	
	// Pratilipi Content (Pratilipi Format)
	public static final String RESOURCE_BOOK_CONTENT    = "book-content/pratilipi/";
	public static final String RESOURCE_POEM_CONTENT    = "poem-content/pratilipi/";
	public static final String RESOURCE_STORY_CONTENT   = "story-content/pratilipi/";
	public static final String RESOURCE_ARTICLE_CONTENT = "article-content/pratilipi/";
	
	// Pratilipi Content (Word Format)
	public static final String RESOURCE_BOOK_CONTENT_WORD    = "book-content/word/";
	public static final String RESOURCE_POEM_CONTENT_WORD    = "poem-content/word/";
	public static final String RESOURCE_STORY_CONTENT_WORD   = "story-content/word/";
	public static final String RESOURCE_ARTICLE_CONTENT_WORD = "article-content/word/";
	
	// Pratilipi Content (JPEG Format)
	public static final String RESOURCE_BOOK_CONTENT_JPEG    = "book-content/jpeg/";
	public static final String RESOURCE_POEM_CONTENT_JPEG    = "poem-content/jpeg/";
	public static final String RESOURCE_STORY_CONTENT_JPEG   = "story-content/jpeg/";
	public static final String RESOURCE_ARTICLE_CONTENT_JPEG = "article-content/jpeg/";

	
	// URL to Pratilipi Content (Pratilipi Format)
	public static final String URL_BOOK_CONTENT    = URL_RESOURCE + "book-content/pratilipi/";
	public static final String URL_POEM_CONTENT    = URL_RESOURCE + "poem-content/pratilipi/";
	public static final String URL_STORY_CONTENT   = URL_RESOURCE + "story-content/pratilipi/";
	public static final String URL_ARTICLE_CONTENT = URL_RESOURCE + "article-content/pratilipi/";
	
	// URL to Pratilipi Content (Word Format)
	public static final String URL_BOOK_CONTENT_WORD    = URL_RESOURCE + "book-content/word/";
	public static final String URL_POEM_CONTENT_WORD    = URL_RESOURCE + "poem-content/word/";
	public static final String URL_STORY_CONTENT_WORD   = URL_RESOURCE + "story-content/word/";
	public static final String URL_ARTICLE_CONTENT_WORD = URL_RESOURCE + "article-content/word/";
	
	// URL to Pratilipi Content (JPEG Format)
	public static final String URL_BOOK_CONTENT_JPEG    = URL_RESOURCE + "book-content/jpeg/";
	public static final String URL_POEM_CONTENT_JPEG    = URL_RESOURCE + "poem-content/jpeg/";
	public static final String URL_STORY_CONTENT_JPEG   = URL_RESOURCE + "story-content/jpeg/";
	public static final String URL_ARTICLE_CONTENT_JPEG = URL_RESOURCE + "article-content/jpeg/";
	
}
