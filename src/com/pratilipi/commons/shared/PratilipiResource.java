package com.pratilipi.commons.shared;

import com.claymus.commons.shared.Resource;

public enum PratilipiResource implements Resource {

	;
	
	
	private String tag;
	
	
	private PratilipiResource( String tag ) {
		this.tag = tag;
	}
	
	public String getTag() {
		return this.tag;
	}
	
}
