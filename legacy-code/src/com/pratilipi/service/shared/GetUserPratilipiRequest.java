package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetUserPratilipiRequest implements IsSerializable {
	
	private Long userId;
	private Long pratilipiId;
	
	
	@SuppressWarnings("unused")
	private GetUserPratilipiRequest() {}
	
	public GetUserPratilipiRequest( Long userId, Long pratilipiId ) {
		this.userId = userId;
		this.pratilipiId = pratilipiId;
	}
	
	
	public Long getUserId(){
		return this.userId;
	}

	public Long getPratilipiId() {
		return this.pratilipiId;
	}
	
}
