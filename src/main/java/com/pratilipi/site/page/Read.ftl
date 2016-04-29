<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?2016042903'>
		<script>
			$(document).keyup( function(e) {
				if( e.which == 37 || e.which == 39 )
					document.querySelector( 'pratilipi-reader-page' ).keyupHandler( e.which );
			});
		</script>
	</head>
	
	<body>
		<pratilipi-reader-page 
				user-data='${ userJson }'
				pratilipi='${ pratilipiJson }'
				page-no='${ pageNo }'
				page-count='${ pageCount }'
				content='${ contentHTML }'
				index='${ indexJson }'
				added-to-library=${ addedToLib?c }
				></pratilipi-pratilipi-page>
	</body>
	
</html>
