package com.pratilipi.common.type;

public enum EmailType {

	PRATILIPI_PUBLISHED_AUTHOR_EMAIL( "Email sent to Author whenever s/he publishes a content." ),
	PRATILIPI_PUBLISHED_FOLLOWER_EMAIL( "Email sent to Author's followers whenever s/he publishes a content." )
	;


	private String description;


	private EmailType( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
