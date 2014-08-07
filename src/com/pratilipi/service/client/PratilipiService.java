package com.pratilipi.service.client;

import com.claymus.client.InsufficientAccessException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;

@RemoteServiceRelativePath("../pratilipiservice")
public interface PratilipiService extends RemoteService {
	
	AddBookResponse addBook( AddBookRequest request )
			throws InsufficientAccessException;

	GetBookListResponse getBookList( GetBookListRequest request );


	AddLanguageResponse addLanguage( AddLanguageRequest request )
			throws InsufficientAccessException;

	GetLanguageListResponse getLanguageList( GetLanguageListRequest request );


	AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws InsufficientAccessException;

	GetAuthorListResponse getAuthorList( GetAuthorListRequest request );

}
