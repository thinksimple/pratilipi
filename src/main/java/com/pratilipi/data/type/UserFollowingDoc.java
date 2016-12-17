package com.pratilipi.data.type;

import java.util.List;

import com.pratilipi.common.type.FollowState;

public interface UserFollowingDoc {

	List<UserAuthorDoc> getUserFollowingDoc( FollowState state );

	void setUserFollowingDoc( FollowState state, List<UserAuthorDoc> userFollowing );

}
