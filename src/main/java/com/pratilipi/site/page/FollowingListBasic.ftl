<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="pratilipi-shadow secondary-500 box">
					<div class="pull-left">
						<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
							${ _strings.author_following }
						</h5>
						<p class="works-number">${ followingList.getNumberFound() } ${ _strings.author_follow_members }</p>
					</div>
					<div class="pull-right">
					  	<a style="cursor: pointer;" onclick="goBack()">
							<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg">
					  	</a>
					</div>					
					<div class="clearfix"></div>
					<hr>				
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
				
				<#-- Add page navigation -->
				<#assign currentPage = currPage>
				<#assign maxPage = maxPage>
				<#include "../element/pratilipi-page-navigation.ftl">
				
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	<script>
		function getUrlParameter( key ) {
		   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
		      return decodeURIComponent( key[1] );
		   else
			   return null;
		}	
		
		function goBack() {
			if( getUrlParameter( "ret" ) != null )
				window.location.href =  getUrlParameter( "ret" );
			else
				window.location.href = "/";
		}
	</script>	
</html>
