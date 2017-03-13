function( params ) {
	var self = this;
	this.searchQuery = ko.observable();
	this.search = function( formElement ) {
		 if( self.searchQuery() && self.searchQuery().trim().length ) {
			  var search_url = "/search?q=" + self.searchQuery();
			  window.location.href = search_url;
		 }
	};

	this.openMenuNavigationDrawer = function() {
		if( !( typeof( componentHandler ) == 'undefined' ) ) {
			componentHandler.upgradeAllRegistered();
		}
	  document.querySelector( '.mdl-layout' ).MaterialLayout.toggleDrawer();
	};

	/* Loading Notifications */
	var notificationContainer = $( "header.pratilipi-header #notificationContainer" );
	var notificationLink = $( "header.pratilipi-header #notificationLink" );
	notificationLink.click( function() {
		notificationContainer.fadeToggle(50);
		resetFbNotificationCount();
		return false;
	});

	$( document ).click( function() { notificationContainer.hide(); } );
	notificationContainer.click( function(e) { e.stopPropagation(); } );

	this.userObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() && getUrlParameter( 'action' ) == "write" ) {
			$( '#pratilipiWrite' ).modal();
		}
	}, this );

}
