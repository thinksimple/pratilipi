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

/* SET AND GET COOKIES */
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

//EXECEUTE ON WINDOW LOAD
window.onload = function() {
	checkWidth();
	$( '#PageContent-Pratilipi-Content' ).on("contextmenu",function(e){ return false; });
	var readerContent = document.getElementById( 'PageContent-Pratilipi-Content' );
	if( readerContent ){
		//pkey = 80; ckey = 67; vkey = 86
		$( document ).bind("keyup keydown", function(e){
	    if( e.ctrlKey && ( e.keyCode == 80 ) ){
		        return false;
		    }
		});
		
		setPageContent();
		setMargin();
	}
}

//EXECUTE ON WINDOW RESIZE EVENT
window.onresize = function(event){
	checkWidth();
	
	var readerContent = document.getElementById( 'PageContent-Pratilipi-Content' );
	if( readerContent ){
		setMargin();
	}
}
