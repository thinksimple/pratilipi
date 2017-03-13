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
		dataAccessor.addOrRemoveFromLibrary( self.pratilipi.pratilipiId(), ! self.pratilipi.addedToLib(), 
			function( pratilipi ) {
				self.updatePratilipi( pratilipi );
				self.libraryRequestOnFlight( false );
				if( self.libraryPageBehaviour )
					ToastUtil.toastCallBack( "${ _strings.success_generic_message }", 5000, "UNDO", self.switchLibraryState );
			}, function( error ) {
				self.libraryRequestOnFlight( false );
				var message = "${ _strings.server_error_message }";
				if( error[ "message" ] != null )
					message = error[ "message" ];
				ToastUtil.toast( message, 3000, true );
		});
	};

	this.addToOrRemoveFromLibrary = function( vm, evt ) {
		evt.stopPropagation();
		if( appViewModel.user.isGuest() ) {
			goToLoginPage(); 
			return;
		}
		self.switchLibraryState();
	};

	var getShareUrl = function() {
		return window.location.origin + self.pratilipi.pageUrl();
	};

	var getWhatsappText = function() {
		return '"' + self.pratilipi.title() + '"${ _strings.whatsapp_read_story } ' + getShareUrl() +" ${ _strings.whatsapp_read_unlimited_stories }";
	};

	this.sharePratilipi = function( vm, evt ) {
		evt.stopPropagation();
		ShareUtil.share( getShareUrl(), getWhatsappText() );
	};

}
