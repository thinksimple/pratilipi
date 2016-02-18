<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-event-page.html?2016021804'>

		<script>
			var didScroll;
			$( window ).scroll( function( event ) {
				didScroll = true;
			});
			
			setInterval( function() {
				if( didScroll ) {
					document.querySelector( 'pratilipi-event-page' ).scrollHandler( $(this).scrollTop() );
					didScroll = false;
				}
			}, 30);
		</script>

	</head>

	<body>
		<pratilipi-event-page 
			user-data='${ userJson }'
			event='${ eventJson }'
			pratilipi-list='${ pratilipiListJson }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-event-page>
	</body>

</html>
