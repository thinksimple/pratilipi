package com.pratilipi.common.type;

public enum PratilipiType {

	BOOK	( "Book", "Books" ),
	POEM	( "Poem", "Poems" ),
	STORY	( "Story", "Stories" ),
	ARTICLE	( "Article", "Articles" ),
	MAGAZINE( "Magazine", "Magazines" ),
	;
	
	
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