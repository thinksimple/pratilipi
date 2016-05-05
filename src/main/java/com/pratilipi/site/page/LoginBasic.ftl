<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<style type="text/css">
	        .box{
	            text-overflow: hidden;
	            overflow: hidden;
	        }
	        .btn-default {
	            border-radius: 1px;
	        }
	        .form-control {
	            border-radius: 1px;
	        }
	        .form-horizontal {
	            max-width: 600px;
	            margin-left: auto;
	            margin-right: auto;
	            padding: 20px;
	        }
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
				background: url(http://0.ptlp.co/resource-all/icon/facebook-login/facebook_transparent_icon_25x25.png), -webkit-gradient(linear, left top, left bottom, color-stop(0%, #4c74c4), color-stop(100%, #3b5998));
				background-size: 25px 25px, cover;
				background-position: 10px center, center center;
				background-repeat: no-repeat, repeat;
			}
    	</style>
    	
    	<script type="text/javascript">
    		function getUrlParameters() {
				var str = decodeURI( location.search.substring(1) ), 
					res = str.split("&"), 
					retObj = {};
				for( var i = 0; i < res.length; i++ ){
					var key = res[i].substring( 0, res[i].indexOf( '=' ) );
					var value = res[i].substring( res[i].indexOf( '=' ) + 1 );
					retObj[ key ] = value;
				}
				return retObj;
			}
    		function validateEmail( email ) {
				var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(email);
			}
			function register() {
				if( getUrlParameters().ret != null )
					window.location.href = "/register" + "?ret=" + getUrlParameters().ret;
				else
					window.location.href = "/register" + "?ret=/";
			}
			function forgotPassword() {
				window.location.href = "/resetpassword";
			}
			function login() {
			
				var email = $( '#inputEmail' ).val();
				var password = $( '#inputPassword' ).val();
				
				if( email == null || email.trim() == "" ) {
					// Throw message - Please Enter Email
					alert( "Please Enter your Email" );
					return;
				}
				
				if( password == null || password.trim() == "" ) {
					// Throw message - Please Enter Password
					alert( "Please Enter your Password" );
					return;
				}
				
				if( ! validateEmail( email ) ) {
					// Throw message - Email is not valid
					alert( "Please Enter a valid Email" );
					return;
				}
				
				// Make Ajax call

				$.ajax({
				
					type: 'post',
					url: '/api/user/login',

					data: { 
						'email': email, 
						'password': password
					},
					
					success: function( response ) {
						if( getUrlParameters().ret != null )
							window.location.href = getUrlParameters().ret.replace( "%26", "&" );
						else
							window.location.href = "/"; 
					},
					
					error: function( response ) {
						var message = jQuery.parseJSON( response.responseText );
						var status = response.status;

						if( message["email"] != null ) 
							alert( "Error " + status + " : " + message["email"] );
						else if( message["password"] != null ) 
							alert( "Error " + status + " : " + message["password"] );
						else if( message["message"] != null )
							alert( "Error " + status + " : " + message["message"] ); 
						else
							alert( "Invalid Credentials" );
					}
				});
			}
			
			function facebookLogin() {
				FB.login( function( response ) {
					$.ajax({
				
						type: 'post',
						url: '/api/user/login/facebook',
	
						data: { 
							'fbUserAccessToken': response.authResponse.accessToken
						},
						
						success: function( response ) {
							if( getUrlParameters().ret != null )
								window.location.href = getUrlParameters().ret;
							else
								window.location.href = "/"; 
						},
						
						error: function( response ) {
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
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="secondary-500 pratilipi-shadow box" style="min-height: 370px;">
		            <div style="margin: 20px auto; text-align: center;">
		                <h4 style="text-align: center;">${ _strings.user_sign_in_to_pratilipi }</h4>
		            </div>
		            
		            <div class="social-wrap" style="margin-top: 30px;">
						<button class="facebook" onclick="facebookLogin()">${ _strings.user_sign_in_with_facebook }</button>
					</div>
	
		            <form id="userLoginForm" class="form-horizontal" action="javascript:void(0);">
		                <div class="form-group">
		                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
		                    <div class="col-sm-10">
		                        <input name="email" type="email" class="form-control" id="inputEmail" placeholder="${ _strings.user_email }">
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label for="inputPassword" class="col-sm-2 control-label">Password</label>
		                    <div class="col-sm-10">
		                        <input name="password" type="password" class="form-control" id="inputPassword" placeholder="${ _strings.user_password }">
		                    </div>
		                </div>
		                <div class="form-group" style="margin: 25px auto; text-align: center;">
		                	<button class="pratilipi-dark-blue-button" onclick="login()">${ _strings.user_sign_in }</button>
		                </div>
		            </form>
		            
		            <div style="text-align: center;">
		            	<button class="pratilipi-new-blue-button" onclick="register()">${ _strings.user_sign_up_for_pratilipi }</button>
		            </div>
		            
		            <div style="text-align: center; margin-top: 25px; margin-bottom: 25px;">
		            	<button class="pratilipi-new-blue-button" onclick="forgotPassword()">${ _strings.user_forgot_password }</button>
		            </div>
	
		        </div>
		        
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>