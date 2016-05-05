<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<script>
			<#if action != "index" && action != "share" && action != "setting">
				$( document ).ready(function() {
					if( getUrlParameter( "addToLib" ) == "true" )
						addToLibrary();
				});
				function getUrlParameter( key ) {
				   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
				      return decodeURIComponent( key[1] );
				   else
					   return null;
				}
				function gotoPage( pageNo ) {
					var redirectUrl =	"${ pratilipi.getReadPageUrl() }" +
										( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
										"pageNo=" + parseInt( pageNo, 10);
	
					if( getUrlParameter( "ret" ) != null )
						redirectUrl = redirectUrl + "&" + "ret=" + getUrlParameter( "ret" ); 
	
					window.location.href = redirectUrl;
	
				}
				function gotoNavigation() {
					var redirectUrl =	"${ pratilipi.getReadPageUrl() }" +
										( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
										"action=index" + "&" + "pageNo=${ pageNo }";
	
					window.location.href = redirectUrl;
				}
				function gotoShare() {
					var redirectUrl =	"${ pratilipi.getReadPageUrl() }" +
										( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
										"action=share" + "&" + "pageNo=${ pageNo }";
	
					window.location.href = redirectUrl;
				}
				function gotoSetting() {
					var redirectUrl =	"${ pratilipi.getReadPageUrl() }" +
										( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
										"action=setting" + "&" + "pageNo=${ pageNo }";
	
					window.location.href = redirectUrl;
				}
				function loadPrevious() {
					gotoPage( ${ pageNo - 1 } );
				}
				function loadNext() {
					gotoPage( ${ pageNo + 1 } );
				}
				function exitReader() {
					window.location.href = getUrlParameter( "ret" ) != null ? getUrlParameter( "ret" ) : "${ pratilipi.getPageUrl() }";
				}
			</#if>
			
			<#if action == "setting">
				function setCookie( name, value, days ) {
					var date = new Date();
					date.setTime( date.getTime() + ( days * 24 * 60 * 60 * 1000 ) );
					var expires = "; expires=" + date.toGMTString();
					document.cookie = name + "=" + value + expires + "; path=/";
				}
				function increaseFontSize() {
					var cutoff = 32;
					if( ${ fontSize + 2 } <= cutoff ) {
						setCookie( "fontSize", ${ fontSize + 2 } );
						window.history.back();
					}
				}
				function decreaseFontSize() {
					var cutoff = 12;
					if( ${ fontSize - 2 } >= cutoff ) {
						setCookie( "fontSize", ${ fontSize - 2 } );
						window.history.back();
					}
				}
				function addToOrRemoveFromLibrary( flag ) {
					$.ajax({
							
						type: 'post',
						url: '/api/userpratilipi/library',
			
						data: { 
							'pratilipiId': ${ pratilipi.getId()?c }, 
							'addedToLib': flag
						},
						
						success: function( response ) {
							alert( "Success" );
							window.history.back();
						},
						
						error: function( response ) {
							var message = jQuery.parseJSON( response.responseText );
							var status = response.status;
			
							if( message["message"] != null )
								alert( "Error " + status + " : " + message["message"] );
							else if( message["error"] != null )
								alert( "Error " + status + " : " + message["error"] ); 
							else
								alert( "Some exception occured at the server! Please try again." );
						}
					});
				}
				function addToLibrary() {
					addToOrRemoveFromLibrary( true );
				}
				function removeFromLibrary() {
					addToOrRemoveFromLibrary( false );
				}
			</#if>

			<#if action == "share">
				function shareOnFacebook() {
					window.open( "http://www.facebook.com/sharer.php?u=" + "http://${ website_host }/read?id=${ pratilipi.getId()?c }", "share", "width=600,height=500,left=70px,top=60px" );
				}
				function shareOnTwitter() {
					window.open( "http://twitter.com/share?url=" + "http://${ website_host }/read?id=${ pratilipi.getId()?c }", "share", "width=500,height=600,left=70px,top=60px" );
				}
				function shareOnGplus() {
					window.open( "https://plus.google.com/share?url=" + "http://${ website_host }/read?id=${ pratilipi.getId()?c }", "share", "width=500,height=600,left=70px,top=60px" );
				}
			</#if>
		</script>
	</head>

	<body>
		<#if action != "index" && action != "share" && action != "setting">
			<#include "../element/pratilipi-reader-header.ftl">
		<#else>
			<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px; height: 64px;">
				<a style="cursor: pointer; position: absolute; right: 16px; top: 20px;" onClick="history.back();return false;">
					<img style="width: 24px; height: 24px;" src="http://0.ptlp.co/resource-all/icon/svg/cancel-circle.svg"/>
				</a>
			</div>
		</#if>
		<div class="parent-container">
			<div class="container">
				<#if action == "index">
					<#include "../element/pratilipi-reader-navigation.ftl">
				<#elseif action == "share">
					<#include "../element/pratilipi-reader-social.ftl">
				<#elseif action == "setting">
					<#include "../element/pratilipi-reader-setting.ftl">
				<#else>
					<#include "../element/pratilipi-reader-content.ftl">
				</#if>
				
			</div>
		</div>
		<#if action != "index" && action != "share" && action != "setting">
			<#include "../element/pratilipi-reader-footer.ftl">
		</#if>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>