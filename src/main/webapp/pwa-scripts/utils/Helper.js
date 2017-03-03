/* Helper functions not specific to Pratilipi */
function isMobile() {
	return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test( navigator.userAgent );
}

function getUrlParameter( variable ) {
	var query = window.location.search.substring(1);
	var vars = query.split( "&" );
	for( var i=0; i<vars.length; i++ ) {
		var pair = vars[i].split( "=" );
		if( pair[0] == variable ) {
			return pair[1];
		}
	}
	return null;
}

function getUrlParameters() {
	var str = decodeURI( location.search.substring(1) ), 
		res = str.split("&"), 
		retObj = {};
	for( var i = 0; i < res.length; i++ ){
		var key = res[i].substring( 0, res[i].indexOf( '=' ) );
		var value = res[i].substring( res[i].indexOf( '=' ) + 1 );
		retObj[ key ] = value;
	}
	if( retObj[""] != null ) delete retObj[""];
	return retObj;
}

function setCookie( name, value, days, path ) {
	if( days ) {
		var date = new Date();
		date.setTime( date.getTime() + ( days * 24 * 60 * 60 * 1000 ) );
	}	
	var expires = days ? "; expires=" + date.toGMTString() : "";
	document.cookie = name + "=" + value + "; path=" + path + expires;
}

function getCookie( cname ) {
	var name = cname + "=";
	var ca = document.cookie.split( ';' );
	for( var i = 0; i < ca.length; i++ ) {
		var c = ca[i];
		while( c.charAt(0) == ' ' ) c = c.substring( 1 );
		if( c.indexOf( name ) == 0 ) return c.substring( name.length, c.length );
	}
	return null;
}

function validateEmail( email ) {
	if( email == null ) return false;
	var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test( email );
}

function validatePhone( phone ) {
	if( phone == null ) return false;
	var pattern = /^[0-9]{10}$/;
	return pattern.test( phone );
}

function isEmpty( obj ) {
	for( var prop in obj ) {
		if( obj.hasOwnProperty( prop ) )
			return false;
	}
	return JSON.stringify( obj ) === JSON.stringify( {} );
}

function roundOffToOneDecimal( number ) {
	return Math.round( number * 10 ) / 10;
}

function abbrNum( n, d ) {
	if( n < 1000 ) return n;
	d = d != null ? d : 2;
	x = ( '' + n ).length, p = Math.pow, d = p( 10,d );
	x -= x % 3;
	return Math.round( n * d / p ( 10, x ) ) / d + " kMGTPE" [ x / 3 ]; 
}

function commaSeparatedNumber(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function capitalizeFirstLetter( string ) {
	return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}