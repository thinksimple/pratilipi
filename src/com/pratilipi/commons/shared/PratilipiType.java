package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PratilipiType implements IsSerializable {
	
	ARTICLE( "Article" ),
	BOOK( "Book" ),
	POEM( "Poem" ),
	STORY( "Story" );
	
	
	private String name;
	
	private PratilipiType( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
