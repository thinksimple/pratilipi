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

			function updatePassword() {
			
				var password = $( '#inputPassword' ).val();
				var newPassword = $( '#inputNewPassword' ).val();
				var newPassword2 = $( '#inputNewPassword2' ).val();
				
				if( password == null || password.trim() == "" ) {
					// Throw message - Please Enter Current Password
					alert( "Please Enter the current password!" );
					return;
				}
				
				if( newPassword == null || newPassword.trim() == "" ) {
					// Throw message - Please Enter new Password
					alert( "Please Enter the new password!" );
					return;
				}
				
				if( newPassword2 == null || newPassword2.trim() == "" ) {
					// Throw message - Please Enter new password again
					alert( "Please Enter the new password again!" );
					return;
				}
				
				if( newPassword != newPassword2 ) {
					alert( "Passwords doesn't match!" );
					return;
				}
				
				// Make Ajax call

				$.ajax({
				
					type: 'post',
					url: '/api/user/passwordupdate',

					data: { 
						'password': password, 
						'newPassword': newPassword
					},
					
					success: function( response ) {
						alert( "${ _strings.password_change_success }" );
						window.location.href = "/"; 
					},
					
					error: function () {
						alert( "Invalid Password!" );
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
	                <h3 style="text-align: center; font-size: 20px;">${ _strings.user_reset_password }</h3>
	            </div>
	            
	            <form id="userPasswordUpdateForm" class="form-horizontal" action="javascript:void(0);">
	                <div class="form-group">
	                    <label for="inputPassword" class="col-sm-2 control-label">Current password</label>
	                    <div class="col-sm-10">
	                        <input name="password" type="password" class="form-control" id="inputPassword" placeholder="${ _strings.user_current_password }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputNewPassword" class="col-sm-2 control-label">New password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword" type="password" class="form-control" id="inputNewPassword" placeholder="${ _strings.user_new_password }">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="inputNewPassword2" class="col-sm-2 control-label">Confirm password</label>
	                    <div class="col-sm-10">
	                        <input name="newPassword2" type="password" class="form-control" id="inputNewPassword2" placeholder="${ _strings.user_confirm_password }">
	                    </div>
	                </div>
	                <div class="form-group" style="margin: 25px auto; text-align: center;">
	                	<button class="btn btn-default red" onclick="updatePassword()">${ _strings.user_reset_password }</button>
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