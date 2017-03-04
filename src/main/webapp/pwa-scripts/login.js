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
					if( status == 200 ) {
						ToastUtil.toastUp( "${ _strings.user_login_success }" );
						window.location.href = isRegister ? response[ "profilePageUrl" ] : getRetUrl( true );
					} else {
						self.requestOnFlight( false );
						var message = "${ _strings.server_error_message }";
						if( response[ "googleIdToken" ] != null )
							message = response[ "googleIdToken" ];
						else if( response[ "message" ] != null )
							message = response[ "message" ];
						ToastUtil.toast( message, 3000, true );
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
					if( status == 200 ) {
						ToastUtil.toastUp( "${ _strings.user_login_success }" );
						window.location.href = isRegister ? response[ "profilePageUrl" ] : getRetUrl( true );
					} else {
						self.requestOnFlight( false );
						var message = "${ _strings.server_error_message }";
						if( response[ "fbUserAccessToken" ] != null )
							message = response[ "fbUserAccessToken" ];
						else if( response[ "message" ] != null )
							message = response[ "message" ];
						ToastUtil.toast( message, 3000, true );
					}
			});
		}, { scope: 'public_profile,email,user_birthday' } );			
	};

	self.login = function() {
		if( self.requestOnFlight() )
			return;
		/* TODO: i18n on error messages */
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			ToastUtil.toast( "Please Enter your Email." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			ToastUtil.toast( "Please Enter your Password." );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			ToastUtil.toast( "Please Enter a valid Email." );
			return;
		}
		self.requestOnFlight( true );
		httpUtil.post( "/api/user/login", { "email": self.userEmail(), "password": self.userPassword() }, 
			function( response, status ) {
				if( status == 200 ) {
					ToastUtil.toastUp( "${ _strings.user_login_success }" );
					window.location.href = getRetUrl( true );
				} else {
					self.requestOnFlight( false );
					/* TODO: Multiple messages handling */
					var message = "${ _strings.server_error_message }";
					if( response[ "email" ] != null )
						message = response[ "email" ];
					else if( response[ "password" ] != null )
						message = response[ "password" ];
					else if( response[ "message" ] != null )
						message = response[ "message" ];
					ToastUtil.toast( message, 3000, true );
				}
		});
	};

	self.register = function() {
		if( self.requestOnFlight() )
			return;
		/* TODO: i18n on error messages */
		if( self.userName() == null || self.userName().trim() == "" ) {
			ToastUtil.toast( "Please Enter your Name." );
			return;
		}
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			ToastUtil.toast( "Please Enter your Email." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			ToastUtil.toast( "Please Enter your Password." );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			ToastUtil.toast( "Please Enter a valid Email." );
			return;
		}
		if( ! self.agreedTerms() ) {
			ToastUtil.toast( "${ _strings.accept_terms }" );
			return;
		}
		self.requestOnFlight( true );
		httpUtil.post( "/api/user/register", { "name": self.userName(), "email": self.userEmail(), "password": self.userPassword() }, 
			function( response, status ) {
				if( status == 200 ) {
					ToastUtil.toastUp( "${ _strings.user_register_success }" );
					window.location.href = response[ "profilePageUrl" ];
				} else {
					self.requestOnFlight( false );
					/* TODO: Multiple messages handling */
					var message = "${ _strings.server_error_message }";
					if( response[ "name" ] != null )
						message = response[ "name" ];
					else if( response[ "email" ] != null )
						message = response[ "email" ];
					else if( response[ "password" ] != null )
						message = response[ "password" ];
					else if( response[ "message" ] != null )
						message = response[ "message" ];
					ToastUtil.toast( message, 3000, true );
				}
		});
	};
}

ko.applyBindings( new ViewModel() );