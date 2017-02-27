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
	document.querySelector( '.notification-ul.mdl-menu' ).addEventListener( 'click', function( event ) {
		event.stopPropagation();
	});

	this.notificationsLoaded = ko.observable( "INITIAL" );
	this.notificationList = ko.observableArray( [] );
	/*
	 * 5 Possible values for 'notificationsLoaded'
	 * INITIAL
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */

	this.updateNotifications = function() {
		if( (  ! appViewModel.user.isGuest() && appViewModel.notificationCount() != 0 ) 
				|| ( self.notificationsLoaded() == "INITIAL" && appViewModel.notificationCount() == 0 ) ) {
			if( self.notificationsLoaded() == "LOADING" ) return;
			self.notificationsLoaded( "LOADING" );
			var dataAccessor = new DataAccessor();
			dataAccessor.getNotificationList( function( notificationResponse ) {
				if( notificationResponse == null ) {
					self.notificationsLoaded( "FAILED" );
					return;
				}
				var notificationList = notificationResponse.notificationList;
				self.notificationList( notificationList );
				self.notificationsLoaded( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
			});
		}
	};

	this.userObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() && getUrlParameter( 'action' ) == "write" ) {
			$( '#pratilipiWrite' ).modal();
		}
	}, this );

	this.notificationCountObserver = ko.computed( function() {
		appViewModel.notificationCount();
		setTimeout( self.updateNotifications, 0 );
	}, this );

}