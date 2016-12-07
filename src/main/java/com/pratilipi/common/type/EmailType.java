package com.pratilipi.common.type;

public enum EmailType {

	PRATILIPI_PUBLISHED_AUTHOR		( "PratilipiPublishedAuthorTemplate.ftl" ),		// Email sent to Author whenever s/he publishes a content.
	PRATILIPI_PUBLISHED_FOLLOWER	( "PratilipiPublishedFollowerTemplate.ftl" ),	// Email sent to Author's followers whenever s/he publishes a content.

	AUTHOR_FOLLOW					( "AuthorFollowTemplate.ftl" ),					// Email sent to Author whenever someone follows him/her.
	
	USER_PRATILIPI_REVIEW			( "UserPratilipiReviewTemplate.ftl" ),			// Email sent to Author when someone reviews his/her content.
	
	COMMENT_ON_REVIEW_REVIEWER		( "CommentOnReviewReviewerTemplate.ftl" ),			// Email sent to Reviewer when someone comments on his/her review.
	COMMENT_ON_REVIEW_AUTHOR		( "CommentOnReviewAuthorTemplate.ftl" ),			// Email sent to Author when someone comments on any review.
	
	;

	
	private String templateName;


	private EmailType( String templateName ) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

}
