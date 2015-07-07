package com.pratilipi.data.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UriAliasUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.site.AccessTokenFilter;

public class AuthorDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( AuthorDataUtil.class.getName() );
	private static final Gson gson = new GsonBuilder().create();
	
	private static final String IMAGE_FOLDER = "author-image";

	
	public static boolean hasAccessToListAuthorData() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.AUTHOR_LIST );
	}

	public static boolean hasAccessToAddAuthorData() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.AUTHOR_ADD );
	}

	public static boolean hasAccessToUpdateAuthorData( Author author ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.AUTHOR_UPDATE )
				|| ( author.getUserId() != null && author.getUserId().equals( accessToken.getUserId() ) );
	}
	
	
	public static String createAuthorImageUrl( Author author ) {
		if( author.hasCustomCover() ) {
			String domain = "//" + author.getId() % 10 + "." + SystemProperty.get( "cdn" );
			String uri = "/author/image?authorId=" + author.getId() + "&width=150&version=" + author.getLastUpdated().getTime();
			return domain + uri;
		} else {
			String domain = "//10." + SystemProperty.get( "cdn" );
			String uri = "/author/image?width=150";
			return domain + uri;
		}
	}
	
	
	public static AuthorData createAuthorData( Author author ) {
		return createAuthorData(author, false, false );
	}
	
	public static AuthorData createAuthorData( Author author, boolean includeAll, boolean includeMetaData ) {
		if( author == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page authorPage = dataAccessor.getPage( PageType.AUTHOR, author.getId() );
		
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );

		authorData.setFirstName( author.getFirstName() );
		authorData.setLastName( author.getLastName() );
		authorData.setPenName( author.getPenName() );

		if( author.getFirstName() != null && author.getLastName() == null )
			authorData.setName( author.getFirstName() );
		else if( author.getFirstName() == null && author.getLastName() != null )
			authorData.setName( author.getLastName() );
		else if( author.getFirstName() != null && author.getLastName() != null )
			authorData.setName( author.getFirstName() + " " + author.getLastName() );
		
		if( authorData.getName() != null && author.getPenName() == null )
			authorData.setFullName( authorData.getName() );
		else if( authorData.getName() == null && author.getPenName() != null )
			authorData.setFullName( author.getPenName() );
		else if( authorData.getName() != null && author.getPenName() != null )
			authorData.setFullName( authorData.getName() + " '" + author.getPenName() + "'" );
		
		authorData.setFirstNameEn( author.getFirstNameEn() );
		authorData.setLastNameEn( author.getLastNameEn() );
		authorData.setPenNameEn( author.getPenNameEn() );
		
		if( author.getFirstNameEn() != null && author.getLastNameEn() == null )
			authorData.setNameEn( author.getFirstNameEn() );
		else if( author.getFirstNameEn() == null && author.getLastNameEn() != null )
			authorData.setNameEn( author.getLastNameEn() );
		else if( author.getFirstNameEn() != null && author.getLastNameEn() != null )
			authorData.setNameEn( author.getFirstNameEn() + " " + author.getLastNameEn() );
		
		if( authorData.getNameEn() != null && author.getPenNameEn() == null )
			authorData.setFullNameEn( authorData.getNameEn() );
		else if( authorData.getNameEn() == null && author.getPenNameEn() != null )
			authorData.setFullNameEn( author.getPenNameEn() );
		else if( authorData.getNameEn() != null && author.getPenNameEn() != null )
			authorData.setFullNameEn( authorData.getNameEn() + " '" + author.getPenNameEn() + "'" );

		if( includeMetaData )
			authorData.setEmail( author.getEmail() );
		authorData.setLanguage( author.getLanguage() );
		if( includeAll )
			authorData.setSummary( author.getSummary() );
		
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setImageUrl( createAuthorImageUrl( author ) );

		authorData.setRegistrationDate( author.getRegistrationDate() );
		authorData.setContentPublished( author.getContentPublished() );
		
		return authorData;
	}
	
	public static List<AuthorData> createAuthorDataList( List<Author> authorList ) {
		List<AuthorData> authorDataList = new ArrayList<>( authorList.size() );
		for( Author author : authorList )
			authorDataList.add( createAuthorData( author ) );
		return authorDataList;
	}
	

	public static DataListCursorTuple<AuthorData> getAuthorDataList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) throws InsufficientAccessException {
		
		if( ! hasAccessToListAuthorData() )
			throw new InsufficientAccessException();
		
		DataListCursorTuple<Author> authorListCursorTuple = DataAccessorFactory
				.getDataAccessor()
				.getAuthorList( authorFilter, cursor, resultCount );
		
		List<AuthorData> authorDataList = createAuthorDataList( authorListCursorTuple.getDataList() );
		
		return new DataListCursorTuple<AuthorData>( authorDataList, authorListCursorTuple.getCursor() );
	}
	
	public static AuthorData saveAuthorData( AuthorData authorData )
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = null;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken(); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		
		if( authorData.getId() == null ) { // Add Author usecase
			
			if( ! hasAccessToAddAuthorData() )
				throw new InsufficientAccessException();
			
			if( ! authorData.hasLanguage() )
				throw new InvalidArgumentException( "'language' is missing !" );

			if( authorData.hasEmail() && dataAccessor.getAuthorByEmailId( authorData.getEmail().toLowerCase() ) != null )
				throw new InvalidArgumentException( "Email is already linked with an exiting Author !" );
			
			author = dataAccessor.newAuthor();
			auditLog.setAccessType( AccessType.AUTHOR_ADD );
			auditLog.setEventDataOld( gson.toJson( author ) );
			
			author.setRegistrationDate( new Date() );
			author.setLastUpdated( new Date() );

		} else { // Update Author usecase

			author = dataAccessor.getAuthor( authorData.getId() );

			if( ! hasAccessToUpdateAuthorData( author ) )
				throw new InsufficientAccessException();
			
			auditLog.setAccessType( AccessType.AUTHOR_UPDATE );
			auditLog.setEventDataOld( gson.toJson( author ) );

			author.setLastUpdated( new Date() );
		}
		
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
		
		if( authorData.hasEmail() )
			author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		if( authorData.hasLanguage() )
			author.setLanguage( authorData.getLanguage() );
		if( authorData.hasSummary() )
			author.setSummary( authorData.getSummary() );
		
		
		author = dataAccessor.createOrUpdateAuthor( author );

		if( authorData.getId() == null )
			createOrUpdateAuthorPageUrl( author.getId() );

		auditLog.setEventDataNew( gson.toJson( author ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		return createAuthorData( author );
	}
	
	public static BlobEntry getAuthorImage( Long authorId, Integer width )
			throws UnexpectedServerException {

		String fileName = "";
		if( authorId != null && DataAccessorFactory.getDataAccessor().getAuthor( authorId ).hasCustomCover() )
			fileName = IMAGE_FOLDER + "/original/" + authorId;
		else
			fileName = IMAGE_FOLDER + "/original/" + "author";

		try {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
			if( width != null )
				blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width, (int) ( 1.5 * width ) ) );
			return blobEntry;
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch author image.", e );
			throw new UnexpectedServerException();
		}
	}
	
	public static void saveAuthorImage( Long authorId, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );

		if( ! hasAccessToUpdateAuthorData( author ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		try {
			blobEntry.setName( IMAGE_FOLDER + "/original/" + authorId );
			blobAccessor.createOrUpdateBlob( blobEntry );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to create/update author image.", e );
			throw new UnexpectedServerException();
		}
		

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.AUTHOR_UPDATE );
		auditLog.setEventDataOld( gson.toJson( author ) );

		author.setCustomCover( true );
		author.setLastUpdated( new Date() );
		author = dataAccessor.createOrUpdateAuthor( author );

		auditLog.setEventDataNew( gson.toJson( author ) );
		auditLog.setEventComment( "Uploaded profile image." );
		auditLog = dataAccessor.createAuditLog( auditLog );
	}
	
	public static boolean createOrUpdateAuthorPageUrl( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );
		
		if( page == null ) {
			page = dataAccessor.newPage();
			page.setType( PageType.AUTHOR );
			page.setUri( PageType.AUTHOR.getUrlPrefix() + authorId );
			page.setPrimaryContentId( authorId );
			page.setCreationDate( new Date() );
		}
		
		String uriAlias = UriAliasUtil.generateUriAlias(
				page.getUriAlias(),
				"/", author.getFirstNameEn(), author.getLastNameEn(), author.getPenNameEn() );

		if( uriAlias.equals( page.getUriAlias() ) )
			return false;
		
		page.setUriAlias( uriAlias );
		page = dataAccessor.createOrUpdatePage( page );
		return true;
	}
	
	public static boolean createOrUpdateAuthorDashboardPageUrl( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );
		
		Page dashboardPage = dataAccessor.getPage( PageType.AUTHOR_DASHBOARD, authorId );
		if( dashboardPage == null ) {
			dashboardPage = dataAccessor.newPage();
			dashboardPage.setType( PageType.AUTHOR_DASHBOARD );
			dashboardPage.setUri( page.getUri() + "/dashboard" );
			dashboardPage.setPrimaryContentId( authorId );
			dashboardPage.setCreationDate( new Date() );
			if( page.getUriAlias() == null )
				dashboardPage = dataAccessor.createOrUpdatePage( dashboardPage );
		}
		
		if( page.getUriAlias() == null ) {
		
			if( dashboardPage.getUriAlias() == null )
				return false;
			
			dashboardPage.setUriAlias( null );
		
		} else {
			
			String uriAlias = page.getUriAlias() + "/dashboard";
			if( uriAlias.equals( dashboardPage.getUriAlias() ) )
				return false;
			
			dashboardPage.setUriAlias( uriAlias );
			
		}

		dashboardPage = dataAccessor.createOrUpdatePage( dashboardPage );

		return true;
	}
	
	public static boolean updateAuthorStats( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();
		
		if( pratilipiList.size() == 0 )
			return false;
		
		int contentPublished = 0;
		long totalReadCount = 0;
		for( Pratilipi pratilipi : pratilipiList ) {
			if( pratilipi.getState() == PratilipiState.PUBLISHED || pratilipi.getState() == PratilipiState.PUBLISHED_PAID )
				contentPublished++;
			totalReadCount = totalReadCount + pratilipi.getReadCount();
		}
		
		Author author = dataAccessor.getAuthor( authorId );
		if( (int) author.getContentPublished() != contentPublished || (long) author.getTotalReadCount() != totalReadCount ) {
			dataAccessor.beginTx();
			author = dataAccessor.getAuthor( authorId );
			author.setContentPublished( contentPublished );
			author.setTotalReadCount( totalReadCount );
			author = dataAccessor.createOrUpdateAuthor( author );
			dataAccessor.commitTx();
			return true;
		} else {
			return false;
		}

	}
	
	public static void updateAuthorSearchIndex( Long authorId )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		Author author = dataAccessor.getAuthor( authorId );
		searchAccessor.indexAuthorData( createAuthorData( author, true, true ) );
	}
	
}
