package com.pratilipi.pagecontent.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class HomeContentProcessor extends PageContentProcessor<HomeContent> {

	@Override
	protected CacheLevel getCacheLevel() {
		return CacheLevel.GLOBAL;
	}

	@Override
	protected String generateHtml( HomeContent homeContent, HttpServletRequest request )
			throws IOException, UnexpectedServerException {

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		List<PratilipiData> bookDataList =
				new ArrayList<>( homeContent.getBookIdList().size() );
		for( Long bookId : homeContent.getBookIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			bookDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, language, author ) );
		}
		
		
		List<PratilipiData> poemDataList =
				new ArrayList<>( homeContent.getPoemIdList().size() );
		for( Long bookId : homeContent.getPoemIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			poemDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, language, author ) );
		}
		
		
		List<PratilipiData> storyDataList =
				new ArrayList<>( homeContent.getStoryIdList().size() );
		for( Long bookId : homeContent.getStoryIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			storyDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, language, author ) );
		}

		
		dataAccessor.destroy();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "showEditOptions", false );

		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/home/HomeContent.ftl" );
	}
	
}
