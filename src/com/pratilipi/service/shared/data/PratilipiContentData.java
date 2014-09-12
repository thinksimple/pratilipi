package com.pratilipi.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiType;

public class PratilipiContentData implements IsSerializable {

	private Long pratilipiId;

	private PratilipiType pratilipiType;

	private Integer pageNo;

	private String content;
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo( Integer pageNo ) {
		this.pageNo = pageNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

}
