<#if author.getUser().getId()?? >
	<div class="pratilipi-shadow secondary-500 box">	
		<#assign followingUrl = "/following?uId=" + author.getUser().getId()?c >
		<div class="pull-left">
			<a href="${ followingUrl }"><h5 class="pratilipi-red pratilipi-no-margin">
				${ _strings.author_following }
			</h5></a>
			<a href="${ followingUrl }"><p class="works-number">${ followingList.getNumberFound() } ${ _strings.author_follow_members }</p></a>
		</div>
		<div class="pull-right">
			<a class="pull-right pratilipi-red pratilipi-view-more-link" href="${ followingUrl }"><img style="height:22px;" src="http://0.ptlp.co/resource-all/icon/svg/chevron-right-red.svg"></img></a>
		</div>
		<div class="clearfix"></div>
		<hr class="pratilipi-margin-top-2">	
		
		<#if followingList.getAuthorList()?has_content>
			<#include "pratilipi-follow-author-card.ftl">
			<#list followingList.getAuthorList() as local_author>
				<#if user.getId()?? && ( user.getId() != local_author.getUser().getId() ) >
					<#assign can_follow_boolean = "true">
				<#else>
					<#assign can_follow_boolean = "false">
				</#if>
				<@follow_author_card isGuest=user.isGuest?c can_follow=can_follow_boolean retUrl=author.getPageUrl() authorId=local_author.getId() followCount=local_author.getFollowCount() following=local_author.isFollowing() name=local_author.getName()!local_author.getNameEn() pageUrl=local_author.getPageUrl() imageUrl=local_author.getImageUrl(100)/>
			</#list>
		</#if>
						
	</div>	
</#if>	