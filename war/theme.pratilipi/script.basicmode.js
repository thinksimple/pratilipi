/* SCRIPT IS USED TO CHANGE HEIGHT OF LOGIN/SIGHNUP PANEL TO ACCOMODATE IN SMALL SCREENS */
$(document).ready(function() {
	// Optimalisation: Store the references outside the event handler:
	var $window = $(window);
	function checkWidth() {
		var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		if (windowsize > 768) {
		$('#Pratilipi-Search-UserAccess').height( '50px' );
		}
		else
		$('#Pratilipi-Search-UserAccess').height( '80px' );
	}
	
	function setMargin(){
		var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var basicReader = document.getElementById("Pratilipi-Reader-Basic");
		var basicReaderWidth = basicReader.offsetWidth;
		alert( "Width : " + basicReaderWidth + "/" + windowSize );
		var padding;
		if( basicReaderWidth == 1000 ){
			var padding = ( windowSize - basicReaderWidth )/2;
			basicReader.setAttribute("style", "margin-left:" + padding.toString() + "px");
			basicReader.setAttribute("style", "margin-right:" + padding.toString() + "px");
		}
		else {
			basicReader.setAttribute("style", "margin-left:" + "10px");
			basicReader.setAttribute("style", "margin-right:" + "10px");
		}
	}
	
	// Execute on load
	checkWidth();
	setMargin();
	
	// Bind event listener
	$(window).resize(checkWidth);
	$(window).resize(setMargin);
	
	
});

