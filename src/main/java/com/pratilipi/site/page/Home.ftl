<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-home-page.html?20160218'>

		<script>
			var didScroll;
			$( window ).scroll( function( event ) {
				didScroll = true;
			});
			
			setInterval( function() {
				if( didScroll ) {
					document.querySelector( 'pratilipi-home-page' ).scrollHandler( $(this).scrollTop() );
					didScroll = false;
				}
			}, 30);
		</script>

	</head>

	<body>
		<pratilipi-home-page 
			user-data='${ userJson }'
			sections-list='${ sectionsJson }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-home-page>
	</body>

</html>
