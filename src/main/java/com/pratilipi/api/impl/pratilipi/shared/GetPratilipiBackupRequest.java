package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiBackupRequest extends GenericRequest {
	
	private Boolean generateCsv;
	
	public boolean generateCsv() {
		return generateCsv == null ? false : generateCsv;
	}

}
