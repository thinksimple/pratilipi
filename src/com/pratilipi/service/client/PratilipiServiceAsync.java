package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

public interface PratilipiServiceAsync {
	
	void addBook(
			AddBookRequest request,
			AsyncCallback<AddBookResponse> callback );

	void updateBook(
			UpdateBookRequest request,
			AsyncCallback<UpdateBookResponse> callback );

	void getBookList(
			GetBookListRequest request,
			AsyncCallback<GetBookListResponse> callback );
	
	void getBookById(
			GetBookRequest request,
			AsyncCallback<GetBookResponse> callback );
	
	void addUserBook( 
			AddUserBookRequest request,
			AsyncCallback<AddUserBookResponse> callback );
	
	void getUserBook( 
			GetUserBookRequest request,
			AsyncCallback<GetUserBookResponse> callBack);

	void getUserBookList( 
			GetUserBookListRequest request,
			AsyncCallback<GetUserBookListResponse> callback );

	
	void addLanguage(
			AddLanguageRequest request,
			AsyncCallback<AddLanguageResponse> callback );

	void getLanguageList(
			GetLanguageListRequest request,
			AsyncCallback<GetLanguageListResponse> callback );

	
	void addAuthor(
			AddAuthorRequest request,
			AsyncCallback<AddAuthorResponse> callback );

	void getAuthorList(
			GetAuthorListRequest request,
			AsyncCallback<GetAuthorListResponse> callback );
	
	void addPublisher(
			AddPublisherRequest request,
			AsyncCallback<AddPublisherResponse> callback );

	void getPublisherList(
			GetPublisherListRequest request,
			AsyncCallback<GetPublisherListResponse> callback );

}
