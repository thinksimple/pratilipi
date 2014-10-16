package com.pratilipi.pagecontent.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class HomeContentProcessor extends PageContentProcessor<HomeContent> {

	@Override
	protected CacheLevel getCacheLevel() {
		return CacheLevel.GLOBAL;
	}

	@Override
	public String generateHtml( HomeContent homeContent, HttpServletRequest request )
			throws UnexpectedServerException {

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		List<PratilipiData> bookDataList =
				new ArrayList<>( homeContent.getBookIdList().size() );
		for( Long bookId : homeContent.getBookIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			bookDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, null, author, null ) );
		}
		
		
		List<PratilipiData> poemDataList =
				new ArrayList<>( homeContent.getPoemIdList().size() );
		for( Long bookId : homeContent.getPoemIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			poemDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, null, author, null ) );
		}
		
		
		List<PratilipiData> storyDataList =
				new ArrayList<>( homeContent.getStoryIdList().size() );
		for( Long bookId : homeContent.getStoryIdList() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			storyDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, null, author, null ) );
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
