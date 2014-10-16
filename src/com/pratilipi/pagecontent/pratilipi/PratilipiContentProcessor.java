package com.pratilipi.pagecontent.pratilipi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentProcessor extends PageContentProcessor<PratilipiContent> {

	public static final String ACCESS_ID_PRATILIPI_LIST = "pratilipi_list";
	public static final String ACCESS_ID_PRATILIPI_READ_META_DATA = "pratilipi_read_meta_data";
	public static final String ACCESS_ID_PRATILIPI_ADD = "pratilipi_add";
	public static final String ACCESS_ID_PRATILIPI_UPDATE = "pratilipi_update";
	
	public static final String ACCESS_ID_PRATILIPI_REVIEW_VIEW =
			"pratilipi_review_view";
	public static final String ACCESS_ID_PRATILIPI_REVIEW_ADD =
			"pratilipi_review_add";

	
	@Override
	public String generateHtml( PratilipiContent pratilipiContent, HttpServletRequest request )
			throws UnexpectedServerException {

		Long pratilipiId = pratilipiContent.getPratilipiId();
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( pratilipiId );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );

		UserPratilipi userPratilipi = null;
		if( pratilipiHelper.isUserLoggedIn() )
			userPratilipi = dataAccessor.getUserPratilipi( pratilipiHelper.getCurrentUserId(), pratilipiId );
		List<UserPratilipi> reviewList = dataAccessor.getUserPratilipiList( pratilipiId );
		
		Map<String, String> userIdNameMap = new HashMap<>();
		for( UserPratilipi review : reviewList) {
			if( userIdNameMap.get( review.getUserId() ) == null ) {
				User user = dataAccessor.getUser( review.getUserId() );
				userIdNameMap.put( user.getId().toString(), pratilipiHelper.createUserName( user ) );
			}
		}

		dataAccessor.destroy();
		
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiData", pratilipiData );
		dataModel.put( "pratilipiUrl", "http://" + ClaymusHelper.getSystemProperty( "domain" ) + "/" + pratilipiData.getType() + "/" + pratilipiId );
		dataModel.put( "pratilipiDataEncodedStr", SerializationUtil.encode( pratilipiData ) );

		dataModel.put( "pratilipi", pratilipi );
		dataModel.put( "author", author );
		dataModel.put( "reviewList", reviewList );
		dataModel.put( "userIdNameMap", userIdNameMap );
		
		User currentUser = pratilipiHelper.getCurrentUser();
		PratilipiType pratilipiType = pratilipi.getType();

		dataModel.put( "userName", pratilipiHelper.createUserName( currentUser ) );
		dataModel.put( "pratilipiHomeUrl", PratilipiHelper.getPageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiCoverUrl", PratilipiHelper.getCoverImage300Url( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiCoverUploadUrl", PratilipiHelper.getCoverImageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiReaderUrl", PratilipiHelper.getReaderPageUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiContentHtmlUrl", PratilipiHelper.getContentHtmlUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "pratilipiContentWordUrl", PratilipiHelper.getContentWordUrl( pratilipiType, pratilipi.getId() ) );
		dataModel.put( "authorHomeUrl", PratilipiHelper.URL_AUTHOR_PAGE + pratilipi.getAuthorId() );
		
		dataModel.put( "showReviewedMessage",
				userPratilipi != null && userPratilipi.getReviewState() != UserReviewState.NOT_SUBMITTED );

		dataModel.put( "showReviewOption",
				! currentUser.getId().equals( author.getUserId() )
				&& ( userPratilipi == null || userPratilipi.getReviewState() == UserReviewState.NOT_SUBMITTED )
				&& pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_REVIEW_ADD, false ) );

		dataModel.put( "showEditOptions",
				( ! currentUser.getId().equals( author.getUserId() ) && pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_ADD, false ) )
				|| pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_UPDATE, false ) );
		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/pratilipi/PratilipiContent.ftl" );
	}
	
}
