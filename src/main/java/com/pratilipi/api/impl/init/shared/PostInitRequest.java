package com.pratilipi.api.impl.init.shared;

import com.pratilipi.api.shared.GenericRequest;

public class PostInitRequest extends GenericRequest {
	
	private Long pratilipiId;
	
	private Long readCountOffset;
	private Long readCount;

	private Long fbLikeShareCountOffset;
	private Long fbLikeShareCount;
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	public Long getReadCountOffset() {
		return readCountOffset;
	}

	public Long getReadCount() {
		return readCount;
	}
	
	public Long getFbLikeShareCountOffset() {
		return fbLikeShareCountOffset;
	}
	
	public Long getFbLikeShareCount() {
		return fbLikeShareCount;
	}
	
}
