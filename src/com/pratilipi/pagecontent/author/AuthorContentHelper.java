package com.pratilipi.pagecontent.author;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.shared.ClaymusPageType;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.pages.PagesContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;

public class AuthorContentHelper extends PageContentHelper<
		AuthorContent,
		AuthorContentData,
		AuthorContentProcessor> {
	
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
				ACCESS_TO_ADD_AUTHOR_DATA,
				ACCESS_TO_UPDATE_AUTHOR_DATA
		};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		return new AuthorContentEntity( authorId );
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
		
		boolean hasAuthor = DataAccessorFactory.getDataAccessor( request ).getAuthorByUserId( userId ) == null ? false : true;
		
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
			author.setLanguageId( 5750790484393984L );
			
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
		
}
