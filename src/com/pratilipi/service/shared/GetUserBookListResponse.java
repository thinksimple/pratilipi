package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserBookData;

public class GetUserBookListResponse implements IsSerializable {
	
	private List<UserBookData> userBookDataList;
	
	
	@SuppressWarnings("unused")
	private GetUserBookListResponse(){}
	
	public GetUserBookListResponse( List<UserBookData> userBookDataList ){
		this.userBookDataList = userBookDataList;
	}

	
	public List<UserBookData> getUserBookList(){
		return userBookDataList;
	}
	
}
