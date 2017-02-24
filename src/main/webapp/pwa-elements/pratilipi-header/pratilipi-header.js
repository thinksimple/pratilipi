function( params ) {
	var self = this;
	this.user = params.user;
	this.notificationCount = params.notificationCount;
	this.notificationText = ko.computed( function() {
		if( this.notificationCount() < 1 ) return "";
		return this.notificationCount() < 100 ? this.notificationCount() : "99+";
	}, this );
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
		window.location = ( "http://" + this.currentLanguage() + ".pratilipi.com" );
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

	var notificationContainer = $( "header.pratilipi-header #notificationContainer" );
	var notificationLink = $( "header.pratilipi-header #notificationLink" );
	notificationLink.click( function() {
		if( self.user && self.user.userId && self.user.userId() != 0 ) {
			notificationContainer.fadeToggle(50);
		} else {
			window.location.href = "/login?retUrl=" + encodeURIComponent( "/notifications" );
		}
		return false;
	});

	$( document ).click( function() { notificationContainer.hide(); } );
	notificationContainer.click( function() { return false; } );

	/* Loading Notifications */
	this.notificationsLoaded = ko.observable( "LOADING" );
	this.notificationList = ko.observableArray( [] );
	/*
	 * 3 Possible values for 'notificationsLoaded'
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	if( this.user.userId && this.user.userId() != 0 && this.notificationCount() != 0 && this.notificationsLoaded() != "LOADING" ) {
		var dataAccessor = new DataAccessor();
		dataAccessor.getNotificationList( function( notificationList ) {
			self.notificationList( notificationList );
			self.notificationsLoaded( notificationList == null ? "FAILED" : ( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" ) );
		});
	}

}