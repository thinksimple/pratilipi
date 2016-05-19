<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160519'>

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
				userpratilipi='${ userpratilipiJson }'
				font-size=${ fontSize }
				page-no='${ pageNo }'
				page-count='${ pageCount }'
				content='${ content }'
				content-type='${ contentType }'
				index='${ indexJson }'
				></pratilipi-pratilipi-page>
	</body>
	
</html>
