function( params ) {

	var self = this;
	var dataAccessor = new DataAccessor();

	/* Params */
	this.pratilipi = params.pratilipi;
	this.userPratilipi = params.userPratilipi;
	this.updatePratilipi = params.updatePratilipi;
	this.updateUserPratilipi = params.updateUserPratilipi;

	/* Constants */
	var firstLoadReviewCount = 5;
	var subsequentLoadReviewCount = 10;

	/* ReviewList and LoadingState */
	this.reviewList = ko.observableArray();
	this.totalReviewCount = ko.observable();
	var cursor = null;

	/*
	 * 4 Possible values for 'loadingState'
	 * LOADING
	 * LOADED_EMPTY
	 * LOADED
	 * FAILED
	 * 
	 * */
	this.loadingState = ko.observable();

	/* Booleans */
	this.addOrDeleteReviewRequestOnFlight = ko.observable( false );

	/* User Inputs */
	this.ratingInput = ko.observable();
	this.reviewInput = ko.observable();


	/* Functions */

	/* Review List */
	this.addToReviewList = function( review ) {
		self.reviewList.unshift( review );
		self.totalReviewCount( self.totalReviewCount() + 1 );
	};

	this.deleteFromReviewList = function( review ) {
		self.reviewList.remove( function( item ) {
			return item.userPratilipiId == self.userPratilipi.userPratilipiId();		   
		});
		self.totalReviewCount( self.totalReviewCount() - 1 );
	};

	this.pushToReviewList = function( revList ) {
		for( var i = 0; i < revList.length; i++ )
			self.reviewList.push( ko.mapping.fromJS( revList[i] ) );
	};

	this.fetchReviewList = function( firstLoad ) {
		if( self.loadingState() == "LOADING" ) return;
		self.loadingState( "LOADING" );
		if( firstLoad && self.reviewList().length > 0 )
			self.reviewList( [] );
		dataAccessor.getReviewList( self.pratilipi.pratilipiId(), cursor, null, firstLoad ? firstLoadReviewCount : subsequentLoadReviewCount,
				function( reviewListResponse ) {
					if( reviewListResponse == null ) {
						self.loadingState( "FAILED" );
						return;
					}
					var reviewList = reviewListResponse[ "reviewList" ];
					cursor = reviewListResponse[ "cursor" ];
					self.pushToReviewList( reviewList );
					self.loadingState( self.reviewList().length > 0 ? "LOADED" : "LOADED_EMPTY" );
					self.totalReviewCount( reviewListResponse[ "numberFound" ] );
		});
	};


	/* Review Modal */
	var reviewInputDialog = $( '#pratilipi-review-input-dialog' );
	this.openReviewModal = function() {		
		if( appViewModel.user.isGuest() ) {
			goToLoginPage( { "action": "openReviewModal", "ratingInput": self.ratingInput() } );
			return;
		}
		reviewInputDialog.modal( 'show' );
	};

	this.closeReviewModal = function() {
		reviewInputDialog.modal( 'hide' );
		self.ratingInput( self.userPratilipi.rating() != null ? self.userPratilipi.rating() : 0 );
		self.reviewInput( self.userPratilipi.state == "PUBLISHED" && self.userPratilipi.review() != null ? self.userPratilipi.review() : null );
	};

	/* Clicking anywhere outside the screen */
	reviewInputDialog.on( 'hidden.bs.modal', function(e) {
		self.closeReviewModal();
	});


	/* Update Pratilipi and UserPratilipi Objects */
	this.updatePratilipiObject = function( userPratilipi ) {
		/* check if its a new Review or updated review, and calculate average rating accordingly and update values.*/
		var pratilipi = {};

		var oldRating = self.userPratilipi.rating() != null ? self.userPratilipi.rating() : 0;
		var newRating = userPratilipi.rating;
		var ratingCount = self.pratilipi.ratingCount();
		var totalRating = self.pratilipi.averageRating() * self.pratilipi.ratingCount();

		if( self.userPratilipi.rating() == null ) { /* Added a review */
			pratilipi[ "ratingCount" ] = ++ratingCount;
		}

		pratilipi[ "averageRating" ] = ( totalRating - oldRating + newRating ) / ratingCount;
		self.updatePratilipi( updatedPratilipiObject );
	};

	this.updateUserPratilipiObject = function( review ) {
		var userPratilipi = {};
		userPratilipi[ "rating" ] = review.rating;
		userPratilipi[ "review" ] = review.state == "PUBLISHED" && review.review != null ? review.review : null;
		self.updateUserPratilipi( userPratilipi );
	};

	/* Add Review */
	this.addReview = function() {
		if( self.addOrDeleteReviewRequestOnFlight() ) return;
		self.addOrDeleteReviewRequestOnFlight( true );
		dataAccessor.createOrUpdateReview( self.pratilipi.pratilipiId(), self.ratingInput(), self.reviewInput(), 
			function( review ) {
				self.addToReviewList( review );
				/* Update Pratilipi Object first and then userPratilipi Object
				 * We need old rating to update in Pratilipi object
				 *  */
				self.updatePratilipiObject( review );
				self.updateUserPratilipiObject( review );
				self.addOrDeleteReviewRequestOnFlight( false );
				self.hideReviewModal();
				ToastUtil.toast( "${ _strings.updated_review }" );
			}, function( error ) {
				self.addOrDeleteReviewRequestOnFlight( false );
				ToastUtil.toast( "${ _strings.server_error_message }" );
		});
	};


	/* Delete Review */
	this.deleteReview = function( review ) {
		if( self.addOrDeleteReviewRequestOnFlight() ) return;
		self.addOrDeleteReviewRequestOnFlight( true );
		dataAccessor.deleteReview( self.pratilipi.pratilipiId(), 
			function( review ) {
				self.deleteFromReviewList( review );
				self.updateUserPratilipiObject( review );
				self.addOrDeleteReviewRequestOnFlight( false );
			}, function( error ) {
				self.addOrDeleteReviewRequestOnFlight( false );
				ToastUtil.toast( "${ _strings.server_error_message }" );
		} );
	};   

	/* Computed Observables */
	this.hasMoreReviews = ko.computed( function() {
		return self.reviewList().length < self.totalReviewCount();
	}, this );

	this.hasAccessToReview = ko.computed( function() {
		return appViewModel.user.isGuest() || self.userPratilipi.hasAccessToReview();  
	}, this );

	this.canAddReview = ko.computed( function() {
		return self.ratingInput() != 0 && ! self.addOrDeleteReviewRequestOnFlight();
	}, this );

	this.pratilipiIdObserver = ko.computed( function() {
		if( self.pratilipi.pratilipiId() == null ) return;
			self.fetchReviewList( true );
	}, this );

	this.userPratilipiMetaObserver = ko.computed( function() {
		self.ratingInput( self.userPratilipi.rating() != null ? self.userPratilipi.rating() : 0 );
		self.reviewInput( self.userPratilipi.state == "PUBLISHED" && self.userPratilipi.review() != null ? self.userPratilipi.review() : null );
	}, this );

	this.ratingInputObserver = ko.computed( function() {
		if( self.ratingInput() == 0 ) return;
		self.openReviewModal();
	}, this );

	this.userObserver = ko.computed( function() {
		if( ! appViewModel.user.isGuest() && getUrlParameter( 'action' ) == "openReviewModal" ) {
			if( getUrlParameter( "ratingInput" ) != null && getUrlParameter( "ratingInput" ) > 0 ) {
				self.ratingInput( parseInt( getUrlParameter( "ratingInput" ) ) );
				/* This will inturn open the modal */
			} else {
				self.openReviewModal();
			}
		}
	}, this );

}