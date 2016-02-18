<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-list-page.html?20160218'>

		<script>
			var didScroll;
			$( window ).scroll( function( event ) {
				didScroll = true;
			});
			
			setInterval( function() {
				if( didScroll ) {
					document.querySelector( 'pratilipi-list-page' ).scrollHandler( $(this).scrollTop() );
					didScroll = false;
				}
			}, 30);
		</script>
	</head>
	
	<body>
		<pratilipi-list-page 
			user-data='${ userJson }'
			heading='${ pratilipiListTitle }'
			pratilipi-list='${ pratilipiListJson }'
			filter='${ pratilipiListFilterJson! }'
			cursor='${ pratilipiListCursor! }'
			pratilipi-types='${ pratilipiTypesJson }'></pratilipi-list-page>
	</body>

</html>
