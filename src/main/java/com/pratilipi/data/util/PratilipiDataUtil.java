package com.pratilipi.data.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.GoogleAnalyticsApi;
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
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.site.AccessTokenFilter;

public class PratilipiDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiDataUtil.class.getName() );

	
	private static final String CONTENT_FOLDER 		 = "pratilipi-content/pratilipi";
	private static final String IMAGE_CONTENT_FOLDER = "pratilipi-content/image";
	private static final String COVER_FOLDER 		 = "pratilipi-cover";
	private static final String RESOURCE_FOLDER		 = "pratilipi-resource";


	public static boolean hasAccessToListPratilipiData( PratilipiFilter pratilipiFilter ) {
		return true;
	}
	
	public static boolean hasAccessToAddPratilipiData( Pratilipi pratilipi ) {
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_ADD ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null )
			return accessToken.getUserId().equals( author.getUserId() );
		
		return false;
	}

	public static boolean hasAccessToUpdatePratilipiData( Pratilipi pratilipi ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_UPDATE ) )
			return true;
			
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null )
			return accessToken.getUserId().equals( author.getUserId() );

		return false;
	}
	
	public static boolean hasAccessToReadPratilipiMetaData() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_READ_META );
	}
	
	public static boolean hasAccessToUpdatePratilipiMetaData() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_UPDATE_META );
	}
	
	public static boolean hasAccessToAddPratilipiReview( Pratilipi pratilipi ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_ADD_REVIEW ) )
			return false;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return false;
		
		return true;
	}
	
	public static boolean hasAccessToReadPratilipiContent( Pratilipi pratilipi ) 
			throws InvalidArgumentException {
		
		if( pratilipi.getState() == PratilipiState.PUBLISHED )
			return true;
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), AccessType.PRATILIPI_READ_CONTENT ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return true;
		
		return false;
	}

	public static boolean hasAccessToUpdatePratilipiContent( Pratilipi pratilipi ) {
		return hasAccessToUpdatePratilipiData( pratilipi );
	}
	

	public static String createPratilipiCoverUrl( Pratilipi pratilipi ) {
		if( pratilipi.hasCustomCover() ) {
			String domain = "//" + pratilipi.getId() % 10 + "." + SystemProperty.get( "cdn" );
			String uri = "/pratilipi/cover?pratilipiId=" + pratilipi.getId() + "&width=150&version=" + pratilipi.getLastUpdated().getTime();
			return domain + uri;
		} else {
			String domain = "//10." + SystemProperty.get( "cdn" );
			String uri = "/pratilipi/cover?width=150";
			return domain + uri;
		}
	}

	private static double calculateRelevance( Pratilipi pratilipi, Author author ) {
		double relevance = pratilipi.getReadCount();
		if( author != null && author.getContentPublished() > 1L )
			relevance = relevance + (double) author.getTotalReadCount() / (double) author.getContentPublished();
		return relevance;
	}
	

	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author ) {
		return createPratilipiData( pratilipi, author, false, false );
	}
	
	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author, boolean includeAll, boolean includeMetaData ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipi.getId() );
		return createPratilipiData( pratilipi, author, pratilipiPage, includeAll, includeMetaData );
	}
	
	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author, Page pratilipiPage ) {
		return createPratilipiData( pratilipi, author, pratilipiPage, false, false );
	}

	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Author author, Page pratilipiPage, boolean includeAll, boolean includeMetaData ) {
		PratilipiData pratilipiData = new PratilipiData();

		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setTitle( pratilipi.getTitle() );
		pratilipiData.setTitleEn( pratilipi.getTitleEn() );
		pratilipiData.setLanguage( pratilipi.getLanguage() );
		pratilipiData.setAuthorId( pratilipi.getAuthorId() );
		pratilipiData.setAuthor( AuthorDataUtil.createAuthorData( author ) );
		if( includeAll )
			pratilipiData.setSummary( pratilipi.getSummary() );
		pratilipiData.setPublicationYear( pratilipi.getPublicationYear() );
		
		pratilipiData.setPageUrl( pratilipiPage.getUri() );
		pratilipiData.setPageUrlAlias( pratilipiPage.getUriAlias() );
		pratilipiData.setCoverImageUrl( createPratilipiCoverUrl( pratilipi ) );
		pratilipiData.setReaderPageUrl( PageType.READ.getUrlPrefix() + pratilipi.getId() );
		pratilipiData.setWriterPageUrl( PageType.WRITE.getUrlPrefix() + pratilipi.getId() );
		
		pratilipiData.setType( pratilipi.getType() );
		pratilipiData.setState( pratilipi.getState() );
		pratilipiData.setListingDate( pratilipi.getListingDate() );
		pratilipiData.setLastUpdated( pratilipi.getLastUpdated() );
		
		if( includeAll )
			pratilipiData.setIndex( pratilipi.getIndex() );
		if( includeMetaData )
			pratilipiData.setKeywords( pratilipi.getKeywords() );
		pratilipiData.setWordCount( pratilipi.getWordCount() );
		pratilipiData.setPageCount( pratilipi.getPageCount() );
		
		pratilipiData.setReviewCount( pratilipi.getReviewCount() );
		pratilipiData.setRatingCount( pratilipi.getRatingCount() );
		pratilipiData.setAverageRating(
				pratilipi.getRatingCount() == 0L
						? 5F : (float) ( (double) pratilipi.getTotalRating() / pratilipi.getRatingCount() ) );
		pratilipiData.setRelevance( calculateRelevance( pratilipi, author ) );
		
		pratilipiData.setReadCount( pratilipi.getReadCount() );

		return pratilipiData;
	}
	
	public static List<PratilipiData> createPratilipiDataList(
			List<Long> pratilipiIdList, boolean includeAuthorData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiIdList );
		
		Map<Long, AuthorData> authorIdToDataMap = null;
		if( includeAuthorData ) {
			List<Long> authorIdList = new LinkedList<>();
			for( Pratilipi pratilipi : pratilipiList )
				if( pratilipi.getAuthorId() != null && ! authorIdList.contains( pratilipi.getAuthorId() ) )
					authorIdList.add( pratilipi.getAuthorId() );
			
			List<Author> authorList = dataAccessor.getAuthorList( authorIdList );
			authorIdToDataMap = new HashMap<>( authorList.size() );
			for( Author author : authorList )
				authorIdToDataMap.put( author.getId(), AuthorDataUtil.createAuthorData( author ) );	
		}

		List<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			PratilipiData pratilipiData = createPratilipiData( pratilipi, null );
			if( includeAuthorData && pratilipi.getAuthorId() != null )
				pratilipiData.setAuthor( authorIdToDataMap.get( pratilipi.getAuthorId() ) );
			pratilipiData.setRelevance( calculateRelevance( pratilipi, dataAccessor.getAuthor( pratilipi.getAuthorId() ) ) );
			pratilipiDataList.add( pratilipiData );
		}
		
		return pratilipiDataList;
	}
	

	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			PratilipiFilter pratilipiFilter, String cursor, Integer resultCount ) {

		return getPratilipiDataList( null, pratilipiFilter, cursor, resultCount);
	}

	public static DataListCursorTuple<PratilipiData> getPratilipiDataList(
			String searchQuery, PratilipiFilter pratilipiFilter, String cursor,
			Integer resultCount ) {
		
		if( ! hasAccessToListPratilipiData( pratilipiFilter ) ) {
			// TODO: Clear filters which are not allow to non-admin roles.
		}

		if( searchQuery != null )
			searchQuery = searchQuery.toLowerCase().trim().replaceAll( "[\\s]+", " OR " );

		DataListCursorTuple<Long> pratilipiIdListCursorTuple = DataAccessorFactory
				.getSearchAccessor()
				.searchPratilipi( searchQuery, pratilipiFilter, cursor, resultCount );

		List<PratilipiData> pratilipiDataList = createPratilipiDataList(
				pratilipiIdListCursorTuple.getDataList(),
				pratilipiFilter.getAuthorId() == null );

		return new DataListCursorTuple<PratilipiData>( pratilipiDataList, pratilipiIdListCursorTuple.getCursor() );
	}
	
	public static PratilipiData savePratilipiData( PratilipiData pratilipiData )
			throws InvalidArgumentException, InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = null;

		Gson gson = new Gson();
		
		AccessToken accessToken = (AccessToken) AccessTokenFilter.getAccessToken(); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );

		if( pratilipiData.getId() == null ) { // Add Pratilipi usecase

			pratilipi = dataAccessor.newPratilipi();
			auditLog.setAccessType( AccessType.PRATILIPI_ADD );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			pratilipi.setContentType( PratilipiContentType.PRATILIPI );
			pratilipi.setAuthorId( pratilipiData.getAuthorId() );
			pratilipi.setListingDate( new Date() );
			pratilipi.setLastUpdated( new Date() );

			if ( ! hasAccessToAddPratilipiData( pratilipi ) )
				throw new InsufficientAccessException();

		} else { // Update Pratilipi usecase
		
			pratilipi = dataAccessor.getPratilipi( pratilipiData.getId() );
			auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );

			// Do NOT update Author
			pratilipi.setLastUpdated( new Date() );

			if( ! hasAccessToUpdatePratilipiData( pratilipi ) )
				throw new InsufficientAccessException();

		}
			
		if( pratilipiData.hasType() )
			pratilipi.setType( pratilipiData.getType() );
		if( pratilipiData.hasTitle() )
			pratilipi.setTitle( pratilipiData.getTitle() );
		if( pratilipiData.hasTitleEn() )
			pratilipi.setTitleEn( pratilipiData.getTitleEn() );
		if( pratilipiData.hasLanguage() )
			pratilipi.setLanguage( pratilipiData.getLanguage() );
		if( pratilipiData.hasPublicationYear() )
			pratilipi.setPublicationYear( pratilipiData.getPublicationYear() );
		if( pratilipiData.hasSummary() )
			pratilipi.setSummary( pratilipiData.getSummary() );
		if( pratilipiData.hasIndex() )
			pratilipi.setIndex( pratilipiData.getIndex() );
		if( pratilipiData.hasWordCount() )
			pratilipi.setWordCount( pratilipiData.getWordCount() );
		if( pratilipiData.hasState() )
			pratilipi.setState( pratilipiData.getState() );

		if( pratilipiData.hasPageCount() && pratilipi.getContentType() == PratilipiContentType.IMAGE )
			pratilipi.setPageCount( pratilipiData.getPageCount() );

		
		if( pratilipi.getType() == PratilipiType.BOOK
				&& pratilipi.getState() != PratilipiState.DRAFTED
				&& pratilipi.getState() != PratilipiState.DELETED
				&& ( pratilipi.getSummary() == null || pratilipi.getSummary().trim().isEmpty() ) )
			throw new InvalidArgumentException(
					pratilipi.getType().getName() + " summary is missing. " +
					pratilipi.getType().getName() + " can not be published without a summary." );
		
		
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		
		if( pratilipiData.getId() == null )
			createOrUpdatePratilipiPageUrl( pratilipi.getId() );
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		return createPratilipiData( pratilipi, dataAccessor.getAuthor( pratilipi.getAuthorId() ) );
	}

	public static BlobEntry getPratilipiCover( Long pratilipiId, Integer width )
			throws UnexpectedServerException {

		String fileName = "";
		if( pratilipiId != null && DataAccessorFactory.getDataAccessor().getPratilipi( pratilipiId ).hasCustomCover() )
			fileName = COVER_FOLDER + "/original/" + pratilipiId;
		else
			fileName = COVER_FOLDER + "/original/" + "pratilipi";

		try {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
			if( width != null )
				blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width, (int) ( 1.5 * width ) ) );
			return blobEntry;
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch pratilipi cover.", e );
			throw new UnexpectedServerException();
		}
	}

	public static void savePratilipiCover( Long pratilipiId, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !hasAccessToUpdatePratilipiData( pratilipi ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		try {
			blobEntry.setName( COVER_FOLDER + "/original/" + pratilipiId );
			blobAccessor.createOrUpdateBlob( blobEntry );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to create/update pratilipi cover.", e );
			throw new UnexpectedServerException();
		}
		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		pratilipi.setCustomCover( true );
		pratilipi.setLastUpdated( new Date() );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog.setEventComment( "Uploaded cover image." );
		auditLog = dataAccessor.createAuditLog( auditLog );
	}
	
	public static void updatePratilipiIndex( Long pratilipiId )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
			
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String index = null;
			if( blobEntry != null ) {
				String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
				
				PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
				index = pratilipiContentUtil.generateIndex();
			}
			
			if( ( pratilipi.getIndex() == null && index != null )
					|| ( pratilipi.getIndex() != null && index == null )
					|| ( pratilipi.getIndex() != null && index != null && ! pratilipi.getIndex().equals( index ) ) ) {
				
				try {
					dataAccessor.beginTx();
					pratilipi = dataAccessor.getPratilipi( pratilipiId );
					pratilipi.setIndex( index );
					dataAccessor.createOrUpdatePratilipi( pratilipi );
					dataAccessor.commitTx();
				} finally {
					if( dataAccessor.isTxActive() )
						dataAccessor.rollbackTx();
				}
				
			}

		} else {
			throw new InvalidArgumentException( "Index generation for " + pratilipi.getContentType() + " content type is not yet supported." );
		}
	}
	
	public static boolean updatePratilipiKeywords( Long pratilipiId ) 
			throws InvalidArgumentException, UnexpectedServerException {
			
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
		
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String keywords = null;
			if( blobEntry != null ) {
				String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
				
				PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
				keywords = pratilipiContentUtil.generateKeywords();
			}
			
			if( ( pratilipi.getKeywords() == null && keywords != null )
					|| ( pratilipi.getKeywords() != null && keywords == null )
					|| ( pratilipi.getKeywords() != null && keywords != null && ! pratilipi.getKeywords().equals( keywords ) ) ) {

				try {
					dataAccessor.beginTx();
					pratilipi = dataAccessor.getPratilipi( pratilipiId );
					pratilipi.setKeywords( keywords );
					dataAccessor.createOrUpdatePratilipi( pratilipi );
					dataAccessor.commitTx();
				} finally {
					if( dataAccessor.isTxActive() )
						dataAccessor.rollbackTx();
				}
				
				return true; 
			
			} else {
				return false;
			}
			
		} else {
			
			throw new InvalidArgumentException( "Keywords generation for " + pratilipi.getContentType() + " content type is not yet supported." );
		
		}
	}
	
	public static boolean createOrUpdatePratilipiPageUrl( Long pratilipiId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Page page = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );

		String uriPrifix = null;
		if( pratilipi.getAuthorId() != null ) {
			Page authorPage = dataAccessor.getPage( PageType.AUTHOR, pratilipi.getAuthorId() );
			if( authorPage.getUriAlias() != null )
				uriPrifix = authorPage.getUriAlias() + "/";
		}

		if( page == null ) {
			page = dataAccessor.newPage();
			page.setType( PageType.PRATILIPI );
			page.setUri( PageType.PRATILIPI.getUrlPrefix() + pratilipiId );
			page.setPrimaryContentId( pratilipiId );
			page.setCreationDate( new Date() );
			if( uriPrifix == null )
				page = dataAccessor.createOrUpdatePage( page );
		}
		
		
		if( uriPrifix == null ) {
		
			if( page.getUriAlias() == null )
				return false;
			
			page.setUriAlias( null );
		
		} else {
			
			String uriAlias = UriAliasUtil.generateUriAlias(
					page.getUriAlias(),
					uriPrifix, pratilipi.getTitleEn() );
			
			if( uriAlias.equals( page.getUriAlias() ) )
				return false;

			page.setUriAlias( uriAlias );
		
		}
		
		page = dataAccessor.createOrUpdatePage( page );
		return true;
	}
	
	
	public static boolean updatePratilipiStats( Long pratilipiId ) throws UnexpectedServerException {
		
		long readCount = GoogleAnalyticsApi.getPratilipiReadCount( pratilipiId );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
		String fbLikeShareUrl = "http://" + SystemProperty.get( "domain" ) + pratilipiPage.getUri();
		long fbLikeShareCount = FacebookApi.getUrlShareCount( fbLikeShareUrl );
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		if( pratilipi.getReadCount() != readCount || pratilipi.getFbLikeShareCount() != fbLikeShareCount ) {
			try {
				dataAccessor.beginTx();
				pratilipi = dataAccessor.getPratilipi( pratilipiId );
				pratilipi.setReadCount( readCount );
				pratilipi.setFbLikeShareCount( fbLikeShareCount );
				pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
				dataAccessor.commitTx();
				return true;
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
		} else {
			return false;
		}

	}

	public static Set<Long> updatePratilipiStats( List<Long> pratilipiIdList ) throws UnexpectedServerException {
		
		Map<Long, Long> idReadCountMap = GoogleAnalyticsApi.getPratilipiReadCount( pratilipiIdList );		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<String> urlList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
			String fbLikeShareUrl = "http://" + SystemProperty.get( "domain" ) + pratilipiPage.getUri();
			urlList.add( fbLikeShareUrl );
		}
		Map<String, Long> urlShareCountMap = FacebookApi.getUrlShareCount( urlList );

		Set<Long> updatedPratilipiIds = new HashSet<Long>();
		int matchCount = 0;
		for( Long pratilipiId : pratilipiIdList ) {
			long readCount = idReadCountMap.get( pratilipiId ) == null ? 0L : idReadCountMap.get( pratilipiId );
			Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
			String fbLikeShareUrl = "http://" + SystemProperty.get( "domain" ) + pratilipiPage.getUri();
			long fbLikeShareCount = urlShareCountMap.get( fbLikeShareUrl ) == null ? 0L : urlShareCountMap.get( fbLikeShareUrl );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			
			if( pratilipi.getReadCount() != readCount || pratilipi.getFbLikeShareCount() != fbLikeShareCount ) {
/*				try {
					dataAccessor.beginTx();
					pratilipi = dataAccessor.getPratilipi( pratilipiId );
					pratilipi.setReadCount( readCount );
					pratilipi.setFbLikeShareCount( fbLikeShareCount );
					pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
					dataAccessor.commitTx();
				} finally {
					if( dataAccessor.isTxActive() )
						dataAccessor.rollbackTx();
				}
				updatedPratilipiIds.add( pratilipiId ); */
				logger.log( Level.WARNING, pratilipiId + ": " + pratilipi.getReadCount() + "->" + readCount + ", " + pratilipi.getFbLikeShareCount() + "->" + fbLikeShareCount );
			} else {
				matchCount++;
			}
		}
		logger.log( Level.INFO, "Match count: " + matchCount + "/" + pratilipiIdList.size() );
		
		return updatedPratilipiIds;
	}	
	
	public static void updatePratilipiSearchIndex( Long pratilipiId, Long authorId )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		
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
		
		
		List<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			
			if( pratilipi.getState() == PratilipiState.PUBLISHED ) {
				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				pratilipiDataList.add( createPratilipiData( pratilipi, author, true, true ) );
				
			} else {
				searchAccessor.deletePratilipiDataIndex( pratilipi.getId() );

			}
			
		}
		
		if( pratilipiDataList.size() > 0 )
			searchAccessor.indexPratilipiDataList( pratilipiDataList );
	}

	
	public static Object getPratilipiContent( long pratilipiId, int pageNo,
			PratilipiContentType contentType ) throws InvalidArgumentException,
			InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		if( ! hasAccessToReadPratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			if( blobEntry == null )
				return "";
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			return pratilipiContentUtil.getContent( pageNo );

		} else if( contentType == PratilipiContentType.IMAGE ) {
			try {
				return blobAccessor.getBlob( IMAGE_CONTENT_FOLDER + "/" + pratilipiId + "/" + pageNo );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
		
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
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( CONTENT_FOLDER + "/" + pratilipiId );
				if( blobEntry == null ) {
					blobEntry = blobAccessor.newBlob( CONTENT_FOLDER + "/" + pratilipiId );
					blobEntry.setData( "&nbsp".getBytes( Charset.forName( "UTF-8" ) ) );
					blobEntry.setMimeType( "text/html" );
				}
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
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
			try {
				blobAccessor.createOrUpdateBlob( blobEntry );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to create/update pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			pratilipi.setPageCount( pageCount );
			if( insertNew )
				auditLog.setEventComment( "Added new page " + pageNo + " in Pratilpi content." );
			else if( ! ( (String) pageContent ).isEmpty() )
				auditLog.setEventComment( "Updated page " + pageNo + " in Pratilpi content." );
			else
				auditLog.setEventComment( "Deleted page " + pageNo + " in Pratilpi content." );
			
		} else if( contentType == PratilipiContentType.IMAGE ) {
			
			try {
				BlobEntry blobEntry = (BlobEntry) pageContent;
				blobEntry.setName( IMAGE_CONTENT_FOLDER + "/" + pratilipiId + "/" + pageNo );
				blobAccessor.createOrUpdateBlob( blobEntry );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to create/update pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			if( pageNo > (int) pratilipi.getPageCount() )
				pratilipi.setPageCount( pageNo );
			
			auditLog.setEventComment( "Uploaded page " + pageNo + " in Image content." );
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
		pratilipi.setLastUpdated( new Date() );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		return pratilipi.getPageCount();
	}
	
	
	public static String getPratilipiResourceFolder( Long pratilipiId ) {
		return RESOURCE_FOLDER + "/" + pratilipiId;
	}
	
	public static BlobEntry getPratilipiResource( long pratilipiId, String fileName )
			throws UnexpectedServerException {

		try {
			return DataAccessorFactory.getBlobAccessor().getBlob( getPratilipiResourceFolder( pratilipiId ) + "/" + fileName );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch pratilipi resource.", e );
			throw new UnexpectedServerException();
		}
		
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
		try {
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
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to create/update pratilipi resource.", e );
			throw new UnexpectedServerException();
		}
		
	}

}
