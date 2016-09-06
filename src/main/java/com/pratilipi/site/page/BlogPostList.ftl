<#compress>
<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-blog-list-page">
		<#include "meta/Head.ftl">
	</head>

	<body>
		<pratilipi-blog-list-page 
			user-data='${ userJson }'
			blog-id='${ blogId?c }'
			has-access-to-add=${ hasAccessToAdd?c }
			blogpost-list='${ blogPostListJson }'
			filter='${ blogPostFilterJson }'
			cursor='${ blogPostListCursor }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-blog-list-page>

		<#include "meta/Foot.ftl">
    </body>

</html>
</#compress>