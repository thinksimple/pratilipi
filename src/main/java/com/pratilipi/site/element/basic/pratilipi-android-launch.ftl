<#if !userNotifed??>
<#assign cookieName = "USER_NOTIFIED_APP_LAUNCHED">
	<div id="androidSubsribeAlert" class="container alert alert-warning alert-dismissible" style="z-index: 1;position: fixed;bottom: 0;right:0;left:0;width: 100%;margin-bottom: 0; border-radius: 0; cursor: pointer; padding: 0; color: #fff; background-color: #D0021B; border-color: #D0021B; margin-top: 8px;" class="alert alert-dismissible fade in" role="alert">
		<button style="outline: none; top:0;right: 0px; margin-top: 10px; opacity: 1; color: #fff !important;margin-right: 10px;" type="button" class="close pull-right" data-dismiss="alert" aria-label="Close" onclick="setCookie( '${ cookieName }', 'true', undefined, '/' ); ga( 'send', 'event', 'app_download_banner', 'banner_close', 'android_app_download' );">
			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		</button>
		<div style="padding: 4px 1px; width: 95%; width: calc(100% - 23px); display: flex;" onclick="setCookie( '${ cookieName }', 'true', 3, '/' );ga( 'send', 'event', 'app_download_banner', 'banner_click', 'android_app_download' ); window.open('https://play.google.com/store/apps/details?id=com.pratilipi.mobile.android&utm_source=pratilipi_mini_${language?lower_case}&utm_medium=bottom_strip&utm_campaign=app_download');">
			<img style="max-width: 40px; max-height: 40px; margin-right: 12px; align-self: center;" src="http://0.ptlp.co/resource-all/home-page/google_playstore_40.png"/>
			<span style="font-weight: 400; align-self: center;">
				<div style="font-size: 15px;line-height: 19px;"> ${ _strings.pratilipi_app_launched_website_strip } </div> 
				<div style="font-size: 13px;"> ${ _strings.pratilipi_app_click_download } </div>
			</span>
		</div>
	</div>
</#if>