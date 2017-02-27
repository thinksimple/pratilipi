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
				/* anHttpRequest.getResponseHeader( 'content-type' ).indexOf( "application/json;" ) */
				aCallback( JSON.parse( anHttpRequest.responseText ), anHttpRequest.status );
		};
		anHttpRequest.open( "GET", aUrl + ( aUrl.indexOf( "?" ) > -1 ? "&" : "?" ) + this.formatParams( params ), true );
		anHttpRequest.setRequestHeader( "Content-type", "application/x-www-form-urlencoded" );
		anHttpRequest.send( null );
	};
	this.post = function( aUrl, params, aCallback ) {
		var anHttpRequest = new XMLHttpRequest();
		anHttpRequest.onreadystatechange = function() { 
			if( anHttpRequest.readyState == 4 && aCallback != null )
				aCallback( JSON.parse( anHttpRequest.responseText ), anHttpRequest.status );
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
	//var API_PREFIX = "http://hindi.gamma.pratilipi.com/api"; 

	var PAGE_API = "/page";
	var PRATILIPI_API = "/pratilipi?_apiVer=2";
	var USER_PRATILIPI_API = "/userpratilipi";
	var AUTHOR_API = "/author";
	var USER_AUTHOR_FOLLOW_API = "/userauthor/follow?_apiVer=2";
	var USER_API = "/user";
	var NOTIFICATION_LIST_API = "/notification/list";
	var NAVIGATION_LIST_API = "/navigation/list";
	var USER_PRATILIPI_REVIEW_LIST_API = "/userpratilipi/review/list";
	var COMMENT_LIST_API = "/comment/list";
	var USER_PRATILIPI_REVIEW_API = "/userpratilipi/review";
	var USER_AUTHOR_FOLLOW_API = "/userauthor/follow?_apiVer=2";
	var USER_PRATILIPI_LIBRARY_API = "/userpratilipi/library";
	var COMMENT_API = "/comment";
	var VOTE_API = "/vote";

	var request = function( name, api, params ) {
		return {
			"name": name,
			"api": api,
			"params": encodeURIComponent( httpUtil.formatParams( params ) )
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

	var processGetResponse = function( response, status, aCallBack ) {
		if( aCallBack != null )
			aCallBack( status == 200 ? response : null );
	};

	var processPostResponse = function( response, status, successCallBack, errorCallBack ) {
		if( status == 200 && successCallBack != null )
			successCallBack( response );
		else if( status != 200 && errorCallBack != null )
			errorCallBack( response );
	};

	this.getPratilipiByUri = function( pageUri, includeUserPratilipi, aCallBack ) {

		var requests = [];
		requests.push( new request( "req1", PAGE_API, { "uri": pageUri } ) );
		requests.push( new request( "req2", PRATILIPI_API, { "pratilipiId": "$req1.primaryContentId" } ) );

		if( includeUserPratilipi )
			requests.push( new request( "req3", USER_PRATILIPI_API, { "pratilipiId": "$req1.primaryContentId" } ) );

		httpUtil.get( API_PREFIX, { "requests": processRequests( requests ) }, 
			function( response, status ) {
				if( aCallBack != null ) {
					var pratilipi = response.req2.status == 200 ? response.req2.response : null;
					var userpratilipi = includeUserPratilipi && response.req3.status == 200 ? response.req3.response : null; 
					aCallBack( pratilipi, userpratilipi );
				}
		});
	};

	this.getPratilipiById = function( pratilipiId, includeUserPratilipi, aCallBack ) {

		var requests = [];
		requests.push( new request( "req1", PRATILIPI_API, { "pratilipiId": pratilipiId } ) );

		if( includeUserPratilipi )
			requests.push( new request( "req2", USER_PRATILIPI_API, { "pratilipiId": pratilipiId } ) );

		httpUtil.get( API_PREFIX, { "requests": processRequests( requests ) }, 
			function( response, status ) {
				if( aCallBack != null ) {
					var pratilipi = response.req1.status == 200 ? response.req1.response : null;
					var userpratilipi = includeUserPratilipi && response.req2.status == 200 ? response.req2.response : null; 
					aCallBack( pratilipi, userpratilipi );
				}
		});
	};

	this.getAuthorByUri = function( pageUri, includeUserAuthor, aCallBack ) {

		var requests = [];
		requests.push( new request( "req1", PAGE_API, { "uri": pageUri } ) );
		requests.push( new request( "req2", AUTHOR_API, { "authorId": "$req1.primaryContentId" } ) );

		if( includeUserAuthor )
			requests.push( new request( "req3", USER_AUTHOR_FOLLOW_API, { "authorId": "$req1.primaryContentId" } ) );

		httpUtil.get( API_PREFIX, { "requests": processRequests( requests ) }, 
			function( response, status ) {
				if( aCallBack != null ) {
					var author = response.req2.status == 200 ? response.req2.response : null;
					var userauthor = includeUserAuthor && response.req3.status == 200 ? response.req3.response : null; 
					aCallBack( author, userauthor );
				}
		});
	};

	this.getAuthorById = function( authorId, includeUserAuthor, aCallBack ) {

		var requests = [];
		requests.push( new request( "req1", AUTHOR_API, { "authorId": authorId } ) );

		if( includeUserAuthor )
			requests.push( new request( "req2", USER_AUTHOR_FOLLOW_API, { "authorId": authorId } ) );

		httpUtil.get( API_PREFIX, { "requests": processRequests( requests ) }, 
			function( response, status ) {
				if( aCallBack != null ) {
					var author = response.req1.status == 200 ? response.req1.response : null;
					var userauthor = includeUserAuthor && response.req2.status == 200 ? response.req2.response : null; 
					aCallBack( author, userauthor );
				}
		});
	};

	this.getUser = function( aCallBack ) {
		httpUtil.get( API_PREFIX + USER_API, 
						null, 
						function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.getNotificationList = function( resultCount, aCallBack ) {
		httpUtil.get( API_PREFIX + NOTIFICATION_LIST_API, 
						resultCount == null ? null : { "resultCount": resultCount }, 
						function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.getNavigationList = function( aCallBack ) {
		httpUtil.get( API_PREFIX + NAVIGATION_LIST_API, 
						{ "language": "${ language }" }, 
						function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.getReviewList = function( pratilipiId, cursor, offset, resultCount, aCallBack ) {
		if( pratilipiId == null ) return;
		var params = { "pratilipiId": pratilipiId };
		if( cursor != null ) params[ "cursor" ] = cursor;
		if( offset != null ) params[ "offset" ] = offset;
		if( resultCount != null ) params[ "resultCount" ] = resultCount;
		httpUtil.get( API_PREFIX + USER_PRATILIPI_REVIEW_LIST_API, 
						params, 
						function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.getReviewCommentList = function( userPratilipiId, cursor, resultCount, aCallBack ) {
		if( userPratilipiId == null ) return;
		var params = { "parentType": "REVIEW", "parentId": userPratilipiId };
		if( cursor != null ) params[ "cursor" ] = cursor;
		if( resultCount != null ) params[ "resultcount" ] = resultcount;
		httpUtil.get( API_PREFIX + COMMENT_LIST_API, 
						params, 
						function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.createOrUpdateReview = function( pratilipiId, rating, review, successCallBack, errorCallBack ) {
		if( pratilipiId == null ) return;
		var params = { "pratilipiId": pratilipiId, "reviewState": "PUBLISHED" };
		if( rating != null ) params[ "rating" ] = rating;
		if( review != null ) params[ "review" ] = review;
		httpUtil.post( API_PREFIX + USER_PRATILIPI_REVIEW_API, 
				params, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	};

	this.createOrUpdateReviewComment = function( userPratilipiId, commentId, content, successCallBack, errorCallBack ) {
		if( userPratilipiId == null && commentId == null ) return;
		var params = { "state": "ACTIVE" };
		if( userPratilipiId != null ) params[ "parentId" ] = userPratilipiId;
		if( commentId != null ) params[ "commentId" ] = commentId;
		if( content != null ) params[ "content" ] = content;
		httpUtil.post( API_PREFIX + COMMENT_API, 
				params, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.followOrUnfollowAuthor = function( authorId, following, successCallBack, errorCallBack ) {
		if( authorId == null || following == null ) return;
		httpUtil.post( API_PREFIX + USER_AUTHOR_FOLLOW_API, 
				{ "authorId": authorId, "following": following }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	};
	
	this.addOrRemoveFromLibrary = function( pratilipiId, addedToLib, successCallBack, errorCallBack ) {
		if( pratilipiId == null || addedToLib == null ) return;
		httpUtil.post( API_PREFIX + USER_PRATILIPI_LIBRARY_API, 
				{ "pratilipiId": pratilipiId, "addedToLib": addedToLib }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	};

	this.likeOrDislikeReview = function( userPratilipiId, isLiked, successCallBack, errorCallBack ) {
		if( userPratilipiId == null || isLiked == null ) return;
		httpUtil.post( API_PREFIX + VOTE_API, 
				{ "parentId": userPratilipiId, "parentType": "REVIEW", "type": isLiked ? "LIKE" : "NONE" }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.likeOrDislikeComment = function( commentId, isLiked, successCallBack, errorCallBack ) {
		if( commentId == null || isLiked == null ) return;
		httpUtil.post( API_PREFIX + VOTE_API, 
				{ "parentId": commentId, "parentType": "COMMENT", "type": isLiked ? "LIKE" : "NONE" }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.deleteReview = function( pratilipiId, successCallBack, errorCallBack ) {
		if( pratilipiId == null ) return;
		httpUtil.post( API_PREFIX + USER_PRATILIPI_REVIEW_API, 
				{ "pratilipiId": pratilipiId, "reviewState": "DELETED" }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	}

	this.deleteComment = function( commentId, successCallBack, errorCallBack ) {
		if( commentId == null ) return;
		httpUtil.post( API_PREFIX + COMMENT_API, 
				{ "commentId": commentId, "state": "DELETED" }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.createOrUpdatePratilipi = function( pratilipi, successCallBack, errorCallBack ) {
		if( pratilipi == null ) return;
		httpUtil.post( API_PREFIX + PRATILIPI_API, 
				pratilipi, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	}

};

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
function convertDate( date ) {
	var d = new Date( date );
	function day(d) { return (d < 10) ? '0' + d : d; }
	function month(m) { var months = ['${ _strings.month_jan }','${ _strings.month_feb }','${ _strings.month_mar }',
									'${ _strings.month_apr }','${ _strings.month_may }','${ _strings.month_jun }',
									'${ _strings.month_jul }','${ _strings.month_aug }','${ _strings.month_sep }',
									'${ _strings.month_oct }','${ _strings.month_nov }','${ _strings.month_dec }']; 
						return months[m]; }
	return [ day( d.getDate() ), month( d.getMonth() ), d.getFullYear() ].join(' ');
}

function goToLoginPage() {
	window.location.href = "/login?retUrl=" + encodeURIComponent( window.location.pathname );
}

function sharePratilipiOnFacebook( url ) {
	window.open( "http://www.facebook.com/sharer.php?u=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnTwitter( url ) {
	window.open( "http://twitter.com/share?url=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnGplus( url ) {
	window.open( "https://plus.google.com/share?url=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnWhatsapp( text ) {
	window.open( "whatsapp://send?text=" + text );
}

function isEmpty( obj ) {
	for( var prop in obj ) {
		if( obj.hasOwnProperty( prop ) )
			return false;
	}
	return JSON.stringify( obj ) === JSON.stringify( {} );
}
