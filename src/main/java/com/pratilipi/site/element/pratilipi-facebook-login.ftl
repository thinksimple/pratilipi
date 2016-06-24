<style type="text/css">
	div.social-wrap button {
			padding-left: 20px;
			padding-right: 0px;
			margin-left: auto;
			margin-right: auto;
			height: 35px;
			background: none;
			border: none;
			display: block;
			background-size: 25px 25px, cover;
			background-position: 10px center, center center;
			background-repeat: no-repeat, repeat;
			border-radius: 4px;
			color: white;
			font-size: 14px;
			margin-bottom: 15px;
			width: 275px;
			border-bottom: 2px solid transparent;
			border-left: 1px solid transparent;
			border-right: 1px solid transparent;
			box-shadow: 0 4px 2px -2px gray;
			text-shadow: rgba(0, 0, 0, .4) -1px -1px 0;
		}

		div.social-wrap > .facebook {
			background-color: #3b5998;
			background: url('http://0.ptlp.co/resource-all/icon/facebook-login/facebook_transparent_icon_25x25.png') no-repeat 10px #3b5998;
			background: url(http://0.ptlp.co/resource-all/icon/facebook-login/facebook_transparent_icon_25x25.png), -webkit-gradient(linear, left top, left bottom, color-stop(0%, #4c74c4), color-stop(100%, #3b5998));
			background-size: 25px 25px, cover;
			background-position: 10px center, center center;
			background-repeat: no-repeat, repeat;
		}
</style>

<script>
	
	var facebookLoginOnFlight = false;
	
	function facebookLogin() {
		if( facebookLoginOnFlight ) return;
		facebookLoginOnFlight = true;

		FB.login( function( response ) {

			<#-- response = null if window closed -->
			if( response == null ) {
				facebookLoginOnFlight = false;
				return;
			}

			$.ajax({
		
				type: 'post',
				url: '/api/user/login/facebook',

				data: { 
					'fbUserAccessToken': response.authResponse.accessToken
				},
				
				success: function( response ) {
					facebookLoginOnFlight = false;
					if( getUrlParameters().ret != null )
						window.location.href = decodeURIComponent( getUrlParameters().ret );
					else
						window.location.href = "/";
				},
				
				error: function( response ) {
					facebookLoginOnFlight = false;
					var message = jQuery.parseJSON( response.responseText );
					var status = response.status;

					if( message["message"] != null )
						alert( "Error " + status + " : " + message["message"] ); 
					else
						alert( "Invalid Credentials" );
				}
			});
		}, { scope: 'public_profile,email,user_birthday' } );
	}
</script>