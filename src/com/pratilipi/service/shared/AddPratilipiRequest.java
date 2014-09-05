package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class AddPratilipiRequest implements IsSerializable {

	private PratilipiData pratilipiData;


	@SuppressWarnings("unused")
	private AddPratilipiRequest() {}
	
	public AddPratilipiRequest( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
	}
	
	
	public PratilipiData getPratilipiData() {
		return this.pratilipiData;
	}

}
