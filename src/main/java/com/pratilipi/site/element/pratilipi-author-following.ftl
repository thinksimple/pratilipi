<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
			<!-- ${ _strings.author_drafts } -->Following
				<!-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
		</h5>
		<p class="works-number">${ followingList.getNumberFound() } Members</p>
	</div>
	<div class="pull-right">
		<br>	
		<a class="pull-right pratilipi-red" href="#">View More</a>
	</div>
	<div class="clearfix"></div>
	<hr style="margin-top:0px;margin-bottom:0px;">	
	
	<#if followingList.getAuthorList()?has_content>
		<#include "pratilipi-follow-author-card.ftl">
		<#list followingList.getAuthorList() as local_author>
			<#if user.userId?? && user.userId != local_author.getUser().getId() >
				<#assign can_follow_boolean = "true">
			<#else>
				<#assign can_follow_boolean = "false">
			</#if>
			<script>
			$( document ).ready(function() {
    			console.log( "Testing!" );
    			console.log(${ user.userId?c });
    			console.log(${ local_author.getUser().getId()?c });
    			console.log(${ can_follow_boolean });
			});
			</script>
			<@follow_author_card isGuest=user.isGuest?c can_follow=can_follow_boolean retUrl=author.getPageUrl() userId=local_author.getUser().getId()?c followCount=local_author.getFollowCount() following=local_author.isFollowing() name=local_author.getName()!local_author.getNameEn() pageUrl=local_author.getPageUrl() imageUrl=local_author.getImageUrl()/>
		</#list>
	</#if>
					
</div>	