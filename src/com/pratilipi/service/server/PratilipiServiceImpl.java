package com.pratilipi.service.server;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.commons.shared.PratilipiHelper;
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
import com.pratilipi.pagecontent.genres.GenresContentProcessor;
import com.pratilipi.pagecontent.languages.LanguagesContentProcessor;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentProcessor;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
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
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.GenreData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiContentData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;
import com.pratilipi.service.shared.data.UserPratilipiData;

@SuppressWarnings("serial")
public class PratilipiServiceImpl extends RemoteServiceServlet
		implements PratilipiService {

	private static final Logger logger =
			Logger.getLogger( PratilipiServiceImpl.class.getName() );

	
	@Override
	public SavePratilipiResponse savePratilipi( SavePratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
	
		ClaymusHelper claymusHelper = new ClaymusHelper( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiData pratilipiData = request.getPratilipiData();
		Pratilipi pratilipi = null;

		try {
			if( pratilipiData.getId() == null ) { // Add Pratilipi usecase

				if ( ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
						throw new InsufficientAccessException();
						
				switch( pratilipiData.getType() ) {
					case BOOK:
						BookData bookData = (BookData) pratilipiData;
						Book book = (Book) dataAccessor.newBook();
						book.setPublisherId( bookData.getPublisherId() );
						pratilipi = book;
						break;
					case POEM:
						pratilipi = dataAccessor.newPoem();
						break;
					case STORY:
						pratilipi = dataAccessor.newStory();
						break;
					case ARTICLE:
						pratilipi = dataAccessor.newArticle();
						break;
					default:
						throw new IllegalArgumentException(
								"PratilipiType '" + pratilipiData.getType()
								+ "' is not yet supported !" );
				}
				
				pratilipi.setType( pratilipiData.getType() );
				pratilipi.setListingDate( new Date() );
				pratilipi.setLastUpdated( new Date() );

			
			} else { // Update Pratilipi usecase
			
				pratilipi =  dataAccessor.getPratilipi( pratilipiData.getId(), pratilipiData.getType() );
				
				if ( ( claymusHelper.getCurrentUserId() == pratilipi.getAuthorId()
						&& ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
						|| ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
					throw new InsufficientAccessException();
				
				pratilipi.setLastUpdated( new Date() );
			}
			
			
			if( pratilipiData.hasPublicDomain() )
				pratilipi.setPublicDomain( pratilipiData.isPublicDomain() );
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
			if( pratilipiData.hasContent() )
				pratilipi.setContent( pratilipiData.getContent() );
			if( pratilipiData.hasWordCount() )
				pratilipi.setWordCount( pratilipiData.getWordCount() );
			if( pratilipiData.hasPageCount() )
				pratilipi.setPageCount( pratilipiData.getPageCount() );
			
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		} finally {
			dataAccessor.destroy();
		}
		
		return new SavePratilipiResponse( pratilipi.getId() );
	}
	
	@Override
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple = dataAccessor.getPratilipiList(
				request.getPratilipiType(),
				request.getPublicDomain(),
				request.getCursor(),
				request.getResultCount() );

		List<Pratilipi> pratilipiList = pratilipiListCursorTuple.getDataList();
		
		ArrayList<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			PratilipiData pratilipiData = request.getPratilipiType().newPratilipiData();
			pratilipiData.setId( pratilipi.getId() );
			pratilipiData.setTitle( pratilipi.getTitle() );
			pratilipiData.setLanguageId( language.getId() );
			pratilipiData.setLanguageName( language.getName() );
			pratilipiData.setAuthorId( author.getId() );
			if( author.getLastName() == null )
				pratilipiData.setAuthorName( author.getFirstName() );
			else
				pratilipiData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
			pratilipiData.setPublicationDate( pratilipi.getPublicationYear() );
			pratilipiData.setPublicDomain( pratilipi.isPublicDomain() );
			pratilipiData.setListingDate( pratilipi.getListingDate() );
			pratilipiData.setSummary( pratilipi.getSummary() );
			pratilipiData.setWordCount( pratilipi.getWordCount() );
			pratilipiData.setType( request.getPratilipiType() );
			pratilipiDataList.add( pratilipiData );
		}

		dataAccessor.destroy();
		
		return new GetPratilipiListResponse( pratilipiDataList, pratilipiListCursorTuple.getCursor() );
	}


	@Override
	public SavePratilipiContentResponse savePratilipiContent(
			SavePratilipiContentRequest request )
			throws IllegalArgumentException,
					InsufficientAccessException,
					UnexpectedServerException {
	
		ClaymusHelper claymusHelper = new ClaymusHelper( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiContentData pratilipiContentData = request.getPratilipiContentData();
		Pratilipi pratilipi =  dataAccessor.getPratilipi(
				pratilipiContentData.getPratilipiId(),
				pratilipiContentData.getPratilipiType() );
				
		try {
			if ( ( claymusHelper.getCurrentUserId() == pratilipi.getAuthorId()
					&& ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
					|| ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
				throw new InsufficientAccessException();
			
			pratilipi.setLastUpdated( new Date() );
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		} finally {
			dataAccessor.destroy();
		}
		
		
		// Fetching Pratilipi content from Blob Store
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		String fileName =
				pratilipiContentData.getPratilipiType().getContentResource()
				+ pratilipiContentData.getPratilipiId();
		BlobEntry blobEntry;
		try {
			blobEntry = blobAccessor.getBlob( fileName );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch blob: " + fileName, e );
			throw new UnexpectedServerException();
		}
		String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );

		
		logger.log( Level.INFO, "Content length: " + content.length() );
		logger.log( Level.INFO, "New page " + pratilipiContentData.getPageNo()
				+ " length: " + pratilipiContentData.getContent().length() );
		
		// Update page
		Matcher matcher =  PratilipiHelper.REGEX_PAGE_BREAK.matcher( content );	
		int pageCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		while( pageCount <= pratilipiContentData.getPageNo() ) {
			pageCount++;
			startIndex = endIndex;
			if( matcher.find() ) {
				endIndex = matcher.end();
				logger.log( Level.INFO, "Page " + pageCount + " length: "
						+ ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ") "
						+ matcher.group() );
			} else {
				endIndex = content.length();
				logger.log( Level.INFO, "Page " + pageCount + " length: "
						+ ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ")");
			}
			
			if( pageCount == pratilipiContentData.getPageNo() ) {
				logger.log( Level.INFO, "Updating page " + pageCount + "..." );
				content = content.substring( 0, startIndex )
						+ pratilipiContentData.getContent()
						+ content.substring( endIndex );
			}
		}
		
		
		logger.log( Level.INFO, "New content length " + content.length() );
		
		
		try {
			blobAccessor.updateBlob( blobEntry, content, Charset.forName( "UTF-8" ) );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to update blob: " + fileName, e );
			throw new UnexpectedServerException();
		}
		
		return new SavePratilipiContentResponse();
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
		author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		author.setRegistrationDate( new Date() );
		author = dataAccessor.createOrUpdateAuthor( author );
		dataAccessor.destroy();
		
		return new AddAuthorResponse( author.getId() );
	}
	
	@Override
	public SaveAuthorResponse saveAuthor(SaveAuthorRequest request)
			throws IllegalArgumentException, InsufficientAccessException {
		
		AuthorData authorData = request.getAuthor();
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		if( ! claymusHelper.hasUserAccess( AuthorsContentProcessor.ACCESS_ID_AUTHOR_ADD, false ) )
			throw new InsufficientAccessException();

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = null;
		
		if( authorData.getId() != null)
			author = dataAccessor.getAuthor( authorData.getId() );
		else
			author = dataAccessor.newAuthor();

		if( authorData.hasLanguageId() )
			author.setLanguageId( authorData.getLanguageId() );
		if( authorData.hasFirstName() )
			author.setFirstName( authorData.getFirstName() );
		if( authorData.hasLastName() )
			author.setLastName( authorData.getLastName() );
		if( authorData.hasFirstNameEn() )
			author.setFirstNameEn( authorData.getFirstNameEn() );
		if( authorData.hasLastNameEn() )
			author.setLastNameEn( authorData.getLastNameEn() );
		if( authorData.hasPenName() )
			author.setPenName( authorData.getPenName() );
		if( authorData.hasPenNameEn() )
			author.setPenNameEn( authorData.getPenNameEn() );
		if( authorData.hasSummary() )
			author.setSummary( authorData.getSummary() );
		if( authorData.hasEmail() )
			author.setEmail( authorData.getEmail().toLowerCase() );
		
		author.setRegistrationDate( new Date() );

		author = dataAccessor.createOrUpdateAuthor( author );
		dataAccessor.destroy();
		
		return new SaveAuthorResponse( author.getId() );
		
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
				|| ! claymusHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_REVIEW_ADD, false ) )
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
