package com.pratilipi.module.pagecontent.homebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.UserBook;

public class HomeBookContentProcessor extends PageContentProcessor<HomeBookContent> {

	public static String ACCESS_ID_BOOK_VIEW = "book_view";
	public static String ACCESS_ID_BOOK_ADD = "book_add";
	public static String ACCESS_ID_BOOK_UPDATE = "book_update";
	
	public static String ACCESS_ID_BOOK_REVIEW_VIEW = "book_review_view";
	public static String ACCESS_ID_BOOK_REVIEW_ADD = "book_review_add";

	
	@Override
	public String getHtml( HomeBookContent homeBookContent,
			HttpServletRequest request, HttpServletResponse response ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.getBook( homeBookContent.getBookId() );
		Author author = dataAccessor.getAuthor( book.getAuthorId() );
		List<UserBook> reviewList = dataAccessor.getUserBookList( book.getId() );

		dataAccessor.destroy();
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "book", book );
		dataModel.put( "author", author );
		dataModel.put( "reviewList", reviewList );
		
		dataModel.put( "bookCoverUrl", PratilipiHelper.BOOK_COVER_URL );
		dataModel.put( "bookHomeUrl", PratilipiHelper.BOOK_PAGE_URL );
		dataModel.put( "authorHomeUrl", PratilipiHelper.AUTHOR_PAGE_URL );
		
		dataModel.put( "showEditOptions",
				( claymusHelper.getCurrentUserId() == book.getAuthorId()
				&& claymusHelper.hasUserAccess( ACCESS_ID_BOOK_ADD, false ) )
				|| claymusHelper.hasUserAccess( ACCESS_ID_BOOK_UPDATE, false ) );
		
		dataModel.put( "showAddReviewOption",
				claymusHelper.getCurrentUserId() != book.getAuthorId()
				&& claymusHelper.hasUserAccess( ACCESS_ID_BOOK_REVIEW_ADD, false ) );
		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/module/pagecontent/homebook/HomeBookContent.ftl" );
	}
	
}
