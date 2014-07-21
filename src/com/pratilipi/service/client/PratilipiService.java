package com.pratilipi.service.client;

import com.claymus.client.InsufficientAccessException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;

@RemoteServiceRelativePath("../pratilipiservice")
public interface PratilipiService extends RemoteService {
	
	String greetServer( String name ) throws IllegalArgumentException;

	AddBookResponse addBook( AddBookRequest request ) throws InsufficientAccessException;

	GetBookListResponse getBookList( GetBookListRequest request );

}
