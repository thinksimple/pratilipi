package com.pratilipi.pagecontent.pratilipi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.GoogleApi;
import com.claymus.commons.server.ImageUtil;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.Page;
import com.claymus.pagecontent.PageContentHelper;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.Analytics.Data.Ga.Get;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.GaData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiAccessTokenType;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;
import com.pratilipi.service.shared.data.LanguageData;

public class PratilipiContentHelper extends PageContentHelper<
		PratilipiContent,
		PratilipiContentData,
		PratilipiContentProcessor> {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiContentHelper.class.getName() );

	private static final Gson gson = new GsonBuilder().create();

	
	private static final String CONTENT_FOLDER 		 = "pratilipi-content/pratilipi";
	private static final String IMAGE_CONTENT_FOLDER = "pratilipi-content/image";
	private static final String COVER_FOLDER 		 = "pratilipi-cover";
	private static final String RESOURCE_FOLDER		 = "pratilipi-resource";

	private static final String COVER_150_URL		 = ClaymusHelper.getSystemProperty( "cdn.asia" ) + "/pratilipi-cover/150/";

	
	private static final Access ACCESS_TO_LIST_PRATILIPI_DATA =
			new Access( "pratilipi_data_list", false, "View Pratilipi Data" );
	private static final Access ACCESS_TO_ADD_PRATILIPI_DATA =
			new Access( "pratilipi_data_add", false, "Add Pratilipi Data" );
	private static final Access ACCESS_TO_UPDATE_PRATILIPI_DATA =
			new Access( "pratilipi_data_update", false, "Update Pratilipi Data" );

	private static final Access ACCESS_TO_READ_PRATILIPI_DATA_META =
			new Access( "pratilipi_data_read_meta", false, "View Pratilipi Meta Data" );
	private static final Access ACCESS_TO_UPDATE_PRATILIPI_DATA_META =
			new Access( "pratilipi_data_update_meta", false, "Update Pratilipi Meta Data" );

	private static final Access ACCESS_TO_ADD_PRATILIPI_REVIEW =
			new Access( "pratilipi_data_add_review", false, "Add Pratilipi Review" );

	private static final Access ACCESS_TO_READ_PRATILIPI_CONTENT =
			new Access( "pratilipi_data_read_content", false, "View Pratilipi Content" );


	@Override
	public String getModuleName() {
		return "Pratilipi";
	}

	@Override
	public Double getModuleVersion() {
		return 5.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_PRATILIPI_DATA,
				ACCESS_TO_ADD_PRATILIPI_DATA,
				ACCESS_TO_UPDATE_PRATILIPI_DATA,
				ACCESS_TO_READ_PRATILIPI_DATA_META,
				ACCESS_TO_UPDATE_PRATILIPI_DATA_META,
				ACCESS_TO_ADD_PRATILIPI_REVIEW,
				ACCESS_TO_READ_PRATILIPI_CONTENT
		};
	}
	
	
	public static PratilipiContent newPratilipiContent( Long pratilipiId ) {
		return new PratilipiContentEntity( pratilipiId );
	}


	public static boolean hasRequestAccessToListPratilipiData( HttpServletRequest request, PratilipiFilter pratilipiFilter ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() )
				&& UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_LIST_PRATILIPI_DATA, request ) )
			return true;
		
		return pratilipiFilter.getState() != null
				&& pratilipiFilter.getState() != PratilipiState.PUBLISHED_DISCONTINUED
				&& pratilipiFilter.getState() != PratilipiState.DELETED;
	}
	
	public static boolean hasRequestAccessToAddPratilipiData( HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			if( UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_ADD_PRATILIPI_DATA, request ) )
				return true;
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
			
			if( author == null && publisher == null )
				return false;
			else if( author != null && !accessToken.getUserId().equals( author.getUserId() ) )
				return false;
			else if( publisher != null && !accessToken.getUserId().equals( publisher.getUserId() ) )
				return false;
			
			if( publisher != null && accessToken.getUserId().equals( publisher.getUserId() ) )
				return true;
			else if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
				return true;

		} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
			return pratilipi.getAuthorId() == null
					&& accessToken.getPublisherId().equals( pratilipi.getPublisherId() );
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
			return false;
		}

		return false;
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiData( HttpServletRequest request, Pratilipi pratilipi ) {
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			if( UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_UPDATE_PRATILIPI_DATA, request ) )
				return true;
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			if( author != null && author.getUserId() != null )
				return accessToken.getUserId().equals( author.getUserId() );
			
			// Grant access to Publisher iff Author is not on-boarded.
			Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
			if( publisher != null && publisher.getUserId() != null )
				return accessToken.getUserId().equals( publisher.getUserId() );

		} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			// Grant access to Publisher iff Author is not on-boarded.
			return ( author == null || author.getUserId() == null )
					&& accessToken.getPublisherId().equals( pratilipi.getPublisherId() );
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
			return false;
		}

		return false;
	}
		
	public static boolean hasRequestAccessToReadPratilipiMetaData( HttpServletRequest request ) {

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_READ_PRATILIPI_DATA_META, request );

		} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
			return false;
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
			return false;
		}
		
		return false;
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiMetaData( HttpServletRequest request ) {

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_UPDATE_PRATILIPI_DATA_META, request );

		} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
			return false;
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
			return false;
		}
		
		return false;
	}
	
	public static boolean hasRequestAccessToAddPratilipiReview( HttpServletRequest request, Pratilipi pratilipi ) {

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			if( ! UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_ADD_PRATILIPI_REVIEW, request ) )
				return false;
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
				return false;
			
			Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
			if( publisher != null && accessToken.getUserId().equals( publisher.getUserId() ) )
				return false;
			
			return true;
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
			return false;
			
		} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
			if( ! accessToken.getPublisherId().equals( pratilipi.getPublisherId() ) )
				return false;
			
			if( ! UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_ADD_PRATILIPI_REVIEW, request ) )
				return false;
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
				return false;
			
			Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
			if( publisher != null && accessToken.getUserId().equals( publisher.getUserId() ) )
				return false;
			
			return true;
		}
		
		return false;
	}
	
	public static boolean hasRequestAccessToReadPratilipiContent( HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( pratilipi.getState() == PratilipiState.PUBLISHED )
			return true;
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		
		if( pratilipi.getState() == PratilipiState.DRAFTED
				|| pratilipi.getState() == PratilipiState.SUBMITTED
				|| pratilipi.getState() == PratilipiState.PUBLISHED_PAID
				|| pratilipi.getState() == PratilipiState.PUBLISHED_DISCONTINUED ) {
			
			if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
				if( UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_READ_PRATILIPI_CONTENT, request ) )
					return true;
				
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
					return true;
				
				Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
				if( publisher != null && accessToken.getUserId().equals( publisher.getUserId() ) )
					return true;

				UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipi.getId() );
				return userPratilipi != null && userPratilipi.getPurchasedFrom() != null;
				
			} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
				return accessToken.getPublisherId().equals( pratilipi.getPublisherId() );
				
			} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
				if( ! accessToken.getPublisherId().equals( pratilipi.getPublisherId() ) )
					return false;
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
				UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipi.getId() );
				return userPratilipi != null && userPratilipi.getPurchasedFrom() != null;
			}
		}
		
		return false;
	}

	public static boolean hasRequestAccessToUpdatePratilipiContent( HttpServletRequest request, Pratilipi pratilipi ) {
		
		return hasRequestAccessToUpdatePratilipiData( request, pratilipi );
	}
	

	public static String createCoverUrl( Pratilipi pratilipi ) {
		if( pratilipi.hasCustomCover() )
			return COVER_150_URL + pratilipi.getId() + "?" + pratilipi.getLastUpdated().getTime();
		else if( pratilipi.isPublicDomain() )
			return COVER_150_URL + "pratilipi-classic-" + pratilipi.getLanguageId();
		else
			return COVER_150_URL + "pratilipi";
	}
	
	public static double calculateRelevance( Pratilipi pratilipi, Author author ) {
		double relevance = pratilipi.getReadCount() + pratilipi.getRelevanceOffset();
		if( author != null && author.getContentPublished() > 1L )
			relevance = relevance + (double) author.getTotalReadCount() / (double) author.getContentPublished();
		return relevance;
	}
	
	public static List<PratilipiData> createPratilipiDataList( List<Long> pratilipiIdList, boolean includeLanguageData, boolean includeAuthorData, HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiIdList );
		

		Map<Long, LanguageData> languageIdToDataMap = null;
		if( includeLanguageData ) {
			List<Long> languageIdList = new LinkedList<>();
			for( Pratilipi pratilipi : pratilipiList )
				if( ! languageIdList.contains( pratilipi.getLanguageId() ) )
					languageIdList.add( pratilipi.getLanguageId() );
			
			List<Language> languageList = dataAccessor.getLanguageList( languageIdList );
			languageIdToDataMap = new HashMap<>( languageList.size() );
			for( Language language : languageList )
				languageIdToDataMap.put( language.getId(), PratilipiHelper.get( request ).createLanguageData( language ) );
		}
			
		
		Map<Long, AuthorData> authorIdToDataMap = null;
		if( includeAuthorData ) {
			List<Long> authorIdList = new LinkedList<>();
			for( Pratilipi pratilipi : pratilipiList )
				if( ! authorIdList.contains( pratilipi.getAuthorId() ) )
					authorIdList.add( pratilipi.getAuthorId() );
			
			List<Author> authorList = dataAccessor.getAuthorList( authorIdList );
			authorIdToDataMap = new HashMap<>( authorList.size() );
			for( Author author : authorList )
				authorIdToDataMap.put( author.getId(), AuthorContentHelper.createAuthorData( author, null, request ) );	
		}

		
		List<PratilipiData> pratilipiDataList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			PratilipiData pratilipiData = createPratilipiData( pratilipi, null, null, request );
			if( includeLanguageData )
				pratilipiData.setLanguageData( languageIdToDataMap.get( pratilipi.getLanguageId() ) );
			if( includeAuthorData && pratilipi.getAuthorId() != null )
				pratilipiData.setAuthorData( authorIdToDataMap.get( pratilipi.getAuthorId() ) );
			pratilipiData.setRelevance( calculateRelevance( pratilipi, dataAccessor.getAuthor( pratilipi.getAuthorId() ) ) );
			pratilipiDataList.add( pratilipiData );
		}
		
		return pratilipiDataList;
		
	}
	
	public static PratilipiData createPratilipiData( Pratilipi pratilipi, Language language, Author author, HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page pratilipiPage = dataAccessor.getPage( PratilipiPageType.PRATILIPI.toString(), pratilipi.getId() );
		
		
		PratilipiData pratilipiData = new PratilipiData();

		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setType( pratilipi.getType() );
		pratilipiData.setPageUrl( pratilipiPage.getUri() );
		pratilipiData.setPageUrlAlias( pratilipiPage.getUriAlias() );
		pratilipiData.setCoverImageUrl( createCoverUrl( pratilipi ) );
		
		pratilipiData.setReaderPageUrl( PratilipiPageType.READ.getUrlPrefix() + pratilipi.getId() );
		pratilipiData.setWriterPageUrl( PratilipiPageType.WRITE.getUrlPrefix() + pratilipi.getId() );
		
		pratilipiData.setTitle( pratilipi.getTitle() );
		pratilipiData.setTitleEn( pratilipi.getTitleEn() );
		pratilipiData.setLanguageId( pratilipi.getLanguageId() );
		pratilipiData.setLanguageData( PratilipiHelper.get( request ).createLanguageData( language ) );

		pratilipiData.setAuthorId( pratilipi.getAuthorId() );
		pratilipiData.setAuthorData( AuthorContentHelper.createAuthorData( author, null, request ) );
		
		pratilipiData.setPublicationYear( pratilipi.getPublicationYear() );
		pratilipiData.setListingDate( pratilipi.getListingDate() );
		pratilipiData.setLastUpdated( pratilipi.getLastUpdated() );
		
		pratilipiData.setSummary( pratilipi.getSummary() );
		pratilipiData.setIndex( pratilipi.getIndex() );
		pratilipiData.setPageCount( pratilipi.getPageCount() );
		
		pratilipiData.setRelevance( calculateRelevance( pratilipi, author ) );
		
		pratilipiData.setContentType( pratilipi.getContentType() );
		pratilipiData.setState( pratilipi.getState() );

		return pratilipiData;
		
	}

	
	public static DataListCursorTuple<PratilipiData> getPratilipiList( PratilipiFilter pratilipiFilter, String cursor, Integer resultCount, HttpServletRequest request )
			throws InsufficientAccessException {
		
		if( ! hasRequestAccessToListPratilipiData( request, pratilipiFilter ) )
			throw new InsufficientAccessException();
		
		DataListCursorTuple<Long> pratilipiIdListCursorTuple = DataAccessorFactory
				.getSearchAccessor()
				.searchPratilipi( pratilipiFilter, cursor, resultCount );
		
		List<PratilipiData> pratilipiDataList = createPratilipiDataList(
				pratilipiIdListCursorTuple.getDataList(),
				pratilipiFilter.getLanguageId() == null,
				pratilipiFilter.getAuthorId() == null,
				request );
		
		return new DataListCursorTuple<PratilipiData>( pratilipiDataList, pratilipiIdListCursorTuple.getCursor() );
	}
	
	@Deprecated
	public static com.pratilipi.service.shared.data.PratilipiData savePratilipi( com.pratilipi.service.shared.data.PratilipiData pratilipiData, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
	
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = null;

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		
		if( pratilipiData.getId() == null ) { // Add Pratilipi usecase

			pratilipi = dataAccessor.newPratilipi();
			auditLog.setEventId( ACCESS_TO_ADD_PRATILIPI_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			pratilipi.setContentType( PratilipiContentType.PRATILIPI );
			if( pratilipiData.hasAuthorId() )
				pratilipi.setAuthorId( pratilipiData.getAuthorId() );
			if( pratilipiData.hasPublisherId() )
				pratilipi.setAuthorId( pratilipiData.getPublisherId() );
			pratilipi.setListingDate( new Date() );
			pratilipi.setLastUpdated( new Date() );

		} else { // Update Pratilipi usecase
		
			pratilipi =  dataAccessor.getPratilipi( pratilipiData.getId() );
			auditLog.setEventId( ACCESS_TO_UPDATE_PRATILIPI_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );

			// Do NOT update Author/Publisher
			pratilipi.setLastUpdated( new Date() );
		}
			
		if( pratilipiData.hasType() )
			pratilipi.setType( pratilipiData.getType() );
		if( pratilipiData.hasPublicDomain() && hasRequestAccessToUpdatePratilipiMetaData( request ) )
			pratilipi.setPublicDomain( pratilipiData.isPublicDomain() );
		if( pratilipiData.hasTitle() )
			pratilipi.setTitle( pratilipiData.getTitle() );
		if( pratilipiData.hasTitleEn() )
			pratilipi.setTitleEn( pratilipiData.getTitleEn() );
		if( pratilipiData.hasLanguageId() )
			pratilipi.setLanguageId( pratilipiData.getLanguageId() );
		if( pratilipiData.hasPublicationYear() )
			pratilipi.setPublicationYear( pratilipiData.getPublicationYear() );
		if( pratilipiData.hasSummary() )
			pratilipi.setSummary( pratilipiData.getSummary() );
		if( pratilipiData.hasIndex() )
			pratilipi.setIndex( pratilipiData.getIndex() );
		if( pratilipiData.hasWordCount() )
			pratilipi.setWordCount( pratilipiData.getWordCount() );
		if( pratilipiData.hasPageCount() && hasRequestAccessToUpdatePratilipiMetaData( request ) )
			pratilipi.setPageCount( (int) (long) pratilipiData.getPageCount() );
		if( pratilipiData.hasState() )
			pratilipi.setState( pratilipiData.getState() );
		if( pratilipiData.hasContentType() && pratilipiData.getContentType() != null  )
			pratilipi.setContentType( pratilipiData.getContentType() );

		
		if( pratilipi.getType() == PratilipiType.BOOK
				&& pratilipi.getState() != PratilipiState.DRAFTED
				&& pratilipi.getState() != PratilipiState.DELETED
				&& ( pratilipi.getSummary() == null || pratilipi.getSummary().trim().isEmpty() ) )
			throw new InvalidArgumentException(
					pratilipi.getType().getName() + " summary is missing. " +
					pratilipi.getType().getName() + " can not be published without a summary." );
		
		
		if( pratilipiData.getId() == null ) { // Add Pratilipi usecase
			if ( ! PratilipiContentHelper.hasRequestAccessToAddPratilipiData( request, pratilipi ) )
				throw new InsufficientAccessException();

			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
			
			Page page = dataAccessor.newPage();
			page.setType( PratilipiPageType.PRATILIPI.toString() );
			page.setUri( PratilipiPageType.PRATILIPI.getUrlPrefix() + pratilipi.getId() );
			page.setPrimaryContentId( pratilipi.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );

		} else { // Update Pratilipi usecase
			if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) )
				throw new InsufficientAccessException();
			
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		}
		
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		
		// Updating Pratilipi page uri
		if( pratilipiData.hasTitleEn() ) {
			Page page = dataAccessor.getPage( PratilipiPageType.PRATILIPI.toString(), pratilipi.getId() );
			Page authorPage = dataAccessor.getPage( PratilipiPageType.AUTHOR.toString(), pratilipi.getAuthorId() );
			if( authorPage.getUriAlias() != null ) {
				String uriAlias = pratilipiHelper.generateUriAlias(
						page.getUriAlias(), authorPage.getUriAlias() + "/", pratilipi.getTitleEn() );
				if( ! uriAlias.equals( page.getUriAlias() ) ) {
					page.setUriAlias( uriAlias );
					page = dataAccessor.createOrUpdatePage( page );
				}
			}
		}
		
		
		return pratilipiHelper.createPratilipiData(
				pratilipi.getId(),
				PratilipiContentHelper.hasRequestAccessToReadPratilipiMetaData( request ) );
	}

	public static PratilipiData savePratilipi( PratilipiData pratilipiData, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
	
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = null;

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		
		if( pratilipiData.getId() == null ) { // Add Pratilipi usecase

			pratilipi = dataAccessor.newPratilipi();
			auditLog.setEventId( ACCESS_TO_ADD_PRATILIPI_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			pratilipi.setContentType( PratilipiContentType.PRATILIPI );
			if( pratilipiData.hasAuthorId() )
				pratilipi.setAuthorId( pratilipiData.getAuthorId() );
			pratilipi.setListingDate( new Date() );
			pratilipi.setLastUpdated( new Date() );

		} else { // Update Pratilipi usecase
		
			pratilipi =  dataAccessor.getPratilipi( pratilipiData.getId() );
			auditLog.setEventId( ACCESS_TO_UPDATE_PRATILIPI_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );

			// Do NOT update Author/Publisher
			pratilipi.setLastUpdated( new Date() );
		}
			
		if( pratilipiData.hasType() )
			pratilipi.setType( pratilipiData.getType() );
		if( pratilipiData.hasTitle() )
			pratilipi.setTitle( pratilipiData.getTitle() );
		if( pratilipiData.hasTitleEn() )
			pratilipi.setTitleEn( pratilipiData.getTitleEn() );
		if( pratilipiData.hasLanguageId() )
			pratilipi.setLanguageId( pratilipiData.getLanguageId() );
		if( pratilipiData.hasPublicationYear() )
			pratilipi.setPublicationYear( pratilipiData.getPublicationYear() );
		if( pratilipiData.hasSummary() )
			pratilipi.setSummary( pratilipiData.getSummary() );
		if( pratilipiData.hasIndex() )
			pratilipi.setIndex( pratilipiData.getIndex() );
		if( pratilipiData.hasWordCount() )
			pratilipi.setWordCount( pratilipiData.getWordCount() );
		if( pratilipiData.hasContentType() && pratilipiData.getContentType() != null  )
			pratilipi.setContentType( pratilipiData.getContentType() );
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
		
		
		if( pratilipiData.getId() == null ) { // Add Pratilipi usecase
			if ( ! PratilipiContentHelper.hasRequestAccessToAddPratilipiData( request, pratilipi ) )
				throw new InsufficientAccessException();

			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
			
			Page page = dataAccessor.newPage();
			page.setType( PratilipiPageType.PRATILIPI.toString() );
			page.setUri( PratilipiPageType.PRATILIPI.getUrlPrefix() + pratilipi.getId() );
			page.setPrimaryContentId( pratilipi.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );

		} else { // Update Pratilipi usecase
			if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) )
				throw new InsufficientAccessException();
			
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		}
		
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		
		// Updating Pratilipi page uri
		if( pratilipiData.hasTitleEn() ) {
			Page page = dataAccessor.getPage( PratilipiPageType.PRATILIPI.toString(), pratilipi.getId() );
			Page authorPage = dataAccessor.getPage( PratilipiPageType.AUTHOR.toString(), pratilipi.getAuthorId() );
			if( authorPage.getUriAlias() != null ) {
				String uriAlias = pratilipiHelper.generateUriAlias(
						page.getUriAlias(), authorPage.getUriAlias() + "/", pratilipi.getTitleEn() );
				if( ! uriAlias.equals( page.getUriAlias() ) ) {
					page.setUriAlias( uriAlias );
					page = dataAccessor.createOrUpdatePage( page );
				}
			}
		}
		
		
		return createPratilipiData(
				pratilipi,
				dataAccessor.getLanguage( pratilipi.getLanguageId() ),
				dataAccessor.getAuthor( pratilipi.getAuthorId() ),
				request );
	}
	
	public static void savePratilipiCover( Long pratilipiId, BlobEntry blobEntry, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		try {
			blobEntry.setName( COVER_FOLDER + "/original/" + pratilipiId );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			blobEntry.setName( COVER_FOLDER + "/150/" + pratilipiId );
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), 150, 1500 ) );
			DataAccessorFactory.getBlobAccessorAsia().createOrUpdateBlob( blobEntry );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to create/update pratilipi cover.", e );
			throw new UnexpectedServerException();
		}
		

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setEventId( ACCESS_TO_UPDATE_PRATILIPI_DATA.getId() );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );

		pratilipi.setCustomCover( true );
		pratilipi.setLastUpdated( new Date() );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		auditLog.setEventComment( "Uploaded cover image." );
		auditLog = dataAccessor.createAuditLog( auditLog );
	}
	
	public static void updatePratilipiIndex( Long pratilipiId, HttpServletRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
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
			
			if( blobEntry == null )
				return;
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			pratilipi.setIndex( pratilipiContentUtil.generateIndex() );
			dataAccessor.createOrUpdatePratilipi( pratilipi );

		} else {
			throw new InvalidArgumentException( "Index generation for " + pratilipi.getContentType() + " content type is not yet supported." );
		}
	}
	
	public static boolean updatePratilipiStats( Long pratilipiId, HttpServletRequest request )
			throws UnexpectedServerException {
		
		List<String> scopes = new LinkedList<>();
		scopes.add( AnalyticsScopes.ANALYTICS_READONLY );
		Analytics analytics = GoogleApi.getAnalytics( scopes );

		long pratilipiReadCount = 0;
		try {
			Get apiQuery = analytics.data().ga()
					.get( "ga:89762686",		// Table Id.
							"2015-01-01",		// Start Date.
							"today",			// End Date.
							"ga:uniqueEvents" )	// Metrics.
					.setDimensions( "ga:eventCategory,ga:eventAction" )
					.setFilters( "ga:eventCategory==Pratilipi:" + pratilipiId + ";ga:eventAction=~^ReadTimeSec:.*" );

			GaData gaData = apiQuery.execute();
			if( gaData.getRows() != null ) {
				for( List<String> row : gaData.getRows() ) {
					long readCount = Long.parseLong( row.get( 2 ) );
					if( readCount > pratilipiReadCount )
						pratilipiReadCount = readCount;
				}
			}
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch data from Google Analytics.", e );
			throw new UnexpectedServerException();
		}
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		if( (long) pratilipi.getReadCount() != pratilipiReadCount ) {
			pratilipi.setReadCount( pratilipiReadCount );
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
			return true;
		} else {
			return false;
		}

	}
	
	public static void updatePratilipiSearchIndex( Long pratilipiId, Long authorId, HttpServletRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		
		List<Pratilipi> pratilipiList = null;
		if( authorId != null ) {
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setAuthorId( authorId );
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
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
				Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
/*				List<PratilipiGenre> pratilipiGenreList = dataAccessor.getPratilipiGenreList( pratilipi.getId() );
				
				List<Genre> genreList = new ArrayList<>( pratilipiGenreList.size() );
				for( PratilipiGenre pratilipiGenre : pratilipiGenreList )
					genreList.add( dataAccessor.getGenre( pratilipiGenre.getGenreId() ) );
TODO: Add Genres in PratilipiData */
				pratilipiDataList.add( createPratilipiData( pratilipi, language, author, request ) );
				
			} else {
				searchAccessor.deletePratilipiDataIndex( pratilipi.getId() );

			}
			
		}
		
		if( pratilipiDataList.size() > 0 )
			searchAccessor.indexPratilipiDataList( pratilipiDataList );
	}

	
	public static Object getPratilipiContent(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			HttpServletRequest request ) throws InvalidArgumentException,
			InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		if( !PratilipiContentHelper.hasRequestAccessToReadPratilipiContent( request, pratilipi ) )
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
			
			String content = blobEntry == null
					? "&nbsp;"
					: new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
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
	
	public static int updatePratilipiContent(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			Object pageContent, boolean insertNew, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !PratilipiContentHelper.hasRequestAccessToUpdatePratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();

		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setEventId( ACCESS_TO_UPDATE_PRATILIPI_DATA.getId() );
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
	
	public static BlobEntry getPratilipiResource(
			long pratilipiId, String fileName, HttpServletRequest request )
			throws UnexpectedServerException {

		try {
			return DataAccessorFactory.getBlobAccessor().getBlob( getPratilipiResourceFolder( pratilipiId ) + "/" + fileName );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch pratilipi resource.", e );
			throw new UnexpectedServerException();
		}
		
	}
	
	public static boolean savePratilipiResource(
			Long pratilipiId, BlobEntry blobEntry, boolean overwrite, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !PratilipiContentHelper.hasRequestAccessToUpdatePratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();

		String fileName = getPratilipiResourceFolder( pratilipiId ) + "/" + blobEntry.getName().replaceAll( "/", "-" );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		try {
			if( !overwrite &&  blobAccessor.getBlob( fileName ) != null ) {
				return false;
			} else {
				blobEntry.setName( fileName );
				blobAccessor.createOrUpdateBlob( blobEntry );
				
				AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
				AuditLog auditLog = dataAccessor.newAuditLog();
				auditLog.setAccessId( accessToken.getId() );
				auditLog.setEventId( ACCESS_TO_UPDATE_PRATILIPI_DATA.getId() );
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
