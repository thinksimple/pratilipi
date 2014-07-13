package com.claymus.data.transfer;

public interface WebsiteLayout {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getTemplate();

	void setTemplate( String template );

}
