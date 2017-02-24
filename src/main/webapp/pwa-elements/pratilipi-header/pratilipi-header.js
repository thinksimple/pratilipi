function( params ) {
	console.log( params.user );
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
	this.currentLanguage = ko.observable( "HINDI" );
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
	  document.querySelector('.mdl-layout').MaterialLayout.toggleDrawer();
	}

	$( "header.pratilipi-header #notificationLink" ).click( function() { $( "header.pratilipi-header #notificationContainer" ).fadeToggle(50); $( "header.pratilipi-header #notificationLink" ).removeClass( "mdl-badge" ); return false; } );
	$( document ).click( function() { $( "#notificationContainer" ).hide(); } );
	$( "header.pratilipi-header #notificationContainer" ).click( function() { return false; } );

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

	var dataAccessor = new DataAccessor();
	dataAccessor.getNotificationList( function( notificationList ) {
		self.notificationList( notificationList );
		self.notificationsLoaded( notificationList == null ? "FAILED" : ( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" ) );
	});

}