<#-- Style CSS -->
<#include "Font.ftl">
<link type="text/css" rel="stylesheet" href="/resources/style.css?1811">

<#-- Scroll Handler -->
<#if mainPage??>
	<script> var didScroll; var lastScrollTop = 0; var delta = 10; var navbarHeight = 75; window.onscroll = function() { var st = $(this).scrollTop(); document.querySelector( '${ mainPage }' ).scrollHandler( st ); if( Math.abs( lastScrollTop - st ) <= delta ) return; if( st > lastScrollTop && st > navbarHeight ) $( 'header' ).removeClass( 'nav-down' ).addClass( 'nav-up' ); else if( st + $(window).height() < $(document).height() || st < navbarHeight ) $( 'header' ).removeClass( 'nav-up' ).addClass( 'nav-down' ); lastScrollTop = st; };</script>
</#if>

<#-- Hotjar Tracking Code for http://www.pratilipi.com -->
<script> (function(h,o,t,j,a,r){ h.hj=h.hj||function(){(h.hj.q=h.hj.q||[]).push(arguments)}; h._hjSettings={hjid:243839,hjsv:5}; a=o.getElementsByTagName('head')[0]; r=o.createElement('script');r.async=1; r.src=t+h._hjSettings.hjid+j+h._hjSettings.hjsv; a.appendChild(r); })(window,document,'//static.hotjar.com/c/hotjar-','.js?sv=');</script>

<#-- Google Transliterate Api -->
<#include "GoogleTransliterate.ftl">


<#-- Android App launch -->
<#assign cookieName = "USER_NOTIFIED">
<script>
HTMLImports.whenReady(function () {
	$( document ).ready( function() {
		if( getCookie( "${ cookieName }" ) != "true" )
			if( document.getElementById( 'androidSubsribeAlert' ) != null )
				document.getElementById( 'androidSubsribeAlert' ).style.display = "block";
	});
});
function subscribeAndroid( email, phone ) {
	document.getElementById( 'success-androidSubsribeModal' ).style.display = 'none';
	document.getElementById( 'failure-androidSubsribeModal' ).style.display = 'none';
	document.getElementById( 'loading-androidSubsribeModal' ).style.display = 'block';
	var errMessage = null;
	if( ( email == null || email.trim() == "" ) 
			&& ( phone == null || phone.trim() == "" ) )
		errMessage = "${ _strings.android_email_or_phone_required }";
	else if( phone != null && phone.trim() != "" && ! validatePhone( phone ) )
		errMessage = "${ _strings.android_phone_incorrect }";
	else if( email != null && email.trim() != "" && ! validateEmail( email ) )
		errMessage = "${ _strings.android_email_incorrect }";

	if( errMessage != null ) {
		document.getElementById( 'loading-androidSubsribeModal' ).style.display = 'none';
		document.getElementById( 'failure-androidSubsribeModal' ).style.display = 'block';
		document.getElementById( 'failure-androidSubsribeModal' ).innerHTML = errMessage;
		return;
	}
	$.ajax({
		type: 'post',
		url: '/api/mailinglist/subscribe',
		data: {
			'mailingList': "LAUNCH_ANNOUNCEMENT_ANDROID_APP",
			'email': email,
			'phone': phone,
			'language': "${ language }"
		},
		success: function( response ) {
			handleSuccessResponse();
			ga( 'send', 'event', 'modal_submit', 'register', 'android_registration' );
		},
		error: function( response ) {
			if( response.status == 400 ) {
				handleSuccessResponse();
				return;
			}
			var message = "${ _strings.server_error_message }";
			var res = jQuery.parseJSON( response.responseText );
			if( res.email != null ) message = res.email;
			else if( res.phone != null ) message = res.phone;
			else if( res.message != null ) message = res.message;
			document.getElementById( 'loading-androidSubsribeModal' ).style.display = 'none';
			document.getElementById( 'failure-androidSubsribeModal' ).style.display = 'block';
			document.getElementById( 'failure-androidSubsribeModal' ).innerHTML = message;
		}
	});
}
function handleSuccessResponse() {
	document.getElementById( 'loading-androidSubsribeModal' ).style.display = 'none';
	document.getElementById( 'success-androidSubsribeModal' ).style.display = 'block';
	setCookie( '${ cookieName }', 'true', 365, "/" );
	setTimeout( function() {
		document.getElementById( "androidSubsribeForm" ).reset();
		document.getElementById( 'success-androidSubsribeModal' ).style.display = 'none';
		document.getElementById( 'androidSubsribeAlert' ).style.display = 'none';
		<#-- jQuery( "#androidSubsribeModal" ).modal( 'hide' ); -->
		document.getElementById( 'androidSubsribeForm' ).style.display = 'none';
		document.getElementById( 'inviteFriends' ).style.display = 'block';
		if( isMobile() )
			document.getElementById( 'whatsappShareAndroidRegistration' ).style.display = 'inline-block';
	}, 2000);
}
</script>