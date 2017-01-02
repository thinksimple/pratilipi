<#-- Android App launch -->
<#assign cookie_show_banner = "USER_NOTIFIED_APP_LAUNCHED">
<#assign cookie_banner_clicked = "APP_LAUNCHED_CLICKED">
<#assign cookie_banner_crossed = "APP_LAUNCHED_CROSSED">

<script>
	var showBanner = getCookie( "${ cookie_show_banner }" ) == null || getCookie( "${ cookie_show_banner }" ) == "true";
	var click_count = getCookie( "${ cookie_banner_clicked }" ) == null ? 0 : parseInt( getCookie( "${ cookie_banner_clicked }" ) );
	var cross_count = getCookie( "${ cookie_banner_crossed }" ) == null ? 0 : parseInt( getCookie( "${ cookie_banner_crossed }" ) );

	function initAndroidBanner() {
		$( document ).ready( function() {
			if( showBanner )
				if( document.getElementById( 'androidSubsribeAlert' ) != null )
					document.getElementById( 'androidSubsribeAlert' ).style.display = "block";
		});
	}

	if( HTMLImports ) {
		HTMLImports.whenReady(function () {
			initAndroidBanner();
		});
	} else {
		initAndroidBanner();
	}

	function showOrHideAndroidBanner() {
		if( click_count >= 3 ) {
			setCookie( "${ cookie_show_banner }", false, 365, "/" );
			return;
		}
		if( click_count > 0 ) {
			if( cross_count == 0 )
				setCookie( "${ cookie_show_banner }", false, 3, "/" );
			if( cross_count == 1 )
				setCookie( "${ cookie_show_banner }", false, 7, "/" );
			if( cross_count >= 2 )
				setCookie( "${ cookie_show_banner }", false, 365, "/" );

			if( document.getElementById( 'androidSubsribeAlert' ) != null )
					document.getElementById( 'androidSubsribeAlert' ).style.display = "none";

		} else {
			if( cross_count == 1 )
				setCookie( "${ cookie_show_banner }", false, null, "/" );
			if( cross_count == 2 )
				setCookie( "${ cookie_show_banner }", false, null, "/" );
			if( cross_count >= 3 )
				setCookie( "${ cookie_show_banner }", false, 14, "/" );
		}
		setCookie( "${ cookie_banner_clicked }", click_count, 365, "/" );
		setCookie( "${ cookie_banner_crossed }", cross_count, 365, "/" );

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
</script>