package com.pratilipi.commons.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.Page;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.EventData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;

@SuppressWarnings("serial")
public class PratilipiHelper extends ClaymusHelper {

	@Deprecated
	public static final Pattern REGEX_HTML_BODY = Pattern.compile(
			"(.*?<body\\s.*?>)(.*?)(</body>.*?)" );

	
	@Deprecated
	public static final String URL_RESOURCE = ClaymusHelper.URL_RESOURCE;
	@Deprecated
	private static final String URL_RESOURCE_STATIC = ClaymusHelper.URL_RESOURCE_STATIC;

	
	protected PratilipiHelper( HttpServletRequest request ) {
		super( request );
	}
	
	public static PratilipiHelper get( HttpServletRequest request ) {
		Memcache memcache = DataAccessorFactory.getL1CacheAccessor();
		PratilipiHelper pratilipiHelper = memcache.get( "PratilipiHelper-" + request.hashCode() );
		if( pratilipiHelper == null ) {
			pratilipiHelper = new PratilipiHelper( request );
			memcache.put( "ClaymusHelper-" + request.hashCode(), pratilipiHelper );
			memcache.put( "PratilipiHelper-" + request.hashCode(), pratilipiHelper );
		}
		return pratilipiHelper;
	}
	

	@Deprecated
	public static String getContent( Long pratilipiId ) {
		return "pratilipi-content/pratilipi/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	
	@Deprecated
	public static String getContentHtml( Long pratilipiId ) {
		return "pratilipi-content/html/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	
	@Deprecated
	public static String getContentWord( Long pratilipiId ) {
		return "pratilipi-content/word/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	

	@Deprecated
	public static String getContentUrl( Long pratilipiId ) {
		return getContentUrl( pratilipiId, true );
	}
	
	@Deprecated
	public static String getContentUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContent( pratilipiId );
	}
	
	@Deprecated
	public static String getContentHtmlUrl( Long pratilipiId ) {
		return getContentHtmlUrl( pratilipiId, true );
	}
	
	@Deprecated
	public static String getContentHtmlUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContentHtml( pratilipiId );
	}
	
	@Deprecated
	public static String getContentWordUrl( Long pratilipiId ) {
		return getContentWordUrl( pratilipiId, true );
	}
	
	@Deprecated
	public static String getContentWordUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContentWord( pratilipiId );
	}



	@Deprecated
	public List<PratilipiData> createPratilipiDataListFromIdList(
			List<Long> pratilipiIdList,
			boolean includeLanguageData, boolean includeAuthorData,
			boolean includeGenreData ) { 
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		List<Pratilipi> pratilipiList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList )
			pratilipiList.add( dataAccessor.getPratilipi( pratilipiId ) );
		
		return createPratilipiDataList(
				pratilipiList, includeLanguageData,
				includeAuthorData, includeGenreData, false );
	}
	
	@Deprecated
	public List<PratilipiData> createPratilipiDataList(
			List<Pratilipi> pratilipiList ) {

		return createPratilipiDataList( pratilipiList, true, true, true );
	}
	
	@Deprecated
	public List<PratilipiData> createPratilipiDataList(
			List<Pratilipi> pratilipiList,
			boolean includeLanguageData, boolean includeAuthorData,
			boolean includeGenreData ) { 
		
		return createPratilipiDataList(
				pratilipiList, includeLanguageData,
				includeAuthorData, includeGenreData, false );
	}
	
	@Deprecated
	public List<PratilipiData> createPratilipiDataList(
			List<Pratilipi> pratilipiList,
			boolean includeLanguageData, boolean includeAuthorData,
			boolean includeGenreData, boolean includeMetaData ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Map<Long, LanguageData> languageIdToDataMap =
				includeLanguageData ? new HashMap<Long, LanguageData>() : null;
		Map<Long, AuthorData> authorIdToDataMap =
				includeAuthorData ? new HashMap<Long, AuthorData>() : null;
		
		List<PratilipiData> pratilipiDataList =
				new ArrayList<>( pratilipiList.size() );

		for( Pratilipi pratilipi : pratilipiList ) {

			PratilipiData pratilipiData = null;
			if( includeGenreData ) {
				List<PratilipiGenre> pratilipiGenreList = dataAccessor.getPratilipiGenreList( pratilipi.getId() );
				List<Genre> genreList = new ArrayList<>( pratilipiGenreList.size() );
				for( PratilipiGenre pratilipiGenre : pratilipiGenreList )
					genreList.add( dataAccessor.getGenre( pratilipiGenre.getGenreId() ) );
			} else {
				pratilipiData = createPratilipiData( pratilipi, null, null, null );
			}
			
			if( includeLanguageData ) {
				LanguageData languageData = languageIdToDataMap.get( pratilipi.getLanguageId() );
				if( languageData == null ) {
					Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
					languageData = createLanguageData( language );
					languageIdToDataMap.put( languageData.getId(), languageData );
				}
				pratilipiData.setLanguageData( languageData );
			}

			if( includeAuthorData && pratilipi.getAuthorId() != null ) {
				AuthorData authorData = authorIdToDataMap.get( pratilipi.getAuthorId() );
				if( authorData == null ) {
					Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
					authorData = createAuthorData( author, null );
					authorIdToDataMap.put( authorData.getId(), authorData );
				}
				pratilipiData.setAuthorData( authorData );
			}
			
			pratilipiDataList.add( pratilipiData );
		}
		
		return pratilipiDataList;
	}
	
	
	@Deprecated
	public PratilipiData createPratilipiData( Long pratilipiId ) {
		return createPratilipiData( pratilipiId, false );
	}
	
	@Deprecated
	public PratilipiData createPratilipiData(
			Long pratilipiId, boolean includeMetaData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
		List<PratilipiGenre> pratilipiGenreList = dataAccessor.getPratilipiGenreList( pratilipiId );
		
		List<Genre> genreList = new ArrayList<>( pratilipiGenreList.size() );
		for( PratilipiGenre pratilipiGenre : pratilipiGenreList )
			genreList.add( dataAccessor.getGenre( pratilipiGenre.getGenreId() ) );
		
		
		return createPratilipiData( pratilipi, language, author, genreList, includeMetaData );
	}

		
	@Deprecated
	public PratilipiData createPratilipiData(
			Pratilipi pratilipi, Language language,
			Author author, List<Genre> genreList ) {
		
		return createPratilipiData( pratilipi, language, author, genreList, false );
	}
	
	@Deprecated
	public PratilipiData createPratilipiData(
			Pratilipi pratilipi, Language language,
			Author author, List<Genre> genreList, boolean includeMetaData ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page pratilipiPage = dataAccessor.getPage( PratilipiPageType.PRATILIPI.toString(), pratilipi.getId() );
		if( genreList == null )
			genreList = new ArrayList<Genre>( 0 );
		
		PratilipiData pratilipiData = new PratilipiData();

		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setType( pratilipi.getType() );
		pratilipiData.setPageUrl( pratilipiPage.getUri() );
		pratilipiData.setPageUrlAlias( pratilipiPage.getUriAlias() );
		
		String coverImage;
		if( pratilipi.hasCustomCover() )
			coverImage = "/pratilipi-cover/150/" + pratilipi.getId() + "?" + pratilipi.getLastUpdated().getTime();
		else if( pratilipi.isPublicDomain() )
			coverImage = "/pratilipi-cover/150/pratilipi-classic-" + pratilipi.getLanguageId();
		else
			coverImage = "/pratilipi-cover/150/pratilipi";

		pratilipiData.setCoverImageUrl( ClaymusHelper.getSystemProperty( "cdn.asia" ) + coverImage );
		pratilipiData.setCoverImageUploadUrl( "/api.pratilipi/pratilipi/cover?pratilipiId=" + pratilipi.getId() );
		pratilipiData.setCoverImage300UploadUrl( URL_RESOURCE + "pratilipi-cover/300/" + pratilipi.getId() );
		pratilipiData.setImageContentUploadUrl( URL_RESOURCE + "pratilipi-content/image/" + pratilipi.getId() );
		pratilipiData.setWordContentUplaodUrl( URL_RESOURCE + "pratilipi-content/word/" + pratilipi.getId() );
		pratilipiData.setPdfContentUplaodUrl( URL_RESOURCE + "pratilipi-content/pdf/" + pratilipi.getId());
		pratilipiData.setReaderPageUrl( PratilipiPageType.READ.getUrlPrefix() + pratilipi.getId() );
		pratilipiData.setWriterPageUrl( PratilipiPageType.WRITE.getUrlPrefix() + pratilipi.getId() );
		if( includeMetaData )
			pratilipiData.setPublicDomain( pratilipi.isPublicDomain() );
		
		pratilipiData.setTitle( pratilipi.getTitle() );
		pratilipiData.setTitleEn( pratilipi.getTitleEn() );
		pratilipiData.setLanguageId( pratilipi.getLanguageId() );
		pratilipiData.setLanguageData( createLanguageData( language ) );

		pratilipiData.setAuthorId( pratilipi.getAuthorId() );
		pratilipiData.setAuthorData( createAuthorData( author, null ) );
		
		pratilipiData.setPublicationYear( pratilipi.getPublicationYear() );
		pratilipiData.setListingDate( pratilipi.getListingDate() );
		pratilipiData.setLastUpdated( pratilipi.getLastUpdated() );
		
		pratilipiData.setSummary( pratilipi.getSummary() );
		pratilipiData.setIndex( pratilipi.getIndex() );
		pratilipiData.setPageCount( pratilipi.getPageCount() == null ? null : (long) (int) pratilipi.getPageCount() );
		
		double relevance = pratilipi.getReadCount() + pratilipi.getRelevanceOffset();
		if( author != null && author.getContentPublished() > 1L )
			relevance = relevance + (double) author.getTotalReadCount() / (double) author.getContentPublished();
		pratilipiData.setRelevance( relevance );
		
		pratilipiData.setContentType( pratilipi.getContentType() );
		pratilipiData.setState( pratilipi.getState() );

		List<Long> genreIdList = new ArrayList<>( genreList.size() );
		List<String> genreNameList = new ArrayList<>( genreList.size() );
		for( Genre genre : genreList ) {
			genreIdList.add( genre.getId() );
			genreNameList.add( genre.getName() );
		}
		pratilipiData.setGenreIdList( genreIdList );
		pratilipiData.setGenreNameList( genreNameList );
		
		return pratilipiData;
	}


	@Deprecated
	public AuthorData createAuthorData( Long authorId ) {
		if( authorId == null )
			return null;
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorId );
		Language language = dataAccessor.getLanguage( author.getLanguageId() );
		return createAuthorData( author, language );
	}
	
	@Deprecated
	public AuthorData createAuthorData( Author author, Language language ) {
		if( author == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page authorPage = dataAccessor.getPage( PratilipiPageType.AUTHOR.toString(), author.getId() );
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setAuthorImageUrl( URL_RESOURCE + "author-image/original/" + author.getId() );
		authorData.setAuthorImageUploadUrl( URL_RESOURCE + "author-image/original/" + author.getId() );
		authorData.setUserId( author.getUserId() );

		authorData.setLanguageId( author.getLanguageId() );
		authorData.setLanguageData( createLanguageData( language ) );

		authorData.setFirstName( author.getFirstName() );
		authorData.setLastName( author.getLastName() );
		authorData.setPenName( author.getPenName() );
		authorData.setFirstNameEn( author.getFirstNameEn() );
		authorData.setLastNameEn( author.getLastNameEn() );
		authorData.setPenNameEn( author.getPenNameEn() );
		authorData.setSummary( author.getSummary() );
		authorData.setEmail( author.getEmail() );
		authorData.setRegistrationDate( new Date() );
		
		return authorData;
	}
	
	
	public LanguageData createLanguageData( Long languageId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Language language = dataAccessor.getLanguage( languageId );
		return createLanguageData( language );
	}
	
	public LanguageData createLanguageData( Language language ) {
		if( language == null )
			return null;
		
		LanguageData languageData = new LanguageData();
		languageData.setId( language.getId() );
		languageData.setName( language.getName() );
		languageData.setNameEn( language.getNameEn() );
		languageData.setCreationDate( language.getCreationDate() );
		return languageData;
	}

	
	public PublisherData createPublisherData( Long publisherId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Publisher publisher = dataAccessor.getPublisher( publisherId );
		Language language = dataAccessor.getLanguage( publisher.getLanguageId() );
		return createPublisherData( publisher, language );
	}
	
	public PublisherData createPublisherData( Publisher publisher, Language language ) {
		if( publisher == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page publisherPage = dataAccessor.getPage( PratilipiPageType.PUBLISHER.toString(), publisher.getId() );
		
		PublisherData publisherData = new PublisherData();
		
		publisherData.setId( publisher.getId() );
		publisherData.setPageUrl( publisherPage.getUri() );
		publisherData.setPageUrlAlias( publisherPage.getUriAlias() );
		publisherData.setPublisherBannerUrl( URL_RESOURCE + "publisher-banner/original/" + publisher.getId() );
		publisherData.setPublisherBannerUploadUrl( URL_RESOURCE + "publisher-banner/original/" + publisher.getId() );
		publisherData.setUserId( publisher.getUserId() );

		publisherData.setLanguageId( publisher.getLanguageId() );
		publisherData.setLanguageData( createLanguageData( language ) );

		publisherData.setName( publisher.getName() );
		publisherData.setNameEn( publisher.getNameEn() );
		publisherData.setEmail( publisher.getEmail() );
		publisherData.setRegistrationDate( new Date() );
		
		return publisherData;
	}
	
	public EventData createEventData( Event event ) {
		if( event == null )
			return null;
		
		EventData eventData = new EventData();
		
		eventData.setId( event.getId() );
		eventData.setEventBannerUrl( URL_RESOURCE + "event-banner/original/" + event.getId() );
		eventData.setEventBannerUploadUrl( URL_RESOURCE + "event-banner/original/" + event.getId() );

		eventData.setName( event.getName() );
		eventData.setNameEn( event.getNameEn() );

		eventData.setStartDate( event.getStartDate() );
		eventData.setEndDate( event.getEndDate() );
		eventData.setCreationDate( event.getCreationDate() );
		
		return eventData;
	}

}
