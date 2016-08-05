<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/pratilipi-header.ftl">
		<#include "../element/pratilipi-facebook-login.ftl">
		<div class="parent-container">
			<div class="container">
				${ notificationListJson }
				<div class="pratilipi-shadow secondary-500 box">
					<div class="pull-left">
						<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
							${ _strings.notification_notifications }
						</h5>
						<p class="works-number">${ followingList.getNumberFound() } ${ _strings.author_follow_members }</p>
					</div>
					<div class="pull-right">
					  	<a style="cursor: pointer;">
							<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg">
					  	</a>
					</div>					
					<div class="clearfix"></div>
					<hr>				
					<#if notificationList?has_content >
						<div class="list-group">
						<#list notificationList as notification>
							<a href="${ notification.getSourceUrl() }" class="list-group-item">${ notification.getMessage() }</a>
						</#list>
						</div>
					</#if>
				</div>				
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>