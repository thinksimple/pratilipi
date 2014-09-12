package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiContentData;

public class SavePratilipiContentRequest implements IsSerializable {

	private PratilipiContentData pratilipiContentData;

	
	@SuppressWarnings("unused")
	private SavePratilipiContentRequest() {}
	
	public SavePratilipiContentRequest( PratilipiContentData pratilipiContentData ) {
		this.pratilipiContentData = pratilipiContentData;
	}
	
	
	public PratilipiContentData getPratilipiContentData() {
		return this.pratilipiContentData;
	}

}
