<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
		<script>
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
		</script>
	</head>

	<body>
		<#include "../element/pratilipi-reader-header.ftl">
		<div class="parent-container">
			<div class="container">
				<#include "../element/pratilipi-reader-content.ftl">
			</div>
		</div>
		<#include "../element/pratilipi-reader-footer.ftl">
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>