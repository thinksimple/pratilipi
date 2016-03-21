<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-pratilipi-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/${ mainPage }.html?20160321'>
	</head>
	
	<body>
		<pratilipi-pratilipi-page 
				user-data='${ userJson }'
				pratilipi-id='${ pratilipi.getId()?c }'
				pratilipi-data='${ pratilipiJson }'
				userpratilipi-data='${ userpratilipiJson! }'
				
				review-list='${ reviewListJson }'
				cursor='${ reviewListCursor! }'
				pratilipi-types='${ pratilipiTypesJson }'
				navigation-list='${ navigationList }'></pratilipi-pratilipi-page>
	</body>
	
</html>
