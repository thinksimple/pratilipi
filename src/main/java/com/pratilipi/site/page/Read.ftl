<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?7'>
		<script>
			$(document).keydown( function(e) {
			<#-- Left Key pressed -->
			if( e.which == 37 )
				document.querySelector( 'pratilipi-reader-page' ).showPrevious();
			<#-- Right Key pressed -->
			else if( e.which == 39 )
				document.querySelector( 'pratilipi-reader-page' ).showNext();
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
				></pratilipi-pratilipi-page>
	</body>
	
</html>
