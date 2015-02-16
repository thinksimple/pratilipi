package com.pratilipi.pagecontent.author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.PratilipiData;

public class AuthorContentProcessor extends PageContentProcessor<AuthorContent> {

	@Override
	public String generateTitle( AuthorContent authorContent, HttpServletRequest request ) {
		AuthorData authorData = PratilipiHelper.get( request ).createAuthorData( authorContent.getId() );
		if( authorData.getFullName() != null )
			return authorData.getFullName() + " (" + authorData.getFullNameEn() + ")";
		else
			return authorData.getFullNameEn();
	}
	
	@Override
	public String generateHtml(
			AuthorContent authorContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Long authorId = authorContent.getId();
		Author author = dataAccessor.getAuthor( authorId );

		boolean showEditOption = AuthorContentHelper
				.hasRequestAccessToUpdateAuthorData( request, author );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );

		pratilipiFilter.setState( PratilipiState.DRAFTED );
		DataListCursorTuple<Pratilipi> draftedPratilipiListCursorTuple = showEditOption
				? (DataListCursorTuple<Pratilipi>) dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 )
				: new DataListCursorTuple<Pratilipi>( new ArrayList<Pratilipi>(0), null );

		pratilipiFilter.setState( PratilipiState.SUBMITTED );
		DataListCursorTuple<Pratilipi> submittedPratilipiListCursorTuple = showEditOption
				? (DataListCursorTuple<Pratilipi>) dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 )
				: new DataListCursorTuple<Pratilipi>( new ArrayList<Pratilipi>(0), null );
				
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		pratilipiFilter.setType( PratilipiType.BOOK );
		DataListCursorTuple<Pratilipi> bookListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.POEM );
		DataListCursorTuple<Pratilipi> poemListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.STORY );
		DataListCursorTuple<Pratilipi> storyListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.ARTICLE );
		DataListCursorTuple<Pratilipi> articleListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		
		AuthorData authorData = pratilipiHelper.createAuthorData( author.getId() );

		List<PratilipiData> draftedPratilipiDataList =
				pratilipiHelper.createPratilipiDataList(
						draftedPratilipiListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> submittedPratilipiDataList =
				pratilipiHelper.createPratilipiDataList(
						submittedPratilipiListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> bookDataList =
				pratilipiHelper.createPratilipiDataList(
						bookListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> poemDataList =
				pratilipiHelper.createPratilipiDataList(
						poemListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> storyDataList =
				pratilipiHelper.createPratilipiDataList(
						storyListCursorTuple.getDataList(), false, false, false );

		List<PratilipiData> articleDataList =
				pratilipiHelper.createPratilipiDataList(
						articleListCursorTuple.getDataList(), false, false, false );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "authorData", authorData );
		dataModel.put( "authorDataEncodedStr", SerializationUtil.encode( authorData ) );
		dataModel.put( "draftedPratilipiDataList", draftedPratilipiDataList );
		dataModel.put( "submittedPratilipiDataList", submittedPratilipiDataList );
		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "articleDataList", articleDataList );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		dataModel.put( "showEditOption", showEditOption );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
