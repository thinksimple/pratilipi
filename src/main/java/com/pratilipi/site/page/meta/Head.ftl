<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">

<#-- Third-Party Library -->
<#list resourceList as resource>
	${ resource }
</#list>
<link rel='import' href='http://0.ptlp.co/resource-all/pratilipi.polymer.elements.html'>
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?86'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?64">

<#include "GoogleAnalytics.ftl">

<#if language == "TAMIL" || language == "HINDI" || language == "TELUGU">
	<script type="text/javascript"> window.smartlook||(function(d) { var o=smartlook=function(){ o.api.push(arguments)},h=d.getElementsByTagName('head')[0]; var c=d.createElement('script');o.api=new Array();c.async=true;c.type='text/javascript'; c.charset='utf-8';c.src='//rec.getsmartlook.com/recorder.js';h.appendChild(c); })(document); smartlook('init', 'c5cada583672ea43f3f80994c203669e472a2954'); </script>
</#if>


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

<script src="https://www.gstatic.com/firebasejs/3.3.0/firebase.js"></script>

<script>

	var config = {
		apiKey: "AIzaSyAAnK0-vDmY1UEcrRRbCzXgdpF2oQn-E0w",
		authDomain: "prod-pratilipi.firebaseapp.com",
		databaseURL: "https://prod-pratilipi.firebaseio.com",
		storageBucket: "prod-pratilipi.appspot.com",
	};

	var app = firebase.initializeApp( config );
	var database = app.database();
	var auth = app.auth();
	var storage = app.storage();
	var databaseRef = database.ref().child( "NOTIFICATION" );
	var node = null;

	function firebaseSignInWithCustomToken( userId, token ) {
		if( token == null ) return;
		firebase.auth().signInWithCustomToken( token ).catch( function( error ) {
			var errorCode = error.code;
			var errorMessage = error.message;
			console.log( errorCode );
			console.log( errorMessage );
		});

		firebase.auth().onAuthStateChanged( function( user ) {
			if( user ) {
				node = databaseRef.child( userId );
				node.on( 'value', function( snapshot ) {
					var snapshot = snapshot.val();
					console.log( snapshot );
				} );
				<#-- node.set( { name: "name", message: "message" } ); -->
			}
		});

	}

	function firebaseUnauth() {
		firebase.auth().signOut();
	}


</script>
