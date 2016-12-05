package com.pratilipi.common.type;

public enum EmailType {

	PRATILIPI_PUBLISHED_AUTHOR_EMAIL( "PratilipiPublishedAuthorTemplate.ftl" ), // Email sent to Author whenever s/he publishes a content.
	PRATILIPI_PUBLISHED_FOLLOWER_EMAIL( "PratilipiPublishedFollowerTemplate.ftl" ), // Email sent to Author's followers whenever s/he publishes a content.
	AUTHOR_FOLLOW_EMAIL( "AuthorFollowTemplate.ftl" ), // Email sent to Author whenever someone follows him/her.
	USER_PRATILIPI_REVIEW_EMAIL( "UserPratilipiReviewTemplate.ftl" ) // Email sent to Author when someone reviews his/her content.
	;

	private String templateName;


	private EmailType( String templateName ) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

}
