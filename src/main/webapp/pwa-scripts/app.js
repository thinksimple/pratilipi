var AppViewModel = function() {
	var defaultUser = {
		"userId": null,
		"author": { "authorId": null },
		"displayName": null,
		"email": null,
		"phone": null,
		"state": null,
		"isGuest": true, 
		"isEmailVerified": false,
		"profilePageUrl": null, 
		"profileImageUrl": null, 
		"firebaseToken": null
	};
	this.user = ko.mapping.fromJS( defaultUser, {}, this.user );
	this.notificationCount = ko.observable( -1 );
	this.searchQuery = ko.observable( window.location.pathname == "/search" ? decodeURI( getUrlParameter( "q" ) ) : null );
	this.pratilipiWriteAuthorId = ko.observable();
	this.userPreferences = ko.observable( {} );
	this.metaTags = ko.observableArray();
	this.pageTitle = ko.observable( "${ _strings.pratilipi } | Pratilipi" );
	this.scrollTop = ko.observable();
	this.notifyOfScrollEvent = function() {
		this.scrollTop( $( ".mdl-layout__content" ).scrollTop() );
	};

	this.isUserPage = ko.observable(); /* Used for GA tracking -> When Author is visiting his/her own page. */

};

var appViewModel = new AppViewModel();

var initFirebase = function() {
	if( appViewModel.user.isGuest() ) return;
	var firebaseLibrary = document.createElement( 'script' );
	firebaseLibrary.setAttribute( "src", "${ firebaseLibrary }" );
	firebaseLibrary.onload = function() {
		var config = {
			apiKey: "AIzaSyAAnK0-vDmY1UEcrRRbCzXgdpF2oQn-E0w",
			authDomain: "prod-pratilipi.firebaseapp.com",
			databaseURL: "https://prod-pratilipi.firebaseio.com",
			storageBucket: "prod-pratilipi.appspot.com",
		};
		firebase.initializeApp( config );
		firebase.auth().onAuthStateChanged( function( fbUser ) {
			if( fbUser ) {
				var newNotificationCountNode = firebase.database().ref( "NOTIFICATION" ).child( fbUser.uid ).child( "newNotificationCount" );
				newNotificationCountNode.on( 'value', function( snapshot ) {
					var newNotificationCount = snapshot.val() != null ? snapshot.val() : 0;
					appViewModel.notificationCount( newNotificationCount );
				});
				var userPreferencesNode = firebase.database().ref( "PREFERENCE" ).child( fbUser.uid );
				userPreferencesNode.on( 'value', function( snapshot ) {
					var userPreferences = snapshot.val() != null ? snapshot.val() : {};
					appViewModel.userPreferences( userPreferences );
				});
			} else {
				firebase.auth().signInWithCustomToken( appViewModel.user.firebaseToken() );
			}
		});
	};
	document.body.appendChild( firebaseLibrary );
};

var resetFbNotificationCount = function() {
	var node = firebase.database().ref( "NOTIFICATION" ).child( appViewModel.user.userId() ).child( "newNotificationCount" );
	node.set( 0 );
};

var setUserPreferences = function( userPreferences ) {
	var node = firebase.database().ref( "PREFERENCE" ).child( appViewModel.user.userId() );
	node.set({
		"emailFrequency": userPreferences[ "emailFrequency" ],
		"notificationSubscriptions": userPreferences[ "notificationSubscriptions" ],
		"lastUpdated": firebase.database.ServerValue.TIMESTAMP 
	});
};

var updateUser = function() {
	var dataAccessor = new DataAccessor();
	dataAccessor.getUser( function( user ) {
		ko.mapping.fromJS( user, {}, appViewModel.user );
		/* GoogleAnalytics Pageview */
		if( ! user.isGuest )
			ga( 'set', 'userId', user.userId );
		ga( 'set', 'dimension1', user.userId );
		ga( 'set', 'dimension2', '${ ga_website }' );
		ga( 'set', 'dimension3', '${ ga_websiteMode }' );
		ga( 'set', 'dimension4', '${ ga_websiteVersion }' );
		ga( 'send', 'pageview' );

		initFirebase();
	});
};

ko.applyBindings( appViewModel, document.getElementsByTagName("html")[0] );
updateUser();