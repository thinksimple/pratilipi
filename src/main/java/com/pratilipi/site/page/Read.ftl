<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?27'>
		<#--
			<script>
				$(document).keyup( function(e) {
				if( e.which == 37 )
					document.querySelector( 'pratilipi-reader-page' ).showPrevious();
				else if( e.which == 39 )
					document.querySelector( 'pratilipi-reader-page' ).showNext();
			});
			</script>
		-->
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
