package com.pratilipi.commons.shared;

public enum PratilipiType {
	
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
