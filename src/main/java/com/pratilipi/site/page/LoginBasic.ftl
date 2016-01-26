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
				return JSON.parse('{"' + decodeURI( location.search.substring(1).replace(/&/g, "\",\"").replace(/=/g,"\":\"")) + '"}' );
			}
    		function validateEmail( email ) {
				var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(email);
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
						window.location.href = getUrlParameters().ret; 
					},
					
					error: function () {
						alert( "Invalid Credentials!" );
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
	                <p class="lead">Log In to Pratilipi!</p>
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
	                <div class="form-group" style="margin-left: 1px;">
	                	<button class="btn btn-default" onclick="login()">${ _strings.user_sign_in }</button>
	                </div>
	            </form>
	            
	        </div>
	        
	        <#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>