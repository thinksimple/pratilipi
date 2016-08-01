<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#include "meta/HeadBasic.ftl">
	</head>
	
	<body>
		<script>
			function getUrlParameter( key ) {
			   if( key = ( new RegExp( '[?&]' +encodeURIComponent( key ) + '=([^&]*)' ) ).exec( location.search ) )
			      return decodeURIComponent( key[1] );
			   else
				   return null;
			}		
			function shareOnFacebook( ) {
				var url = getUrlParameter( "url" );
				url += encodeURIComponent( "&share=facebook" )
				window.open( url, "share", "width=600,height=500,left=70px,top=60px" );
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
					<a class="menu-item" style="cursor: pointer;" >
						<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/twitter.svg"/>
						<span>${ _strings.share_on_twitter }</span>
					</a>
					<a class="menu-item" style="cursor: pointer;" >
						<img class="reader-icon" src="http://0.ptlp.co/resource-all/icon/svg/google-plus2.svg"/>
						<span>${ _strings.share_on_gplus }</span>
					</a>
				</div>	
			</div>
		</div>
		<#include "../element/pratilipi-footer.ftl">
	</body>
	
</html>