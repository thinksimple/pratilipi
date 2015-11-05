package com.pratilipi.api.user.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetUserBackupRequest extends GenericRequest {
	
	private Boolean csv;
		
	public Boolean getCsv() {
		return csv == null ? false : csv;
	}
	
}
