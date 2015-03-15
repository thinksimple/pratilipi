package com.pratilipi.pagecontent.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;
import com.pratilipi.service.shared.data.LanguageData;

public class AuthorContentHelper extends PageContentHelper<
		AuthorContent,
		AuthorContentData,
		AuthorContentProcessor> {
	
	
	public static final Access ACCESS_TO_LIST_AUTHOR_DATA =
			new Access( "author_data_list", false, "View Author Data" );
	public static final Access ACCESS_TO_ADD_AUTHOR_DATA =
			new Access( "author_data_add", false, "Add Author Data" );
	public static final Access ACCESS_TO_UPDATE_AUTHOR_DATA =
			new Access( "author_data_update", false, "Update Author Data" );
	private static Logger logger = Logger.getLogger( AuthorContentHelper.class.getName() );
	
	
	@Override
	public String getModuleName() {
		return "Author";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_AUTHOR_DATA,
				ACCESS_TO_ADD_AUTHOR_DATA,
				ACCESS_TO_UPDATE_AUTHOR_DATA
		};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		return new AuthorContentEntity( authorId );
	}

	
	public static boolean hasRequestAccessToListAuthorData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		return accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() )
				&& UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_LIST_AUTHOR_DATA, request );
	}
	
	public static boolean hasRequestAccessToAddAuthorData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_AUTHOR_DATA );
	}
	
	public static boolean hasRequestAccessToUpdateAuthorData(
			HttpServletRequest request, Author author ) {
		
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_AUTHOR_DATA ) ||
				(
					author.getUserId() != null &&
					author.getUserId().equals( PratilipiHelper.get( request ).getCurrentUserId() )
				);
	}
	
	
	public static boolean hasAuthorProfile( HttpServletRequest request, Long userId ){
		
		return DataAccessorFactory.getDataAccessor( request ).getAuthorByUserId( userId ) == null ? false : true;
	}
	
	public static Author createAuthorProfile( HttpServletRequest request, User user ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthorByEmailId( user.getEmail() );
		
		if( author == null ){
			author = dataAccessor.newAuthor();
			author.setEmail( user.getEmail() );
			author.setFirstNameEn( user.getFirstName() );
			author.setLastNameEn( user.getLastName() );
			author.setUserId( user.getId() );
			author.setRegistrationDate( new Date() );
			author.setLanguageId( 5130467284090880L );
			
			logger.log( Level.INFO, "Created Author Id : " + author.getId() );
			author = dataAccessor.createOrUpdateAuthor( author );
			
			Page page = dataAccessor.newPage();
			page.setType( PratilipiPageType.AUTHOR.toString() );
			page.setUri( "/author/" + author.getId().toString() );
			page.setPrimaryContentId( author.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
			
		} else if( author.getUserId() == null ){
			author.setUserId( user.getId() );
			logger.log( Level.INFO, "Updated Author Id : " + author.getId() );
			dataAccessor.createOrUpdateAuthor( author );
		}
		
		return author;
	}
	
	
	public static List<AuthorData> createAuthorDataList( List<Author> authorList, boolean includeLanguageData, HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Map<Long, LanguageData> languageIdToDataMap = includeLanguageData ? new HashMap<Long, LanguageData>() : null;
		
		List<AuthorData> authorDataList = new ArrayList<>( authorList.size() );

		for( Author author : authorList ) {
			AuthorData authorData = createAuthorData( author, null, request );

			if( includeLanguageData ) {
				LanguageData languageData = languageIdToDataMap.get( author.getLanguageId() );
				if( languageData == null ) {
					Language language = dataAccessor.getLanguage( author.getLanguageId() );
					languageData = PratilipiHelper.get( request ).createLanguageData( language );
					languageIdToDataMap.put( languageData.getId(), languageData );
				}
				authorData.setLanguageData( languageData );
			}

			authorDataList.add( authorData );
		}
		
		return authorDataList;
		
	}
	
	public static AuthorData createAuthorData( Author author, Language language, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page authorPage = dataAccessor.getPage( PratilipiPageType.AUTHOR.toString(), author.getId() );
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setAuthorImageUrl( "/resource.author-image/original/" + author.getId() );

		authorData.setLanguageId( author.getLanguageId() );
		authorData.setLanguageData( PratilipiHelper.get( request ).createLanguageData( language ) );

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
		
		authorData.setSummary( author.getSummary() );
		authorData.setEmail( author.getEmail() );
		authorData.setRegistrationDate( new Date() );
		authorData.setContentPublished( author.getContentPublished() );
		
		return authorData;
	}
	
	public static DataListCursorTuple<AuthorData> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount, HttpServletRequest request )
			throws InsufficientAccessException {
		
		if( ! hasRequestAccessToListAuthorData( request ) )
			throw new InsufficientAccessException();
		
		DataListCursorTuple<Author> authorListCursorTuple = DataAccessorFactory
				.getDataAccessor( request )
				.getAuthorList( authorFilter, cursor, resultCount );
		
		List<AuthorData> authorDataList = createAuthorDataList(
				authorListCursorTuple.getDataList(),
				authorFilter.getLanguageId() == null,
				request );
		
		return new DataListCursorTuple<AuthorData>( authorDataList, authorListCursorTuple.getCursor() );
	}

	
	public static boolean updateAuthorStats( Long authorId, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();
		
		if( pratilipiList.size() == 0 )
			return false;
		
		long contentPublished = 0;
		long totalReadCount = 0;
		for( Pratilipi pratilipi : pratilipiList ) {
			if( pratilipi.getState() == PratilipiState.PUBLISHED || pratilipi.getState() == PratilipiState.PUBLISHED_PAID )
				contentPublished++;
			totalReadCount = totalReadCount + pratilipi.getReadCount();
		}
		
		Author author = dataAccessor.getAuthor( authorId );
		if( (long) author.getContentPublished() != contentPublished || (long) author.getTotalReadCount() != totalReadCount ) {
			author.setContentPublished( contentPublished );
			author.setTotalReadCount( totalReadCount );
			author = dataAccessor.createOrUpdateAuthor( author );
			return true;
		} else {
			return false;
		}

	}
	
	public static void updateAuthorSearchIndex( Long authorId, HttpServletRequest request )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		Author author = dataAccessor.getAuthor( authorId );
		Language language = dataAccessor.getLanguage( author.getLanguageId() );
		searchAccessor.indexAuthorData( createAuthorData( author, language, request ) );
	}
	
}
