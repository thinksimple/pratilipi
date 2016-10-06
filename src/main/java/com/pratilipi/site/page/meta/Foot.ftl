<#include "Font.ftl">

<link type="text/css" rel="stylesheet" href="/resources/style.css?77">

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

<#-- CustomerLabs Tag -->
<script>!function(t,e,r,c,a,n,s){t.ClAnalyticsObject=a,t[a]=t[a]||[],t[a].methods=["trackSubmit","trackClick","pageview","identify","track"],t[a].factory=function(e){return function(){var r=Array.prototype.slice.call(arguments);return r.unshift(e),t[a].push(r),t[a]}};for(var i=0;i<t[a].methods.length;i++){var o=t[a].methods[i];t[a][o]=t[a].factory(o)};n=e.createElement(r),s=e.getElementsByTagName(r)[0],n.async=1,n.crossOrigin="anonymous",n.src=c,s.parentNode.insertBefore(n,s)}(window,document,"script","//cdn.js.customerlabs.co/cl63u8jemht8.js","_cl");_cl.SNIPPET_VERSION="1.0.0"</script>

<#-- Google Transliterate Api -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load( "elements", "1", {
		packages: "transliteration"
	});
	function onLoad() {
		if( google.elements.transliteration.isBrowserCompatible() ) {
			var options = {
				sourceLanguage:
					google.elements.transliteration.LanguageCode.ENGLISH,
				destinationLanguage:
					[google.elements.transliteration.LanguageCode.${language}],
				shortcutKey: 'ctrl+g',
				transliterationEnabled: true
			};
	    	var control = new google.elements.transliteration.TransliterationControl( options );
	    	var allTextBoxes = $( "[name='pratilipiInputTransliterable'], textarea" ).toArray();
			control.makeTransliteratable( allTextBoxes );
		} 
	}
	google.setOnLoadCallback( onLoad );
</script>