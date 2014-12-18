/* SCRIPT IS USED TO CHANGE HEIGHT OF LOGIN/SIGHNUP PANEL TO ACCOMODATE IN SMALL SCREENS */
// Optimalisation: Store the references outside the event handler:
function checkScreenWidth() {
	var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var userAccessDiv = document.getElementById( "Pratilipi-Search-UserAccess" );
	if (windowsize > 768) {
		userAccessDiv.style.height = '50px';
	}
	else
		userAccessDiv.style.height = '80px';
}


if( window.attachEvent ){	//For IE8 and below
	window.attachEvent( 'onload', function( event ){
		checkScreenWidth();
	});

	window.attachEvent( 'onresize', function( event ){
		checkScreenWidth();
	});
} else {
	window.addEventListener( 'load', function( event ){
		checkScreenWidth();
	});

	window.addEventListener( 'resize', function( event ){
		checkScreenWidth();
	});
}

