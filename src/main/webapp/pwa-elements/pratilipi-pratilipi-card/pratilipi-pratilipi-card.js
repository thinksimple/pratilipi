function( params ) { 
	var self = this;

	this.libraryPageBehaviour = params.libraryPageBehaviour != null ? params.libraryPageBehaviour : false;
	this.pratilipi = params.pratilipi;
	this.libraryRequestOnFlight = ko.observable( false );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
	};

	this.switchLibraryState = function() {
		var dataAccessor = new DataAccessor();
		self.libraryRequestOnFlight( true );
		var addedToLib = self.pratilipi.addedToLib();
		self.updatePratilipi( { "addedToLib": ! addedToLib } );
		if( self.libraryPageBehaviour && addedToLib ) /* Toast only for remove action */
			ToastUtil.toastCallBack( "${ _strings.removed_from_library }", 5000, "UNDO", self.switchLibraryState );
		if( self.libraryPageBehaviour && ! addedToLib ) /* Toast Down only for UNDO action */
			ToastUtil.toastDown();
		dataAccessor.addOrRemoveFromLibrary( self.pratilipi.pratilipiId(), ! addedToLib, 
			function( pratilipi ) {
				self.updatePratilipi( pratilipi );
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

}
