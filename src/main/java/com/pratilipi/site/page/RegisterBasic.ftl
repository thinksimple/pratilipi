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
	            max-width: 100%;
	            padding: 20px;
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
				
				var name = $( '#inputName' ).val();
				var email = $( '#inputEmail' ).val();
				var password = $( '#inputPassword' ).val();
				
				if( name == null || name.trim() == "" ) {
					// Throw message - Please Enter Name
					alert( "Please Enter your Name" );
					return;
				}
				
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
					url: '/api/user/register',

					data: {
						'name': name,
						'email': email, 
						'password': password
					},
					
					success: function( response ) {
						if( getUrlParameters().ret != null )
							window.location.href = getUrlParameters().ret;
						else
							window.location.href = "/";
					},
					
					error: function ( response ) {
						var message = jQuery.parseJSON( response.responseText );
						var status = response.status;

						if( message["name"] != null ) 
							alert( "Error " + status + " : " + message["name"] );
						else if( message["email"] != null ) 
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
    	</script>
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			
			<div class="box" style="min-height: 370px;">
	            <div style="margin: 20px auto; text-align: center;">
	                <h3 style="text-align: center; font-size: 20px;">${ _strings.user_sign_up_for_pratilipi }</h3>
	            </div>
	            
	            <form id="userRegisterForm" class="form-horizontal" action="javascript:void(0);">
	            	<div class="form-group">
	                    <label for="inputName" class="col-sm-2 control-label">Name</label>
	                    <div class="col-sm-10">
	                        <input name="name" type="text" class="form-control" id="inputName" placeholder="${ _strings.user_full_name }">
	                    </div>
	                </div>
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
	                <div class="form-group" style="margin: 0 auto; text-align: center;">
	                	<button class="btn btn-default" onclick="register()">${ _strings.user_sign_up }</button>
	                </div>
	            </form>
	            <p style="display: block; margin: 15px 15px 30px 15px;">
					${ _strings.register_part_1 }
					<a style="color: #107FE5; white-space: nowrap; font-size: 16px;" href="/privacy-policy">privacy policy</a>
					&nbsp;${ _strings.register_part_2 }&nbsp;
					<a style="color: #107FE5; white-space: nowrap; font-size: 16px;" href="/terms-of-service">terms of service</a>
					&nbsp;${ _strings.register_part_3 }
				</p>
				<div style="text-align: center;">
	            	<a href="/">${ _strings.back }</a>
	            </div>
	        </div>
	        
	        <#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>