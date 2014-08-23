package com.pratilipi.module.pagecontent.homebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.claymus.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.UserBook;
import com.pratilipi.shared.PratilipiHelper;

public class HomeBookContentProcessor extends PageContentProcessor<HomeBookContent> {


	public String getHtml( HomeBookContent homeBookContent ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Book book = dataAccessor.getBook( homeBookContent.getBookId() );
		Author author = dataAccessor.getAuthor( book.getAuthorId() );
		List<UserBook> reviewList = dataAccessor.getUserBookList( book.getId() );
		dataAccessor.destroy();
		
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "book", book );
		dataModel.put( "author", author );
		dataModel.put( "reviewList", reviewList );
		dataModel.put( "bookCoverUrl", PratilipiHelper.BOOK_COVER_URL );
		dataModel.put( "bookHomeUrl", PratilipiHelper.BOOK_PAGE_URL );
		dataModel.put( "authorHomeUrl", PratilipiHelper.AUTHOR_PAGE_URL );
		dataModel.put( "showAddOptions", homeBookContent.getCurrentUserId() != null );
		dataModel.put( "showEditOptions", ClaymusHelper.isUserAdmin() );
		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/module/pagecontent/homebook/HomeBookContent.ftl" );
	}
	
}
