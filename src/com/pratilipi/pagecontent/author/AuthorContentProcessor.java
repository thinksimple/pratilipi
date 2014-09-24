package com.pratilipi.pagecontent.author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;

public class AuthorContentProcessor extends PageContentProcessor<AuthorContent> {

	public static final String ACCESS_ID_AUTHOR_UPDATE = "author_update";
	
	@Override
	public String getHtml( AuthorContent authorContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		Long authorId = authorContent.getAuthorId();
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		//Fetching current user id
		Long currentUserId = claymusHelper.getCurrentUserId();
		
		// Fetching Author list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		String authorImage = PratilipiHelper.URL_AUTHOR_IMAGE + authorId;
		
		//Update privileges
		boolean isAuthor = currentUserId.equals( author.getUserId());
		boolean showUpdateOption =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_UPDATE, false )
				|| isAuthor ;
		
		//Fetching pratilipi list for the author.
		DataListCursorTuple<Pratilipi> bookList = dataAccessor.getPratilipiListByAuthor( 
								PratilipiType.BOOK, authorId, null, 200 );
		List<Pratilipi> bookDataList = bookList.getDataList();
		
		Map<String, String> bookCoverMap = new HashMap<>();
		Map<String, String> bookUrlMap = new HashMap<>();
		for( Pratilipi book : bookDataList ){
			if( bookCoverMap.get( book.getId() ) == null ){
				bookCoverMap.put( book.getId().toString(),
						PratilipiHelper.getCoverImage300Url( PratilipiType.BOOK, book.getId(), false ) );
			}
			if( bookUrlMap.get( book.getId() ) == null ){
				bookUrlMap.put( book.getId().toString(), 
						PratilipiHelper.getPageUrl( PratilipiType.BOOK, book.getId() ) );
			}
		}
		
		DataListCursorTuple<Pratilipi> poemList = dataAccessor.getPratilipiListByAuthor( 
				PratilipiType.POEM, authorId, null, 300 );
		List<Pratilipi> poemDataList = poemList.getDataList();
		Map<String, String> poemCoverMap = new HashMap<>();
		Map<String, String> poemUrlMap = new HashMap<>();
		for( Pratilipi poem : poemDataList ){
			if( poemCoverMap.get( poem.getId() ) == null ){
				poemCoverMap.put( poem.getId().toString(),
						PratilipiHelper.getCoverImage300Url( PratilipiType.POEM, poem.getId(), false ) );
			}
			if( poemUrlMap.get( poem.getId() ) == null ){
				poemUrlMap.put( poem.getId().toString(),
						PratilipiHelper.getPageUrl( PratilipiType.POEM, poem.getId() ) );
			}
		}
		
		DataListCursorTuple<Pratilipi> storyList = dataAccessor.getPratilipiListByAuthor( 
				PratilipiType.STORY, authorId, null, 500 );
		List<Pratilipi> storyDataList = storyList.getDataList();
		Map<String, String> storyCoverMap = new HashMap<>();
		Map<String, String> storyUrlMap = new HashMap<>();
		for( Pratilipi story : storyDataList ){
			if( storyCoverMap.get( story.getId() ) == null ){
				storyCoverMap.put( story.getId().toString(),
						PratilipiHelper.getCoverImage300Url( PratilipiType.STORY, story.getId(), false ) );
			}
			if( storyUrlMap.get( story.getId() ) == null ){
				storyUrlMap.put( story.getId().toString(),
						PratilipiHelper.getPageUrl( PratilipiType.STORY, story.getId() ) );
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
		dataModel.put( "showUpdateOption", showUpdateOption );
		dataModel.put( "timeZone", claymusHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/author/AuthorContent.ftl";
	}

}
