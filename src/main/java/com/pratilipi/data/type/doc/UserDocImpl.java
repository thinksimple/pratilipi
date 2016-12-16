package com.pratilipi.data.type.doc;

import java.util.List;

import com.pratilipi.data.type.UserAuthorDoc;
import com.pratilipi.data.type.UserDoc;

public class UserDocImpl implements UserDoc {

	private List<UserAuthorDoc> followedUserAuthors;

	@Override
	public List<UserAuthorDoc> getUserAuthorDoc() {
		return followedUserAuthors;
	}

	@Override
	public void setUserAuthorDoc( List<UserAuthorDoc> followedUserAuthors ) {
		this.followedUserAuthors = followedUserAuthors;
		// TODO Auto-generated method stub
		
	}

}
