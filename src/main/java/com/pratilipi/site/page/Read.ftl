<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?1'>

		<#if contentType == "PRATILIPI">
			<link rel='import' href='/elements.${lang}/pratilipi-reader-content.html?20160506'>
		<#elseif contentType == "IMAGE">
			<link rel='import' href='/elements.${lang}/pratilipi-reader-image-content.html?1'>
		</#if>

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
				page-no='${ pageNo }'
				page-count='${ pageCount }'
				content='${ content }'
				content-type='${ contentType }'
				index='${ indexJson }'
				></pratilipi-pratilipi-page>
	</body>
	
</html>
