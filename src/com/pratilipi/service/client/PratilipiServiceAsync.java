package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPratilipiRequest;
import com.pratilipi.service.shared.AddPratilipiResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetBookRequest;
import com.pratilipi.service.shared.GetBookResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.GetUserPratilipiListRequest;
import com.pratilipi.service.shared.GetUserPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;
import com.pratilipi.service.shared.UpdatePratilipiRequest;
import com.pratilipi.service.shared.UpdatePratilipiResponse;

public interface PratilipiServiceAsync {
	
	void addPratilipi(
			AddPratilipiRequest request,
			AsyncCallback<AddPratilipiResponse> callback );

	void updatePratilipi(
			UpdatePratilipiRequest request,
			AsyncCallback<UpdatePratilipiResponse> callback );

	void getPratilipiList(
			GetPratilipiListRequest request,
			AsyncCallback<GetPratilipiListResponse> callback );

	
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

	
	void addGenre(
			AddGenreRequest request,
			AsyncCallback<AddGenreResponse> callback );


	void addUserPratilipi( 
			AddUserPratilipiRequest request,
			AsyncCallback<AddUserPratilipiResponse> callback );
	
	void getUserPratilipi( 
			GetUserPratilipiRequest request,
			AsyncCallback<GetUserPratilipiResponse> callBack);

	void getUserPratilipiList( 
			GetUserPratilipiListRequest request,
			AsyncCallback<GetUserPratilipiListResponse> callback );

}
