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
		authorData.setFirstNameEn( request.getFirstNameEn() );
		authorData.setLastNameEn( request.getLastNameEn() );
		authorData.setEmail( request.getEmail() );
		
		authorData = AuthorContentHelper.saveAuthor( this.getThreadLocalRequest(), authorData );
		
		return new PutSaveAuthorResponse( authorData.getPageUrl() );
	}
	
}
