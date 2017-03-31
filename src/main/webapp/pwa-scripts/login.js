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
	var dataAccessor = new DataAccessor();

	self.userName = ko.observable( "" );
	self.userEmail = ko.observable( "" );
	self.userPassword = ko.observable( "" );
	self.requestOnFlight = ko.observable( false );
	self.agreedTerms = ko.observable( false );

	self.googleLogin = function( isRegister ) {
		if( self.requestOnFlight() )
			return;

		var GoogleAuth = gapi.auth2.getAuthInstance();
		GoogleAuth.signIn().then( function( googleUser ) {
			self.requestOnFlight( true );
			ToastUtil.toastUp( "${ _strings.working }" );
			dataAccessor.loginGoogleUser( googleUser.getAuthResponse().id_token,
				function( user ) {
					ToastUtil.toastUp( "${ _strings.user_login_success }" );
					window.location.href = isRegister ? user[ "profilePageUrl" ] : getRetUrl( true );
				}, function( error ) {
					self.requestOnFlight( false );
					var message = "${ _strings.server_error_message }";
					if( error[ "googleIdToken" ] != null )
						message = error[ "googleIdToken" ];
					else if( error[ "message" ] != null )
						message = error[ "message" ];
					ToastUtil.toast( message, 3000, true );
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
			ToastUtil.toastUp( "${ _strings.working }" );
			dataAccessor.loginFacebookUser( fbResponse.authResponse.accessToken,
				function( user ) {
					ToastUtil.toastUp( "${ _strings.user_login_success }" );
					window.location.href = isRegister ? user[ "profilePageUrl" ] : getRetUrl( true );
				}, function( error ) {
					self.requestOnFlight( false );
					var message = "${ _strings.server_error_message }";
					if( error[ "fbUserAccessToken" ] != null )
						message = error[ "fbUserAccessToken" ];
					else if( error[ "message" ] != null )
						message = error[ "message" ];
					ToastUtil.toast( message, 3000, true );
			});
		}, { scope: 'public_profile,email,user_birthday' } );			
	};

	self.login = function() {
		if( self.requestOnFlight() )
			return;
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			ToastUtil.toast( "${ _strings.email_empty }." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			ToastUtil.toast( "${ _strings.password_empty }" );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			ToastUtil.toast( "${ _strings.email_invalid }" );
			return;
		}
		self.requestOnFlight( true );
		ToastUtil.toastUp( "${ _strings.working }" );

		dataAccessor.loginUser( self.userEmail(), self.userPassword(), 
			function( user ) {
				ToastUtil.toastUp( "${ _strings.user_login_success }" );
				window.location.href = getRetUrl( true );
			}, function( error ) {
				self.requestOnFlight( false );
				/* TODO: Multiple messages handling */
				var message = "${ _strings.server_error_message }";
				if( error[ "email" ] != null )
					message = error[ "email" ];
				else if( error[ "password" ] != null )
					message = error[ "password" ];
				else if( error[ "message" ] != null )
					message = error[ "message" ];
				ToastUtil.toast( message, 3000, true );
		});
	};

	self.register = function() {
		if( self.requestOnFlight() )
			return;
		if( self.userName() == null || self.userName().trim() == "" ) {
			ToastUtil.toast( "${ _strings.name_empty }" );
			return;
		}
		if( self.userEmail() == null || self.userEmail().trim() == "" ) {
			ToastUtil.toast( "${ _strings.email_empty }." );
			return;
		}
		if( self.userPassword() == null || self.userPassword().trim() == "" ) {
			ToastUtil.toast( "${ _strings.password_empty }" );
			return;
		}
		if( ! validateEmail( self.userEmail() ) ) {
			ToastUtil.toast( "${ _strings.email_invalid }" );
			return;
		}
		if( ! self.agreedTerms() ) {
			ToastUtil.toast( "${ _strings.accept_terms }" );
			return;
		}
		self.requestOnFlight( true );
		ToastUtil.toastUp( "${ _strings.working }" );
		dataAccessor.registerUser( self.userName(), self.userEmail(), self.userPassword(), 
			function( user ) {
				ToastUtil.toastUp( "${ _strings.user_register_success }" );
				window.location.href = user[ "profilePageUrl" ];
			}, function( error ) {
				self.requestOnFlight( false );
				/* TODO: Multiple messages handling */
				var message = "${ _strings.server_error_message }";
				if( error[ "name" ] != null )
					message = error[ "name" ];
				else if( error[ "email" ] != null )
					message = error[ "email" ];
				else if( error[ "password" ] != null )
					message = error[ "password" ];
				else if( error[ "message" ] != null )
					message = error[ "message" ];
				ToastUtil.toast( message, 3000, true );
		});
	};

	self.loginButtonEnabled = ko.computed( function() {
		return self.userEmail() != null && self.userEmail().trim() != "" 
			&& self.userPassword() != null && self.userPassword().trim() != ""
			&& ! self.requestOnFlight();
	}, self );

	self.registerButtonEnabled = ko.computed( function() {
		return self.userName() != null && self.userName().trim() != "" 
			&& self.userEmail() != null && self.userEmail().trim() != "" 
			&& self.userPassword() != null && self.userPassword().trim() != ""
			&& self.agreedTerms() && ! self.requestOnFlight();
	}, self );

	if( getUrlParameter( "message" ) != null ) {
		var type = getUrlParameter( "message" );
		var message = null;
		if( type == "NOTIFICATIONS" )
			message = "${ _strings.user_login_to_view_notifications }";
		else if( type == "LIBRARY" )
			message = "${ _strings.user_login_to_view_library }";
		else if( type == "WRITE" )
			message = "${ _strings.write_on_desktop_only }";
		else if( type == "FOLLOW" )
			message = "${ _strings.user_login_to_follow }";
		if( message != null ) {
			ToastUtil.toastUp( message );
			setTimeout( function(){ ToastUtil.toastDown(); }, 5000 );
			/* Hack: Mdl toast doesn't work properly when called toast function. */
		}
	}

}

ko.applyBindings( new ViewModel() );

var dataAccessor = new DataAccessor();
dataAccessor.getUser( function( user ) {
	if( ! user.isGuest ) {
		ToastUtil.toastUp( "<a href='" + getRetUrl( true ) + "'>${ _strings.logged_in_already }</a>" );
	}
});