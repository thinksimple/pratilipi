/* DataAccessor */
var DataAccessor = function() {

	var httpUtil = new HttpUtil();

	var API_PREFIX = "/api";

	var PAGE_API = "/page";
	var PRATILIPI_API = "/pratilipi?_apiVer=2";
	var PRATILIPI_LIST_API = "/pratilipi/list?_apiVer=2";
	var USER_PRATILIPI_API = "/userpratilipi";
	var USER_PRATILIPI_LIBRARY_LIST_API = "/userpratilipi/library/list";
	var AUTHOR_API = "/author";
	var USER_AUTHOR_FOLLOW_API = "/userauthor/follow?_apiVer=2";
	var USER_API = "/user";
	var USER_LOGIN_API = "/user/login";
	var USER_LOGIN_FACEBOOK_API = "/user/login/facebook";
	var USER_LOGIN_GOOGLE_API = "/user/login/google";
	var USER_REGISTER_API = "/user/register";
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

	this.getNotificationList = function( cursor, resultCount, aCallBack ) {
		var params = {};
		if( cursor != null ) params[ "cursor" ] = cursor;
		if( resultCount != null ) params[ "resultCount" ] = resultCount;
		httpUtil.get( API_PREFIX + NOTIFICATION_LIST_API, 
						params, 
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
	
	this.getPratilipiListByListName = function( listName, cursor, offset, resultCount, aCallBack ) {
		if( listName == null ) return;
		var params = { "listName": listName, "state": "PUBLISHED", "language": "${ language }" };
		if( cursor != null ) params[ "cursor" ] = cursor;
		if( offset != null ) params[ "offset" ] = offset;
		if( resultCount != null ) params[ "resultCount" ] = resultCount;
		httpUtil.get( API_PREFIX + PRATILIPI_LIST_API, 
				params, 
				function( response, status ) { processGetResponse( response, status, aCallBack ) } );
	};

	this.getUserLibraryList = function( cursor, resultCount, aCallBack ) {
		var params = {};
		if( cursor != null ) params[ "cursor" ] = cursor;
		if( resultCount != null ) params[ "resultCount" ] = resultCount;
		httpUtil.get( API_PREFIX + USER_PRATILIPI_LIBRARY_LIST_API, 
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
				{ "authorId": authorId, "state": following ? "FOLLOWING" : "UNFOLLOWED" }, 
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
	},

	this.deleteComment = function( commentId, successCallBack, errorCallBack ) {
		if( commentId == null ) return;
		httpUtil.post( API_PREFIX + COMMENT_API, 
				{ "commentId": commentId, "state": "DELETED" }, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.createOrUpdatePratilipi = function( pratilipi, successCallBack, errorCallBack ) {
		if( pratilipi == null ) return;
		if( pratilipi[ "pratilipiId" ] == null ) pratilipi[ "oldContent" ] = false;
		httpUtil.post( API_PREFIX + PRATILIPI_API, 
				pratilipi, 
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.loginUser = function( email, password, successCallBack, errorCallBack ) {
		if( email == null || password == null ) return;
		httpUtil.post( API_PREFIX + USER_LOGIN_API, 
				{ "email": email, "password": password },
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.loginGoogleUser = function( googleIdToken, successCallBack, errorCallBack ) {
		if( googleIdToken == null ) return;
		httpUtil.post( API_PREFIX + USER_LOGIN_GOOGLE_API, 
				{ "googleIdToken": googleIdToken },
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.loginFacebookUser = function( fbUserAccessToken, successCallBack, errorCallBack ) {
		if( fbUserAccessToken == null ) return;
		httpUtil.post( API_PREFIX + USER_LOGIN_FACEBOOK_API, 
				{ "fbUserAccessToken": fbUserAccessToken },
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	},

	this.registerUser = function( name, email, password, successCallBack, errorCallBack ) {
		if( name == null || email == null || password == null ) return;
		httpUtil.post( API_PREFIX + USER_REGISTER_API, 
				{ "name": name, "email": email, "password": password },
				function( response, status ) { processPostResponse( response, status, successCallBack, errorCallBack ) } );
	}

};
