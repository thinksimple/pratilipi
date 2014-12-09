package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GenericRequest implements IsSerializable {

	private String accessToken;


	@SuppressWarnings("unused")
	private GenericRequest() {}

	public GenericRequest( String accessToken ) {
		this.accessToken = accessToken;
	}

	public final String getAccessToken() {
		return accessToken;
	}

}
