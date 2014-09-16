package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Page extends Serializable {

	Long getId();

	String getUri();

	void setUri( String uri );

	String getTitle();

	void setTitle( String title );

	Long getLayoutId();
	
	void setLayout( Long layoutId );
	
	Date getCreationDate();

	void setCreationDate( Date creationDate );

}