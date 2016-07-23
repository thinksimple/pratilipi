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
			<#if followingList.getAuthorList()?has_content>
				<#include "../element/pratilipi-follow-author-card.ftl">
				<#list followingList.getAuthorList() as local_author>
					<#if user.getId()?? && ( user.getId() != local_author.getUser().getId() ) >
						<#assign can_follow_boolean = "true">
					<#else>
						<#assign can_follow_boolean = "false">
					</#if>
					<@follow_author_card isGuest=user.isGuest?c can_follow=can_follow_boolean retUrl=author.getPageUrl() authorId=local_author.getId() followCount=local_author.getFollowCount() following=local_author.isFollowing() name=local_author.getName()!local_author.getNameEn() pageUrl=local_author.getPageUrl() imageUrl=local_author.getImageUrl()/>
				</#list>
			</#if>
		</div>
		<div>
			currPage = ${ currPage }
		</div>
		<div>
			maxPage = ${ maxPage }
		</div>
	</body>
	
</html>