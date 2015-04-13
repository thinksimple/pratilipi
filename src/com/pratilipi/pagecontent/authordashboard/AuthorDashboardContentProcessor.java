package com.pratilipi.pagecontent.authordashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

public class AuthorDashboardContentProcessor extends PageContentProcessor<AuthorDashboardContent> {

	@Override
	public String generateTitle(
			AuthorDashboardContent authorDashboardContent,
			HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorDashboardContent.getId() );
		AuthorData authorData = AuthorContentHelper.createAuthorData( author, null, request );
		if( authorData.getName() != null )
			return "Dasboard » " + authorData.getName() + " (" + authorData.getNameEn() + ")";
		else
			return "Dasboard » " + authorData.getNameEn();
	}
	
	@Override
	public String generateHtml(
			AuthorDashboardContent authorDashboardContent,
			HttpServletRequest request ) throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Long authorId = authorDashboardContent.getId();
		Author author = dataAccessor.getAuthor( authorId );

		if( !AuthorContentHelper.hasRequestAccessToUpdateAuthorData( request, author ) )
			throw new InsufficientAccessException();
		
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		pratilipiFilter.setOrderByReadCount( false );

		pratilipiFilter.setType( PratilipiType.BOOK );
		List<PratilipiData> bookDataList = PratilipiContentHelper
				.getPratilipiList( pratilipiFilter, null, 1000, request )
				.getDataList();

		pratilipiFilter.setType( PratilipiType.POEM );
		List<PratilipiData> poemDataList = PratilipiContentHelper
				.getPratilipiList( pratilipiFilter, null, 1000, request )
				.getDataList();

		pratilipiFilter.setType( PratilipiType.STORY );
		List<PratilipiData> storyDataList = PratilipiContentHelper
				.getPratilipiList( pratilipiFilter, null, 1000, request )
				.getDataList();

		pratilipiFilter.setType( PratilipiType.ARTICLE );
		List<PratilipiData> articleDataList = PratilipiContentHelper
				.getPratilipiList( pratilipiFilter, null, 1000, request )
				.getDataList();

		
		AuthorData authorData = AuthorContentHelper.createAuthorData( author, null, request );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "authorData", authorData );

		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "articleDataList", articleDataList );
		
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
