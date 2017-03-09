function( params ) { 
	var self = this;

	var dataAccessor = new DataAccessor();
	this.pratilipi = params.pratilipi;
	this.libraryRequestOnFlight = ko.observable( false );

	this.updatePratilipi = function( pratilipi ) {
		ko.mapping.fromJS( pratilipi, {}, self.pratilipi );
	};

	this.addToLibrary = function( vm, evt ) {
		evt.stopPropagation();
		self.libraryRequestOnFlight( true );
		var addedToLib = self.pratilipi.addedToLib();
		self.updatePratilipi( { "addedToLib": ! addedToLib } );
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

	this.test = function( vm, evt ) {
		evt.stopPropagation();
	};

}
