/* Initialise Google Client App */
var googleAuth2 = document.createElement( 'script' );
googleAuth2.setAttribute( "src", "https://apis.google.com/js/api:client.js" );
googleAuth2.onload = function() {
	gapi.load( 'auth2', function() {
		auth2 = gapi.auth2.init({
			client_id: '659873510744-kfim969enh181h4gbctffrjg5j47tfuq.apps.googleusercontent.com',
			cookiepolicy: 'single_host_origin',
		});
	});
};
document.body.appendChild( googleAuth2 );

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


function ViewModel() {

	var self = this;
	var httpUtil = new HttpUtil();

	self.userName = ko.observable( "" );
	self.userEmail = ko.observable( "" );
	self.userPassword = ko.observable( "" );
	self.requestOnFlight = ko.observable( false );
	self.agreedTerms = ko.observable( false );

	self.googleLogin = function( isRegister ) {
		if( self.requestOnFlight() )
			return;
		self.requestOnFlight( true );

		var GoogleAuth = gapi.auth2.getAuthInstance();
		GoogleAuth.signIn().then( function( googleUser ) {
			httpUtil.post( "/api/user/login/google", { "googleIdToken": googleUser.getAuthResponse().id_token }, 
				function( response, status ) {
					self.requestOnFlight( false );
					if( status == 200 ) {
						window.location.href = isRegister ? response[ "profilePageUrl" ] : getRetUrl( true );
					} else {
						alert( "Error: " + status + " - "
								+ ( response != null && response[ "message" ] != null ? 
										response[ "message" ] : "${ _strings.server_error_message }" ) );
					}
			});
		}, function( error ) {
			console.log( JSON.stringify( error, undefined, 2 ) );
			self.requestOnFlight( false );
		}); 
	};

	self.facebookLogin = function( isRegister ) {
		if( self.requestOnFlight() )
			return;
		FB.login( function( fbResponse ) {
			if( fbResponse == null || fbResponse.authResponse == null ) {
				self.requestOnFlight( false );
				return;
			}
			self.requestOnFlight( true );
			httpUtil.post( "/api/user/login/facebook", { "fbUserAccessToken": fbResponse.authResponse.accessToken }, 
				function( response, status ) {
					self.requestOnFlight( false );
					if( status == 200 ) {
						window.location.href = isRegister ? response[ "profilePageUrl" ] : getRetUrl( true );
					} else {
						alert( "Error: " + status + " - "
								+ ( response != null && response[ "message" ] != null ? 
										response[ "message" ] : "${ _strings.server_error_message }" ) );
					}
			});
		}, { scope: 'public_profile,email,user_birthday' } );			
	};

	self.login = function() {
		if( self.requestOnFlight() )
			return;
		/* TODO: i18n on error messages */
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			alert( "Please Enter your Email." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			alert( "Please Enter your Password." );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			alert( "Please Enter a valid Email." );
			return;
		}
		self.requestOnFlight( true );
		httpUtil.post( "/api/user/login", { "email": self.userEmail(), "password": self.userPassword() }, 
			function( response, status ) {
				self.requestOnFlight( false );
				if( status == 200 ) {
					window.location.href = getRetUrl( true );
				} else {
					/* TODO: Multiple messages handling */
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
				}
		});
	};

	self.register = function() {
		if( self.requestOnFlight() )
			return;
		/* TODO: i18n on error messages */
		if( self.userName() == null || self.userName().trim() == "" ) {
			alert( "Please Enter your Name." );
			return;
		}
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			alert( "Please Enter your Email." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			alert( "Please Enter your Password." );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			alert( "Please Enter a valid Email." );
			return;
		}
		if( ! self.agreedTerms() ) {
			alert( "Please agree to our terms and conditions." );
			return;
		}
		self.requestOnFlight( true );
		httpUtil.post( "/api/user/register", { "name": self.userName(), "email": self.userEmail(), "password": self.userPassword() }, 
			function( response, status ) {
				self.requestOnFlight( false );
				if( status == 200 ) {
					window.location.href = response[ "profilePageUrl" ];
				} else {
					/* TODO: Multiple messages handling */
					var message = null;
					if( response[ "name" ] != null )
						message = response[ "name" ];
					else if( response[ "email" ] != null )
						message = response[ "email" ];
					else if( response[ "password" ] != null )
						message = response[ "password" ];
					else if( response[ "message" ] != null )
						message = response[ "message" ];
					else
						message = "${ _strings.server_error_message }";
					alert( "Error: " + status + " - " + message );
				}
		});
	};
}

ko.applyBindings( new ViewModel() );