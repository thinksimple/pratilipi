<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		
		<script>
			function getCookie( cname ) {
				var name = cname + "=";
				var ca = document.cookie.split( ';' );
				for( var i = 0; i < ca.length; i++ ) {
					var c = ca[i];
					while ( c.charAt(0)==' ' ) c = c.substring( 1 );
					if( c.indexOf( name ) == 0 ) return c.substring( name.length,c.length );
				}
				return "";
			}
			function redirectToReader( pratilipiId ) {
				window.location.href = "http://www.pratilipi.com/read?id=" + pratilipiId + "&ret=" + window.location.href + "&accessToken=" + getCookie( "access_token" );
			}
			function getUrlParameters() {
				return JSON.parse('{"' + decodeURI( location.search.substring(1).replace(/&/g, "\",\"").replace(/=/g,"\":\"")) + '"}' );
			}			
		</script>
	</head>

	<body>
		<div class="container">
			<#include "../element/pratilipi-header.ftl">
			<#include "../element/pratilipi-user-login.ftl">
			
			<#include "../element/pratilipi-user-register.ftl">
			<#include "../element/pratilipi-user-password-reset.ftl">
			<#include "../element/pratilipi-user-password-update.ftl">
			<#include "../element/pratilipi-user-password-update-email.ftl">
	<#--	<#include "../element/pratilipi-user-verification.ftl">		-->
			
			<#list pratilipiList as pratilipi>
				<#include "../element/pratilipi-pratilipi-card.ftl">
			</#list>
			
			<#include "../element/pratilipi-page-navigation.ftl">
			
			<#include "../element/pratilipi-navigation.ftl">
			<#include "../element/pratilipi-footer.ftl">
		</div>
	</body>
	
</html>