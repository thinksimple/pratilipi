package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PratilipiType implements IsSerializable {

	BOOK( "Book", "Books" ),
	POEM( "Poem", "Poems" ),
	STORY( "Story", "Stories" ),
	ARTICLE( "Article", "Articles" );
	
	
	private final String name;
	private final String namePlural;
	
	
	private PratilipiType( String name, String namePlural ) {
		this.name = name;
		this.namePlural = namePlural;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getNamePlural() {
		return namePlural;
	}
	
}
