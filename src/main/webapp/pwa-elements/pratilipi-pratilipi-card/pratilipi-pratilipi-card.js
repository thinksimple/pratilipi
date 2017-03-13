function( params ) { 
	var self = this;

	var dataAccessor = new DataAccessor();
	var libraryPageBehaviour = params.libraryPageBehaviour != null ? params.libraryPageBehaviour : false;
	this.pratilipi = params.pratilipi;
	this.libraryRequestOnFlight = ko.observable( false );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
	};

	this.addToOrRemoveFromLibrary = function( vm, evt ) {
		evt.stopPropagation();
		if( appViewModel.user.isGuest() ) {
			goToLoginPage(); 
			return;
		}
		self.libraryRequestOnFlight( true );
		var addedToLib = self.pratilipi.addedToLib();
		dataAccessor.addOrRemoveFromLibrary( self.pratilipi.pratilipiId(), ! addedToLib, 
			function( pratilipi ) {
				self.updatePratilipi( pratilipi );
				self.libraryRequestOnFlight( false );
				if( libraryRequestOnFlight )
					ToastUtil.toastCallBack( "${ _strings.success_generic_message }", 5000, "UNDO", self.addToOrRemoveFromLibrary );
			}, function( error ) {
				self.libraryRequestOnFlight( false );
				var message = "${ _strings.server_error_message }";
				if( error[ "message" ] != null )
					message = error[ "message" ];
				ToastUtil.toast( message, 3000, true );
		});
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
