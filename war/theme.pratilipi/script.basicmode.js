/* SCRIPT IS USED TO CHANGE HEIGHT OF LOGIN/SIGHNUP PANEL TO ACCOMODATE IN SMALL SCREENS */
// Optimalisation: Store the references outside the event handler:
function checkWidth() {
	var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	if (windowsize > 768) {
		$('#Pratilipi-Search-UserAccess').height( '50px' );
	}
	else
		$('#Pratilipi-Search-UserAccess').height( '80px' );
}


//EXECUTE ON WINDOW RESIZE EVENT
window.onresize = function(event){
	checkWidth();
	
	var readerContent = document.getElementById( 'PageContent-Pratilipi-Content' );
	if( readerContent ){
		centerAlignBasicReader();
	}
}

