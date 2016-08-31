<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<#-- Page Title & Favicon -->
<title>${ title }</title>
<link rel="shortcut icon" type="image/png" href="/favicon.png">



<#-- Polymer Check -->
<script>
function supportsPolymer() {
  return 'content' in document.createElement('template') && 'import' in document.createElement('link') && 'registerElement' in document && document.head.createShadowRoot;
}
if(!supportsPolymer())
	window.location = "http://${ lang }.pratilipi.com"  
</script>


<#-- Third-Party Library -->
<#list resourceList as resource>
	${ resource }
</#list>

<#-- Polymer dependencies -->
<script src='http://0.ptlp.co/resource-all/jquery.bootstrap.polymer.firebase.js'></script>
<link rel='import' href='http://0.ptlp.co/resource-all/pratilipi.polymer.elements.html'>
<link rel='import' href='/elements.${lang}/pratilipi-custom-elements.html?27'>

<#-- Custom Stylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/style.css?68">

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

<script>
	var config = {
		apiKey: "AIzaSyAAnK0-vDmY1UEcrRRbCzXgdpF2oQn-E0w",
		authDomain: "prod-pratilipi.firebaseapp.com",
		databaseURL: "https://prod-pratilipi.firebaseio.com",
		storageBucket: "prod-pratilipi.appspot.com",
	};

	firebase.initializeApp( config );

	var node = null;
	var newNotificationCount = null;
	var userLoggedIn = null;
	var user = ${ userJson };

	function firebaseLogin() {
		firebase.auth().signInWithCustomToken( user.firebaseToken );
	}

	function firebaseLogout() {
		firebase.auth().signOut();
	}

	function userChanged( usr ) {
		user = usr;
		if( userLoggedIn == null )
			return;
		if( usr.isGuest && userLoggedIn )
			firebaseLogout();
		else if( ! usr.isGuest && ! userLoggedIn )
			firebaseLogin();
	}

	function resetNewNotificationCount() {
		if( newNotificationCount != 0 )
			node.set( 0 );
	}

	firebase.auth().onAuthStateChanged( function( usr ) {
		if( usr ) {
			/* Session expired in server but not in firebase - very rare case */
			if( user.isGuest ) {
				firebaseLogout();
				return;
			}
			userLoggedIn = true;
			node = firebase.database().ref( "NOTIFICATION" ).child( usr.uid ).child( "newNotificationCount" );
			node.on( 'value', function( snapshot ) {
				newNotificationCount = snapshot.val() != null ? snapshot.val() : 0;
				if( document.querySelector( 'pratilipi-header' ) != null )
					document.querySelector( 'pratilipi-header' ).updateNewNotificationCount( newNotificationCount );
			});
		} else {
			/* Session expired in firebase but not in server - very rare case */
			if( ! user.isGuest ) {
				firebaseLogin();
				return;
			}
			userLoggedIn = false;
			node = null;
		}
	});
	
</script>