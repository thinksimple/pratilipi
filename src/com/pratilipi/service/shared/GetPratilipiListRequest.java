package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiType;

public class GetPratilipiListRequest implements IsSerializable {

	private PratilipiType pratilipiType;
	
	
	@SuppressWarnings("unused")
	private GetPratilipiListRequest() {}

	public GetPratilipiListRequest( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}

	
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}
	
}
