package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
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
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.UserQueryRequest;
import com.pratilipi.service.shared.UserQueryResponse;

public interface PratilipiServiceAsync {
	
	void savePratilipi(
			SavePratilipiRequest request,
			AsyncCallback<SavePratilipiResponse> callback );

	void getPratilipiList(
			GetPratilipiListRequest request,
			AsyncCallback<GetPratilipiListResponse> callback );

	
	void savePratilipiContent(
			SavePratilipiContentRequest request,
			AsyncCallback<SavePratilipiContentResponse> callback );

	
	void addLanguage(
			AddLanguageRequest request,
			AsyncCallback<AddLanguageResponse> callback );

	void getLanguageList(
			GetLanguageListRequest request,
			AsyncCallback<GetLanguageListResponse> callback );

	
	void addAuthor(
			AddAuthorRequest request,
			AsyncCallback<AddAuthorResponse> callback );
	
	void saveAuthor(
			SaveAuthorRequest request,
			AsyncCallback<SaveAuthorResponse> callback );

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
	
	void userQueryMail(
			UserQueryRequest request,
			AsyncCallback<UserQueryResponse> callback );

}
