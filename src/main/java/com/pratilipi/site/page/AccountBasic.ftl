<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<style>
			.account {
				text-align: center;
				margin-top: 15px;
				margin-bottom: 20px;
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
			function logout() {
				// Make Ajax call
				$.ajax({
					type: 'get',
					url: '/api/user/logout',
					success: function( response ) {
						if( getUrlParameters().ret != null && 
							getUrlParameters().ret.substring( getUrlParameters().ret.lastIndexOf('/') ) != "library#" && 
							getUrlParameters().ret.substring( getUrlParameters().ret.lastIndexOf('/') ) != "account#"  )
							window.location.href = getUrlParameters().ret;
						else
							window.location.href = "/";
					},
					error: function () {
						alert( "Logout Failed!" );
					}
				});
			}
		</script>
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="secondary-500 pratilipi-shadow box">
					<h3 style="margin-bottom: 20px;" class="text-center">${ _strings.user_my_account }</h3>
					<#if user.email??><h5 class="text-center">${ user.email }</h5></#if>
					<#--
					<#if user.isEmailVerified == true>
						<h6 class="text-center">${ _strings.edit_account_email_verified }</h6>
					</#if>
					-->
					<hr/>
					<a href="${ user.profilePageUrl }"><h6 class="account">${ _strings.user_my_profile }</h6></a>
					<a href="/updatepassword"><h6 class="account">${ _strings.edit_account_change_password }</h6></a>
					<#--
					<#if user.isEmailVerified != true>
						<a href="/verifyemail?ret=/"><h6 class="account">${ _strings.edit_account_verify_email }</h6></a>
					</#if>
					-->
					<a href="/library"><h6 class="account">${ _strings.my_library }</h6></a>
					<a href="#" onclick="logout()"><h6 class="account">${ _strings.user_sign_out }</h6></a>
				</div>
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>