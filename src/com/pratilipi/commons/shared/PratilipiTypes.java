package com.pratilipi.commons.shared;

public enum PratilipiTypes {
	
	ARTICLE( "Article" ),
	BOOK( "Book" ),
	POEM( "Poem" ),
	STORY( "Story" );
	
	
	private String name;
	
	private PratilipiTypes( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
