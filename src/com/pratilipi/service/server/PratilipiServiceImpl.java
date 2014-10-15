package com.pratilipi.service.server;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.IllegalArgumentException;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.User;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.authors.AuthorsContentProcessor;
import com.pratilipi.pagecontent.genres.GenresContentProcessor;
import com.pratilipi.pagecontent.languages.LanguagesContentProcessor;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentProcessor;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPratilipiGenreRequest;
import com.pratilipi.service.shared.AddPratilipiGenreResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.DeletePratilipiGenreRequest;
import com.pratilipi.service.shared.DeletePratilipiGenreResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetGenreListRequest;
import com.pratilipi.service.shared.GetGenreListResponse;
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
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SaveGenreRequest;
import com.pratilipi.service.shared.SaveGenreResponse;
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.GenreData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiContentData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;
import com.pratilipi.service.shared.data.UserPratilipiData;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
public class PratilipiServiceImpl extends RemoteServiceServlet
		implements PratilipiService {

	private static final Logger logger =
			Logger.getLogger( PratilipiServiceImpl.class.getName() );

	
	@Override
	public SavePratilipiResponse savePratilipi( SavePratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException,
					UnexpectedServerException {
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiData pratilipiData = request.getPratilipiData();
		Pratilipi pratilipi = null;

		try {
			if( pratilipiData.getId() == null ) { // Add Pratilipi usecase

				if ( ! PratilipiContentHelper.hasRequestAccessToAddData( this.getThreadLocalRequest() ) )
						throw new InsufficientAccessException();
						
				pratilipi = dataAccessor.newPratilipi();
				pratilipi.setType( pratilipiData.getType() );
				pratilipi.setListingDate( new Date() );
				pratilipi.setLastUpdated( new Date() );

			
			} else { // Update Pratilipi usecase
			
				pratilipi =  dataAccessor.getPratilipi( pratilipiData.getId() );
				
				if( ! PratilipiContentHelper.hasRequestAccessToUpdateData( this.getThreadLocalRequest(), pratilipi ) )
					throw new InsufficientAccessException();
				
				pratilipi.setLastUpdated( new Date() );
			}
			
			
			if( pratilipiData.hasPublicDomain() )
				pratilipi.setPublicDomain( pratilipiData.isPublicDomain() );
			if( pratilipiData.hasTitle() )
				pratilipi.setTitle( pratilipiData.getTitle() );
			if( pratilipiData.hasTitleEn() )
				pratilipi.setTitleEn( pratilipiData.getTitleEn() );
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
			if( pratilipiData.hasPageCount() )
				pratilipi.setPageCount( pratilipiData.getPageCount() );
			if( pratilipiData.hasState() ) {

				if( pratilipiData.getState() == PratilipiState.PUBLISHED ) {
					if( pratilipi.getSummary() == null ) {
						throw new IllegalArgumentException(
								pratilipi.getType().getName() + " summary is missing. " +
								pratilipi.getType().getName() + " can not be published without a summary." );
					}
				}

				pratilipi.setState( pratilipiData.getState() );
			}
				
				
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		} finally {
			dataAccessor.destroy();
		}

		
		Task task = TaskQueueFactory.newTask();
		task.addParam( "pratilipiId", pratilipi.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getCreateOrUpdateDefaultCoverTaskQueue();
		taskQueue.add( task );

		
		pratilipiData = new PratilipiData();
		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setPageUrl( PratilipiHelper.getPageUrl( pratilipi.getType(), pratilipi.getId() ) );
		
		return new SavePratilipiResponse( pratilipiData );
	}
	
	@Override
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple
				= dataAccessor.getPratilipiList(
						request.getPratilipiFilter(),
						request.getCursor(),
						request.getResultCount() );

		List<Pratilipi> pratilipiList = pratilipiListCursorTuple.getDataList();
		
		ArrayList<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			PratilipiData pratilipiData = new PratilipiData();
			pratilipiData.setId( pratilipi.getId() );
			pratilipiData.setType( pratilipi.getType() );
			pratilipiData.setTitle( pratilipi.getTitle() );
			pratilipiData.setLanguageId( language.getId() );
			pratilipiData.setLanguageName( language.getName() );
			pratilipiData.setAuthorId( author.getId() );
			if( author.getLastName() == null )
				pratilipiData.setAuthorName( author.getFirstName() );
			else
				pratilipiData.setAuthorName( author.getFirstName() + " " + author.getLastName() );
			pratilipiData.setPublicationYear( pratilipi.getPublicationYear() );
			pratilipiData.setPublicDomain( pratilipi.isPublicDomain() );
			pratilipiData.setListingDate( pratilipi.getListingDate() );
			pratilipiData.setSummary( pratilipi.getSummary() );
			pratilipiData.setWordCount( pratilipi.getWordCount() );
			pratilipiData.setType( pratilipi.getType() );
			pratilipiDataList.add( pratilipiData );
			
			pratilipiData.setPageUrl(
					PratilipiHelper.getPageUrl( pratilipi.getType(), pratilipi.getId() ) );
			pratilipiData.setCoverImageUrl(
					PratilipiHelper.getCoverImage300Url( pratilipi.getType(), pratilipi.getId(), false ) );
			pratilipiData.setAuthorPageUrl(
					PratilipiHelper.getAuthorPageUrl( pratilipi.getAuthorId() ) );
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
	
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiContentData pratilipiContentData = request.getPratilipiContentData();
		Pratilipi pratilipi =  dataAccessor.getPratilipi( pratilipiContentData.getPratilipiId() );
				
		try {
			if ( ( pratilipiHelper.getCurrentUserId() == pratilipi.getAuthorId()
					&& ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
					|| ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
				throw new InsufficientAccessException();
			
			pratilipi.setLastUpdated( new Date() );
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		} finally {
			dataAccessor.destroy();
		}
		
		
		// Fetching Pratilipi content from Blob Store
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		String fileName = PratilipiHelper.getContent(
				pratilipi.getType(),
				pratilipiContentData.getPratilipiId() );
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

		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( LanguagesContentProcessor.ACCESS_ID_LANGUAGE_ADD, false ) )
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
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( LanguagesContentProcessor.ACCESS_ID_LANGUAGE_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = pratilipiHelper.hasUserAccess(
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
		
		
		PratilipiHelper claymusHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
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
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = null;
		
		if( authorData.getId() == null) { // Add Author usecase
		
			if( ! AuthorContentHelper.hasRequestAccessToAddData( this.getThreadLocalRequest() ) ) {
				dataAccessor.destroy();
				throw new InsufficientAccessException();
			}
			
			author = dataAccessor.newAuthor();
			author.setRegistrationDate( new Date() );

		} else { // Update Author usecase
		
			author = dataAccessor.getAuthor( authorData.getId() );
		
			if( ! AuthorContentHelper.hasRequestAccessToUpdateData( this.getThreadLocalRequest(), author ) ) {
				dataAccessor.destroy();
				throw new InsufficientAccessException();
			}

		}
		
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
			author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		
		
		author = dataAccessor.createOrUpdateAuthor( author );
		dataAccessor.destroy();
		
		return new SaveAuthorResponse( author.getId() );
		
	}

	@Override
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException {
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( AuthorsContentProcessor.ACCESS_ID_AUTHOR_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = pratilipiHelper.hasUserAccess(
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
//			authorData.setLanguageName( language.getName() );
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
	public SaveGenreResponse saveGenre( SaveGenreRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		GenreData genreData = request.getGenre();
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_ADD, false ) )
			throw new InsufficientAccessException();
		

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Genre genre = null;
		
		if( genreData.getId() == null) { // Add Genre usecase
			
			genre = dataAccessor.newGenre();
			genre.setCreationDate( new Date() );
				
		} else { // Update Genre usecase
		
			genre = dataAccessor.getGenre( genreData.getId() );
			
		}
		
		genre.setName( genreData.getName() );
		
		
		genre = dataAccessor.createOrUpdateGenre( genre );
		dataAccessor.destroy();
		
		return new SaveGenreResponse( genre.getId() );
	}

	public GetGenreListResponse getGenreList( GetGenreListRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = pratilipiHelper.hasUserAccess(
				GenresContentProcessor.ACCESS_ID_GENRE_READ_META_DATA, false );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Genre> genreList = dataAccessor.getGenreList();
		dataAccessor.destroy();
		
		
		ArrayList<GenreData> genreDataList = new ArrayList<>( genreList.size() );
		for( Genre genre : genreList ) {
			GenreData genreData = new GenreData();
			genreData.setId( genre.getId() );
			genreData.setName( genre.getName() );
			if( sendMetaData )
				genreData.setCreationDate( genre.getCreationDate() );
			
			genreDataList.add( genreData );
		}


		return new GetGenreListResponse( genreDataList );
	}
	
	
	public AddPratilipiGenreResponse addPratilipiGenre( AddPratilipiGenreRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );

		if( ( pratilipiHelper.getCurrentUserId().equals( pratilipi.getAuthorId() )
				&& ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
				|| ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
			throw new InsufficientAccessException();
		
		
		PratilipiGenre pratilipiGenre = dataAccessor.newPratilipiGenre();
		pratilipiGenre.setPratilipiId( request.getPratilipiId() );
		pratilipiGenre.setGenreId( request.getGenreId() );
		
		dataAccessor.createPratilipiGenre( pratilipiGenre );
		dataAccessor.destroy();
		
		return new AddPratilipiGenreResponse();
	}

	public DeletePratilipiGenreResponse deletePratilipiGenre( DeletePratilipiGenreRequest request )
			throws IllegalArgumentException, InsufficientAccessException {

		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );

		if( ( pratilipiHelper.getCurrentUserId().equals( pratilipi.getAuthorId() )
				&& ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_ADD, false ) )
				|| ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_UPDATE, false ) )
			throw new InsufficientAccessException();

		
		dataAccessor.deletePratilipiGenre( request.getPratilipiId(), request.getGenreId() );
		dataAccessor.destroy();
		
		return new DeletePratilipiGenreResponse();
	}

	
	@Override
	public AddUserPratilipiResponse addUserPratilipi( AddUserPratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException {
		
		UserPratilipiData userBookData = request.getUserPratilipi();

		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Pratilipi book = dataAccessor.getPratilipi( userBookData.getPratilipiId() );
		UserPratilipi userBook = dataAccessor.getUserPratilipi(
				pratilipiHelper.getCurrentUserId(), book.getId() );
		
		if( pratilipiHelper.getCurrentUserId() == book.getAuthorId()
				|| ( userBook != null && userBook.getReviewState() != UserReviewState.NOT_SUBMITTED )
				|| ! pratilipiHelper.hasUserAccess( PratilipiContentProcessor.ACCESS_ID_PRATILIPI_REVIEW_ADD, false ) )
			throw new InsufficientAccessException();

		userBook = dataAccessor.newUserPratilipi();
		userBook.setUserId( pratilipiHelper.getCurrentUserId() );
		userBook.setPratilipiId( book.getId() );
		userBook.setRating( userBookData.getRating() );
		userBook.setReview( userBookData.getReview() );
		userBook.setReviewState( UserReviewState.PENDING_APPROVAL );
		userBook.setReviewDate( new Date() );

		userBook = dataAccessor.createOrUpdateUserPratilipi( userBook );
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
