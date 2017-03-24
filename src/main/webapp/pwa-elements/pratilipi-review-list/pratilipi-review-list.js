function( params ) {

	var self = this;
	var dataAccessor = new DataAccessor();

	/* Constants */
	var maxRating = 5;	
	var firstLoadReviewCount = 5;
	var subsequentLoadReviewCount = 10;

	/* Variables */
	this.pratilipi = params.pratilipi;
	this.userPratilipi = params.userPratilipi;
	this.updatePratilipi = params.updatePratilipi;
	this.updateUserPratilipi = params.updateUserPratilipi;

	this.reviewList = ko.observableArray( [] );

	this.isReviewsLoaded = ko.observable( false );
	this.areMoreReviewsLoading = ko.observable( false );
	this.isSaveInProgress = ko.observable( false );
	this.totalReviewsPresent = ko.observable( 0 );
	this.selectedReviewRating = ko.observable( 0 ); /* init the initially until we get userpratiipi object */
	this.initialReviewRating = ko.observable( 0 );
	this.newReviewContent = ko.observable( "" ); /* init the initially until we get userpratiipi object */
	this.hasUserAlreadyPostedReview = ko.observable( false );


	/* helper functions */
	this.areMoreReviews = ko.computed( function() {
		return this.reviewList().length < this.totalReviewsPresent();
	}, this);

	this.isNewReviewRatingZero = function() {
	  return this.selectedReviewRating() == 0;
	}; 

	this.pushToReviewList = function( revList ) {
		for( var i = 0; i < revList.length; i++ ) {
			this.reviewList.push( revList[i] );
		}
	};

	this.addToReviewList = function( review ) {
		if( this.hasUserAlreadyPostedReview() ) {
			this.deleteAlreadyPostedReview();
		}
		this.reviewList.unshift( review );
		this.setTotalReviewsPresent( this.totalReviewsPresent() + 1 );
	}; 

	this.setTotalReviewsPresent = function( count ) {
		this.totalReviewsPresent( count );
	};

	this.updatePratilipiObject = function( review ) {
	  /* check if its a new Review or updated review, and calculate average rating accordingly and send updated values.*/
		var updatedPratilipiObject = {};
		var newRating;
		if( this.hasUserAlreadyPostedReview() ) { /* updated review*/
			newRating = ( ( this.pratilipi.averageRating() * this.pratilipi.ratingCount() ) - this.initialReviewRating() + this.selectedReviewRating() ) / this.pratilipi.ratingCount();
		} else {
			newRating = ( ( this.pratilipi.averageRating() * this.pratilipi.ratingCount() ) + this.selectedReviewRating() ) / ( this.pratilipi.ratingCount() + 1 );
			updatedPratilipiObject[ "ratingCount" ] = this.pratilipi.ratingCount() + 1;
		}
		updatedPratilipiObject[ "averageRating" ] = newRating;
		this.updatePratilipi( updatedPratilipiObject );
	};

	this.updateUserPratilipiObject = function( review ) {
		var updatedUserPratilipiObject = {};
		updatedUserPratilipiObject[ "rating" ] = review.rating;
		updatedUserPratilipiObject[ "review" ] = review.review ? review.review.trim() : "";
		this.updateUserPratilipi( updatedUserPratilipiObject );
	};

	this.getReviewListCallback = function( response ) {
		if( response ) {
			this.pushToReviewList( response[ "reviewList" ] );
			this.setTotalReviewsPresent( response[ "numberFound" ] );			
		}
		this.isReviewsLoaded( true );
	};

	this.loadMoreReviewsCallback = function( response ) {
		if( response ) {
			this.pushToReviewList( response[ "reviewList" ] );		   
		}
		this.areMoreReviewsLoading( false );
	};

	this.getReviewList = function() {
		dataAccessor.getReviewList( this.pratilipiId, null, null, firstLoadReviewCount, this.getReviewListCallback.bind( this ) );
	};

	this.loadMoreReviews = function() {
		this.areMoreReviewsLoading( true );
		dataAccessor.getReviewList( this.pratilipiId, this.reviewList().length, null, subsequentLoadReviewCount, this.loadMoreReviewsCallback.bind( this ) );
	};  

	this.setSelectedReviewRating = function ( rating ) {
		this.selectedReviewRating( rating );
	};   
	
	this.changeReviewRating = function( rating1 ) {
		self.setSelectedReviewRating( rating1 );
	};

	this.restoreRating = function() {
		self.setSelectedReviewRating( self.initialReviewRating() );
	};
	
	this.openReviewModalWithRating = function( rating ) {
		if( self.isGuest() ) {
			goToLoginPage();
			return;
		}	  
		self.setSelectedReviewRating( rating );
		self.openReviewModal();
	};

	this.postReviewSuccessCallback = function( response ) {
		self.addToReviewList( response ); 
		self.updatePratilipiObject( response );
		self.updateUserPratilipiObject( response );
		/* self.hasUserAlreadyPostedReview( true ); */
		self.postReviewCompleteCallback();
	};

	this.postReviewErrorCallback = function() {
		self.postReviewCompleteCallback();
	};

	this.postReviewCompleteCallback = function() {
		self.isSaveInProgress( false );
		self.hideReviewModal();
	};		
	
	this.postNewReview = function() {
		this.isSaveInProgress( true );
		dataAccessor.createOrUpdateReview( self.pratilipiId, self.selectedReviewRating(), self.newReviewContent(), this.postReviewSuccessCallback, this.postReviewErrorCallback );
	};

	this.emptyReviewRating = ko.computed(function() {  /* TODO change its name to remptyReviewSTars*/
		return ( maxRating - this.selectedReviewRating() );
	}, this);
	
	this.isSaveDisabled = ko.computed( function() {
		return this.isNewReviewRatingZero() ||  this.isSaveInProgress();
	}, this );

	var dialog = $( '#pratilipi-review-dialog' );

	this.openReviewModal = function() {		
		if( self.isGuest() ) {
			goToLoginPage();
			return;
		}
		else {
			componentHandler.upgradeDom();
			dialog.modal('show');
		}
	};

	this.hideReviewModal = function() {
		dialog.modal( 'hide' );
	};	

	this.hasAccessToReview = ko.computed( function() { /* can be true if person has already reviewed */
		if( appViewModel.user.isGuest() )
			return  true;
		/* else if( !isEmpty( this.userpratilipi ) ) /* if user is not guest, this will never be empty */
		else
			return this.userPratilipi.hasAccessToReview(); /* whenever userPratilipi has accessToReview Changes this will change */
	}, this);

	 
	this.pratilipiIdObserver = ko.computed( function() {
		if( this.pratilipi.pratilipiId() ) {
		  this.pratilipiId = this.pratilipi.pratilipiId();
		  this.getReviewList();
		}
	}, this );

	this.userPratilipiRatingObserver = ko.computed( function() {
		if( this.userPratilipi.rating && this.userPratilipi.rating() ) { /* TODO CHnage this to 1 condition */
			this.selectedReviewRating( this.userPratilipi.rating() );
			this.initialReviewRating( this.userPratilipi.rating() );
		}
	}, this);

	this.userPratilipiReviewContentObserver = ko.computed( function() {
		if( this.userPratilipi.review && this.userPratilipi.review() ) {
			this.newReviewContent( this.userPratilipi.review() );
			this.hasUserAlreadyPostedReview( true );
		}
	}, this);	  

	this.deleteReview = function( review ) {
		self.reviewList.remove( review );
		self.setTotalReviewsPresent( self.totalReviewsPresent() - 1 );
	};   

	this.deleteAlreadyPostedReview = function() {
		self.reviewList.remove(  function(item) {
			return item.userPratilipiId == self.userPratilipi.userPratilipiId();		   
		} ); 
		self.setTotalReviewsPresent( self.totalReviewsPresent() - 1 );
	};

}