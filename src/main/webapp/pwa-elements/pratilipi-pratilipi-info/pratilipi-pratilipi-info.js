function( params ) { 
	var self = this;
	var dataAccessor = new DataAccessor();

	/* Setting Local Variables */
	this.pratilipi = params.pratilipi;
	this.userPratilipi = params.userPratilipi;
	this.updatePratilipi = params.updatePratilipi;
	this.updateUserPratilipi = params.updateUserPratilipi;

	var defaultAuthor = { 
		"authorId": null, 
		"name": null, 
		"nameEn": null, 
		"pageUrl": null, 
		"profileImageUrl": null,
		"summary": null
	};

	var defaultUserAuthor = {
		"following": false 
	};

	this.author = ko.mapping.fromJS( defaultAuthor, {}, self.author );
	this.userAuthor = ko.mapping.fromJS( defaultUserAuthor, {}, self.userAuthor );

	this.canAddToLibrary = ko.observable( false );
	this.canFollowAuthor = ko.observable( false );
	this.canPublishUnpublish = ko.observable( false );
	this.canShare = ko.observable( false );

	this.pratilipiRequestOnFlight = ko.observable( false );
	this.userPratilipiRequestOnFlight = ko.observable( false );
	this.userAuthorRequestOnFlight = ko.observable( false );


	/* Author */
	this.updateAuthor = function( author ) {
		ko.mapping.fromJS( author, {}, self.author );
	};

	this.updateUserAuthor = function( userAuthor ) {
		ko.mapping.fromJS( userAuthor, {}, self.userAuthor );
	};

	this.fetchAuthorAndUserAuthor = function() {
		dataAccessor.getAuthorById( self.pratilipi.author.authorId() , true, 
			function( author, userAuthor ) {
				self.updateAuthor( author );
				self.updateUserAuthor( userAuthor );
		});
	};


	/* Add to Library */
	this.addToLibrary = function() {
		if( appViewModel.user.isGuest() ) {
			goToLoginPage();
		} else {
			var addedToLib = self.userPratilipi.addedToLib();
			self.updateUserPratilipi( { "addedToLib": ! addedToLib } );
			dataAccessor.addOrRemoveFromLibrary( self.pratilipi.pratilipiId(), ! addedToLib, 
				function( userPratilipi ) {
					/* Do Nothing */
				}, function( error ) { 
					ToastUtil.toast( error[ "message" ] != null ? error[ "message" ] : "${ _strings.server_error_message }" );
					self.updateUserPratilipi( { "addedToLib": addedToLib } );
			});
		}
	};


	/* Follow Author */
	this.sendFollowAuthorAjaxRequest = function() {
		if( appViewModel.user.isGuest() ) {
			goToLoginPage();
		} else {
			var following = self.userAuthor.following();
			self.updateUserAuthor( { "following": ! following } );
			dataAccessor.followOrUnfollowAuthor( self.author.authorId(), ! following, 
				function( userAuthor ) {
					/* Do Nothing */
				}, function( error ) {
					ToastUtil.toast( error[ "message" ] != null ? error[ "message" ] : "${ _strings.server_error_message }" );
					self.updateUserPratilipi( { "following": following } );
			});
		}
	};


	/* Publish or Unpublish */
	this.togglePratilipiState = function() {
		var state = self.pratilipi.state();
		var pratilipi = {
			"pratilipiId": self.pratilipi.pratilipiId(),
			"state": state != "PUBLISHED" ? "PUBLISHED" : "DRAFTED"
		};
		self.updatePratilipi( pratilipi );
		dataAccessor.createOrUpdatePratilipi( pratilipi, function( pratilipi ) {
			/* Do Nothing */
		}, function( error ) {
			self.updatePratilipi( { "state": state } );
			ToastUtil.toast( error.message, 3000 );
		}); 
	};


	/* Share */
	var dialog = document.querySelector( '#pratilipi-share-dialog' );
	if( ! dialog.showModal ) { dialogPolyfill.registerDialog( dialog ); }

	this.openShareModal = function() {
		dialog.showModal();
	};

	this.hideShareModal = function() {
		dialog.close();
	};

	this.getPratilipiShareUrl = function( platform ) {
		var shareUrl = encodeURIComponent( window.location.origin + self.pratilipi.pageUrl() );
		if( platform == "WHATSAPP" )
			return "%22" + self.pratilipi.title() + "%22${ _strings.whatsapp_read_story }%20" + shareUrl +"%0A${ _strings.whatsapp_read_unlimited_stories }";

		return shareUrl;
	};

	this.shareOnSocialMedia = function( item, event ) {
		var platform = event.currentTarget.getAttribute( 'data-share-platform' );
		var shareUrl = self.getPratilipiShareUrl( platform );
		var functionToInvoke = "sharePratilipiOn" + capitalizeFirstLetter( platform );
		window[ functionToInvoke ]( shareUrl );
		self.hideShareModal();
	};


	/* Image Upload */
	this.imageUploaded = ko.observable( false );
	this.chooseImageFile = function() {
		document.getElementById( "uploadPratilipiImageInput" ).click();
	};

	this.uploadPratilipiImage = function( vm, evt ) {
		var files = evt.target.files;
		var file = files[0];
		if( file != null && ( file.name.match( ".*\.jpg" ) || file.name.match( ".*\.png" ) ) ) {
			self.imageUploaded( true );
			document.getElementById( "uploadPratilipiImageForm" ).submit();
		}
	};

	this.iframeLoaded = function( vm, evt ) {
		if( ! self.imageUploaded() )
			return;
		self.imageUploaded( false );
		var response = JSON.parse( evt.currentTarget.contentDocument.body.innerText );
		if( response[ "coverImageUrl" ] != null ) {
			/* Success Callback */
			var coverImageUrl = response[ "coverImageUrl" ];
			self.updatePratilipi( { "coverImageUrl": coverImageUrl } );
			ToastUtil.toast( "${ _strings.success_generic_message }", 3000 );
		} else if( response[ "message" ] != null ) {
			ToastUtil.toast( response[ "message" ], 3000 );
		}
	};


	/* Helper functions */
	this.formatReadCount = function( num ) {
		if( isMobile() )
			return num > 999 ? (num/1000).toFixed(1) + 'k' : num;
		else
			return num.toLocaleString( 'en-US' );
	};


	/* Computed Observables */
	this.pratilipiObserver = ko.computed( function() {
		/* Load author */
		self.fetchAuthorAndUserAuthor( pratilipi.pratilipiId() );

		/* set flags */
		self.canAddToLibrary( self.pratilipi.state() == "PUBLISHED" 
			&& ( appViewModel.user.isGuest() || self.pratilipi.author.authorId() != appViewModel.user.author.authorId() ) );

		self.canFollowAuthor( self.pratilipi.author.authorId() != appViewModel.user.author.authorId() );
		self.canPublishUnpublish( self.pratilipi.hasAccessToUpdate() );
		self.canShare( self.pratilipi.state() == "PUBLISHED" );
	}, this );

	this.pratilipiTypeObserver = ko.computed( function() {
		return getPratilipiTypeVernacular( self.pratilipi.type() );
	}, this );

}
