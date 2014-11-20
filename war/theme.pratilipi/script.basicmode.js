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
	
	//Center align reader when screen size is greater than 1000px
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
	}
	
	// Execute on load
	checkWidth();
	setMargin();
	
	// Bind event listener
	$(window).resize(checkWidth);
	$(window).resize(setMargin);
	
	
});

