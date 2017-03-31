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
		for( var i = 0; i < processedUserList; i++ )
			self.followersList.push( ko.mapping.fromJS( processedUserList[i] ) );
	};

	this.updateFollowingList = function( authorList ) {
		var processedAuthorList = processAuthorList( authorList );
		for( var i = 0; i < processedAuthorList; i++ )
			self.followersList.push( ko.mapping.fromJS( processedAuthorList[i] ) );
	};


	/* Fetching followers and following list */
	this._loadFollowersList = function( resultCount ) {
		
	};

	this._loadFollowingList = function( resultCount ) {
		
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
			console.log( self.author.authorId() );
		}, 0 );
	}, this );

	this.authorUserIdObserver = ko.computed( function() {
		if( self.author.user.userId() == null ) return;
		setTimeout( function() {
			console.log( self.author.user.userId() );
		}, 0 );
	}, this );

}
