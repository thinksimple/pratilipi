<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="pratilipi-shadow secondary-500 box">
					<#if ( !followingList?? || !author?? ) >
						<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
							<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
									src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
							<div class="text-center"><a class="login-link" href="/login?ret=/following">${ _strings.user_login_to_view_following }</a></div>
						</div>
					<#else>				
						<div class="pull-left">
							<a style="cursor: pointer;" onclick="goBack()">
								<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/arrow-left.svg">
						  	</a>
						</div>
						<div class="align-text-center">
							<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
								${ _strings.author_following_heading }
							</h5>
							<p class="works-number align-text-center">${ followingList.getNumberFound() } ${ _strings.author_follow_members }</p>					
						</div>					
						<div class="clearfix"></div>
						<hr>				
						<#if followingList.getAuthorList()?has_content>
							<#include "../element/basic/pratilipi-follow-author-card.ftl">
							<#list followingList.getAuthorList() as local_author>
								<#if !local_author.getUser().getId()?? || ( user.getId()?? && ( user.getId() != local_author.getUser().getId() ) ) >
									<#assign can_follow_boolean = "true">
								<#else>
									<#assign can_follow_boolean = "false">
								</#if>
								<@follow_author_card isGuest=user.isGuest()?c can_follow=can_follow_boolean retUrl=author.getPageUrl() authorId=local_author.getId() followCount=local_author.getFollowCount() following=local_author.isFollowing() name=local_author.getName()!local_author.getNameEn() pageUrl=local_author.getPageUrl() imageUrl=local_author.getImageUrl()/>
							</#list>
							
							<#-- Add page navigation -->
								<#assign currentPage = currPage>
								<#assign maxPage = maxPage>
								<#include "../element/basic/pratilipi-page-navigation.ftl">
						<#else>
							<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
								<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
										src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
								<div class="text-center">${ _strings.author_no_following }</div>
							</div>													
						</#if>
					</#if>	
				</div>
				
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
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
