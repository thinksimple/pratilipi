package com.pratilipi.pagecontent.author.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.author.api.shared.PutSaveAuthorRequest;
import com.pratilipi.pagecontent.author.api.shared.PutSaveAuthorResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/author" )
public class AuthorApi extends GenericApi {

	@Put
	public PutSaveAuthorResponse saveAuthor( PutSaveAuthorRequest request ) 
			throws InsufficientAccessException, InvalidArgumentException {
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( request.getId() );
		
		if( request.hasLanguageId() )
			authorData.setLanguageId( request.getLanguageId() );
		if( request.hasFirstName() )
			authorData.setFirstName( request.getFirstName() );
		if( request.hasLastName() )
			authorData.setLastName( request.getLastName() );
		if( request.hasPenName() )
			authorData.setPenName( request.getPenName() );
		if( request.hasFirstNameEn() )
			authorData.setFirstNameEn( request.getFirstNameEn() );
		if( request.hasLastNameEn() )
			authorData.setLastNameEn( request.getLastNameEn() );
		if( request.hasPenNameEn() )
			authorData.setPenNameEn( request.getPenNameEn() );
		if( request.hasEmail() )
			authorData.setEmail( request.getEmail() == null ? null : request.getEmail().toLowerCase() );
		
		authorData = AuthorContentHelper.saveAuthor( this.getThreadLocalRequest(), authorData );
		
		return new PutSaveAuthorResponse( authorData );
	}
	
}
