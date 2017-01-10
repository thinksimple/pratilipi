<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#include "../element/basic/pratilipi-android-launch.ftl">
				<#include "../element/basic/pratilipi-pratilipi-card.ftl">
				<#list sections as section>
					<div class="secondary-500 pratilipi-shadow box" style="padding: 12px 10px;">
						<h2 class="pratilipi-red" style="display: inline-block;">${ section.getTitle() }</h2>
						<#if section["listPageUrl"]??>
							<a href="${ section.getListPageUrl() }" class="link pull-right pratilipi-blue" style="font-size: 13px;">${ _strings.view_more }...</a>
						</#if>
					</div>
					<#list section.getPratilipiList() as local_pratilipi>
						<@pratilipi_card from="home" pratilipi=local_pratilipi />
					</#list>
					<div style="min-height: 10px;"></div>
				</#list>
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
		<script>
			$( document ).ready(function() {
				if( getUrlParameter( 'email' ) != null && getUrlParameter( 'token' ) != null ) {
					if( getUrlParameter( 'passwordReset' ) == "true" ) {
						window.location.href = "/updatepassword" + "?" + "email=" + getUrlParameter( 'email' ) + "&" + "token=" + getUrlParameter( 'token' );
					} else if( getUrlParameter( 'verifyUser' ) == "true" ) {
						$.ajax({
							type: 'post',
							url: '/api/user/verification',
							data: {
								'email' : getUrlParameter( 'email' ),
								'verificationToken': getUrlParameter( 'token' )
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
	</body>
	
</html>