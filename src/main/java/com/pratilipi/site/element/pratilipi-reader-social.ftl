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
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnFacebook()">
		<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/facebook2.svg"/>
		<span>${ _strings.share_on_facebook }</span>
	</a>
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnTwitter()">
		<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/twitter.svg"/>
		<span>${ _strings.share_on_twitter }</span>
	</a>
	<a class="menu-item" style="cursor: pointer;" onCLick="shareOnGplus()">
		<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/google-plus2.svg"/>
		<span>${ _strings.share_on_gplus }</span>
	</a>
</div>