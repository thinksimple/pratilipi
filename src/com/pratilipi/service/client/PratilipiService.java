package com.pratilipi.service.client;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.InsufficientAccessException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserBookRequest;
import com.pratilipi.service.shared.AddUserBookResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetBookRequest;
import com.pratilipi.service.shared.GetBookResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.GetUserBookListRequest;
import com.pratilipi.service.shared.GetUserBookListResponse;
import com.pratilipi.service.shared.GetUserBookRequest;
import com.pratilipi.service.shared.GetUserBookResponse;
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;

@RemoteServiceRelativePath("../service.pratilipi")
public interface PratilipiService extends RemoteService {
	
	AddBookResponse addBook( AddBookRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	UpdateBookResponse updateBook( UpdateBookRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetBookListResponse getBookList( GetBookListRequest request );
	
	GetBookResponse getBookById( GetBookRequest request );
	

	AddLanguageResponse addLanguage( AddLanguageRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetLanguageListResponse getLanguageList( GetLanguageListRequest request )
			throws InsufficientAccessException;


	AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException;
	
	
	AddPublisherResponse addPublisher( AddPublisherRequest request )
			throws InsufficientAccessException;

	GetPublisherListResponse getPublisherList( GetPublisherListRequest request );
	

	AddUserBookResponse addUserBook( AddUserBookRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetUserBookResponse getUserBook( GetUserBookRequest request );
	
	GetUserBookListResponse getUserBookList( GetUserBookListRequest request );

}
