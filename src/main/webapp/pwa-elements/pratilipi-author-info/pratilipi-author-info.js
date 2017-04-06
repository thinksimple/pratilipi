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

	/* Can Follow and Settings Link */
	this.canFollow = ko.observable( false );
	this.settingsLink = ko.observable( "/settings" );

	this.authorObserver = ko.computed( function() {
	    if( self.author.user.userId() == null ) return;
    	self.canFollow( appViewModel.user.userId() != self.author.user.userId() );
		if( self.author.hasAccessToUpdate() && appViewModel.user.userId() != self.author.user.userId() ) /* AEEs */
			self.settingsLink( "/settings?authorId=" + self.author.authorId() + "&userId=" + self.author.user.userId() );
		else
			self.settingsLink( "/settings" );
    }, this );


	/* Share Author */
	this.shareAuthor = function() {
		ShareUtil.shareAuthor( ko.mapping.toJS( self.author ) );
	};


	/* Image Upload */
    this.imageUploaded = ko.observable( false );
    this.chooseImageFile = function() {
        document.getElementById( 'uploadAuthorImageInput' ).value= null;
        document.getElementById( "uploadAuthorImageInput" ).click();
    };

    this.uploadAuthorImage = function( vm, evt ) {
        var files = evt.target.files;
        var file = files[0];
        if( file != null && ( file.name.match( ".*\.jpg" ) || file.name.match( ".*\.png" ) || file.name.match( ".*\.jpeg" ) ) ) {
            self.imageUploaded( true );
            document.getElementById( "uploadAuthorImageForm" ).submit();
        }
    };

    this.iframeLoaded = function( vm, evt ) {
        if( ! self.imageUploaded() )
            return;
        var response = JSON.parse( evt.currentTarget.contentDocument.body.innerText );
        if( response[ "coverImageUrl" ] != null ) {
            /* Success Callback */
            var profileImageUrl = response[ "coverImageUrl" ];
            self.updatePratilipi( { "profileImageUrl": profileImageUrl } );
            ToastUtil.toast( "${ _strings.success_generic_message }", 3000 );
        } else if( response[ "message" ] != null ) {
            ToastUtil.toast( response[ "message" ], 3000 );
        }
        self.imageUploaded( false );
    };

}