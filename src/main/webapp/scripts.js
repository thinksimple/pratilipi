/* TODO: Move these functions to different files and compress and minify */
/* HttpUtil */
var HttpUtil = function() {
	this.formatParams = function( params ) {
		if( params == null ) return "";
		if( typeof( params ) === "string" ) return params;
		return Object.keys( params ).map( function(key) { return key + "=" + params[key] }).join("&");
	}
	this.get = function( aUrl, params, aCallback ) {
		var anHttpRequest = new XMLHttpRequest();
		anHttpRequest.onreadystatechange = function() { 
			if( anHttpRequest.readyState == 4 && aCallback != null )
				aCallback( anHttpRequest.getResponseHeader( 'content-type' ).indexOf( "application/json;" ) > -1 
					? JSON.parse( anHttpRequest.responseText ) : anHttpRequest.responseText, 
					anHttpRequest.status );
		};
		anHttpRequest.open( "GET", aUrl + ( aUrl.indexOf( "?" ) > -1 ? "&" : "?" ) + this.formatParams( params ), true );
		anHttpRequest.setRequestHeader( "Content-type", "application/x-www-form-urlencoded" );
		anHttpRequest.send( null );
	};
	this.post = function( aUrl, params, aCallback ) {
		var anHttpRequest = new XMLHttpRequest();
		anHttpRequest.onreadystatechange = function() { 
			if( anHttpRequest.readyState == 4 && aCallback != null )
				aCallback( anHttpRequest.getResponseHeader( 'content-type' ).indexOf( "application/json;" ) > -1 
					? JSON.parse( anHttpRequest.responseText ) : anHttpRequest.responseText, 
					anHttpRequest.status );
		};
		anHttpRequest.open( "POST", aUrl, true );
		anHttpRequest.setRequestHeader( "Content-type", "application/x-www-form-urlencoded" );
		anHttpRequest.send( this.formatParams( params ) );
	};
}

/* DataAccessor */
var DataAccessor = function() {

	var httpUtil = new HttpUtil();

	var API_PREFIX = "/api";

	var PAGE_API = "/page";
	var PRATILIPI_API = "/pratilipi?_apiVer=2";
	var USER_PRATILIPI_API = "/userpratilipi";

	var request = function( name, api, params ) {
		return {
			"name": name,
			"api": api,
			"params": httpUtil.formatParams( params )
		};
    };

	var processRequests = function( requests ) {
		var params = {};
		for( var i = 0; i < requests.length; i++ ) {
			var request = requests[i];
			params[ request.name ] = request.api + encodeURIComponent( request.api.indexOf( "?" ) > -1 ? "&" : "?" ) + request.params;
		}
		return JSON.stringify( params );
	};

	this.getPratilipiByUri = function( pageUri, includeUserPratilipi ) {

		var requests = [];
		requests.push( new request( "req1", PAGE_API, { "uri": pageUri } ) );
		requests.push( new request( "req2", PRATILIPI_API, { "pratilipiId": "$req1.primaryContentId" } ) );

		if( includeUserPratilipi )
			requests.push( new request( "req3", USER_PRATILIPI_API, { "pratilipiId": "$req1.primaryContentId" } ) );

		httpUtil.get( API_PREFIX, { "requests": processRequests( requests ) }, function( response, status ) {
			console.log( response );
			console.log( status );
		});
	}

	this.getPratilipiById = function( pratilipiId ) {
		httpUtil.get( API_PREFIX + PRATILIPI_API, { "pratilipiId": pratilipiId }, function( response, status ) {
			console.log( response );
			console.log( status );
		});
	}

}

/* Toaster & SnackBar */
var snackbarContainer = document.createElement( 'div' );
snackbarContainer.id = "pratilipi-toast";
snackbarContainer.className = "mdl-js-snackbar mdl-snackbar";
snackbarContainer.innerHTML = '<div class="mdl-snackbar__text"></div><button class="mdl-snackbar__action" type="button"></button>';
document.body.appendChild( snackbarContainer );

var ToastUtil = (function() {
	return {
		closeToast: function( event ) {
			/* snackbarContainer.MaterialSnackbar.cleanup_(); */
			/* As cleanup_ is not part of the public api and buggy, we are not using it. */
			/* Removing "mdl-snackbar--active" woduln't allow additional toasts to display until that long timeout is completed */
			snackbarContainer.classList.remove( "mdl-snackbar--active" );
		},
		toast: function( message, timeout, includeCloseButton ) {
			if( message == null ) return;
			var data = { message: message,
						timeout: timeout == null ? 2000 : timeout };
			if( includeCloseButton ) {
				data[ "actionHandler" ] = this.closeToast;
				data[ "actionText" ] = "x";
			}
			snackbarContainer.MaterialSnackbar.showSnackbar( data );
		},
		toastUp: function( message ) {
			var snackbarText = document.querySelector( '.mdl-snackbar__text' );
			snackbarText.innerHTML = message;
			snackbarContainer.classList.add( "mdl-snackbar--active" );
		},
		toastDown: function( count ) {
			setTimeout( function() {
				snackbarContainer.classList.remove( "mdl-snackbar--active" );
			}, 50 );
		}
	};
})();

/* Helper functions */
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

function getRetUrl( decoded ) {
	return getUrlParameter( "retUrl" ) == null
			? "/" 
			: ( decoded ? decodeURIComponent( getUrlParameter( "retUrl" ) ) : getUrlParameter( "retUrl" ) );
}

function isMobile() {
	return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test( navigator.userAgent );
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