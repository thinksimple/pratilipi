package com.pratilipi.pagecontent.genres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Genre;

public class GenresContentProcessor
		extends PageContentProcessor<GenresContent> {

	public static final String ACCESS_ID_GENRE_LIST = "genre_list";
	public static final String ACCESS_ID_GENRE_READ_META_DATA = "genre_read_meta_data";
	public static final String ACCESS_ID_GENRE_ADD = "genre_add";
	
	
	@Override
	public String generateHtml( GenresContent genreContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		boolean showMetaData =
				pratilipiHelper.hasUserAccess( ACCESS_ID_GENRE_READ_META_DATA, false );
		boolean showAddOption =
				pratilipiHelper.hasUserAccess( ACCESS_ID_GENRE_ADD, false );

		
		// Fetching Genre list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Genre> genreList = dataAccessor.getGenreList();
		dataAccessor.destroy();

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "genreList", genreList );
		dataModel.put( "genrePageUrl", PratilipiHelper.URL_GENRE_PAGE );
		dataModel.put( "showMetaData", showMetaData );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}

	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/genres/GenresContent.ftl";
	}
	
}
