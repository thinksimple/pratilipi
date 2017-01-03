<div id="androidSubsribeAlert" class="container alert alert-warning alert-dismissible fade in" style="z-index: 1;position: fixed;bottom: 0;right:0;left:0;width: 100%;margin-bottom: 0; border-radius: 0; cursor: pointer; padding: 0; color: #fff; background-color: #D0021B; border-color: #D0021B; margin-top: 8px; display: none;" role="alert">
	<button style="outline: none; top:0;right: 0px; margin-top: 10px; opacity: 1; color: #fff !important;margin-right: 10px;" type="button" class="close pull-right" data-dismiss="alert" aria-label="Close" onclick="androidBannerCrossed();">
		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
	</button>
	<div style="padding: 4px 1px; width: 95%; width: calc(100% - 23px); display: flex;" onclick="androidBannerClicked(); window.open( 'https://play.google.com/store/apps/details?id=com.pratilipi.mobile.android&referrer=utm_source%3Dpratilipi_mini_web%26utm_medium%3Dweb_bottom_strip%26utm_campaign%3Dapp_download' );">
		<img style="max-width: 40px; max-height: 40px; margin-right: 12px; align-self: center;" src="http://0.ptlp.co/resource-all/home-page/google_playstore_40.png"/>
		<span style="font-weight: 400; align-self: center;">
			<span style="font-size: 15px; line-height: 19px;">${ _strings.pratilipi_app_launched_website_strip }</span>
			<br /> 
			<span style="font-size: 13px;">${ _strings.pratilipi_app_click_download }</span>
		</span>
	</div>
</div>
