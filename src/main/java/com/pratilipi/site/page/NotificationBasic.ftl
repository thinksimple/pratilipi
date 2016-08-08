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
				<div class="pratilipi-shadow secondary-500 box">
					<div class="pull-left">
						<a style="cursor: pointer;">
							<img style="width: 20px;height: 20px;" onclick="goBack()" src="http://0.ptlp.co/resource-all/icon/svg/arrow-left.svg">
					  	</a>	
					</div>
					<div class="center-heading">
						<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
							${ _strings.notification_notifications }
						</h5>
					</div>					
					<div class="clearfix"></div>
					<hr>				
					<#if notificationList?has_content >
						<div class="list-group">
						<#list notificationList as notification>
							<#if ( notification.getMessage()?? && notification.getSourceUrl()?? ) >
								<a href="${ notification.getSourceUrl() }" style="margin-bottom:4px;" class="list-group-item">${ notification.getMessage() }</a>
							</#if>	
						</#list>
						</div>
					</#if>
				</div>				
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