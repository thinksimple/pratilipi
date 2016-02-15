<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi-page.html?20160215'>

		<script>
			var didScroll;
			$( window ).scroll( function( event ) {
				didScroll = true;
			});
			
			setInterval( function() {
				if( didScroll ) {
					document.querySelector( 'pratilipi-pratilipi-page' ).scrollHandler( $(this).scrollTop() );
					didScroll = false;
				}
			}, 30);
		</script>
	</head>
	
	<body>
		<pratilipi-pratilipi-page 
				user-data='${ userJson }'
				pratilipi-id='${ pratilipi.getId()?c }'
				pratilipi-data='${ pratilipiJson }'
				userpratilipi-data='${ userpratilipiJson! }'
				
				review-list='${ reviewListJson }'
				cursor='${ reviewListCursor! }'
				pratilipi-types='${ pratilipiTypesJson }'></pratilipi-pratilipi-page>
	</body>
	
</html>
