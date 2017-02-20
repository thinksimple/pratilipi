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

	/*
	 * 3 Possible values for 'notificationsLoaded'
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */

	var httpUtil = new HttpUtil();
	var notificationList = null;

	httpUtil.get( "/api/notification/list", { "resultCount": 10 }, function( response, status ) {
		if( status == 200 ) {
			notificationList = response.notificationList;
			self.notificationsLoaded( notificationList.length > 0 ? "LOADED" : "LOADED_EMPTY" );
			console.log( self.notificationsLoaded() );
		} else {
			self.notificationsLoaded( 'FAILED' );
		}
	});

	this.notificationList = ko.observableArray( notificationList );

}