<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-bold pratilipi-no-margin">
			${ _strings.author_following }
		</h5>
		<p class="works-number">${ followingList.getNumberFound() } ${ _strings.author_follow_members }</p>
	</div>
	<div class="pull-right">
		<a class="pull-right pratilipi-red pratilipi-view-more-link" href="/following?uId=${ author.getUser().getId()?c }">${ _strings.view_more }</a>
	</div>
	<div class="clearfix"></div>
	<hr>	
	
	<#if followingList.getAuthorList()?has_content>
		<#include "pratilipi-follow-author-card.ftl">
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