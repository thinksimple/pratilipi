<#if !userNotifed??>
<#assign cookieName = "USER_NOTIFIED_APP_LAUNCHED">
<div id="androidSubsribeAlert" class="container" style="position: fixed;bottom: 0;width: 100%;margin-bottom: 0; border-radius: 0; cursor: pointer; padding: 0; color: #fff; background-color: #D0021B; border-color: #D0021B; margin-top: 8px;" class="alert alert-dismissible fade in" role="alert">
	<button style="outline: none; right: 6px; margin-top: 16px; opacity: 1; color: #fff !important;margin-right: 10px;" type="button" class="close pull-right" data-dismiss="alert" aria-label="Close" onclick="setCookie( '${ cookieName }', 'true', 7, '/' ); ga( 'send', 'event', 'android_banner', 'banner_close', 'android_registration' );">
		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
	</button>
	<div style="padding: 8px 20px; width: 95%; width: calc(100% - 32px); display: flex;" onclick="ga( 'send', 'event', 'android_banner', 'banner_click', 'android_registration' ); window.open('/android-app-registration');">
		<img style="max-width: 40px; max-height: 40px; margin-right: 12px; align-self: center;" src="http://0.ptlp.co/resource-all/home-page/google_playstore_40.png"/>
		<span style="font-size: 13px; font-weight: 400; align-self: center;">${ _strings.android_banner_1 }</span>
	</div>
</div>
</#if>