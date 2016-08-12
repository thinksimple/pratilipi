<script>
	
	function openRegisterModal() {
		jQuery( "#pratilipiUserRegister" ).modal( {backdrop: 'static'} );
	}
	
	function register( e, input ) {
		if( e != null ) {
			var code = ( e.keyCode ? e.keyCode : e.which );
			if( code != 13 )
				return;
		}
		document.getElementById( "register-message" ).innerText = "Please Wait...";
		var name = document.getElementById( "userRegisterName" ).value;
		var email = document.getElementById( "userRegisterEmail" ).value;
		var password = document.getElementById( "userRegisterPassword" ).value;
		if( name == null || name.trim() == "" ) {
			document.getElementById( "register-message" ).innerText = "Please Enter your Name";
			return;
		}
		if( email == null || email.trim() == "" ) {
			document.getElementById( "register-message" ).innerText = "Please Enter your Email";
			return;
		}
		if( password == null || password.trim() == "" ) {
			document.getElementById( "register-message" ).innerText = "Please Enter the Password";
			return;
		}
		if( ! validateEmail( email ) ) {
			document.getElementById( "register-message" ).innerText = "Please Enter a valid e-mail ID!";
			return;
		}
		
		// Disable button and Make Ajax call
		jQuery( '#registerButton' ).prop( 'disabled', true );
		$.ajax({
			type: 'post',
			url: '/api/user/register',
			data: { 
				'name': name,
				'email': email, 
				'password': password
			},
			success: function( response ) {
				var displayName = "Hello " + jQuery.parseJSON( response )[ "displayName" ];
				jQuery( '#registerButton' ).prop( 'disabled', false );
				
				document.getElementById( "register-message" ).innerText = "Registered Successfully!";
				document.getElementById( "login-signup" ).style.display = "none";
				document.getElementById( "user-dropdown" ).style.display = "block";
				document.getElementById( "username" ).innerText = displayName;
				
				setTimeout( function() {
					<#-- Clear Messages -->
					document.getElementById( "userRegisterName" ).value = null;
					document.getElementById( "userRegisterEmail" ).value = null;
					document.getElementById( "userRegisterPassword" ).value = null;
					document.getElementById( "register-message" ).innerText = null;
			        jQuery( "#pratilipiUserRegister" ).modal( 'hide' );
				}, 1500);
			},
			error: function( response ) {
				jQuery( '#registerButton' ).prop( 'disabled', false );
				var message = jQuery.parseJSON( response.responseText );
				var status = response.status;

				if( message["name"] != null ) 
					document.getElementById( "register-message" ).innerText = "Error " + status + " : " + message["name"];
				else if( message["email"] != null ) 
					document.getElementById( "register-message" ).innerText = "Error " + status + " : " + message["email"];
				else if( message["password"] != null ) 
					document.getElementById( "register-message" ).innerText = "Error " + status + " : " + message["password"];
				else if( message["message"] != null )
					document.getElementById( "register-message" ).innerText = "Error " + status + " : " + message["message"]; 
				else
					document.getElementById( "register-message" ).innerText = "Invalid Credentials";
			}
		});
	}
</script>

<div class="modal modal-fullscreen fade" id="pratilipiUserRegister" role="dialog" tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-center">
			<div class="modal-content">
				<div class="modal-fullscreen-close-button">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg"/>
					</button>
				</div>
				<img title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-all/home-page/pratilipi_logo.png" />
				<h6 class="modal-fullscreen-heading">Register on Pratilipi</h6>
				<div class="social-wrap">
					<button class="facebook" onclick="facebookLogin()">Sign up with Facebook</button>
				</div>
				<input class="input-field" type="text" name="name" id="userRegisterName" placeholder="Name" onKeyPress="register( event, this )" />
				<input class="input-field" type="email" name="email" id="userRegisterEmail" placeholder="Email" onKeyPress="register( event, this )" />
				<input class="input-field" type="password" name="password" id="userRegisterPassword" placeholder="Password" onKeyPress="register( event, this )" />
				<div id="register-message" class="display-message-div"></div>
				<button id="registerButton" class="pratilipi-blue-button" onClick="register()">Register</button>
			</div>
		</div>
	</div>
</div>