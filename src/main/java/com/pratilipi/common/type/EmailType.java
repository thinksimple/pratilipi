package com.pratilipi.common.type;

public enum EmailType {

	PRATILIPI_PUBLISHED_AUTHOR_EMAIL( "PratilipiPublishedAuthorTemplate.ftl", "Email sent to Author whenever s/he publishes a content." ),
	PRATILIPI_PUBLISHED_FOLLOWER_EMAIL( "PratilipiPublisedFollowerTemplate.ftl", "Email sent to Author's followers whenever s/he publishes a content." )
	;

	private String templateName;
	private String description;


	private EmailType( String templateName, String description ) {
		this.templateName = templateName;
		this.description = description;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getDescription() {
		return description;
	}

}
