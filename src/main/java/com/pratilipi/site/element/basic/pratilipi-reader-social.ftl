<style>
	.menu-item {
		margin:32px 16px;
		display: block;
		text-align: left;
	}
	.menu-item img {
		margin-right: 12px;
		width: 20px;
		height: 20px;
	}
	.menu-item span {
		font-size: 14px;
	}
</style>

<div class="secondary-500 pratilipi-shadow box">
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnFacebook( 'menu' )">
		<div class="sprites-icon reader-setting-icon fb-black-icon"></div>
		<span>${ _strings.share_on_facebook }</span>
	</a>
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnTwitter( 'menu' )">
		<div class="sprites-icon reader-setting-icon twitter-black-icon"></div>
		<span>${ _strings.share_on_twitter }</span>
	</a>
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnGplus( 'menu' )">
		<div class="sprites-icon reader-setting-icon gplus-black-icon"></div>
		<span>${ _strings.share_on_gplus }</span>
	</a>
	<a class="menu-item" style="cursor: pointer;" href="whatsapp://send?text=http%3A%2F%2F${ language?lower_case }.pratilipi.com${ pratilipi.getPageUrl()?url('UTF-8') }%3Futm_language%3D${ language?lower_case }%26utm_version%3Dlite%26utm_device%3Dmobile%26utm_parent%3Dreader%26utm_location%3Dmenu%26utm_action%3Dshare%26utm_source%3Dwhatsapp">
		<div class="sprites-icon reader-footer-icon whatsapp-black-icon"></div>
	</a>	
</div>