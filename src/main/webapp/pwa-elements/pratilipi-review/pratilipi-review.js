function( params ) {
	var self = this;
	var dataAccessor = new DataAccessor();

	this.review = params.review;
	this.likeCount = ko.observable( this.review.likeCount() );
	this.commentCount = ko.observable( this.review.commentCount() );
	this.isLiked = ko.observable( this.review.isLiked() );

	this.maxRating = 5;
	this.filledStars = this.review.rating != null ? this.review.rating() : 0;
	this.emptyStars = this.maxRating - this.filledStars;

	this.userImageUrl = getImageUrl( this.review.userImageUrl(), 48 );
	this.reviewDate = convertDate( this.review.reviewDateMillis() );
	this.isCommentsShown = ko.observable( false );
	this.isReplyStateOn = ko.observable( false );

	this.isSubreviewListVisible = ko.computed( function() {
		return ( this.isCommentsShown() && this.comments().length ) || this.isReplyStateOn(); /* test */
	}, this );

	this.showRepliesText = ko.computed( function() {
		return this.isCommentsShown() ? "${ _strings.hide_comments }" : "${ _strings.review_see_all_reviews }";
	}, this );

	this.comments = ko.observableArray([]);

	this.isCommentsLoaded = ko.computed( function() {
		return this.comments().length ? true : false;
	}, this );


	this.likeDislikeReview = function() {
		if( appViewModel.user.isGuest() ) {
			goToLoginPage();
			return;
		}
	  /* increase or decrease like count and change boolean and also change the like button*/
		this.updateLikeCount();
		this.generateLikeAjaxRequest();
	};

	this.addComment = function( comment ) {
		this.generatePostCommentAjaxRequest( comment );
	};

	this.showReplyState = function( item ) {
		if( appViewModel.user.isGuest() ) {
			goToLoginPage();
			return;
		}
	   else {
		   this.isReplyStateOn( true );
		   componentHandler.upgradeDom();
	   }
	};

	this.hideReplyState = function() {
	  this.isReplyStateOn( false );
	}

	this.updateLikeCount = function() {
		if( this.isLiked() ) {
			this.isLiked( false );
			this.likeCount( this.likeCount() - 1 );
		} else {
			this.isLiked( true );
			this.likeCount( this.likeCount() + 1 );
		}
	}

	this.toggleShowRepliesState = function() {
		this.isCommentsShown( !this.isCommentsShown() );
		if( this.isCommentsLoaded() == false && this.isCommentsShown() ) {
		  this.generateGetCommentsAjaxRequest();
		}
	};

	this.getLikeParam = function() {
		return this.isLiked() ? "LIKE" : "NONE";
	}

	this.likeSuccessCallback = function() {

	};

	this.likeErrorCallback = function() {
		self.updateLikeCount();
	};

	this.generateLikeAjaxRequest = function() {
		dataAccessor.likeOrDislikeReview( self.review.userPratilipiId(), this.isLiked(), this.likeSuccessCallback, this.likeErrorCallback );	
	}

	this.getCommentsCallback = function( response ) {
		if( response ) {
			this.populateCommentsList( response["commentList"] );
		}
	};

	this.generateGetCommentsAjaxRequest = function() {
		dataAccessor.getReviewCommentList( this.review.userPratilipiId(), null, null, this.getCommentsCallback.bind( this ) );			
	};

	this.postCommentSuccessCallback = function( response ) {
		comment.reply("");
		self.addToCommentsList( response );
	}; 

	this.generatePostCommentAjaxRequest = function( comment ) {
	  comment.saveInProgress( true );
	  $.ajax({
		  type: 'post',
		  url: '/api/comment',
		  data: {
			  parentType: "REVIEW",
			  parentId: self.review.userPratilipiId(),
			  content: comment.reply()
		  }, 
		  success: function( response ) {
			  var res = response;   
			  comment.reply("");
			  self.addToCommentsList( response );

		  },
		  error: function( response ) {
		  },
		  complete: function() {
			  comment.saveInProgress( false );
			  self.hideReplyState();
			  self.isCommentsShown( true );
		  }
	  });
   };	
	
	this.populateCommentsList = function( commentList ) {
		for(var i = 0; i < commentList.length; i++) {
			this.comments.push( commentList[i] );
		}
	}; 
	
	this.addToCommentsList = function( comment ) {
		this.comments.push( comment );
	};
	
	this.deleteSuccessCallback = function() {
		params.deleteReview( params.value );
	};
	
	this.deleteErrorCallback = function() {
		//params.deleteReview( params.value ); <#-- only  localhost -->
	};
	
	this.deleteSelf = function() {
		dataAccessor.deleteReview( params.pratilipiId, this.deleteSuccessCallback.bind( this ), this.deleteErrorCallback.bind( this ) )
	};
	
	this.deleteComment = function( review ) {
	  self.comments.remove( review );
	  self.commentCount( self.commentCount() - 1 )
	};

	componentHandler.upgradeDom();

}