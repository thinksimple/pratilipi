package com.pratilipi.data.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.Website;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.common.util.PratilipiContentUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UriAliasUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.Memcache;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.filter.AccessTokenFilter;

public class PratilipiDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiDataUtil.class.getName() );

	
	private static final String CONTENT_FOLDER 		 = "pratilipi-content/pratilipi";
	private static final String IMAGE_CONTENT_FOLDER = "pratilipi-content/image";
	private static final String KEYWORDS_FOLDER 	 = "pratilipi-keywords/pratilipi";
	private static final String COVER_FOLDER 		 = "pratilipi-cover/original";
	private static final String RESOURCE_FOLDER		 = "pratilipi-resource";


	public static boolean hasAccessToListPratilipiData( PratilipiFilter pratilipiFilter ) {
		
		// Case 1: User can list PUBLISHED Pratilipis.

		if( pratilipiFilter.getState() == PratilipiState.PUBLISHED )
			return true;

		
		// Case 2: User with PRATILIPI_LIST access can list Pratilipis in any State.

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipiFilter.getLanguage(), AccessType.PRATILIPI_LIST ) )
			return true;

		
		// Case 3: User can list Pratilipis in any State, except DELETED state, for their own Author profile.

		if( pratilipiFilter.getState() == PratilipiState.DELETED )
			return false;
		
		if( pratilipiFilter.getAuthorId() == null )
			return false;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipiFilter.getAuthorId() );
		if( author == null )
			return false;
		
		return accessToken.getUserId().equals( author.getUserId() );
		
	}
	
	public static boolean hasAccessToAddPratilipiData( PratilipiData pratilipiData ) {
		
		Author author = pratilipiData.getAuthorId() == null
				? null :
				DataAccessorFactory.getDataAccessor().getAuthor( pratilipiData.getAuthorId() );

	
		// Case 1: Content pieces can be added against ACTIVE Author profiles only.
		if( author != null && author.getState() != AuthorState.ACTIVE )
			return false;
		
		
		// Case 2: User with PRATILIPI_ADD access can add Pratilipi against any Author profile.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipiData.getLanguage(), AccessType.PRATILIPI_ADD ) )
			return true;

		
		// Case 3: User can add Pratilipi against his/her own Author profile.
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return true;
		
		
		return false;
		
	}

	public static boolean hasAccessToUpdatePratilipiData( Pratilipi pratilipi, PratilipiData pratilipiData ) {

		// Case 1: Only DRAFTED/SUBMITTED/PUBLISHED content pieces can be updated.
		if( pratilipi.getState() != PratilipiState.DRAFTED
				&& pratilipi.getState() != PratilipiState.SUBMITTED
				&& pratilipi.getState() != PratilipiState.PUBLISHED )
			return false;

		
		Author author = pratilipi.getAuthorId() == null
				? null :
				DataAccessorFactory.getDataAccessor().getAuthor( pratilipi.getAuthorId() );

	
		// Case 2: Content pieces can be updated only if Author profile is ACTIVE.
		if( author != null && author.getState() != AuthorState.ACTIVE )
			return false;
		
		
		// Case 3: User with PRATILIPI_UPDATE access can update any content piece.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipi.getLanguage(), AccessType.PRATILIPI_UPDATE ) ) {
			if( pratilipiData == null || ! pratilipiData.hasLanguage() || pratilipiData.getLanguage() == pratilipi.getLanguage() )
				return true;
			else if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipiData.getLanguage(), AccessType.PRATILIPI_UPDATE ) )
				return true;
		}

		
		// Case 4: User can update content piece linked with his/her own Author profile.
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return true;
		
		
		return false;
		
	}
	
	public static boolean hasAccessToReadPratilipiMetaData( Pratilipi pratilipi ) {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipi.getLanguage(), AccessType.PRATILIPI_READ_META ) )
			return true;
		
		Author author = pratilipi.getAuthorId() == null
				? null
				: DataAccessorFactory.getDataAccessor().getAuthor( pratilipi.getAuthorId() );
		if( author != null && author.getUserId() != null && author.getUserId().equals( accessToken.getUserId() ) )
			return true;
		
		return false;
		
	}
	
	public static boolean hasAccessToUpdatePratilipiMetaData( Language language ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), language, AccessType.PRATILIPI_UPDATE_META );
	}
	
	public static boolean hasAccessToAddPratilipiReview( Pratilipi pratilipi ) {

		// Case 1: Content piece must be PUBLISHED.
		if( pratilipi.getState() != PratilipiState.PUBLISHED )
			return false;

		
		// Case 2: User must have PRATILIPI_ADD_REVIEW access.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipi.getLanguage(), AccessType.PRATILIPI_ADD_REVIEW ) )
			return false;

		
		// Case 3: User must not be the owner of Pratilpi's Author profile.
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return false;

		
		return true;
		
	}
	
	public static boolean hasAccessToReadPratilipiContent( Pratilipi pratilipi ) {

		// Case 1: Any user can read PUBLISHED content.
		if( pratilipi.getState() == PratilipiState.PUBLISHED )
			return true;

		
		// Case 2: Nobody can read DELETED content.
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;

		
		// Case 3: User with PRATILIPI_READ_CONTENT access can read any content in any state.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), pratilipi.getLanguage(), AccessType.PRATILIPI_READ_CONTENT ) )
			return true;

		
		// Case 4: User can read content, in any state, linked with his/her own Author profile.
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return true;
		
		
		return false;
		
	}

	public static boolean hasAccessToUpdatePratilipiContent( Pratilipi pratilipi ) {
		return hasAccessToUpdatePratilipiData( pratilipi, null );
	}
	

	public static String createPratilipiCoverUrl( Pratilipi pratilipi ) {
		return createPratilipiCoverUrl( pratilipi, null );
	}
	
	public static String createPratilipiCoverUrl( Pratilipi pratilipi, Integer width ) {
		String url = "/pratilipi/cover";
		if( pratilipi.hasCustomCover() ) {
			url = url + "?pratilipiId=" + pratilipi.getId() + "&version=" + pratilipi.getLastUpdated().getTime();
			if( width != null )
				url = url + "&width=" + width;
			if( SystemProperty.CDN != null )
				url = SystemProperty.CDN.replace( "*", pratilipi.getId() % 10 + "" ) + url;
		} else {
			if( width != null )
				url = url + "?width=" + width;
			if( SystemProperty.CDN != null )
				url = SystemProperty.CDN.replace( "*", "10" ) + url;
		}
		return url;
	}

	private static double calculateRelevance( Pratilipi pratilipi, Author author ) {
		double relevance = pratilipi.getReadCount();
		if( author != null && author.getContentPublished() > 1L )
			relevance = relevance + (double) author.getTotalReadCount() / (double) author.getContentPublished();
		return relevance;
	}
	

	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author ) {
		return createPratilipiData( pratilipi, null, author, false );
	}
	
	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author, boolean includeMetaData ) {
		return createPratilipiData( pratilipi, null, author, includeMetaData );
	}
	
	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Page pratilipiPage, Author author, boolean includeMetaData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		if( pratilipiPage == null )
			pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipi.getId() );
		Page readPage = null; // dataAccessor.getPage( PageType.READ, pratilipi.getId() ); TODO: Uncomment this once data in DataStore is fixed.
		Page writePage = null; // dataAccessor.getPage( PageType.WRITE, pratilipi.getId() ); TODO: Uncomment this once data in DataStore is fixed.
		
		PratilipiData pratilipiData = new PratilipiData();

		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setTitle( pratilipi.getTitle() );
		pratilipiData.setTitleEn( pratilipi.getTitleEn() );
		pratilipiData.setLanguage( pratilipi.getLanguage() );
		pratilipiData.setAuthorId( pratilipi.getAuthorId() );
		pratilipiData.setAuthor( AuthorDataUtil.createAuthorData( author ) );
		pratilipiData.setSummary( pratilipi.getSummary() );
		
		pratilipiData.setPageUrl( pratilipiPage.getUriAlias() == null
				? pratilipiPage.getUri()
				: pratilipiPage.getUriAlias() );
		pratilipiData.setCoverImageUrl( createPratilipiCoverUrl( pratilipi ) );
		pratilipiData.setReadPageUrl( readPage == null || readPage.getUriAlias() == null
				? PageType.READ.getUrlPrefix() + pratilipi.getId()
				: readPage.getUriAlias() );
		pratilipiData.setWritePageUrl( writePage == null || writePage.getUriAlias() == null
				? PageType.WRITE.getUrlPrefix() + pratilipi.getId()
				: writePage.getUriAlias() );
		
		pratilipiData.setType( pratilipi.getType() );
		pratilipiData.setContentType( pratilipi.getContentType() );
		pratilipiData.setState( pratilipi.getState() );
		pratilipiData.setListingDate( pratilipi.getListingDate() );
		if( includeMetaData )
			pratilipiData.setLastUpdated( pratilipi.getLastUpdated() );
		
		pratilipiData.setIndex( pratilipi.getIndex() );
		
		pratilipiData.setReviewCount( pratilipi.getReviewCount() );
		pratilipiData.setRatingCount( pratilipi.getRatingCount() );
		pratilipiData.setAverageRating( pratilipi.getRatingCount() == 0L
				? 0F
				: (float) ( (double) pratilipi.getTotalRating() / pratilipi.getRatingCount() ) );
		pratilipiData.setRelevance( calculateRelevance( pratilipi, author ) );
		pratilipiData.setReadCount( pratilipi.getReadCountOffset() + pratilipi.getReadCount() );
		pratilipiData.setFbLikeShareCount( pratilipi.getFbLikeShareCountOffset() + pratilipi.getFbLikeShareCount() );

		pratilipiData.setAccessToUpdate( hasAccessToUpdatePratilipiData( pratilipi, null ) );
		
		return pratilipiData;
		
	}
	
	
	public static List<PratilipiData> createPratilipiDataList( List<Long> pratilipiIdList, boolean includeAuthorData ) {
		return createPratilipiDataList( pratilipiIdList, includeAuthorData, false );
	}

	public static List<PratilipiData> createPratilipiDataList( List<Long> pratilipiIdList, boolean includeAuthorData, boolean includeMetaData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiIdList );
		Map<Long, Page> pratilipiPages = dataAccessor.getPages( PageType.PRATILIPI, pratilipiIdList );
		
		Map<Long, AuthorData> authorIdToDataMap = null;
		if( includeAuthorData ) {
			List<Long> authorIdList = new LinkedList<>();
			for( Pratilipi pratilipi : pratilipiList )
				if( pratilipi.getAuthorId() != null && ! authorIdList.contains( pratilipi.getAuthorId() ) )
					authorIdList.add( pratilipi.getAuthorId() );
			
			List<AuthorData> authorDataList = AuthorDataUtil.createAuthorDataList( authorIdList );
			authorIdToDataMap = new HashMap<>( authorDataList.size() );
			for( AuthorData authorData : authorDataList )
				authorIdToDataMap.put( authorData.getId(), authorData );	
		}

		List<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			PratilipiData pratilipiData = createPratilipiData( pratilipi, pratilipiPages.get( pratilipi.getId() ), null, includeMetaData );
			if( includeAuthorData && pratilipi.getAuthorId() != null )
				pratilipiData.setAuthor( authorIdToDataMap.get( pratilipi.getAuthorId() ) );
			pratilipiData.setRelevance( calculateRelevance( pratilipi, dataAccessor.getAuthor( pratilipi.getAuthorId() ) ) );
			pratilipiDataList.add( pratilipiData );
		}
		
		return pratilipiDataList;
		
	}
	

	@Deprecated
	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			PratilipiFilter pratilipiFilter, String cursor, Integer resultCount )
			throws InsufficientAccessException {

		return getPratilipiDataList( null, pratilipiFilter, cursor, null, resultCount);
	}
	
	@Deprecated
	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			PratilipiFilter pratilipiFilter, String cursor, Integer offset, Integer resultCount )
			throws InsufficientAccessException {

		return getPratilipiDataList( null, pratilipiFilter, cursor, offset, resultCount);
	}
	
	@Deprecated
	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			String searchQuery, PratilipiFilter pratilipiFilter,
			String cursor, Integer resultCount )
			throws InsufficientAccessException {
		
		return getPratilipiDataList( searchQuery, pratilipiFilter, cursor, null, resultCount );
	}
	
	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			String searchQuery, PratilipiFilter pratilipiFilter,
			String cursor, Integer offset, Integer resultCount )
			throws InsufficientAccessException {
		
		if( ! hasAccessToListPratilipiData( pratilipiFilter ) )
			throw new InsufficientAccessException();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Memcache l2Cache = DataAccessorFactory.getL2CacheAccessor();

		// Skip cache for cases when User has access to update the Author.
		boolean skipCache = pratilipiFilter.getAuthorId() != null
				&& AuthorDataUtil.hasAccessToUpdateAuthorData( dataAccessor.getAuthor( pratilipiFilter.getAuthorId() ), null );
		
		// Processing search query
		if( searchQuery != null )
			searchQuery = searchQuery.toLowerCase().trim()
					.replaceAll( ",|\\sor\\s", " " )
					.replaceAll( "[\\s]+", " OR " );

		// Creating memcache id
		String memcacheId = PratilipiDataUtil.class.getSimpleName() + "-" + pratilipiFilter.toUrlEncodedString();
		if( searchQuery != null )
			memcacheId += "&searchQuery=" + searchQuery;
		if( resultCount != null )
			memcacheId += "&resultCount=" + resultCount;
		if( offset != null )
			memcacheId += "&offset=" + offset;
		if( cursor != null )
			memcacheId += "&cursor=" + cursor;
		memcacheId += "?" + ( new Date().getTime() / TimeUnit.MINUTES.toMillis( 10 ) );

		// Fetching cached response from Memcache
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= skipCache ? null : (DataListCursorTuple<PratilipiData>) l2Cache.get( memcacheId );

		if( pratilipiDataListCursorTuple == null || pratilipiDataListCursorTuple.getDataList().size() == 0 ) {
			// Fetching Pratilipi id list from DataStore/SearchIndex
			DataListCursorTuple<Long> pratilipiIdListCursorTuple =
					pratilipiFilter.getListName() == null && pratilipiFilter.getState() == PratilipiState.PUBLISHED
					? DataAccessorFactory.getSearchAccessor().searchPratilipi( searchQuery, pratilipiFilter, cursor, offset, resultCount )
					: dataAccessor.getPratilipiIdList( pratilipiFilter, cursor, offset, resultCount );
			// Creating PratilipiData list from Pratilipi id list
			List<PratilipiData> pratilipiDataList = createPratilipiDataList(
					pratilipiIdListCursorTuple.getDataList(),
					pratilipiFilter.getAuthorId() == null,
					false );
			// Creating response object
			pratilipiDataListCursorTuple = new DataListCursorTuple<PratilipiData>(
					pratilipiDataList,
					pratilipiIdListCursorTuple.getCursor(),
					pratilipiIdListCursorTuple.getNumberFound() );
			// Caching response object in Memcache
			// Need to have this check because sometimes GAE fails to read existent file(s)
			if( pratilipiFilter.getListName() == null || pratilipiIdListCursorTuple.getNumberFound() != 0 )
				l2Cache.put( memcacheId, pratilipiDataListCursorTuple );
		}

		return pratilipiDataListCursorTuple;
		
	}
	
	public static PratilipiData savePratilipiData( PratilipiData pratilipiData )
			throws InvalidArgumentException, InsufficientAccessException {

		_validatePratilipiDataForSave( pratilipiData );
		
		boolean isNew = pratilipiData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = isNew ? dataAccessor.newPratilipi() : dataAccessor.getPratilipi( pratilipiData.getId() );

		if ( isNew && ! hasAccessToAddPratilipiData( pratilipiData ) )
			throw new InsufficientAccessException();
		if( ! isNew && ! hasAccessToUpdatePratilipiData( pratilipi, pratilipiData ) )
			throw new InsufficientAccessException();
		

		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( isNew ? AccessType.PRATILIPI_ADD : AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		
		if( pratilipiData.hasTitle() )
			pratilipi.setTitle( pratilipiData.getTitle() );
		if( pratilipiData.hasTitleEn() )
			pratilipi.setTitleEn( pratilipiData.getTitleEn() );
		if( pratilipiData.hasLanguage() )
			pratilipi.setLanguage( pratilipiData.getLanguage() );
		// Do NOT update Author for existing content pieces.
		if( isNew && pratilipiData.hasAuthorId() )
			pratilipi.setAuthorId( pratilipiData.getAuthorId() );
		
		if( pratilipiData.hasSummary() )
			pratilipi.setSummary( pratilipiData.getSummary() );
		
		if( pratilipiData.hasType() )
			pratilipi.setType( pratilipiData.getType() );
		if( pratilipiData.hasContentType() )
			pratilipi.setContentType( pratilipiData.getContentType() );
		else if( isNew )
			pratilipi.setContentType( PratilipiContentType.PRATILIPI );
		if( pratilipiData.hasState() )
			pratilipi.setState( pratilipiData.getState() );
		else if( isNew )
			pratilipi.setState( PratilipiState.DRAFTED );

		if( isNew )
			pratilipi.setListingDate( new Date() );
		pratilipi.setLastUpdated( new Date() );

		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		
		
		if( isNew ) {
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
			dataAccessor.createOrUpdatePage( _updatePratilipiPageUrl( pratilipi ) );
		} else {
			pratilipi = dataAccessor.createOrUpdatePratilipi(
					pratilipi,
					_updatePratilipiPageUrl( pratilipi ),
					auditLog );
		}		

		
		return createPratilipiData(
				pratilipi,
				dataAccessor.getAuthor( pratilipi.getAuthorId() ),
				hasAccessToReadPratilipiMetaData( pratilipi ) );
		
	}

	private static void _validatePratilipiDataForSave( PratilipiData pratilipiData )
			throws InvalidArgumentException {
		
		boolean isNew = pratilipiData.getId() == null;
		
		JsonObject errorMessages = new JsonObject();

		// New content piece must have language.
		if( isNew && ( ! pratilipiData.hasLanguage() || pratilipiData.getLanguage() == null ) )
			errorMessages.addProperty( "langauge", GenericRequest.ERR_LANGUAGE_REQUIRED );
		// Language can not be un-set or set to null.
		else if( ! isNew && pratilipiData.hasLanguage() && pratilipiData.getLanguage() == null )
			errorMessages.addProperty( "langauge", GenericRequest.ERR_LANGUAGE_REQUIRED );

		// New content piece must have type.
		if( isNew && ( ! pratilipiData.hasType() || pratilipiData.getType() == null ) )
			errorMessages.addProperty( "type", GenericRequest.ERR_PRATILIPI_TYPE_REQUIRED );
		// Type can not be un-set or set to null.
		else if( ! isNew && pratilipiData.hasType() && pratilipiData.getType() == null )
			errorMessages.addProperty( "type", GenericRequest.ERR_PRATILIPI_TYPE_REQUIRED );
		
		// Content type can not be un-set or set to null.
		if( ! isNew && pratilipiData.hasContentType() && pratilipiData.getContentType() == null )
			errorMessages.addProperty( "type", GenericRequest.ERR_PRATILIPI_CONTENT_TYPE_REQUIRED );

		// State can not be un-set or set to null.
		if( pratilipiData.hasState() && pratilipiData.getState() == null ) // isNew || ! isNew
			errorMessages.addProperty( "state", GenericRequest.ERR_PRATILIPI_STATE_REQUIRED );
		// isNew || !isNew
		else if( pratilipiData.hasState()
				&& pratilipiData.getState() != PratilipiState.DRAFTED
				&& pratilipiData.getState() != PratilipiState.SUBMITTED
				&& pratilipiData.getState() != PratilipiState.PUBLISHED
				&& pratilipiData.getState() != PratilipiState.DELETED )
			errorMessages.addProperty( "state", GenericRequest.ERR_PRATILIPI_STATE_INVALID );

		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );

	}
	
	
	public static BlobEntry getPratilipiCover( Long pratilipiId, Integer width )
			throws UnexpectedServerException {

		Pratilipi pratilipi = pratilipiId == null ? null : DataAccessorFactory.getDataAccessor().getPratilipi( pratilipiId );
		
		BlobEntry blobEntry = null;
		
		if( pratilipi != null && pratilipi.hasCustomCover() )
			blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( COVER_FOLDER + "/" + pratilipiId );
		
		if( blobEntry == null )
			blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( COVER_FOLDER + "/" + "pratilipi" );
		
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width, (int) ( 1.5 * width ) ) );
		
		return blobEntry;
		
	}

	public static void savePratilipiCover( Long pratilipiId, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !hasAccessToUpdatePratilipiData( pratilipi, null ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( COVER_FOLDER + "/" + pratilipiId );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		pratilipi.setCustomCover( true );
		pratilipi.setLastUpdated( new Date() );

		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog.setEventComment( "Uploaded cover image." );
		
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
	}
	
	
	public static void updatePratilipiIndex( Long pratilipiId )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
			
			BlobEntry blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			
			String index = null;
			Integer pageCount = 0;
			if( blobEntry != null ) {
				String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
				
				PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
				index = pratilipiContentUtil.generateIndex();
				pageCount = pratilipiContentUtil.getPageCount();
			}
			
			Gson gson = new Gson();

			AccessToken accessToken = AccessTokenFilter.getAccessToken();
			AuditLog auditLog = dataAccessor.newAuditLogOfy();
			auditLog.setAccessId( accessToken.getId() );
			auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			boolean isChanged = false; 
			if( ( pratilipi.getIndex() == null && index != null )
					|| ( pratilipi.getIndex() != null && index == null )
					|| ( pratilipi.getIndex() != null && index != null && ! pratilipi.getIndex().equals( index ) ) ) {

				pratilipi.setIndex( index );
				isChanged = true;
				
			}

			if( pratilipi.getPageCount() == null || ! pratilipi.getPageCount().equals( pageCount ) ) {
				pratilipi.setPageCount( pageCount );
				isChanged = true;
			}

			auditLog.setEventDataNew( gson.toJson( pratilipi ) );
			
			if( isChanged )
				pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );

		} else {
			throw new InvalidArgumentException( "Index generation for " + pratilipi.getContentType() + " content type is not yet supported." );
		}
		
	}
	
	public static Page _updatePratilipiPageUrl( Pratilipi pratilipi ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipi.getId() );

		if( pratilipi.getState() == PratilipiState.DELETED ) {
			if( pratilipiPage != null )
				dataAccessor.deletePage( pratilipiPage );
			return null;
		}
		
		boolean isNew = pratilipiPage == null;
		
		if( isNew ) {
			pratilipiPage = dataAccessor.newPage();
			pratilipiPage.setType( PageType.PRATILIPI );
			pratilipiPage.setUri( PageType.PRATILIPI.getUrlPrefix() + pratilipi.getId() );
			pratilipiPage.setPrimaryContentId( pratilipi.getId() );
			pratilipiPage.setCreationDate( new Date() );
		}

		
		String uriPrifix = null;
		if( pratilipi.getAuthorId() != null ) {
			Page authorPage = dataAccessor.getPage( PageType.AUTHOR, pratilipi.getAuthorId() );
			if( authorPage.getUriAlias() != null )
				uriPrifix = authorPage.getUriAlias() + "/";
		}

		
		if( uriPrifix == null ) {
		
			if( isNew ) {
				// Do NOT return.
			} else if( pratilipiPage.getUriAlias() == null ) {
				return null; // Do Nothing.
			} else {
				logger.log( Level.INFO, "Clearing Pratilipi Page Url: '" + pratilipiPage.getUriAlias() + "' -> 'null'" );
				pratilipiPage.setUriAlias( null );
			}
		
		} else {
			
			String uriAlias = UriAliasUtil.generateUriAlias(
					pratilipiPage.getUriAlias(),
					uriPrifix,
					pratilipi.getTitleEn() == null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
			
			if( isNew && uriAlias == null ) {
				// Do NOT return.
			} else if( uriAlias == pratilipiPage.getUriAlias()
					|| ( uriAlias != null && uriAlias.equals( pratilipiPage.getUriAlias() ) )
					|| ( pratilipiPage.getUriAlias() != null && pratilipiPage.getUriAlias().equals( uriAlias ) ) ) {
				return null; // Do Nothing.
			} else {
				logger.log( Level.INFO, "Updating Pratilipi Page Url: '" + pratilipiPage.getUriAlias() + "' -> '" + uriAlias + "'" );
				pratilipiPage.setUriAlias( uriAlias );
			}
		
		}
	
		
		return pratilipiPage;
	
	}
	
	public static boolean createOrUpdatePratilipiReadPageUrl( Long pratilipiId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
		Page readPage = dataAccessor.getPage( PageType.READ, pratilipiId );

		if( readPage == null ) {
			readPage = dataAccessor.newPage();
			readPage.setType( PageType.READ );
			readPage.setUri( PageType.READ.getUrlPrefix() + pratilipiId );
			readPage.setPrimaryContentId( pratilipiId );
			readPage.setCreationDate( new Date() );
		} else if( ( page.getUriAlias() == null && readPage.getUriAlias() == null )
				|| ( page.getUriAlias() != null && readPage.getUriAlias() != null && page.getUri().equals( page.getUriAlias() ) ) ) {
			return false;
		}
		
		readPage.setUriAlias( page.getUriAlias() == null ? null : page.getUriAlias() + "/read" );
		readPage = dataAccessor.createOrUpdatePage( readPage );
		
		return true;
	}
	
	public static void updatePratilipiStats( Long pratilipiId )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		PratilipiGoogleAnalyticsDoc gaDoc = docAccessor.getPratilipiGoogleAnalyticsDoc( pratilipiId );
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
		
		long readCountOffset = 0L;
		for( int month = 1; month <= 12; month++ )
			for( int day = 1; day <= 31; day++ )
				readCountOffset += gaDoc.getPageViews( 2015, month, day );
		for( int month = 1; month <= 4; month++ )
			for( int day = 1; day <= 31; day++ )
				readCountOffset += gaDoc.getPageViews( 2016, month, day );
		
		long readCount = gaDoc.getTotalReadPageViews();
		long fbLikeShareCount = FacebookApi.getUrlShareCount( "http://" + Website.ALL_LANGUAGE.getHostName() + pratilipiPage.getUri() );
		
		
		if( pratilipi.getFbLikeShareCount() - 1 > fbLikeShareCount ) {
			logger.log( Level.SEVERE, "Facebook LikeShare count for " + pratilipiId
					+ " decreased from " + pratilipi.getFbLikeShareCount()
					+ " to " + fbLikeShareCount + "." );
			throw new UnexpectedServerException();
		}
		
		if( pratilipi.getReadCountOffset() == readCountOffset
				&& pratilipi.getReadCount() == readCount
				&& pratilipi.getFbLikeShareCount() == fbLikeShareCount )
			return;
		
		
		updatePratilipiStats( pratilipiId, readCountOffset, readCount, null, fbLikeShareCount );
	
	}	

	public static void updatePratilipiStats( Long pratilipiId,
			Long readCountOffset, Long readCount,
			Long fbLikeShareCountOffset, Long fbLikeShareCount ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Gson gson = new Gson();
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );
		
		if( readCountOffset != null )
			pratilipi.setReadCountOffset( readCountOffset );
		if( readCount != null )
			pratilipi.setReadCount( readCount );
		if( fbLikeShareCountOffset != null )
			pratilipi.setFbLikeShareCountOffset( fbLikeShareCountOffset );
		if( fbLikeShareCount != null )
			pratilipi.setFbLikeShareCount( fbLikeShareCount );
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );

		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );

	}
	
	public static void updateUserPratilipiStats( Long pratilipiId ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		if( pratilipi.getState() != PratilipiState.PUBLISHED )
			return;
		
		DataListCursorTuple<UserPratilipi> userPratilipiDataListCursorTuple =
				dataAccessor.getUserPratilipiList( null, pratilipiId, null, 1000 );
		long reviewCount = 0;
		long ratingCount = 0;
		long totalRating = 0;
		for( UserPratilipi userPratilipi : userPratilipiDataListCursorTuple.getDataList() ) {
			if( userPratilipi.getReviewState() == UserReviewState.PUBLISHED )
				reviewCount++;
			if( userPratilipi.getRating() != null && userPratilipi.getRating() > 0 ) {
				ratingCount++;
				totalRating += userPratilipi.getRating();
			}
		}
		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		if( pratilipi.getReviewCount() == reviewCount
				&& pratilipi.getRatingCount() == ratingCount
				&& pratilipi.getTotalRating() == totalRating )
			return;
		
		pratilipi.setReviewCount( reviewCount );
		pratilipi.setRatingCount( ratingCount );
		pratilipi.setTotalRating( totalRating );
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );

		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
	}
	
	public static void updatePratilipiSearchIndex( Long pratilipiId, Long authorId )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Pratilipi> pratilipiList = null;
		if( authorId != null ) {
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setAuthorId( authorId );
			pratilipiList = dataAccessor
					.getPratilipiList( pratilipiFilter, null, null )
					.getDataList();
			
		} else if( pratilipiId != null ) {
			pratilipiList = new ArrayList<>( 1 );
			pratilipiList.add( dataAccessor.getPratilipi( pratilipiId ) );
			
		} else {
			logger.log( Level.SEVERE, "Neither AuthorId, nor PratilipiId is provided !" );
			throw new InvalidArgumentException( "Neither AuthorId, nor PratilipiId is provided !" );
		}
		
		_updatePratilipiSearchIndex( pratilipiList );
	}

	public static void updatePratilipiSearchIndex( List<Long> pratilipiIdList )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		_updatePratilipiSearchIndex( dataAccessor.getPratilipiList( pratilipiIdList ) );
		
	}
	
	private static void _updatePratilipiSearchIndex( List<Pratilipi> pratilipiList )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		List<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			
			if( pratilipi.getState() == PratilipiState.PUBLISHED ) {
				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				pratilipiDataList.add( createPratilipiData( pratilipi, author, true ) );
				
			} else {
				searchAccessor.deletePratilipiDataIndex( pratilipi.getId() );

			}
			
		}
		
		if( pratilipiDataList.size() > 0 ) {
			Map<PratilipiData, String> pratilipiDataKeywordsMap = new HashMap<>();
			for( PratilipiData pratilipiData : pratilipiDataList )
				pratilipiDataKeywordsMap.put( pratilipiData, getPratilipiKeywords( pratilipiData.getId() ) );
			
			searchAccessor.indexPratilipiDataList( pratilipiDataKeywordsMap );
		}
	}
	
	public static void updateFacebookScrape( List<Long> pratilipiIdList ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		for( Long pratilipiId : pratilipiIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			if( pratilipi.getState() != PratilipiState.PUBLISHED && pratilipi.getState() != PratilipiState.PUBLISHED_PAID )
				return;
			
			Page page = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
			if( pratilipi.getLanguage() == Language.GUJARATI || pratilipi.getLanguage() == Language.TAMIL)
				FacebookApi.postScrapeRequest( "http://" + pratilipi.getLanguage().getHostName() + page.getUri() );
			else
				FacebookApi.postScrapeRequest( "http://" + Website.ALL_LANGUAGE.getHostName() + page.getUri() );
		}
				
	}
	
	
	public static Object getPratilipiContent( long pratilipiId, Integer chapterNo, Integer pageNo,
			PratilipiContentType contentType ) throws InvalidArgumentException,
			InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( ! hasAccessToReadPratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		if( contentType == PratilipiContentType.PRATILIPI ) {

			BlobEntry blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			
			if( blobEntry == null )
				return null;
			
			String contentHtml = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( contentHtml );

			if( contentHtml != null )
				contentHtml = pratilipiContentUtil.getContent( chapterNo != null ? chapterNo : pageNo );

//			Object content = pratilipiContentUtil.toPratilipiContentData();
//			if( content != null && chapterNo != null )
//				content = ( (PratilipiContentData) content ).getChapter( chapterNo );
//			if( content != null && pageNo != null )
//				content = ( (Chapter) content ).getPage( pageNo );
			return contentHtml;

		} else if( contentType == PratilipiContentType.IMAGE ) {
			
			return blobAccessor.getBlob( IMAGE_CONTENT_FOLDER + "/" + pratilipiId + "/" + pageNo );
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
	}
	
	public static int updatePratilipiContent( long pratilipiId, int pageNo,
			PratilipiContentType contentType, Object pageContent, boolean insertNew )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( ! hasAccessToUpdatePratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();

		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			if( blobEntry == null ) {
				blobEntry = blobAccessor.newBlob( CONTENT_FOLDER + "/" + pratilipiId );
				blobEntry.setData( "&nbsp".getBytes( Charset.forName( "UTF-8" ) ) );
				blobEntry.setMimeType( "text/html" );
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			content = pratilipiContentUtil.updateContent( pageNo, (String) pageContent, insertNew );
			int pageCount = pratilipiContentUtil.getPageCount();
			if( content.isEmpty() ) {
				content = "&nbsp";
				pageCount = 1;
			}
			blobEntry.setData( content.getBytes( Charset.forName( "UTF-8" ) ) );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			pratilipi.setPageCount( pageCount );
			if( insertNew )
				auditLog.setEventComment( "Added new page " + pageNo + " in Pratilpi content." );
			else if( ! ( (String) pageContent ).isEmpty() )
				auditLog.setEventComment( "Updated page " + pageNo + " in Pratilpi content." );
			else
				auditLog.setEventComment( "Deleted page " + pageNo + " in Pratilpi content." );
			
		} else if( contentType == PratilipiContentType.IMAGE ) {
			
			BlobEntry blobEntry = (BlobEntry) pageContent;
			blobEntry.setName( IMAGE_CONTENT_FOLDER + "/" + pratilipiId + "/" + pageNo );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			if( pageNo > (int) pratilipi.getPageCount() )
				pratilipi.setPageCount( pageNo );
			
			auditLog.setEventComment( "Uploaded page " + pageNo + " in Image content." );
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
		pratilipi.setLastUpdated( new Date() );
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );

		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
		return pratilipi.getPageCount();
	}
	
	
	public static String getPratilipiKeywords( Long pratilipiId )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
			
			BlobEntry blobEntry = blobAccessor.getBlob( KEYWORDS_FOLDER + "/" + pratilipiId );
			if( blobEntry == null )
				return null;
			return new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );

		} else {

			return null;
			
		}
		
	}
	
	public static boolean updatePratilipiKeywords( Long pratilipiId ) 
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
		
			BlobEntry blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			BlobEntry keywordsBlobEntry = blobAccessor.getBlob( KEYWORDS_FOLDER + "/" + pratilipiId );
			
			String generatedKeywords = null;
			if( blobEntry != null ) {
				String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
				PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
				generatedKeywords = pratilipiContentUtil.generateKeywords();
			}
			
			if( generatedKeywords == null || generatedKeywords.isEmpty() ) {

				if( keywordsBlobEntry == null ) {
					return false;
				} else {
					blobAccessor.deleteBlob( keywordsBlobEntry );
					return true;
				}
				
			} else {
				
				if( keywordsBlobEntry == null )
					keywordsBlobEntry = blobAccessor.newBlob( KEYWORDS_FOLDER + "/" + pratilipiId, null, "text/plain" );
				else if( generatedKeywords.equals( new String( keywordsBlobEntry.getData(), Charset.forName( "UTF-8" ) ) ) )
					return false;

				keywordsBlobEntry.setData( generatedKeywords.getBytes( Charset.forName( "UTF-8" ) ) );
				blobAccessor.createOrUpdateBlob( keywordsBlobEntry );
				return true;
				
			}
			
		} else {
			
			throw new InvalidArgumentException( "Keywords generation for " + pratilipi.getContentType() + " content type is not yet supported." );
		
		}
		
	}
	
	
	public static String getPratilipiResourceFolder( Long pratilipiId ) {
		return RESOURCE_FOLDER + "/" + pratilipiId;
	}
	
	public static BlobEntry getPratilipiResource( long pratilipiId, String fileName )
			throws UnexpectedServerException {

		return DataAccessorFactory.getBlobAccessor().getBlob( getPratilipiResourceFolder( pratilipiId ) + "/" + fileName );
		
	}
	
	public static boolean savePratilipiResource(
			Long pratilipiId, BlobEntry blobEntry, boolean overwrite )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( ! hasAccessToUpdatePratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();

		String fileName = getPratilipiResourceFolder( pratilipiId ) + "/" + blobEntry.getName().replaceAll( "/", "-" );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( !overwrite &&  blobAccessor.getBlob( fileName ) != null ) {
		
			return false;

		} else {

			blobEntry.setName( fileName );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			Gson gson = new Gson();

			AccessToken accessToken = AccessTokenFilter.getAccessToken();
			AuditLog auditLog = dataAccessor.newAuditLog();
			auditLog.setAccessId( accessToken.getId() );
			auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			auditLog.setEventDataNew( gson.toJson( pratilipi ) );
			auditLog.setEventComment( "Uploaded content image (resource)." );
			auditLog = dataAccessor.createAuditLog( auditLog );
			
			return true;
			
		}
		
	}
	
}
