function( params ) {
	var self = this;

	this.userObserver = ko.computed( function() {
		if( appViewModel.user.isGuest() && appViewModel.user.userId() == 0 ) {
			goToLoginPage( null, { "message": "NOTIFICATIONS" } );
			return;
		}
		if( getUrlParameter( 'action' ) == "settings" )
			$( "#pratilipiNotificationSettings" ).modal();
	}, this );

}
