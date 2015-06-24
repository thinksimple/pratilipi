package com.pratilipi.data.util;

import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;

public class AuthorDataUtil {
	
	public static String creatAuthorImageUrl( Author author ) {
		if( author.hasCustomCover() ) {
			String domain = "//" + author.getId() % 10 + "." + SystemProperty.get( "cdn" );
			String uri = "/author-image/150/" + author.getId() + "?" + author.getLastUpdated().getTime();
			return domain + uri;
		} else {
			String domain = "//10." + SystemProperty.get( "cdn" );
			String uri = "/author-image/150/author";
			return domain + uri;
		}
	}
	
	public static AuthorData createData( Author author ) {
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
		
		authorData.setEmail( author.getEmail() );
		authorData.setLanguageCode( author.getLanguage().getCode() );
		authorData.setSummary( author.getSummary() );
		
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setImageUrl( creatAuthorImageUrl( author ) );

		authorData.setRegistrationDate( author.getRegistrationDate() );
		authorData.setContentPublished( author.getContentPublished() );
		
		return authorData;
	}
	
}
