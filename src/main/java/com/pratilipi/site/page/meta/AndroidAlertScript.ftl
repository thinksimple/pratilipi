<#-- Android App launch -->
<#assign cookie_show_banner = "USER_NOTIFIED_APP_LAUNCHED">
<#assign cookie_banner_clicked = "APP_LAUNCHED_CLICKED">
<#assign cookie_banner_crossed = "APP_LAUNCHED_CROSSED">

<script>
	var showBanner = getCookie( "${ cookie_show_banner }" ) == null || getCookie( "${ cookie_show_banner }" ) == "true";
	var click_count = getCookie( "${ cookie_banner_clicked }" ) == null ? 0 : parseInt( getCookie( "${ cookie_banner_clicked }" ) );
	var cross_count = getCookie( "${ cookie_banner_crossed }" ) == null ? 0 : parseInt( getCookie( "${ cookie_banner_crossed }" ) );

	<#if basicMode>
		$( document ).ready( function() {
			if( showBanner ) {
				if( document.getElementById( 'androidSubsribeAlert' ) != null ) {
					document.getElementById( 'androidSubsribeAlert' ).style.display = "block";
					ga( 'send', 'event', 'app_download_strip', 'app_strip_show', 'android_app_download' );
				}
			}
		});
		function setPaddingForAndroidBannerBottom() {
			var androidLaunchBottom = document.getElementById( "androidLaunchBottom" );
			var androidSubscribeAlert = document.getElementById( "androidSubsribeAlert" );
			if( $( androidSubscribeAlert ).css( "display" ) == "none" )
				$( androidLaunchBottom ).css( "padding-top", 0 );
			else
				$( androidLaunchBottom ).css( "padding-top", $( androidSubscribeAlert ).height() + 8 );
		}
		function hideAndroidBanner() {
			if( document.getElementById( 'androidSubsribeAlert' ) != null )
				document.getElementById( 'androidSubsribeAlert' ).style.display = "none";
			setPaddingForAndroidBannerBottom();
		}
		$( document ).ready( function() {
			setPaddingForAndroidBannerBottom();
		});
		$( window ).resize(function() {
			setPaddingForAndroidBannerBottom();
		});
	</#if>

	function showOrHideAndroidBanner() {
		if( click_count >= 3 ) {
			setCookie( "${ cookie_show_banner }", false, 365, "/" );
			return;
		}
		if( click_count < 3 && click_count > 0 ) {
			if ( cross_count > 2)
				resetCrossCount();
			if( cross_count == 0 )
				setCookie( "${ cookie_show_banner }", false, 3, "/" );
			if( cross_count == 1 )
				setCookie( "${ cookie_show_banner }", false, 7, "/" );
			if( cross_count == 2 )
				setCookie( "${ cookie_show_banner }", false, 30, "/" );
		} 
		else {
			if( cross_count < 3 )
				setCookie( "${ cookie_show_banner }", false, null, "/" );
			if( cross_count >= 3 && cross_count < 6)
				setCookie( "${ cookie_show_banner }", false, 2, "/" );
			if( cross_count >= 6 )
				setCookie( "${ cookie_show_banner }", false, 7, "/" );
		}
		setCookie( "${ cookie_banner_clicked }", click_count, 365, "/" );
		setCookie( "${ cookie_banner_crossed }", cross_count, 365, "/" );
		
		<#if basicMode>
			hideAndroidBanner();
		<#else>
			if( document.querySelector( 'pratilipi-android-launch' ) != null )
				document.querySelector( 'pratilipi-android-launch' ).hide();
		</#if>

	}
	function androidBannerClicked() {
		click_count++;
		showOrHideAndroidBanner();
		ga( 'send', 'event', 'app_download_strip', 'app_strip_click', 'android_app_download' );
	}
	function androidBannerCrossed() {
		cross_count++;
		showOrHideAndroidBanner();
		ga( 'send', 'event', 'app_download_strip', 'app_strip_close', 'android_app_download' );
	}
	function resetCrossCount() {
		cross_count = 0;
	}
</script>