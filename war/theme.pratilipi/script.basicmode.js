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

/* SCRIPT TO KEEP READER CENTER ALIGNED IRRESPECTIVE OF THE SCREEN SIZE AND RESTRICT READER'S WIDTH TO 1000PX */
function setMargin(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var paper = document.getElementById( "paper" );
	var basicReaderWidth = basicReader.offsetWidth;
	var padding;
	if( basicReaderWidth >= 1000 ){
		var padding = ( windowSize - paper.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
	}
	else{
		basicReader.setAttribute("style", "padding-left: 10px; padding-right: 10px; background-color: #f5f5f5;");
	}
}

//EXECEUTE ON WINDOW LOAD
window.onload = function() {
	checkWidth();
	setMargin();
}

//EXECUTE ON WINDOW RESIZE EVENT
window.onresize = function(event){
	checkWidth();
	setMargin();
}

