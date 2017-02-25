function( params ) {
	var self = this;
	this.languageList = [
		{ value: "TAMIL", text:  "தமிழ்"},
		{ value: "MALAYALAM", text: "മലയാളം"},
		{ value: "MARATHI", text: "मराठी"},
		{ value: "BENGALI", text:  "বাংলা"},
		{ value: "HINDI", text:  "हिंदी"},
		{ value: "GUJARATI", text:  "ગુજરાતી"},
		{ value: "TELUGU", text:  "తెలుగు"},
		{ value: "KANNADA", text: "ಕನ್ನಡ"}
	];
	this.currentLanguage = ko.observable( "${ language }" );
	this.searchQuery = ko.observable();

	this.languageChangeHandler = function() {
		window.location = ( "http://" + self.currentLanguage() + ".pratilipi.com" );
	};

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
	}

	/* Notifications */
	var notificationContainer = $( "header.pratilipi-header #notificationContainer" );
	var notificationLink = $( "header.pratilipi-header #notificationLink" );
	notificationLink.click( function() {
		if( ! appViewModel.user.isGuest() ) {
			notificationContainer.fadeToggle(50);
			resetFbNotificationCount();
		} else {
			window.location.href = "/login?retUrl=" + encodeURIComponent( "/notifications" );
		}
		return false;
	});

	$( document ).click( function() { notificationContainer.hide(); } );
	notificationContainer.click( function() { return false; } );

	/* Loading Notifications */
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
				var notificationList = notificationResponse.notificationList;
				self.notificationList( notificationList );
				self.notificationsLoaded( notificationResponse == null ? "FAILED" : ( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" ) );
			});
		}
	};

	this.computedNotificationCount = ko.computed( function() {
		setTimeout( self.updateNotifications, 0 );
		if( appViewModel.notificationCount() < 1 ) {
			notificationLink.removeClass( "mdl-badge" );
			return "";
		}
		notificationLink.addClass( "mdl-badge" );
		return appViewModel.notificationCount() < 100 ? appViewModel.notificationCount() : "99+";
	}, this );
}