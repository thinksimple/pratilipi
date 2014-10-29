package com.pratilipi.commons.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.Page;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

@SuppressWarnings("serial")
public class PratilipiHelper extends ClaymusHelper {

	public static final Pattern REGEX_PAGE_BREAK = Pattern.compile(
			"(<hr\\s+style=\"page-break-(before|after).+?>)"
			+ "|"
			+ "(<div\\s+style=\"page-break-(before|after).+?>(.+?)</div>)" );

	public static final Pattern REGEX_HTML_BODY = Pattern.compile(
			"(.*?<body\\s.*?>)(.*?)(</body>.*?)" );

	
	public static final String URL_RESOURCE = ClaymusHelper.URL_RESOURCE;
	private static final String URL_RESOURCE_STATIC = ClaymusHelper.URL_RESOURCE_STATIC;

	
	@Deprecated
	public static final String URL_AUTHOR_PAGE = "/author/";
	@Deprecated
	public static final String URL_AUTHOR_IMAGE = "/resource.author-image/original/";

	public static final String URL_LANGUAGE_PAGE = "/language/";

	public static final String URL_GENRE_PAGE = "/genre/";


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
	public static String getPageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return "/" + pratilipiType.getName().toLowerCase() + "/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	@Deprecated
	public static String getReaderPageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return "/read/" + pratilipiType.getName().toLowerCase() + "/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	
	public static String getCoverImage( Long pratilipiId ) {
		
		return "pratilipi-cover/original/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getCoverImage300( Long pratilipiId ) {
		
		return "pratilipi-cover/300/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	

	@Deprecated
	public static String getCoverImageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getCoverImageUrl( pratilipiType, pratilipiId, true );
	}
	
	@Deprecated
	public static String getCoverImageUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getCoverImage( pratilipiId );
	}
	
	@Deprecated
	public static String getCoverImage300Url(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getCoverImage300Url( pratilipiType, pratilipiId, true );
	}
	
	@Deprecated
	public static String getCoverImage300Url(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getCoverImage300( pratilipiId );
	}

	
	public static String getContent( Long pratilipiId ) {
		return "pratilipi-content/pratilipi/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentHtml( Long pratilipiId ) {
		return "pratilipi-content/html/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentWord( Long pratilipiId ) {
		return "pratilipi-content/word/" + ( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentImage( Long pratilipiId ) {
		return "pratilipi-content/image/" + ( pratilipiId == null ? "" : pratilipiId );
	}


	public static String getContentUrl( Long pratilipiId ) {
		return getContentUrl( pratilipiId, true );
	}
	
	public static String getContentUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContent( pratilipiId );
	}
	
	public static String getContentHtmlUrl( Long pratilipiId ) {
		return getContentHtmlUrl( pratilipiId, true );
	}
	
	public static String getContentHtmlUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContentHtml( pratilipiId );
	}
	
	public static String getContentWordUrl( Long pratilipiId ) {
		return getContentWordUrl( pratilipiId, true );
	}
	
	public static String getContentWordUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContentWord( pratilipiId );
	}
	
	public static String getContentImageUrl( Long pratilipiId ) {
		return getContentImageUrl( pratilipiId, true );
	}

	public static String getContentImageUrl( Long pratilipiId, boolean dynamic ) {
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) + getContentImage( pratilipiId );
	}


	@Deprecated
	public static String getAuthorPageUrl( Long authorId ) {
		return URL_AUTHOR_PAGE + authorId;
	}
	

	@Deprecated
	public String createAuthorName( Author author ) {
		return author.getFirstName()
				+ ( author.getLastName() == null ? "" : " " + author.getLastName() );
	}
	
	@Deprecated
	public String createAuthorNameEn( Author author ) {
		return author.getFirstNameEn()
				+ ( author.getLastNameEn() == null ? "" : " " + author.getLastNameEn() );
	}

	
	public PratilipiData createPratilipiData( Long pratilipiId ) {
		return createPratilipiData( pratilipiId, false );
	}
	
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

		
	public PratilipiData createPratilipiData(
			Pratilipi pratilipi, Language language,
			Author author, List<Genre> genreList ) {
		
		return createPratilipiData( pratilipi, language, author, genreList, false );
	}
	
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
		pratilipiData.setCoverImageUrl( getCoverImage300Url( pratilipi.getType(), pratilipi.getId(), false ) );
		pratilipiData.setCoverImageUploadUrl( getCoverImageUrl( pratilipi.getType(), pratilipi.getId(), true ) );
		pratilipiData.setReaderPageUrl( getReaderPageUrl( pratilipi.getType(), pratilipi.getId() ) );
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
		
		pratilipiData.setSummary( pratilipi.getSummary() );
		pratilipiData.setPageCount( pratilipi.getPageCount() );
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

	public AuthorData createAuthorData( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorId );
		Language language = dataAccessor.getLanguage( author.getLanguageId() );
		return createAuthorData( author, language );
	}
	
	public AuthorData createAuthorData( Author author, Language language ) {
		if( author == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page authorPage = dataAccessor.getPage( PratilipiPageType.AUTHOR.toString(), author.getId() );
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setAuthorImageUrl( URL_AUTHOR_IMAGE + author.getId() );
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

}
