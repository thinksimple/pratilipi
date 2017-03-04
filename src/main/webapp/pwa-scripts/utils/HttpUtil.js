/* HttpUtil */
var HttpUtil = function() {
	function processResponseText( repsonseText ) {
		var res = {};
		try {
			res = JSON.parse( repsonseText );
		} catch( err ) {
		    res[ "message" ] = "${ _strings.server_error_message }";
		}
		return res;
	};
	this.formatParams = function( params ) {
		if( params == null ) return "";
		if( typeof( params ) === "string" ) return params;
		return Object.keys( params ).map( function(key) { return key + "=" + params[key] }).join("&");
	};
	this.get = function( aUrl, params, aCallback ) {
		if( 'onLine' in navigator ) {
			if( ! navigator[ 'onLine' ] ) {
				aCallback( null, 0 );
				return;
			}
		}
		var anHttpRequest = new XMLHttpRequest();
		anHttpRequest.onreadystatechange = function() { 
			if( anHttpRequest.readyState == 4 && aCallback != null )
				aCallback( processResponseText( anHttpRequest.responseText ), anHttpRequest.status );
		};
		anHttpRequest.open( "GET", aUrl + ( aUrl.indexOf( "?" ) > -1 ? "&" : "?" ) + this.formatParams( params ), true );
		anHttpRequest.setRequestHeader( "Content-type", "application/x-www-form-urlencoded" );
		anHttpRequest.send( null );
	};
	this.post = function( aUrl, params, aCallback ) {
		if( 'onLine' in navigator ) {
			if( ! navigator[ 'onLine' ] ) {
				aCallback( { "message": "${ _strings.could_not_connect_server }" }, 0 );
				return;
			}
		}
		var anHttpRequest = new XMLHttpRequest();
		anHttpRequest.onreadystatechange = function() { 
			if( anHttpRequest.readyState == 4 && aCallback != null )
				aCallback( processResponseText( anHttpRequest.responseText ), anHttpRequest.status );
		};
		anHttpRequest.open( "POST", aUrl, true );
		anHttpRequest.setRequestHeader( "Content-type", "application/x-www-form-urlencoded" );
		anHttpRequest.send( this.formatParams( params ) );
	};
};
