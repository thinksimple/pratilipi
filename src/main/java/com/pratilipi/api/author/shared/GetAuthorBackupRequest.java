package com.pratilipi.api.author.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetAuthorBackupRequest extends GenericRequest {
	
	private Boolean csv;
	
	public Boolean getCsv() {
		return csv == null ? false : csv;
	}

}
