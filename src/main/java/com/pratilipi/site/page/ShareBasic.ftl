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
			function shareOnWhatsapp() {
				var url = encodeURIComponent( getUrlParameter( "url" ) );
				url += encodeURIComponent( "&utm_source=whatsapp" );				
				window.location.href = ( "whatsapp://send?text=" + url);	
			}
		</script>
		<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px; height: 64px;">
			<a style="cursor: pointer; position: absolute; right: 16px; top: 20px;" onClick="history.back();return false;">
				<div class="sprites-icon black-cross-icon"></div>
			</a>
		</div>		
		<div class="parent-container" style="padding-top: 0px;">
			<div class="container">
				<div class="secondary-500 pratilipi-shadow box">
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnFacebook()" >
						<div class="sprites-icon reader-setting-icon fb-black-icon"></div>
						<span>${ _strings.share_on_facebook }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnTwitter()" >
						<div class="sprites-icon reader-setting-icon twitter-black-icon"></div>
						<span>${ _strings.share_on_twitter }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnGplus()">
						<div class="sprites-icon reader-setting-icon gplus-black-icon"></div>
						<span>${ _strings.share_on_gplus }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" onclick="shareOnWhatsapp()" >
						<div class="sprites-icon reader-setting-icon whatsapp-black-icon"></div>
						<span>${ _strings.share_on_whatsapp }</span>
					</a>					
				</div>	
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	
</html>