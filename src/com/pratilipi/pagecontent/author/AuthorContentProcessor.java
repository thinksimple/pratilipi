package com.pratilipi.pagecontent.author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;

public class AuthorContentProcessor extends PageContentProcessor<AuthorContent> {

	public static final String ACCESS_ID_AUTHOR_ADD = "author_add";
	
	@Override
	public String getHtml( AuthorContent authorContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		Long authorId = authorContent.getAuthorId();
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		boolean showAddOption =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_ADD, false );

		
		// Fetching Author list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		String authorImage = PratilipiHelper.URL_AUTHOR_IMAGE + authorId;
		
		DataListCursorTuple<Pratilipi> bookList = dataAccessor.getPratilipiListByAuthor( 
								authorId, PratilipiType.BOOK, null, 100 );
		List<Pratilipi> bookDataList = bookList.getDataList();
		
		Map<String, String> bookCoverMap = new HashMap<>();
		Map<String, String> bookUrlMap = new HashMap<>();
		for( Pratilipi book : bookDataList ){
			if( bookCoverMap.get( book.getId() ) == null ){
				bookCoverMap.put( book.getId().toString(),
								  PratilipiHelper.URL_BOOK_COVER + book.getId() );
			}
			if( bookUrlMap.get( book.getId() ) == null ){
				bookUrlMap.put( book.getId().toString(), 
								PratilipiHelper.URL_BOOK_PAGE + book.getId() );
			}
		}
		
		DataListCursorTuple<Pratilipi> poemList = dataAccessor.getPratilipiListByAuthor( 
				authorId, PratilipiType.POEM, null, 100 );
		List<Pratilipi> poemDataList = poemList.getDataList();
		Map<String, String> poemCoverMap = new HashMap<>();
		Map<String, String> poemUrlMap = new HashMap<>();
		for( Pratilipi poem : poemDataList ){
			if( poemCoverMap.get( poem.getId() ) == null ){
				poemCoverMap.put( poem.getId().toString(),
								  PratilipiHelper.URL_POEM_COVER + poem.getId() );
			}
			if( poemUrlMap.get( poem.getId() ) == null ){
				poemUrlMap.put( poem.getId().toString(),
								PratilipiHelper.URL_POEM_PAGE + poem.getId() );
			}
		}
		
		DataListCursorTuple<Pratilipi> storyList = dataAccessor.getPratilipiListByAuthor( 
				authorId, PratilipiType.STORY, null, 100 );
		List<Pratilipi> storyDataList = storyList.getDataList();
		Map<String, String> storyCoverMap = new HashMap<>();
		Map<String, String> storyUrlMap = new HashMap<>();
		for( Pratilipi story : storyDataList ){
			if( storyCoverMap.get( story.getId() ) == null ){
				storyCoverMap.put( story.getId().toString(),
								   PratilipiHelper.URL_STORY_COVER + story.getId() );
			}
			if( storyUrlMap.get( story.getId() ) == null ){
				storyUrlMap.put( story.getId().toString(),
								 PratilipiHelper.URL_STORY_PAGE + story.getId() );
			}
		}
		//Fetching language list
		List<Language> languageList = dataAccessor.getLanguageList();
		Map<String, String> languageMap = new HashMap<>();
		for( Language language : languageList ){
			if( languageMap.get( language.getId() ) == null ){
				languageMap.put( language.getId().toString(),
								 language.getName() + " (" + language.getNameEn() + ")" );
			}
		}
		
		dataAccessor.destroy();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "author", author );
		dataModel.put( "authorImage", authorImage );
		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "bookCoverMap",  bookCoverMap );
		dataModel.put( "bookUrlMap",  bookUrlMap );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "poemCoverMap",  poemCoverMap );
		dataModel.put( "poemUrlMap",  poemUrlMap );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "storyCoverMap", storyCoverMap );
		dataModel.put( "storyUrlMap", storyUrlMap );
		dataModel.put( "languageMap", languageMap );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", claymusHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/author/AuthorContent.ftl";
	}

}
