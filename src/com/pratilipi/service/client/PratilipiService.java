package com.pratilipi.service.client;

import com.claymus.client.IllegalArgumentException;
import com.claymus.client.InsufficientAccessException;
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
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;

@RemoteServiceRelativePath("../service.pratilipi")
public interface PratilipiService extends RemoteService {
	
	AddBookResponse addBook( AddBookRequest request )
			throws InsufficientAccessException,
					IllegalArgumentException;

	UpdateBookResponse updateBook( UpdateBookRequest request )
			throws InsufficientAccessException,
					IllegalArgumentException;

	GetBookListResponse getBookList( GetBookListRequest request );
	
	GetBookResponse getBookById( GetBookRequest request );
	
	//Function to get and add user rating and review.
	AddUserBookResponse addUserBook( AddUserBookRequest request )
			throws InsufficientAccessException;

	GetUserBookListResponse getUserBookList( GetUserBookListRequest request );

	AddLanguageResponse addLanguage( AddLanguageRequest request )
			throws InsufficientAccessException;

	GetLanguageListResponse getLanguageList( GetLanguageListRequest request );


	AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws InsufficientAccessException;

	GetAuthorListResponse getAuthorList( GetAuthorListRequest request );
	
	AddPublisherResponse addPublisher( AddPublisherRequest request )
			throws InsufficientAccessException;

	GetPublisherListResponse getPublisherList( GetPublisherListRequest request );

}
