package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;

public interface PratilipiServiceAsync {
	
	void greetServer( String input, AsyncCallback<String> callback );
	
	void addBook(
			AddBookRequest request,
			AsyncCallback<AddBookResponse> callback );

	void getBookList(
			GetBookListRequest request,
			AsyncCallback<GetBookListResponse> callback );

	void addLanguage(
			AddLanguageRequest request,
			AsyncCallback<AddLanguageResponse> callback );

}
