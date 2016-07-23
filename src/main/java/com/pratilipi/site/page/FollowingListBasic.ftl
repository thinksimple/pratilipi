<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<#include "meta/HeadBasic.ftl">
		<style>
			div {
				margin-bottom: 16px;
			}
		</style>
	</head>

	<body>
		<div>
			followersList = ${ followingListJson }
		</div>
		<div>
			currPage = ${ currPage }
		</div>
		<div>
			maxPage = ${ maxPage }
		</div>
	</body>
	
</html>