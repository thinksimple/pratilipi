package com.pratilipi.common.type;

public enum EmailType {

	PRATILIPI_PUBLISHED_AUTHOR		( "PratilipiPublishedAuthorTemplate.ftl" ),		// Email sent to Author whenever s/he publishes a content.
	PRATILIPI_PUBLISHED_FOLLOWER	( "PratilipiPublishedFollowerTemplate.ftl" ),	// Email sent to Author's followers whenever s/he publishes a content.

	AUTHOR_FOLLOW					( "AuthorFollowTemplate.ftl" ),			// Email sent to Author whenever someone follows him/her.

	USER_PRATILIPI_REVIEW			( "UserPratilipiReviewTemplate.ftl" ),	// Email sent to Author when someone reviews his/her content.

	COMMENT_REVIEW_REVIEWER		( "CommentReviewReviewerTemplate.ftl" ),	// Email sent to Reviewer when someone comments on his/her review.
	COMMENT_REVIEW_AUTHOR		( "CommentReviewAuthorTemplate.ftl" ),		// Email sent to Author when someone comments on any review.

	VOTE_REVIEW_REVIEWER 		( "VoteReviewReviewerTemplate.ftl" ),		// Email sent to the reviewer when someone likes his/her comment.
	VOTE_REVIEW_AUTHOR 			( "VoteReviewAuthorTemplate.ftl" ),			// Email sent to the author when someone likes on review of his/her content.

	VOTE_COMMENT_COMMENTOR 		( "VoteCommentCommentorTemplate.ftl" ),		// Email sent to the commentor when someone likes his/her comment.

	;

	
	private String templateName;


	private EmailType( String templateName ) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

}
