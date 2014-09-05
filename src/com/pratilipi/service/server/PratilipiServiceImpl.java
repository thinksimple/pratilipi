package com.pratilipi.service.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.pagecontent.authors.AuthorsContentProcessor;
import com.pratilipi.pagecontent.book.BookContentProcessor;
import com.pratilipi.pagecontent.genres.GenresContentProcessor;
import com.pratilipi.pagecontent.languages.LanguagesContentProcessor;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentProcessor;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentProcessor;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPratilipiRequest;
import com.pratilipi.service.shared.AddPratilipiResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetBookRequest;
import com.pratilipi.service.shared.GetBookResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.GetUserPratilipiListRequest;
import com.pratilipi.service.shared.GetUserPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;
import com.pratilipi.service.shared.UpdatePratilipiRequest;
import com.pratilipi.service.shared.UpdatePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.GenreData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;
import com.pratilipi.service.shared.data.UserPratilipiData;

@SuppressWarnings("serial")
public class PratilipiServiceImpl
		extends RemoteServiceServlet
		implements PratilipiService {

	@Override
	public AddPratilipiResponse addPratilipi( AddPratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		PratilipiData pratilipiData = request.getPratilipiData();
		if( pratilipiData.hasId() )
			throw new IllegalArgumentException(
					"PratilipiId exist already. Did you mean to call updatePratilipi ?" );

		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( PratilipisContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
			throw new InsufficientAccessException();
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Pratilipi pratilipi = null;
		if( pratilipiData.getType() == PratilipiType.BOOK ) {
			pratilipi = dataAccessor.newBook();
			
			BookData bookData = (BookData) pratilipiData;
			Book book = (Book) pratilipi;
			book.setPublisherId( bookData.getPublisherId() );
			
		} else if( pratilipiData.getType() == PratilipiType.POEM ) {
			pratilipi = dataAccessor.newPoem();

		} else if( pratilipiData.getType() == PratilipiType.STORY ) {
			pratilipi = dataAccessor.newStory();

		} else if( pratilipiData.getType() == PratilipiType.ARTICLE ) {
			pratilipi = dataAccessor.newArticle();
		}

		
		pratilipi.setTitle( pratilipiData.getTitle() );
		pratilipi.setLanguageId( pratilipiData.getLanguageId() );
		pratilipi.setAuthorId( pratilipiData.getAuthorId() );
		pratilipi.setPublicationYear( pratilipiData.getPublicationYear() );
		pratilipi.setSummary( pratilipiData.getSummary() );
		pratilipi.setWordCount( pratilipiData.getWordCount() );

		pratilipi.setType( pratilipiData.getType() );
		pratilipi.setListingDate( new Date() );

		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		dataAccessor.destroy();
		
		return new AddPratilipiResponse( pratilipi.getId() );
	}

	@Override
	public UpdatePratilipiResponse updatePratilipi( UpdatePratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
	
		PratilipiData pratilipiData = request.getPratilipiData();
		if( ! pratilipiData.hasId() )
			throw new IllegalArgumentException(
					"PratilipiId is not set. Did you mean to call addPratilipi ?" );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi(
				pratilipiData.getId(),
				pratilipiData.getType() );
		
		ClaymusHelper claymusHelper = new ClaymusHelper( this.getThreadLocalRequest() );
		
		if ( ( claymusHelper.getCurrentUserId() == pratilipi.getAuthorId()
				&& ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
				|| ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
			throw new InsufficientAccessException();
		
		
		if( pratilipiData.hasTitle() )
			pratilipi.setTitle( pratilipiData.getTitle() );
		if( pratilipiData.hasLanguageId() )
			pratilipi.setLanguageId( pratilipiData.getLanguageId() );
		if( pratilipiData.hasAuthorId() )
			pratilipi.setAuthorId( pratilipiData.getAuthorId() );
		if( pratilipiData.hasPublicationYear() )
			pratilipi.setPublicationYear( pratilipiData.getPublicationYear() );
		if( pratilipiData.hasSummary() )
			pratilipi.setSummary( pratilipiData.getSummary() );
		if( pratilipiData.hasWordCount() )
			pratilipi.setWordCount( pratilipiData.getWordCount() );
		
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		dataAccessor.destroy();

		return new UpdatePratilipiResponse();
	}
	
	@Override
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList(
				request.getPratilipiType(),
				null, 100 ).getDataList();
		
		ArrayList<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
//			Publisher publisher = dataAccessor.getPublisher( book.getPublisherId() );
			
			PratilipiData pratilipiData = request.getPratilipiType().newPratilipiData();
			pratilipiData.setId( pratilipi.getId() );
			pratilipiData.setTitle( pratilipi.getTitle() );
			pratilipiData.setLanguageId( language.getId() );
			pratilipiData.setLanguageName( language.getName() );
			pratilipiData.setAuthorId( author.getId() );
			pratilipiData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
//			bookData.setPublisherId( publisher.getId() );
//			bookData.setPublisherName( publisher.getName() );
			pratilipiData.setPublicationDate( pratilipi.getPublicationYear() );
			pratilipiData.setListingDate( pratilipi.getListingDate() );
			pratilipiData.setSummary( pratilipi.getSummary() );
			pratilipiData.setWordCount( pratilipi.getWordCount() );
			pratilipiData.setType( request.getPratilipiType() );
			pratilipiDataList.add( pratilipiData );
		}

		dataAccessor.destroy();
		
		return new GetPratilipiListResponse( pratilipiDataList );
	}

	
	@Override
	public AddBookResponse addBook( AddBookRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
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
			throws IllegalArgumentException, InsufficientAccessException {

		BookData bookData = request.getBook();
		if( ! bookData.hasId() )
			throw new IllegalArgumentException(
					"BookId is not set. Did you mean to call addBook ?" );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Book book = dataAccessor.getBook( bookData.getId() );
		
		ClaymusHelper claymusHelper = new ClaymusHelper( this.getThreadLocalRequest() );
		
		if ( ( claymusHelper.getCurrentUserId() == book.getAuthorId()
				&& ! claymusHelper.hasUserAccess( BookContentProcessor.ACCESS_ID_BOOK_ADD, false ) )
				|| ! claymusHelper.hasUserAccess( BookContentProcessor.ACCESS_ID_BOOK_UPDATE, false ) )
			throw new InsufficientAccessException();
		
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
	public GetBookResponse getBookById( GetBookRequest request ) {
		
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
			throws IllegalArgumentException, InsufficientAccessException {
		
		LanguageData languageData = request.getLanguage();
		if( languageData.getId() != null )
			throw new IllegalArgumentException(
					"LanguageId exist already. Did you mean to call updateLanguage ?" );

		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( LanguagesContentProcessor.ACCESS_ID_LANGUAGE_ADD, false ) )
			throw new InsufficientAccessException();
		
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Language language = dataAccessor.newLanguage();
		language.setName( languageData.getName() );
		language.setNameEn( languageData.getNameEn() );
		language.setCreationDate( new Date() );
		language = dataAccessor.createOrUpdateLanguage( language );
		dataAccessor.destroy();
		
		return new AddLanguageResponse( language.getId() );
	}

	@Override
	public GetLanguageListResponse getLanguageList(
			GetLanguageListRequest request ) throws InsufficientAccessException {
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( LanguagesContentProcessor.ACCESS_ID_LANGUAGE_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = claymusHelper.hasUserAccess(
				LanguagesContentProcessor.ACCESS_ID_LANGUAGE_READ_META_DATA, false );
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Language> languageList = dataAccessor.getLanguageList();
		dataAccessor.destroy();
		
		
		ArrayList<LanguageData> languageDataList = new ArrayList<>( languageList.size() );
		for( Language language : languageList ) {
			LanguageData languageData = new LanguageData();
			languageData.setId( language.getId() );
			languageData.setName( language.getName() );
			languageData.setNameEn( language.getNameEn() );
			if( sendMetaData )
				languageData.setCreationDate( language.getCreationDate() );
			
			languageDataList.add( languageData );
		}

		
		return new GetLanguageListResponse( languageDataList );
	}


	@Override
	public AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		AuthorData authorData = request.getAuthor();
		if( authorData.getId() != null )
			throw new IllegalArgumentException(
					"AuthorId exist already. Did you mean to call updateAuthor ?" );
		
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( AuthorsContentProcessor.ACCESS_ID_AUTHOR_ADD, false ) )
			throw new InsufficientAccessException();

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.newAuthor();
		author.setLanguageId( authorData.getLanguageId() );
		author.setFirstName( authorData.getFirstName() );
		author.setLastName( authorData.getLastName() );
		author.setPenName( authorData.getPenName() );
		author.setFirstNameEn( authorData.getFirstNameEn() );
		author.setLastNameEn( authorData.getLastNameEn() );
		author.setPenNameEn( authorData.getPenNameEn() );
		author.setEmail( authorData.getEmail().toLowerCase() );
		author.setRegistrationDate( new Date() );
		author = dataAccessor.createOrUpdateAuthor( author );
		dataAccessor.destroy();
		
		return new AddAuthorResponse( author.getId() );
	}

	@Override
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException {
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( AuthorsContentProcessor.ACCESS_ID_AUTHOR_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = claymusHelper.hasUserAccess(
				AuthorsContentProcessor.ACCESS_ID_AUTHOR_READ_META_DATA, false );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Author> authorListCursorTuple =
				dataAccessor.getAuthorList( request.getCursor(), request.getResultCount() );
		List<Author> authorList = authorListCursorTuple.getDataList();
		String cursor = authorListCursorTuple.getCursor();
		
		ArrayList<AuthorData> authorDataList = new ArrayList<>( authorList.size() );
		for( Author author : authorList ) {
			Language language = dataAccessor.getLanguage( author.getLanguageId() );

			AuthorData authorData = new AuthorData();
			authorData.setId( author.getId() );
			authorData.setLanguageId( language.getId() );
			authorData.setLanguageName( language.getName() );
			authorData.setFirstName( author.getFirstName() );
			authorData.setLastName( author.getLastName() );
			authorData.setPenName( author.getPenName() );
			authorData.setFirstNameEn( author.getFirstNameEn() );
			authorData.setLastNameEn( author.getLastNameEn() );
			authorData.setPenNameEn( author.getPenNameEn() );
			authorData.setEmail( author.getEmail() );
			if( sendMetaData )
				authorData.setRegistrationDate( author.getRegistrationDate() );
			
			authorDataList.add( authorData );
		}

		dataAccessor.destroy();

		
		return new GetAuthorListResponse( authorDataList, cursor );
	}

	
	@Override
	public AddGenreResponse addGenre( AddGenreRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		GenreData genreData = request.getGenre();
		if( genreData.getId() != null )
			throw new IllegalArgumentException(
					"GenreId exist already. Did you mean to call updateGenre ?" );

		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_ADD, false ) )
			throw new InsufficientAccessException();
		
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Genre genre = dataAccessor.newGenre();
		genre.setName( genreData.getName() );
		genre.setCreationDate( new Date() );
		genre = dataAccessor.createOrUpdateGenre( genre );
		dataAccessor.destroy();
		
		return new AddGenreResponse( genre.getId() );
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

	
	@Override
	public AddUserPratilipiResponse addUserPratilipi( AddUserPratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		UserPratilipiData userBookData = request.getUserPratilipi();

		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Book book = dataAccessor.getBook( userBookData.getPratilipiId() );
		UserPratilipi userBook = dataAccessor.getUserPratilipi(
				claymusHelper.getCurrentUserId(), book.getId() );
		
		if( claymusHelper.getCurrentUserId() == book.getAuthorId()
				|| ( userBook != null && userBook.getReviewState() != UserReviewState.NOT_SUBMITTED )
				|| ! claymusHelper.hasUserAccess( BookContentProcessor.ACCESS_ID_BOOK_REVIEW_ADD, false ) )
			throw new InsufficientAccessException();

		userBook = dataAccessor.newUserPratilipi();
		userBook.setUserId( claymusHelper.getCurrentUserId() );
		userBook.setPratilipiId( book.getId() );
		userBook.setRating( userBookData.getRating() );
		userBook.setReview( userBookData.getReview() );
		userBook.setReviewState( UserReviewState.PENDING_APPROVAL );
		userBook.setReviewDate( new Date() );

		userBook = dataAccessor.createOrUpdateUserBook( userBook );
		dataAccessor.destroy();
		
		return new AddUserPratilipiResponse( userBook.getId() );
	}
	
	@Override
	public GetUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userBook = dataAccessor.getUserPratilipi(
				request.getUserId(),
				request.getPratilipiId());
		
		User user = dataAccessor.getUser( userBook.getUserId() );
		
		UserPratilipiData userBookData = new UserPratilipiData();
		userBookData.setId( userBook.getId() );
		userBookData.setUserId( user.getId() );
		userBookData.setUserName( user.getFirstName() + " " + user.getLastName() );
		userBookData.setPratilipiId( userBook.getPratilipiId() );
		userBookData.setRating( userBook.getRating() );
		userBookData.setReview( userBook.getReview() );
		userBookData.setReviewState( userBook.getReviewState() );
		userBookData.setReviewDate( userBook.getReviewDate() );
		
		return new GetUserPratilipiResponse( userBookData );
	}

	@Override
	public GetUserPratilipiListResponse getUserPratilipiList(GetUserPratilipiListRequest request) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<UserPratilipi> userBookList = dataAccessor.getUserPratilipiList( request.getPratilipiId() );
		
		ArrayList<UserPratilipiData> userBookDataList = new ArrayList<>( userBookList.size() );
		for( UserPratilipi userBook : userBookList ) {
			User user = dataAccessor.getUser( request.getUserId() );
			
			UserPratilipiData userBookData = new UserPratilipiData();
			userBookData.setPratilipiId(userBook.getPratilipiId());
			userBookData.setUserId(userBook.getUserId());
			userBookData.setRating(userBook.getRating());
			userBookData.setReview(userBook.getReview());
			userBookData.setReviewState(userBook.getReviewState());
			userBookData.setReviewDate(userBook.getReviewDate());
			userBookData.setUserName( user.getFirstName() + " " + user.getLastName() );
			userBookData.setId(userBook.getUserId()+"-"+userBook.getPratilipiId());
			
			userBookDataList.add( userBookData );
			
		}

		dataAccessor.destroy();
		
		return new GetUserPratilipiListResponse( userBookDataList );
	}

}
