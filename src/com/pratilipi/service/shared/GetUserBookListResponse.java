package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserBookData;

public class GetUserBookListResponse implements IsSerializable {
	
	private List<UserBookData> userBookData;
	
	
	@SuppressWarnings("unused")
	private GetUserBookListResponse(){}
	
	public GetUserBookListResponse( List<UserBookData> userBookData ){
		this.userBookData = userBookData;
	}

	
	public List<UserBookData> getBook(){
		return this.userBookData;
	}
	
}
