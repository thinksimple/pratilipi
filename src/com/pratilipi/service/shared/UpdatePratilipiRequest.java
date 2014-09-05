package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class UpdatePratilipiRequest implements IsSerializable {

	private PratilipiData pratilipiData;


	@SuppressWarnings("unused")
	private UpdatePratilipiRequest() {}
	
	public UpdatePratilipiRequest( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
	}
	
	
	public PratilipiData getPratilipiData() {
		return this.pratilipiData;
	}

}
