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

<#-- Polymer dependencies -->
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.js'></script>
<link rel='import' href='http://0.ptlp.co/resource-all/pratilipi.polymer.elements.html'>
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?4'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?64">

<#include "GoogleAnalytics.ftl">

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

<#--
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
	var databaseRef = database.ref().child( "NOTIFICATION" );
	var node = null;

	function firebaseSignInWithCustomToken( userId, token ) {
		firebase.auth().onAuthStateChanged( function( user ) {
			if( user ) {
				console.log( "in" );
				node = databaseRef.child( userId );
				node.on( 'value', function( snapshot ) {
					var snapshot = snapshot.val();
					console.log( snapshot );
				} );
			} else {
				console.log( "out" );
				firebase.auth().signInWithCustomToken( token ).catch( function( error ) {
					console.log( error.code );
					console.log( error.message );
				});
			}
		});

	}

	function firebaseUnauth() {
		firebase.auth().signOut();
	}


</script>
-->