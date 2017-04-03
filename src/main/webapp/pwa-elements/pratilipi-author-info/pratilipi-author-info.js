function( params ) { 
	var self = this;
	var dataAccessor = new DataAccessor();

	/* Setting Local Variables */
	this.author = params.author;
	this.userAuthor = params.userAuthor;
	this.updateAuthor = params.updateAuthor;
	this.updateUserAuthor = params.updateUserAuthor;

	/* Follow/Unfollow Author */
	this.followRequestOnFlight = ko.observable( false );
	this.followOrUnfollowAuthor = function() {
		if( appViewModel.user.isGuest() ) {
			goToLoginPage( null, { "message": "FOLLOW" } );
			return;
		}
		if( self.followRequestOnFlight() ) 
			return;
		self.followRequestOnFlight( true );

		ToastUtil.toast( "${ _strings.success_generic_message }" );
		var switchState = function() {
			self.updateAuthor( { "followCount": self.userAuthor.following() ? self.author.followCount() - 1 : self.author.followCount() + 1 } );
			self.updateUserAuthor( { "following": ! self.userAuthor.following() } );
		};

		switchState();
		dataAccessor.followOrUnfollowAuthor( self.author.authorId(), self.userAuthor.following(), 
			function( userAuthor ) {
				self.followRequestOnFlight( false );
			}, function( error ) {
				self.followRequestOnFlight( false );
				switchState();
				ToastUtil.toast( error[ "message" ] != null ? error[ "message" ] : "${ _strings.server_error_message }", 3000, true );
		});
	};

	/* Can Follow */
	this.canFollow = ko.observable( false );
	this.authorIdObserver = ko.computed( function() {
	    if( self.author.authorId() == null ) return;
    		self.canFollow( appViewModel.user.isGuest() != self.author.authorId() );
    	}, this );

	/* Share Author */
	this.shareAuthor = function() {
		ShareUtil.shareAuthor( ko.mapping.toJS( self.author ) );
	};

}