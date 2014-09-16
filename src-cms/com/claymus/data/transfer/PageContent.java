package com.claymus.data.transfer;

import java.io.Serializable;

public interface PageContent extends Serializable {

	Long getId();

	Long getPageId();
	
	void setPageId( Long pageId );

	String getPosition();

	void setPosition( String position );

}
