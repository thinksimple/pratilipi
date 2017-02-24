var AppViewModel = function() {
	this.user = ko.mapping.fromJS( { "isGuest": true }, {}, this.user );
	this.notificationCount = ko.observable( -1 );
};

var appViewModel = new AppViewModel();

var initFirebase = function() {
	if( appViewModel.user.isGuest() ) return;
	var firebaseLibrary = document.createElement( 'script' );
	firebaseLibrary.setAttribute( "src", "https://www.gstatic.com/firebasejs/3.6.10/firebase.js" );
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
				var node = firebase.database().ref( "NOTIFICATION" ).child( fbUser.uid ).child( "newNotificationCount" );
				node.on( 'value', function( snapshot ) {
					var newNotificationCount = snapshot.val() != null ? snapshot.val() : 0;
					if( appViewModel.notificationCount() != newNotificationCount )
						appViewModel.notificationCount( newNotificationCount );
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

var updateUser = function() {
	var dataAccessor = new DataAccessor();
	dataAccessor.getUser( function( user ) {
		ko.mapping.fromJS( user, {}, appViewModel.user );
		initFirebase();
	});
};

ko.applyBindings( appViewModel );
updateUser();