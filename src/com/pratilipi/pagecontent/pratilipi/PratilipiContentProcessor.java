package com.pratilipi.pagecontent.pratilipi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.data.transfer.User;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentProcessor;

public class PratilipiContentProcessor extends PageContentProcessor<PratilipiContent> {

	public static final String ACCESS_ID_PRATILIPI_READ_META_DATA = PratilipisContentProcessor.ACCESS_ID_PRATILIPI_READ_META_DATA;
	public static final String ACCESS_ID_PRATILIPI_ADD = PratilipisContentProcessor.ACCESS_ID_PRATILIPI_ADD;
	public static final String ACCESS_ID_PRATILIPI_UPDATE = PratilipisContentProcessor.ACCESS_ID_PRATILIPI_UPDATE;
	
	public static final String ACCESS_ID_PRATILIPI_REVIEW_VIEW = "pratilipi_review_view";
	public static final String ACCESS_ID_PRATILIPI_REVIEW_ADD = "pratilipi_review_add";

	
	@Override
	protected String generateHtml( PratilipiContent pratilipiContent, HttpServletRequest request )
			throws IOException, UnexpectedServerException {

		Long pratilipiId = pratilipiContent.getPratilipiId();
		PratilipiType pratilipiType = pratilipiContent.getPratilipiType();
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );

		
		// Fetching Pratilipi, Author and Reviews
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		UserPratilipi pratilipiBook = null;
		if( pratilipiHelper.isUserLoggedIn() )
			pratilipiBook = dataAccessor.getUserPratilipi(
				pratilipiHelper.getCurrentUserId(), pratilipiId );
		List<UserPratilipi> reviewList =
				dataAccessor.getUserPratilipiList( pratilipiId );
		
		Map<String, String> userIdNameMap = new HashMap<>();
		for( UserPratilipi review : reviewList) {
			if( userIdNameMap.get( review.getUserId() ) == null ) {
				User user = dataAccessor.getUser( review.getUserId() );
				userIdNameMap.put(
						user.getId().toString(),
						user.getFirstName() + ( user.getLastName() == null ? "" : " " + user.getLastName() ) );
			}
		}
		dataAccessor.destroy();
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipi", pratilipi );
		dataModel.put( "author", author );
		dataModel.put( "reviewList", reviewList );
		dataModel.put( "userIdNameMap", userIdNameMap );
		
		User currentUser = pratilipiHelper.getCurrentUser();
		
		dataModel.put( "userName", pratilipiHelper.createUserName( currentUser ) );
		dataModel.put( "isAuthor", pratilipiHelper.getCurrentUserId().equals( author.getUserId()) );
		dataModel.put( "pratilipiHomeUrl", PratilipiHelper.getPageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiCoverUrl", PratilipiHelper.getCoverImageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiReaderUrl", PratilipiHelper.getReaderPageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiContentHtmlUrl", PratilipiHelper.getContentHtmlUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiContentWordUrl", PratilipiHelper.getContentWordUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "authorHomeUrl", PratilipiHelper.URL_AUTHOR_PAGE + pratilipi.getAuthorId() );
		
		dataModel.put( "showReviewedMessage",
				pratilipiBook != null && pratilipiBook.getReviewState() != UserReviewState.NOT_SUBMITTED );

		dataModel.put( "showReviewOption",
				pratilipiHelper.getCurrentUserId() != pratilipi.getAuthorId()
				&& ( pratilipiBook == null || pratilipiBook.getReviewState() == UserReviewState.NOT_SUBMITTED )
				&& pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_REVIEW_ADD, false ) );

		dataModel.put( "showEditOptions",
				( pratilipiHelper.getCurrentUserId() == pratilipi.getAuthorId() && pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_ADD, false ) )
				|| pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_UPDATE, false ) );
		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/pratilipi/PratilipiContent.ftl" );
	}
	
}
