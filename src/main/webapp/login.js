/* Initialise Google Client App */
gapi.load( 'auth2', function() {
	auth2 = gapi.auth2.init({
		client_id: '659873510744-kfim969enh181h4gbctffrjg5j47tfuq.apps.googleusercontent.com',
		cookiepolicy: 'single_host_origin',
	});
});

/* Initialise Facebook Client App */
window.fbAsyncInit = function() {
	FB.init({
		appId: '293990794105516',
		cookie : true,
		xfbml: true,
		version: 'v2.6'
	});
};
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if(d.getElementById(id)) {return;}
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));



function getQueryVariable( variable ) {
	var query = window.location.search.substring(1);
	var vars = query.split( "&" );
	for( var i=0; i<vars.length; i++ ) {
		var pair = vars[i].split( "=" );
		if( pair[0] == variable ) {
			return pair[1];
		}
	}
	return null;
}

function ViewModel() {

	var self = this;

	self.userEmail = ko.observable( "" );
	self.userPassword = ko.observable( "" );
	self.requestOnFlight = ko.observable( false );

	self.login_from_google = function( data, event ) {
		if( self.requestOnFlight() )
			return;
		self.requestOnFlight( true );

		var GoogleAuth = gapi.auth2.getAuthInstance();
		GoogleAuth.signIn().then( function( googleUser ) {
			$.ajax({
				type: 'post',
				url: '/api/user/login/google',
				data: {
					'googleIdToken': googleUser.getAuthResponse().id_token
				},
				success: function( response ) {
					window.location.href = getQueryVariable( "retUrl" ) != null ? 
							decodeURIComponent( getQueryVariable( "retUrl" ) ) : "/";
				},
				error: function( response ) {
					var status = response.status;
					response = jQuery.parseJSON( response.responseText );
					alert( "Error: " + status + " - " 
							+ ( response != null && response[ "message" ] != null ? response[ "message" ] : "${ _strings.server_error_message }" ) ); 
				},
				complete: function( event ) {
					self.requestOnFlight( false );
				}
			});
		}, function( error ) {
			console.log( JSON.stringify( error, undefined, 2 ) );
			self.requestOnFlight( false );
		}); 
	};

	self.login_from_fb = function() {
		if( self.requestOnFlight() )
			return;
		FB.login( function( response ) {
			if( response == null || response.authResponse == null ) {
				self.requestOnFlight( false );
				return;
			}
			self.requestOnFlight( true );
			$.ajax({
				type: 'post',
				url: '/api/user/login/facebook',
				data: {
					'fbUserAccessToken': response.authResponse.accessToken
				},
				success: function( response ) {
					window.location.href = getQueryVariable( "retUrl" ) != null ? 
							decodeURIComponent( getQueryVariable( "retUrl" ) ) : "/";
				},
				error: function( response ) {
					var status = response.status;
					response = jQuery.parseJSON( response.responseText );
					alert( "Error: " + status + " - " 
							+ ( response != null && response[ "message" ] != null ? response[ "message" ] : "${ _strings.server_error_message }" ) ); 
				},
				complete: function( event ) {
					self.requestOnFlight( false );
				}
			});
		}, { scope: 'public_profile,email,user_birthday' } );			
	};

	self.login = function() {
		if( self.requestOnFlight() )
			return;
		self.requestOnFlight( true );
		$.ajax({
			type: 'post',
			url: '/api/user/login',
			data: { 
				 email: self.userEmail(),
				 password: self.userPassword()
			},
			success: function( response ) {
				window.location.href = getQueryVariable( "retUrl" ) != null ? 
						decodeURIComponent( getQueryVariable( "retUrl" ) ) : "/";
			},
			error: function( response ) {
				var status = response.status;
				response = jQuery.parseJSON( response.responseText );
				var message = null;
				if( response[ "email" ] != null )
					message = response[ "email" ];
				else if( response[ "password" ] != null )
					message = response[ "password" ];
				else if( response[ "message" ] != null )
					message = response[ "message" ];
				else
					message = "${ _strings.server_error_message }";
				alert( "Error: " + status + " - " + message );
			},
			complete: function( event ) {
				self.requestOnFlight( false );
			}
		});
	};
}

ko.applyBindings( new ViewModel() );