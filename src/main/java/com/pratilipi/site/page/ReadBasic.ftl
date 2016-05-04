<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<script>
			function getUrlParameter( key ) {
			   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
			      return decodeURIComponent( key[1] );
			   else
				   return null;
			}
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
					window.location.reload();
				}
			}
			function decreaseFontSize() {
				var cutoff = 12;
				if( ${ fontSize - 2 } >= cutoff ) {
					setCookie( "fontSize", ${ fontSize - 2 } );
					window.location.reload();
				}
			}
			function gotoPage( pageNo ) {
				var redirectUrl =	"${ pratilipi.getReadPageUrl() }" +
									( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
									"pageNo=" + parseInt( pageNo, 10);

				if( getUrlParameter( "ret" ) != null )
					redirectUrl = redirectUrl + "&" + "ret=" + getUrlParameter( "ret" ); 

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
			$( document ).ready(function() {
				if( getUrlParameter( "addToLib" ) == "true" )
					addToLibrary();
			});
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
						location.reload(); 
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
		</script>
	</head>

	<body>
		<#include "../element/pratilipi-reader-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#if action == "read">
					<#include "../element/pratilipi-reader-content.ftl">
				<#elseif action == "index">
					<#include "../element/pratilipi-reader-navigation.ftl">
				<#elseif action == "social">
					<#include "../element/pratilipi-reader-social.ftl">
				<#elseif action == "setting">
					<#include "../element/pratilipi-reader-setting.ftl">
				</#if>
				
			</div>
		</div>
		<#include "../element/pratilipi-reader-footer.ftl">
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>