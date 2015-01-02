/* For Google Analytics tracking */
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-53742841-2', 'auto');
ga('require', 'displayfeatures');
ga('send', 'pageview');




/* Get-Set document cookies */

function getCookie( cname ) {
	var name = cname + "=";
	var ca = document.cookie.split( ';' );
	for( var i = 0; i < ca.length; i++ ) {
		var c = ca[i];
		while( c.charAt(0)==' ' ) c = c.substring(1);
		if( c.indexOf(name) != -1 ) {
			return c.substring( name.length );
		}
	}
	return "";
}

function setCookie( cname, cvalue, exdays, path ) {
	if( exdays ) {
		var d = new Date();
		d.setTime( d.getTime() + (exdays*24*60*60*1000) );
		var expires = "; expires=" + d.toUTCString();
	} else {
		var expires = "";
	}
	
	if( path ) {
		if( path.indexOf( "?" ) != -1 )
			path = path.substring( 0, path.indexOf( "?" ) );
		path = "; path=" + path;
	} else {
		path = "; path=" + window.location.pathname;
	}
	document.cookie = cname + "=" + cvalue + expires + path;
}

