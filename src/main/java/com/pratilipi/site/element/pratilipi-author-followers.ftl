<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
			<!-- ${ _strings.author_drafts } -->Followers
		</h5>
		<p class="works-number">${ followersList.getNumberFound() } Members</p>
	</div>
	<div class="pull-right">
		<br>	
		<a class="pull-right pratilipi-red" href="#">View More</a>
	</div>
	<div class="clearfix"></div>
	<hr style="margin-top:0px;margin-bottom:0px;">	

	<#if followersList.getUserList()?has_content>
		<#include "pratilipi-follow-author-card.ftl">
		<#list followersList.getUserList() as local_user>
			<#if user.getId()?? && ( user.getId() != local_user.getId() ) >
				<#assign can_follow_boolean = "true">
			<#else>
				<#assign can_follow_boolean = "false">
			</#if>
			<#assign local_author = local_user.getAuthor() >
			<@follow_author_card isGuest=user.isGuest?c can_follow=can_follow_boolean retUrl=author.getPageUrl() authorId=local_author.getId() followCount=local_author.getFollowCount() following=local_author.isFollowing() name=local_user.getDisplayName() pageUrl=local_user.getProfilePageUrl() imageUrl=local_user.getProfileImageUrl()/>
		</#list>
	</#if>		
</div>	