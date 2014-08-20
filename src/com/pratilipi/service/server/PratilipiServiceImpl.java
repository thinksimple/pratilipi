package com.pratilipi.service.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.ClaymusHelper;
import com.claymus.client.IllegalArgumentException;
import com.claymus.client.InsufficientAccessException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetBookRequest;
import com.pratilipi.service.shared.GetBookResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PublisherData;

@SuppressWarnings("serial")
public class PratilipiServiceImpl
		extends RemoteServiceServlet
		implements PratilipiService {

	@Override
	public AddBookResponse addBook( AddBookRequest request )
			throws InsufficientAccessException, IllegalArgumentException {
		
		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();
		
		BookData bookData = request.getBook();
		if( bookData.hasId() )
			throw new IllegalArgumentException(
					"BookId exist already. Did you mean to call updateBook ?" );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.newBook();
		book.setTitle( bookData.getTitle() );
		book.setLanguageId( bookData.getLanguageId() );
		book.setAuthorId( bookData.getAuthorId() );
		book.setPublisherId( bookData.getPublisherId() );
		book.setPublicationYear( bookData.getPublicationYear() );
		book.setSummary( bookData.getSummary() );
		book.setWordCount( bookData.getWordCount() );
		
		book.setListingDate( new Date() );

		book = dataAccessor.createOrUpdateBook( book );
		dataAccessor.destroy();
		
		return new AddBookResponse( book.getId() );
	}

	@Override
	public UpdateBookResponse updateBook( UpdateBookRequest request )
			throws InsufficientAccessException, IllegalArgumentException {

		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();
		
		BookData bookData = request.getBook();
		if( ! bookData.hasId() )
			throw new IllegalArgumentException(
					"BookId is not set. Did you mean to call addBook ?" );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.getBook( bookData.getId() );
		if( bookData.hasTitle() )
			book.setTitle( bookData.getTitle() );
		if( bookData.hasLanguageId() )
			book.setLanguageId( bookData.getLanguageId() );
		if( bookData.hasAuthorId() )
			book.setAuthorId( bookData.getAuthorId() );
		if( bookData.hasPublisherId() )
			book.setPublisherId( bookData.getPublisherId() );
		if( bookData.hasPublicationYear() )
			book.setPublicationYear( bookData.getPublicationYear() );
		if( bookData.hasSummary() )
			book.setSummary( bookData.getSummary() );
		if( bookData.hasWordCount() )
			book.setWordCount( bookData.getWordCount() );
		
		book = dataAccessor.createOrUpdateBook( book );
		dataAccessor.destroy();

		return new UpdateBookResponse();
	}
	
	@Override
	public GetBookListResponse getBookList( GetBookListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Book> bookList = dataAccessor.getBookList();
		
		ArrayList<BookData> bookDataList = new ArrayList<>( bookList.size() );
		for( Book book : bookList ) {
			Language language = dataAccessor.getLanguage( book.getLanguageId() );
			Author author = dataAccessor.getAuthor( book.getAuthorId() );
			Publisher publisher = dataAccessor.getPublisher( book.getPublisherId() );
			
			BookData bookData = new BookData();
			bookData.setId( book.getId() );
			bookData.setTitle( book.getTitle() );
			bookData.setLanguageId( language.getId() );
			bookData.setLanguageName( language.getName() );
			bookData.setAuthorId( author.getId() );
			bookData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
			bookData.setPublisherId( publisher.getId() );
			bookData.setPublisherName( publisher.getName() );
			bookData.setPublicationDate( book.getPublicationYear() );
			bookData.setListingDate( book.getListingDate() );
			bookData.setSummary( book.getSummary() );
			bookData.setWordCount( book.getWordCount() );
			
			bookDataList.add( bookData );
		}

		dataAccessor.destroy();
		
		return new GetBookListResponse( bookDataList );
	}
	
	
	@Override
	public GetBookResponse getBookById(GetBookRequest request) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Book book = dataAccessor.getBook(request.getBookId());
		
		Language language = dataAccessor.getLanguage( book.getLanguageId() );
		Author author = dataAccessor.getAuthor( book.getAuthorId() );
		Publisher publisher = dataAccessor.getPublisher( book.getPublisherId() );
		
		BookData bookData = new BookData();
		bookData.setId( book.getId() );
		bookData.setTitle( book.getTitle() );
		bookData.setLanguageId( language.getId() );
		bookData.setLanguageName( language.getName() );
		bookData.setAuthorId( author.getId() );
		bookData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
		bookData.setPublisherId( publisher.getId() );
		bookData.setPublisherName( publisher.getName() );
		bookData.setPublicationDate( book.getPublicationYear() );
		bookData.setListingDate( book.getListingDate() );
		bookData.setSummary( book.getSummary() );
		bookData.setWordCount( book.getWordCount() );
		
		dataAccessor.destroy();

		return new GetBookResponse(bookData);
	}

	
	@Override
	public AddLanguageResponse addLanguage( AddLanguageRequest request )
			throws InsufficientAccessException {
		
		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();
		
		LanguageData languageData = request.getLanguage();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Language language = dataAccessor.newLanguage();
		language.setName( languageData.getName() );
		language.setCreationDate( new Date() );
		
		language = dataAccessor.createOrUpdateLanguage( language );
		dataAccessor.destroy();
		
		return new AddLanguageResponse( language.getId() );
	}

	@Override
	public GetLanguageListResponse getLanguageList( GetLanguageListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Language> languageList = dataAccessor.getLanguageList();
		
		ArrayList<LanguageData> languageDataList = new ArrayList<>( languageList.size() );
		for( Language language : languageList ) {
			LanguageData languageData = new LanguageData();
			languageData.setId( language.getId() );
			languageData.setName( language.getName() );
			languageData.setCreationDate( language.getCreationDate() );
			
			languageDataList.add( languageData );
		}

		dataAccessor.destroy();
		
		return new GetLanguageListResponse( languageDataList );
	}


	@Override
	public AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws InsufficientAccessException {
		
		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();
		
		AuthorData authorData = request.getAuthor();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Author author = dataAccessor.newAuthor();
		author.setFirstName( authorData.getFirstName() );
		author.setLastName( authorData.getLastName() );
		author.setPenName( authorData.getPenName() );
		author.setEmail( authorData.getEmail() );
		author.setRegistrationDate( new Date() );
		
		author = dataAccessor.createOrUpdateAuthor( author );
		dataAccessor.destroy();
		
		return new AddAuthorResponse( author.getId() );
	}

	@Override
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Author> authorList = dataAccessor.getAuthorList();
		
		ArrayList<AuthorData> authorDataList = new ArrayList<>( authorList.size() );
		for( Author author : authorList ) {
			AuthorData authorData = new AuthorData();
			authorData.setId( author.getId() );
			authorData.setFirstName( author.getFirstName() );
			authorData.setLastName( author.getLastName() );
			authorData.setEmail( author.getEmail() );
			authorData.setRegistrationDate( author.getRegistrationDate() );
			
			authorDataList.add( authorData );
		}

		dataAccessor.destroy();
		
		return new GetAuthorListResponse( authorDataList );
	}

	@Override
	public AddPublisherResponse addPublisher(AddPublisherRequest request)
			throws InsufficientAccessException {
		
		if( ! ClaymusHelper.isUserAdmin() )
			throw new InsufficientAccessException();
		
		PublisherData publisherData = request.getPublisher();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Publisher publisher = dataAccessor.newPublisher();
		publisher.setName( publisherData.getName() );
		publisher.setEmail( publisherData.getEmail() );
		publisher.setRegistrationDate( new Date() );
		
		publisher = dataAccessor.createOrUpdatePublisher( publisher );
		dataAccessor.destroy();
		
		return new AddPublisherResponse( publisher.getId() );
	}

	@Override
	public GetPublisherListResponse getPublisherList(
			GetPublisherListRequest request) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Publisher> publisherList = dataAccessor.getPublisherList();
		
		ArrayList<PublisherData> publisherDataList = new ArrayList<>( publisherList.size() );
		for( Publisher publisher : publisherList ) {
			PublisherData publisherData = new PublisherData();
			publisherData.setId( publisher.getId() );
			publisherData.setName( publisher.getName() );
			publisherData.setEmail( publisher.getEmail() );
			publisherData.setRegistrationDate( publisher.getRegistrationDate() );
			
			publisherDataList.add( publisherData );
		}

		dataAccessor.destroy();
		
		return new GetPublisherListResponse( publisherDataList );
	}

}
