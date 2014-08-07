package com.pratilipi.service.server;

import java.util.ArrayList;
import java.util.List;

import com.claymus.ClaymusHelper;
import com.claymus.client.InsufficientAccessException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Publisher;
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
		book.setTitle( bookData.getTitle() );
//		book.setLanguage( bookData.getLanguage() );
		book.setAuthorId( bookData.getAuthorId() );
		book.setPublisherId( bookData.getPublisherId() );
		book.setPublicationDate( bookData.getPublicationDate() );
		book.setListingDate( bookData.getListingDate() );
		book.setWordCount( bookData.getWordCount() );
		
		book = dataAccessor.createOrUpdateBook( book );
		dataAccessor.destroy();
		
		return new AddBookResponse( book.getId() );
	}

	@Override
	public GetBookListResponse getBookList( GetBookListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Book> bookList = dataAccessor.getBookList();
		
		ArrayList<BookData> bookDataList = new ArrayList<BookData>( bookList.size() );
		for( Book book : bookList ) {
			Author author = dataAccessor.getAuthor( book.getAuthorId() );
			Publisher publisher = dataAccessor.getPublisher( book.getPublisherId() );
			
			BookData bookData = new BookData();
			bookData.setId( book.getId() );
			bookData.setTitle( book.getTitle() );
//			bookData.setLanguageId( languageId );
			bookData.setLanguageName( book.getLanguage().getName() );
			bookData.setAuthorId( author.getId() );
			bookData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
			bookData.setPublisherId( publisher.getId() );
			bookData.setPublisherName( publisher.getFirstName() + " " + publisher.getLastName() );
			bookData.setPublicationDate( book.getPublicationDate() );
			bookData.setListingDate( book.getListingDate() );
			bookData.setWordCount( book.getWordCount() );
			
			bookDataList.add( bookData );
		}

		dataAccessor.destroy();
		
		return new GetBookListResponse( bookDataList );
	}

}
