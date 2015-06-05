package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetUserPratilipiListRequest implements IsSerializable {
	
	private Long userId;
	private Long pratilipiId;
	
	
	@SuppressWarnings("unused")
	private GetUserPratilipiListRequest() {}
	
	public GetUserPratilipiListRequest( Long userId, Long pratilipiId ) {
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
