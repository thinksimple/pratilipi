<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-reader-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160414'>
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
