function( params ) { 
	var self = this;
	var dataAccessor = new DataAccessor();

	this.pratilipi = params.pratilipi;
	this.canRemoveFromLibrary = params.canRemoveFromLibrary != null ? params.canRemoveFromLibrary : false;
	this.redirectToReaderOnClick = params.redirectToReaderOnClick != null ? params.redirectToReaderOnClick : false;

	this.libraryButtonVisible = ko.observable( false );
	this.shareButtonVisible = ko.observable( false );
	this.libraryRequestOnFlight = ko.observable( false );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
	};

	this.switchLibraryState = function() {

		if( ! self.canRemoveFromLibrary && self.pratilipi.addedToLib() ) 
			return;

		self.libraryRequestOnFlight( true );

		if( self.pratilipi.addedToLib() ) /* Toast only for remove action */
			ToastUtil.toastCallBack( "${ _strings.removed_from_library }", 3000, "UNDO", self.switchLibraryState );
		else 
			ToastUtil.toastUp( "${ _strings.added_to_library }", 2000 );

		var addedToLib = self.pratilipi.addedToLib();
		self.updatePratilipi( { "addedToLib": ! addedToLib } );
		dataAccessor.addOrRemoveFromLibrary( self.pratilipi.pratilipiId(), ! addedToLib, 
			function( pratilipi ) {
				/* self.updatePratilipi( pratilipi ); */
				self.libraryRequestOnFlight( false );
			}, function( error ) {
				self.libraryRequestOnFlight( false );
				self.updatePratilipi( { "addedToLib": addedToLib } );
				var message = "${ _strings.server_error_message }";
				if( error[ "message" ] != null )
					message = error[ "message" ];
				ToastUtil.toast( message, 3000, true );
		});
	};

	this.addToOrRemoveFromLibrary = function( vm, evt ) {
		evt.stopPropagation();
		if( appViewModel.user.isGuest() ) {
			goToLoginPage( null, { "message": "LIBRARY" } );
			return;
		}
		self.switchLibraryState();
	};

	this.sharePratilipi = function( vm, evt ) {
		evt.stopPropagation();
		ShareUtil.sharePratilipi( ko.mapping.toJS( self.pratilipi ) );
	};

	this.userObserver = ko.computed( function() {
		self.libraryButtonVisible( appViewModel.user.author.authorId() != self.pratilipi.author.authorId() );
	}, this );

	this.pratilipiMetaObserver = ko.computed( function() {
		self.shareButtonVisible( self.pratilipi.state == null || self.pratilipi.state() == "PUBLISHED" );
	}, this );

}
