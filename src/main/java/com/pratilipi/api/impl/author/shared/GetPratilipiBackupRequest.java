package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiBackupRequest extends GenericRequest {
	
	private Boolean generateCsv;
	
	public boolean generateCsv() {
		return generateCsv == null ? false : generateCsv;
	}

}
