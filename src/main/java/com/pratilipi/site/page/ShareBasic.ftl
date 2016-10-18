<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<script>
			function shareOnFacebook() {
				var url = encodeURIComponent( getUrlParameter( "url" ) );
				url += encodeURIComponent( "&utm_source=facebook" )
				var fbUrl = ( "http://www.facebook.com/sharer.php?u=" + url );
				window.open( fbUrl, "share", "width=600,height=500,left=70px,top=60px" );
			}
			function shareOnTwitter() {
				var url = encodeURIComponent( getUrlParameter( "url" ) );
				url += encodeURIComponent( "&utm_source=twitter" );		
				window.open( "http://twitter.com/share?url=" + url ,
				"share", "width=500,height=600,left=70px,top=60px" );
			}
			function shareOnGplus() {
				var url = encodeURIComponent( getUrlParameter( "url" ) );
				url += encodeURIComponent( "&utm_source=gplus" );				
				window.open( "https://plus.google.com/share?url=" + url, "share", "width=500,height=600,left=70px,top=60px" );
			}
		</script>
		<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px; height: 64px;">
			<a style="cursor: pointer; position: absolute; right: 16px; top: 20px;" onClick="history.back();return false;">
				<img style="width: 20px;height: 20px;" src="http://0.ptlp.co/resource-all/icon/svg/cross.svg"/>
			</a>
		</div>		
		<div class="parent-container">
			<div class="container">
				<div class="secondary-500 pratilipi-shadow box">
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnFacebook()" >
						<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/facebook2.svg"/>
						<span>${ _strings.share_on_facebook }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnTwitter()" >
						<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/twitter.svg"/>
						<span>${ _strings.share_on_twitter }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnGplus()">
						<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/google-plus2.svg"/>
						<span>${ _strings.share_on_gplus }</span>
					</a>
				</div>	
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>