function( params ) {
	var self = this;
	var dataAccessor = new DataAccessor();

	this.author = params.author;

	var processUserList = function( userList ) {
		if( userList == null ) return [];
		var followList = [];
		for( var i = 0; i < userList.length; i++ ) {
			var user = userList[i];
			var follow = {
				authorId: user.author.authorId,
				name: user.author.name,
				pageUrl: user.profilePageUrl,
				followCount: user.author.followCount,
				imageUrl: user.profileImageUrl,
				following: user.author.following,
				canFollow: user.userId != appViewModel.user.userId() 
			};
			followList.push( follow );
		}
		return followList;
	};

	var processAuthorList = function( authorList ) {
		if( authorList == null ) return [];
		var followList = [];
		for( var i = 0; i < authorList.length; i++ ) {
			var author = authorList[i];
			var follow = {
					authorId: author.authorId,
					name: author.name,
					pageUrl: author.pageUrl,
					followCount: author.followCount,
					imageUrl: author.imageUrl,
					following: author.following,
					canFollow: author.user.userId != appViewModel.user.userId()
			};
			followList.push( follow );
		}
		return followList;
	};

	/* Followers and Following List */
	this.followersList = ko.observableArray();
	this.followingList = ko.observableArray();

	this.updateFollowersList = function( userList ) {
		var processedUserList = processUserList( userList );
		for( var i = 0; i < processedUserList.length; i++ )
			self.followersList.push( ko.mapping.fromJS( processedUserList[i] ) );
	};

	this.updateFollowingList = function( authorList ) {
		var processedAuthorList = processAuthorList( authorList );
		for( var i = 0; i < processedAuthorList.length; i++ )
			self.followingList.push( ko.mapping.fromJS( processedAuthorList[i] ) );
	};


	/* Fetching followers and following list */
	var followersCursor = null;
	var followingCursor = null;

	this.isLoadingFollowers = ko.observable( false );
	this.isLoadingFollowing = ko.observable( false );

	this.hasMoreFollowers = ko.observable( true );
	this.hasMoreFollowing = ko.observable( true );

	this.numberFoundFollowers = ko.observable();
	this.numberFoundFollowing = ko.observable();

	this._loadFollowersList = function( resultCount ) {
		if( self.isLoadingFollowers() ) return;
		self.isLoadingFollowers( true );
		dataAccessor.getAuthorFollowers( self.author.authorId(), followersCursor, null, resultCount,
			function( followersListResponse ) {
				if( followersListResponse == null ) {
					self.isLoadingFollowers( false );
					return;
				}
				var userList = followersListResponse.userList;
				self.updateFollowersList( userList );
				followersCursor = followersListResponse.cursor;
				self.numberFoundFollowers( followersListResponse.numberFound );
				self.isLoadingFollowers( false );
				self.hasMoreFollowers( userList.length == resultCount );
		});
	};

	this._loadFollowingList = function( resultCount ) {
		if( self.isLoadingFollowing() ) return;
		self.isLoadingFollowing( true );
		dataAccessor.getUserFollowing( self.author.user.userId(), followingCursor, null, resultCount,
				function( followingListResponse ) {
					if( followingListResponse == null ) {
						self.isLoadingFollowing( false );
						return;
					}
					var authorList = followingListResponse.authorList;
					self.updateFollowingList( authorList );
					followingCursor = followingListResponse.cursor;
					self.numberFoundFollowing( followingListResponse.numberFound );
					self.isLoadingFollowing( false );
					self.hasMoreFollowing( authorList.length == resultCount );
			});
	};


	/* Constants */
	var initialFollowersResultCount = 10;
	var subsequentFollowersResultCount = 15;
	var initialFollowingResultCount = 10;
	var subsequentFollowingResultCount = 15;


	this.loadInitialFollowers = function() {
		self._loadFollowersList( initialFollowersResultCount );
	};

	this.loadMoreFollowers =  function() {
		self._loadFollowersList( subsequentFollowersResultCount );
	};

	this.loadInitialFollowing = function() {
		self._loadFollowingList( initialFollowingResultCount );
	};

	this.loadMoreFollowing =  function() {
		self._loadFollowingList( subsequentFollowingResultCount );
	};


	/* Computed Observables */
	this.authorIdObserver = ko.computed( function() {
		if( self.author.authorId() == null ) return;
		setTimeout( function() {
			self.loadInitialFollowers();
		}, 0 );
	}, this );

	this.authorUserIdObserver = ko.computed( function() {
		if( self.author.user.userId() == null ) return;
		setTimeout( function() {
			self.loadInitialFollowing();
		}, 0 );
	}, this );

}
