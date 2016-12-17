package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.FollowState;
import com.pratilipi.data.type.UserAuthorDoc;
import com.pratilipi.data.type.UserFollowingDoc;

public class UserFollowingDocImpl implements UserFollowingDoc {

	private Map<FollowState, List<UserAuthorDoc>> userFollowing;

	@Override
	public List<UserAuthorDoc> getUserFollowingDoc( FollowState state ) {

		if( userFollowing.containsKey( state ) )
			return userFollowing.get( state );
		else
			return new ArrayList<>();

	}

	@Override
	public void setUserFollowingDoc( FollowState state, List<UserAuthorDoc> userFollowing ) {

		if( this.userFollowing == null )
			this.userFollowing = new HashMap<>();

		this.userFollowing.put( state, userFollowing );

	}

}
