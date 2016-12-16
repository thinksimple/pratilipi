package com.pratilipi.data.type;

import java.util.List;

public interface UserDoc {

	List<UserAuthorDoc> getUserAuthorDoc();

	void setUserAuthorDoc( List<UserAuthorDoc> followedUserAuthors );

}
