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


//EXECUTE ON WINDOW LOAD EVENT
window.addEventListener( 'load', function( event ){
	checkScreenWidth();
});

//EXECUTE ON WINDOW RESIZE EVENT
window.addEventListener( 'resize', function( event ){
	checkScreenWidth();
});

