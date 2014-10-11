<#macro toolbar shareUrl>
	<div style="line-height:10px;">
		<!-- Facebook like and share buttons -->
		<div class="fb-like" data-href="${ shareUrl }" data-layout="button_count" data-action="like" data-show-faces="true" data-share="true"></div>
		<!-- Twitter tweet button -->
		<a href="https://twitter.com/share" class="twitter-share-button" data-url="${ shareUrl }"></a>
	</div>
</#macro>