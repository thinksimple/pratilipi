package com.pratilipi.service.server;

import java.util.ArrayList;
import java.util.List;

import com.claymus.ClaymusHelper;
import com.claymus.client.InsufficientAccessException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.data.BookData;

@SuppressWarnings("serial")
public class PratilipiServiceImpl
		extends RemoteServiceServlet
		implements PratilipiService {

	public String greetServer( String input ) throws IllegalArgumentException {

		return "Hello from server !";
		
	}

	@Override
	public AddBookResponse addBook( AddBookRequest request ) throws InsufficientAccessException {
		
		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();

		BookData bookData = request.getBook();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.newBook();
		book.setIsbn( bookData.getIsbn() );
		book.setTitle( bookData.getTitle() );
		
		book = dataAccessor.createOrUpdateBook( book );
		dataAccessor.destroy();
		
		return new AddBookResponse( bookData );
	}

	@Override
	public GetBookListResponse getBookList( GetBookListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Book> bookList = dataAccessor.getBookList();
		
		List<BookData> bookDataList = new ArrayList<BookData>( bookList.size() );
		for( Book book : bookList ) {
			BookData bookData = new BookData();
			bookData.setIsbn( book.getIsbn() );
			bookData.setTitle( book.getTitle() );
			bookDataList.add( bookData );
		}

		dataAccessor.destroy();
		
		return new GetBookListResponse( bookDataList );
	}

}
