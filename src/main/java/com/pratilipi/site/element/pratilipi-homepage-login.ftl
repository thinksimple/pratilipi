<script>
	
	function openLoginModal() {
		jQuery( "#pratilipiUserLogin" ).modal( {backdrop: 'static'} );
	}
	
	function login() {
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
				console.log( displayName );
				
				
				setTimeout( function() {
					document.getElementById( "login-message" ).innerText = "";
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
					<button type="button" data-dismiss="modal"></button>
				</div>
				<img title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-all/home-page/pratilipi_logo.png" />
				<h6 class="modal-fullscreen-heading">Sign in to Pratilipi</h6>
				<input class="input-field" type="email" name="email" id="userLoginEmail" placeholder="Email" />
				<input class="input-field" type="password" name="password" id="userLoginPassword" placeholder="Password" />
				<div id="login-message" class="display-message-div"></div>
				<button id="loginButton" class="pratilipi-blue-button" onClick="login()">Sign In</button>
			</div>
		</div>
	</div>
</div>