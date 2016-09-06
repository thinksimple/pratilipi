<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-author-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<pratilipi-author-page 
			user-data='${ userJson }'
			author='${ authorJson }'
			user-author='${ userAuthorJson }'
			published-pratilipi-list='${ publishedPratilipiListObjectJson }'
			following-list='${ followingListJson }'
			followers-list='${ followersListJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-author-page>

		<#include "meta/Foot.ftl">
	</body>
	
</html>
</#compress>