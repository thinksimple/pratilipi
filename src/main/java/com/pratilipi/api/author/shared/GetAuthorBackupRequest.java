package com.pratilipi.api.author.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetAuthorBackupRequest extends GenericRequest {
	
	private Boolean generateCsv;
	
	public boolean generateCsv() {
		return generateCsv == null ? false : generateCsv;
	}

}
