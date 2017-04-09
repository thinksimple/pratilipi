function( params ) {
	var self = this;

	this.userObserver = ko.computed( function() {
		if( appViewModel.user.isGuest() && appViewModel.user.userId() == 0 ) {
			goToLoginPage( getUrlParameter( 'action' ) == "settings" ? { "action": "settings" } : null, { "message": "NOTIFICATIONS" } );
			return;
		}
		if( getUrlParameter( 'action' ) == "settings" ) {
			setTimeout( function() {
				$( "#pratilipiNotificationSettings" ).modal();
			}, 0 );
		}
	}, this );

}
