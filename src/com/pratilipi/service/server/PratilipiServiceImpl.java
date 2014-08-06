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
		
		BookData bookData = request.getBook(); //get book to be inserted into database
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.newBook();  //returns database book entity.
		//insert data into database.
		book.setIsbn( bookData.getIsbn() );
		book.setTitle( bookData.getTitle() );
		
		book = dataAccessor.createOrUpdateBook( book );
		dataAccessor.destroy();
		
		return new AddBookResponse( bookData );
	}

	@Override
	public GetBookListResponse getBookList( GetBookListRequest request ) {
		
		// DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		// List<Book> bookList = dataAccessor.getBookList();
		BookData[] books = new BookData[3];
		for(int i=0; i<3; ++i)
			books[i] = new BookData();
		//first record
		books[0].setTitle("Facebook");
		books[0].setAuthorId(new Long(1234));
		//books[0].setCoverPage("/images/facebook.png");
		//Second record
		books[1].setTitle("Twitter");
		books[1].setAuthorId(new Long(4567));
		//books[1].setCoverPage("/images/twitter.png");
		//Third record
		books[2].setTitle("BlackTwitter");
		books[2].setAuthorId(new Long(8901));
		//books[2].setCoverPage("/images/BlackTwitter.png");
		
		ArrayList<BookData> bookList = new ArrayList<BookData>();
		for(int i=0;i<books.length;++i)
			bookList.add(books[i]);
		
		//Static data is only for testing purpose.
		List<BookData> bookDataList = new ArrayList<BookData>( bookList.size() );
		for( BookData book : bookList ) {
			BookData bookData = new BookData();
			bookData.setIsbn( book.getIsbn() );
			bookData.setTitle( book.getTitle() );
			bookDataList.add( bookData );
		}

		//dataAccessor.destroy();
		
		return new GetBookListResponse( bookDataList );
	}

}
