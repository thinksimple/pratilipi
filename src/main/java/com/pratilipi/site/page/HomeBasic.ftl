<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
		<script>
			function getParameter( key ) {
			   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
			      return decodeURIComponent( key[1] );
			   else
				   return null;
			}
			$( document ).ready(function() {
				if( getParameter( 'email' ) != null && getParameter( 'token' ) != null ) {
					if( getParameter( 'passwordReset' ) == "true" ) {
						window.location.href = "/updatepassword" + "?" + "email=" + getParameter( 'email' ) + "&" + "token=" + getParameter( 'token' );
					} else if( getParameter( 'verifyUser' ) == "true" ) {
						$.ajax({
							type: 'post',
							url: '/api/user/verification',
							data: {
								'email' : getParameter( 'email' ),
								'verificationToken': getParameter( 'token' )
							},
							success: function( response ) {
								alert( "${ _strings.user_verified_success }" );
								window.location.href = "/";
							},
							error: function ( response ) {
								var message = jQuery.parseJSON( response.responseText );
								var status = response.status;
								if( message["email"] != null ) 
									alert( "Error " + status + " : " + message["email"] );
								else if( message["message"] != null )
									alert( "Error " + status + " : " + message["message"] ); 
								else
									alert( "Invalid Credentials" );
							}
						});
					}
				}
			});
		</script>
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#list sections as section>
					<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
						<h2 class="pratilipi-red" style="display: inline-block;">${ section["title"] }</h2>
						<#if section["listPageUrl"]??>
							<a href="${ section["listPageUrl"] }" class="link pull-right pratilipi-blue" style="font-size: 13px;">${ _strings.view_more }...</a>
						</#if>
					</div>
					<#list section["pratilipiList"] as pratilipi>
						<#include "../element/pratilipi-pratilipi-card.ftl">
					</#list>
					<div style="min-height: 10px;"></div>
				</#list>
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>