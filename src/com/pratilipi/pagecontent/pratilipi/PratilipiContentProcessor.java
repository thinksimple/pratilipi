package com.pratilipi.pagecontent.pratilipi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
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
	public String getHtml( PratilipiContent pratilipiContent,
			HttpServletRequest request, HttpServletResponse response ) {

		Long pratilipiId = pratilipiContent.getPratilipiId();
		PratilipiType pratilipiType = pratilipiContent.getPratilipiType();
		ClaymusHelper claymusHelper = new ClaymusHelper( request );

		
		// Fetching Pratilipi, Author and Reviews
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId,
				pratilipiContent.getPratilipiType() );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		UserPratilipi pratilipiBook = dataAccessor.getUserPratilipi(
				claymusHelper.getCurrentUserId(), pratilipiId );
		List<UserPratilipi> reviewList =
				dataAccessor.getUserPratilipiList( pratilipiId );
		dataAccessor.destroy();
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipi", pratilipi );
		dataModel.put( "author", author );
		dataModel.put( "reviewList", reviewList );
		
		dataModel.put( "pratilipiCoverUrl", pratilipiType.getCoverImageUrl() + pratilipi.getId() );
		dataModel.put( "pratilipiHomeUrl", pratilipiType.getPageUrl() + pratilipi.getId() );
		dataModel.put( "authorHomeUrl", PratilipiHelper.URL_AUTHOR_PAGE + pratilipi.getId() );
		
		dataModel.put( "showContent", pratilipiType != PratilipiType.BOOK );

		dataModel.put( "showAddReviewOption",
				claymusHelper.getCurrentUserId() != pratilipi.getAuthorId()
				&& ( pratilipiBook == null || pratilipiBook.getReviewState() == UserReviewState.NOT_SUBMITTED )
				&& claymusHelper.hasUserAccess( ACCESS_ID_PRATILIPI_REVIEW_ADD, false ) );

		dataModel.put( "showEditOptions",
				( claymusHelper.getCurrentUserId() == pratilipi.getAuthorId() && claymusHelper.hasUserAccess( ACCESS_ID_PRATILIPI_ADD, false ) )
				|| claymusHelper.hasUserAccess( ACCESS_ID_PRATILIPI_UPDATE, false ) );
		

		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/pratilipi/PratilipiContent.ftl" );
	}
	
}
