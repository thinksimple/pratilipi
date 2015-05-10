package com.pratilipi.service.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.User;
import com.claymus.taskqueue.Task;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.commons.server.ConvertWordToHtml;
import com.pratilipi.commons.server.GlobalSearch;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.authors.AuthorsContentProcessor;
import com.pratilipi.pagecontent.genres.GenresContentProcessor;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.shared.AddPratilipiGenreRequest;
import com.pratilipi.service.shared.AddPratilipiGenreResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.ConvertPratilipiWordToHtmlRequest;
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
import com.pratilipi.service.shared.GetUserPratilipiListRequest;
import com.pratilipi.service.shared.GetUserPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SaveGenreRequest;
import com.pratilipi.service.shared.SaveGenreResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.SearchRequest;
import com.pratilipi.service.shared.SearchResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.GenreData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.UserPratilipiData;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
public class PratilipiServiceImpl extends RemoteServiceServlet
		implements PratilipiService {

	@Override
	public SavePratilipiResponse savePratilipi( SavePratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
	
		PratilipiData pratilipiData = request.getPratilipiData();
		pratilipiData = PratilipiContentHelper.savePratilipi( pratilipiData, this.getThreadLocalRequest() );
		
		Task task = TaskQueueFactory.newTask()
				.addParam( "pratilipiId", pratilipiData.getId().toString() )
				.addParam( "processData", "true" )
				.setUrl( "/pratilipi/process" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );

		return new SavePratilipiResponse( pratilipiData );
	}
	
	@Override
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request ) {
		
		if( request.getPratilipiFilter() == null )
			return new GetPratilipiListResponse( new ArrayList<PratilipiData>(0), null, null );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple
				= dataAccessor.getPratilipiList(
						request.getPratilipiFilter(),
						request.getCursor(),
						request.getResultCount() );

		List<Pratilipi> pratilipiList = pratilipiListCursorTuple.getDataList();
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( this.getThreadLocalRequest() );
		List<PratilipiData> pratilipiDataList = pratilipiHelper
				.createPratilipiDataList( pratilipiList, false, true, false );
				
		return new GetPratilipiListResponse( pratilipiDataList, null, pratilipiListCursorTuple.getCursor() );
	}


	@Override
	public GetLanguageListResponse getLanguageList( GetLanguageListRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Language> languageList = dataAccessor.getLanguageList();
		
		
		ArrayList<LanguageData> languageDataList = new ArrayList<>( languageList.size() );
		for( Language language : languageList ) {
			if( !language.getHidden() ){
				LanguageData languageData = new LanguageData();
				languageData.setId( language.getId() );
				languageData.setName( language.getName() );
				languageData.setNameEn( language.getNameEn() );
				languageData.setCreationDate( language.getCreationDate() );
				languageData.setHidden( language.getHidden() );
				
				languageDataList.add( languageData );
			}
		}

		
		return new GetLanguageListResponse( languageDataList );
	}


	@Override
	public SaveAuthorResponse saveAuthor( SaveAuthorRequest request )
			throws InsufficientAccessException, InvalidArgumentException {
		
		AuthorData authorData = request.getAuthor();
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( this.getThreadLocalRequest() );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Author author = null;
		
		if( authorData.getId() == null ) { // Add Author usecase
		
			if( ! AuthorContentHelper.hasRequestAccessToAddAuthorData( this.getThreadLocalRequest() ) )
				throw new InsufficientAccessException();
			
			boolean isAuthorExist = dataAccessor.getAuthorByEmailId( authorData.getEmail() ) != null ?
						true : false;
			if( isAuthorExist )
				throw new InvalidArgumentException( "This author is already registered !" );
			
			author = dataAccessor.newAuthor();
			author.setRegistrationDate( new Date() );
			
			author = dataAccessor.createOrUpdateAuthor( author );
			
			Page page = dataAccessor.newPage();
			page.setType( PratilipiPageType.AUTHOR.toString() );
			page.setUri( PratilipiPageType.AUTHOR.getUrlPrefix() + author.getId() );
			page.setPrimaryContentId( author.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
			
		} else { // Update Author usecase
		
			author = dataAccessor.getAuthor( authorData.getId() );
		
			if( ! AuthorContentHelper.hasRequestAccessToUpdateAuthorData( this.getThreadLocalRequest(), author ) )
				throw new InsufficientAccessException();

		}
		
		if( authorData.hasLanguageId() )
			author.setLanguageId( authorData.getLanguageId() );
		if( authorData.hasFirstName() )
			author.setFirstName( authorData.getFirstName() );
		if( authorData.hasLastName() )
			author.setLastName( authorData.getLastName() );
		if( authorData.hasPenName() )
			author.setPenName( authorData.getPenName() );
		if( authorData.hasFirstNameEn() )
			author.setFirstNameEn( authorData.getFirstNameEn() );
		if( authorData.hasLastNameEn() )
			author.setLastNameEn( authorData.getLastNameEn() );
		if( authorData.hasPenNameEn() )
			author.setPenNameEn( authorData.getPenNameEn() );
		if( authorData.hasSummary() )
			author.setSummary( authorData.getSummary() );
		if( authorData.hasEmail() )
			author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		
		
		author = dataAccessor.createOrUpdateAuthor( author );

		
		// Creating/Updating search index
		Task task = TaskQueueFactory.newTask()
				.addParam( "authorId", author.getId().toString() )
				.addParam( "processData", "true" )
				.setUrl( "/author/process" );
		TaskQueueFactory.getAuthorTaskQueue().add( task );

		
		return new SaveAuthorResponse( pratilipiHelper.createAuthorData( author.getId() ) );
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

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
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

		
		return new GetAuthorListResponse( authorDataList, cursor );
	}

	
	@Override
	public SaveGenreResponse saveGenre( SaveGenreRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		GenreData genreData = request.getGenre();
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_ADD, false ) )
			throw new InsufficientAccessException();
		

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Genre genre = null;
		
		if( genreData.getId() == null) { // Add Genre usecase
			
			genre = dataAccessor.newGenre();
			genre.setCreationDate( new Date() );
				
		} else { // Update Genre usecase
		
			genre = dataAccessor.getGenre( genreData.getId() );
			
		}
		
		genre.setName( genreData.getName() );
		
		
		genre = dataAccessor.createOrUpdateGenre( genre );
		
		return new SaveGenreResponse( genre.getId() );
	}

	public GetGenreListResponse getGenreList( GetGenreListRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );
		
		if( ! pratilipiHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_LIST, false ) )
			throw new InsufficientAccessException();

		boolean sendMetaData = pratilipiHelper.hasUserAccess(
				GenresContentProcessor.ACCESS_ID_GENRE_READ_META_DATA, false );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Genre> genreList = dataAccessor.getGenreList();
		
		
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
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );

		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );

		if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( this.getThreadLocalRequest(), pratilipi ) )
			throw new InsufficientAccessException();
		
		
		PratilipiGenre pratilipiGenre = dataAccessor.newPratilipiGenre();
		pratilipiGenre.setPratilipiId( request.getPratilipiId() );
		pratilipiGenre.setGenreId( request.getGenreId() );
		
		dataAccessor.createPratilipiGenre( pratilipiGenre );

		
		// Updating search index
		Task task = TaskQueueFactory.newTask()
				.addParam( "pratilipiId", request.getPratilipiId().toString() )
				.addParam( "processData", "true" )
				.setUrl( "/pratilipi/process" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		
		return new AddPratilipiGenreResponse();
	}

	public DeletePratilipiGenreResponse deletePratilipiGenre( DeletePratilipiGenreRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );

		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );

		if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( this.getThreadLocalRequest(), pratilipi ) )
			throw new InsufficientAccessException();

		
		dataAccessor.deletePratilipiGenre( request.getPratilipiId(), request.getGenreId() );
		
		
		// Updating search index
		Task task = TaskQueueFactory.newTask()
				.addParam( "pratilipiId", request.getPratilipiId().toString() )
				.addParam( "processData", "true" )
				.setUrl( "/pratilipi/process" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );

		
		return new DeletePratilipiGenreResponse();
	}

	
	@Override
	public AddUserPratilipiResponse addUserPratilipi( AddUserPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		UserPratilipiData userPratilipiData = request.getUserPratilipi();

		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( this.getThreadLocalRequest() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( userPratilipiData.getPratilipiId() );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi(
				pratilipiHelper.getCurrentUserId(), pratilipi.getId() );
		
		if( userPratilipiData.hasRating() ){
			
			if( !PratilipiContentHelper.hasRequestAccessToAddPratilipiReview( this.getThreadLocalRequest(), pratilipi ) )
				throw new InsufficientAccessException();
			
			if( userPratilipi == null ){
				userPratilipi = dataAccessor.newUserPratilipi();
				userPratilipi.setUserId( pratilipiHelper.getCurrentUserId() );
				userPratilipi.setPratilipiId( pratilipi.getId() );
				userPratilipi.setRating( userPratilipiData.getRating() );
				
				pratilipi.setRatingCount( ( pratilipi.getRatingCount() == null ? 0 : pratilipi.getRatingCount() ) + 1 );
				pratilipi.setStarCount( ( pratilipi.getStarCount() == null ? 0 : pratilipi.getStarCount() ) + userPratilipiData.getRating() );
			} else {
				int earlierRating = userPratilipi.getRating() == null ? 0 : userPratilipi.getRating();
				userPratilipi.setRating( userPratilipiData.getRating() );
				
				pratilipi.setStarCount( ( pratilipi.getStarCount() == null ? 0 : pratilipi.getStarCount() ) - earlierRating + userPratilipiData.getRating() );
				
				if( userPratilipiData.getRating() == 0 )
					pratilipi.setRatingCount( pratilipi.getRatingCount() - 1 );
				
				if( earlierRating == 0 )
					pratilipi.setRatingCount( pratilipi.getRatingCount() + 1 );
			}
			
			
			userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
		}
		
		if( userPratilipiData.hasReview() ){
			
			if( !PratilipiContentHelper.hasRequestAccessToAddPratilipiReview( this.getThreadLocalRequest(), pratilipi ) )
				throw new InsufficientAccessException();
	
			if( userPratilipi == null ){
				userPratilipi = dataAccessor.newUserPratilipi();
				userPratilipi.setUserId( pratilipiHelper.getCurrentUserId() );
				userPratilipi.setPratilipiId( pratilipi.getId() );
				userPratilipi.setReviewDate( new Date() );
				userPratilipi.setReviewState( UserReviewState.PENDING_APPROVAL );
			}
			
			userPratilipi.setReview( userPratilipiData.getReview() );
			userPratilipi.setReviewLastUpdatedDate( new Date() );
	
			userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		}
		
		return new AddUserPratilipiResponse( userPratilipi.getId() );
	}
	
	@Override
	public GetUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Long userId = request.getUserId();
		Long pratilipiId = request.getPratilipiId();
		if( userId == null ){
			PratilipiHelper pratilipiHelper = PratilipiHelper.get( this.getThreadLocalRequest() );
			userId = pratilipiHelper.getCurrentUserId();
		}

		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi(
				userId,
				pratilipiId );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		
		if( userPratilipi != null ){
			User user = dataAccessor.getUser( userPratilipi.getUserId() );
			userPratilipiData.setId( userPratilipi.getId() );
			userPratilipiData.setUserId( user.getId() );
			userPratilipiData.setUserName( user.getFirstName() + " " + user.getLastName() );
			userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
			userPratilipiData.setRating( userPratilipi.getRating() );
			userPratilipiData.setReview( userPratilipi.getReview() );
			userPratilipiData.setReviewState( userPratilipi.getReviewState() );
			userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		}
		
		return new GetUserPratilipiResponse( userPratilipiData );
	}

	@Override
	public GetUserPratilipiListResponse getUserPratilipiList(GetUserPratilipiListRequest request) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
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

		return new GetUserPratilipiListResponse( userBookDataList );
	}

	@Override
	public SearchResponse search( SearchRequest request ) {
		GlobalSearch globalSearch = new GlobalSearch( this.getThreadLocalRequest() );
		globalSearch.setCursor( request.getCursor() );
		if( request.getResultCount() != null )
			globalSearch.setResultCount( request.getResultCount() );
		
		List<IsSerializable> dataList = request.getDocType() == null || request.getDocType().isEmpty()
				? globalSearch.search( request.getQuery() )
				: globalSearch.search( request.getQuery(), "docType:" + request.getDocType() );
		
		return new SearchResponse( dataList, globalSearch.getCursor() );
	}
	
	@Override
	public void ConvertWordToHtml( ConvertPratilipiWordToHtmlRequest request ) throws IOException {

		Long pratilipiId = request.getPratilipiId();
		ConvertWordToHtml convertWordToHtml = new ConvertWordToHtml( pratilipiId );
		convertWordToHtml.convert();
		
	}
	
}
