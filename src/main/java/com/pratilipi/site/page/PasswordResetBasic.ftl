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
    		function validateEmail( email ) {
				var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(email);
			}
			function resetPassword() {
			
				var email = $( '#inputEmail' ).val();
				
				if( email == null || email.trim() == "" ) {
					// Throw message - Please Enter Email
					alert( "Please Enter your Email" );
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
					url: '/api/user/email',

					data: { 
						'email': email, 
						'sendPasswordResetMail': true
					},
					
					success: function( response ) {
						alert( "${ _strings.password_reset_request_success }" );
						window.location.href = "/"; 
					},
					
					error: function () {
						alert( "Invalid Email!" );
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
	                <h3 style="text-align: center; font-size: 20px;">${ _strings.user_forgot_password }</h3>
	            </div>
	            
	            <p class="text-muted">${ _strings.user_reset_password_help }</p>
	            <form id="userPasswordResetForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
	                    <div class="col-sm-10">
	                        <input name="email" type="email" class="form-control" id="inputEmail" placeholder="${ _strings.user_email }">
	                    </div>
	                </div>
	                <div class="form-group" style="margin: 25px auto; text-align: center;">
	                	<button class="btn btn-default red" onclick="resetPassword()">${ _strings.user_reset_password }</button>
	                </div>
	            </form>
	            
	            <div style="text-align: center;">
	            	<a href="/">${ _strings.back }</a>
	            </div>
	            
	        </div>
	        
	        <#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>