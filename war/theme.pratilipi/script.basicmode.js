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
	var imageContent = document.getElementById( "imageContent" );
	var basicReaderWidth = basicReader.offsetWidth;
	var padding;

	if( basicReaderWidth >= 1000 ){
		if( imageContent )
			padding = ( windowSize - imageContent.offsetWidth )/2;
		else
			padding = ( windowSize - 1000 )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
	}
	else{
		basicReader.setAttribute("style", "padding-left: 10px; padding-right: 10px; background-color: #f5f5f5;");
	}
}

/* SET AND GET COOKIE TO KEEP TRACK OF WORD CONTAINT FONT SIZE AND IMAGE HEIGHT IN CASE OF IMAGE CONTENT */
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}

/* SET PAGE CONTENT AS PER COOKIE  */
function setPageContent(){
	var imageHeight = getCookie( "image-height" );
	var fontSize = getCookie( "font-size" );
	var pageContentDiv = document.getElementById( "PageContent-Pratilipi-Content" );
	
	if( pageContentDiv){
		if( document.getElementById( "imageContent" ) && imageHeight ){
			document.getElementById( "imageContent" ).height = imageHeight;
			document.getElementById( "imageContent" ).style.width = 'auto';
			var contentTd = document.getElementById( "Pratilipi-Content-td" );
			contentTd.width = document.getElementById( "imageContent" ).width; 
		}
		
		if( pageContentDiv.getElementsByTagName( "p" ) && fontSize ){
			var tagList = Array.prototype.slice.call( pageContentDiv.getElementsByTagName( "p" ) );
			tagList.forEach( function( value, index, p ) {
				p[index].style.fontSize = fontSize + "px";
			} );
		}
	}
}

//EXECEUTE ON WINDOW LOAD
window.onload = function() {
	checkWidth();
	setPageContent();
	setMargin();
	
}

//EXECUTE ON WINDOW RESIZE EVENT
window.onresize = function(event){
	checkWidth();
	setMargin();
}

