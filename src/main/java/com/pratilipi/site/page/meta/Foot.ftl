<style>
	<#if language == "HINDI">
		@import 'https://fonts.googleapis.com/css?family=Noto+Sans&subset=devanagari';
		*{font-family: Helvetica, 'Noto Sans', sans-serif !important;}
	</#if>
	<#if language == "BENGALI">
		@import url(http://fonts.googleapis.com/earlyaccess/notosansbengali.css);
		*{font-family: Helvetica, 'Noto Sans Bengali', sans-serif !important;}
	</#if>
	<#if language == "MARATHI">
		@import url(http://fonts.googleapis.com/earlyaccess/notosansdevanagari.css);
		*{font-family: Helvetica, 'Noto Sans Devanagari', sans-serif !important;}
	</#if>
	<#if language == "GUJARATI">
		@import url(http://0.ptlp.co/resource-gu/font/shruti.css);
		*{font-family: Helvetica, 'Shruti', sans-serif !important;}
	</#if>
	<#if language == "TAMIL">
		@import url(http://fonts.googleapis.com/earlyaccess/notosanstamil.css);
		*{font-family: Helvetica, 'Noto Sans Tamil', sans-serif !important;}
	</#if>
	<#if language == "MALAYALAM">
		@import url(http://fonts.googleapis.com/earlyaccess/notosansmalayalam.css);
		*{font-family: Helvetica, 'Noto Sans Malayalam', sans-serif !important;}
	</#if>
	<#if language == "TELUGU">
		@import url(http://fonts.googleapis.com/earlyaccess/notosanstelugu.css);
		*{font-family: Helvetica, 'Noto Sans Telugu', sans-serif !important;}
	</#if>
	<#if language == "KANNADA">
		@import url(http://fonts.googleapis.com/earlyaccess/notosanskannada.css);
		*{font-family: Helvetica, 'Noto Sans Kannada', sans-serif !important;}
	</#if>
</style>

<link type="text/css" rel="stylesheet" href="/resources/style.css?75">

<script>
	var didScroll;
	var lastScrollTop = 0;
	var delta = 10;
	var navbarHeight = 75;
	
	window.onscroll = function() {
		var st = $(this).scrollTop();
		document.querySelector( '${ mainPage }' ).scrollHandler( st );
		if( Math.abs( lastScrollTop - st ) <= delta )
				return;
		if( st > lastScrollTop && st > navbarHeight )
			$( 'header' ).removeClass( 'nav-down' ).addClass( 'nav-up' );
		else if( st + $(window).height() < $(document).height() || st < navbarHeight )
			$( 'header' ).removeClass( 'nav-up' ).addClass( 'nav-down' );
		lastScrollTop = st; 
	};
</script>

<#-- Hotjar Tracking Code for http://www.pratilipi.com -->
<script>
    (function(h,o,t,j,a,r){
        h.hj=h.hj||function(){(h.hj.q=h.hj.q||[]).push(arguments)};
        h._hjSettings={hjid:243839,hjsv:5};
        a=o.getElementsByTagName('head')[0];
        r=o.createElement('script');r.async=1;
        r.src=t+h._hjSettings.hjid+j+h._hjSettings.hjsv;
        a.appendChild(r);
    })(window,document,'//static.hotjar.com/c/hotjar-','.js?sv=');
</script>