package com.pratilipi.common.type;

public enum PratilipiState {

	DRAFTED,				// Work in progress
	SUBMITTED,				// Final work waiting for approval from Moderator
	PUBLISHED,				// Published and free
	PUBLISHED_PAID,			// Published and paid

	// A content piece can be put in this state in two ways:
	// 1. Manually, by Author or Author Engagement person
	// 2. Automatically, if an Author chooses to delete his/her profile 
	PUBLISHED_DISCONTINUED,	// Published and discontinued

	DELETED,
	
}