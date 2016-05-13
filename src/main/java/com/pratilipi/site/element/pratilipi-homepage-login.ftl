<script>
	
	function openLoginModal() {
		jQuery( "#pratilipiUserLogin" ).modal( {backdrop: 'static'} );
	}
	
	function login( e, input ) {
		if( e != null ) {
			var code = ( e.keyCode ? e.keyCode : e.which );
			if( code != 13 )
				return;
		}
		document.getElementById( "login-message" ).innerText = "Please Wait...";
		var email = document.getElementById( "userLoginEmail" ).value;
		var password = document.getElementById( "userLoginPassword" ).value;
		if( email == null || email.trim() == "" ) {
			document.getElementById( "login-message" ).innerText = "Please Enter your Email";
			return;
		}
		if( password == null || password.trim() == "" ) {
			document.getElementById( "login-message" ).innerText = "Please Enter your Password";
			return;
		}
		if( ! validateEmail( email ) ) {
			document.getElementById( "login-message" ).innerText = "Please Enter a valid e-mail ID!";
			return;
		}
		
		// Disable button and Make Ajax call
		jQuery( '#loginButton' ).prop( 'disabled', true );
		$.ajax({
			type: 'post',
			url: '/api/user/login',
			data: { 
				'email': email, 
				'password': password
			},
			success: function( response ) {
				var displayName = jQuery.parseJSON( response )[ "displayName" ];
				jQuery( '#loginButton' ).prop( 'disabled', false );
				
				document.getElementById( "login-message" ).innerText = "Signed in Successfully!";
				document.getElementById( "login-signup" ).style.display = "none";
				document.getElementById( "user-dropdown" ).style.display = "block";
				document.getElementById( "username" ).innerText = displayName;
				
				setTimeout( function() {
					<#-- Clear Messages -->
					document.getElementById( "userLoginEmail" ).value = null;
					document.getElementById( "userLoginPassword" ).value = null;
					document.getElementById( "login-message" ).innerText = null;
			        jQuery( "#pratilipiUserLogin" ).modal( 'hide' );
				}, 1500);
			},
			error: function( response ) {
				jQuery( '#loginButton' ).prop( 'disabled', false );
				var message = jQuery.parseJSON( response.responseText );
				var status = response.status;

				if( message["email"] != null ) 
					document.getElementById( "login-message" ).innerText = "Error " + status + " : " + message["email"];
				else if( message["password"] != null ) 
					document.getElementById( "login-message" ).innerText = "Error " + status + " : " + message["password"];
				else if( message["message"] != null )
					document.getElementById( "login-message" ).innerText = "Error " + status + " : " + message["message"]; 
				else
					document.getElementById( "login-message" ).innerText = "Invalid Credentials";
			}
		});
	}
</script>

<div class="modal modal-fullscreen fade" id="pratilipiUserLogin" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-center">
			<div class="modal-content">
				<div class="modal-fullscreen-close-button">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg"/>
					</button>
				</div>
				<img title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-all/home-page/pratilipi_logo.png" />
				<h6 class="modal-fullscreen-heading">Sign in to Pratilipi</h6>
				<input class="input-field" type="email" name="email" id="userLoginEmail" placeholder="Email" onKeyPress="login( event, this )" />
				<input class="input-field" type="password" name="password" id="userLoginPassword" placeholder="Password" onKeyPress="login( event, this )" />
				<div id="login-message" class="display-message-div"></div>
				<button id="loginButton" class="pratilipi-blue-button" onClick="login()">Sign In</button>
			</div>
		</div>
	</div>
</div>