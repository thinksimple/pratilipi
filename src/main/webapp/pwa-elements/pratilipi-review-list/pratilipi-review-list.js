function( params ) {
    var self = this;
    this.pratilipi = params.pratilipi;
    this.userPratilipi = params.userPratilipi;
    this.updatePratilipi = params.updatePratilipi;
    this.updateUserPratilipi = params.updateUserPratilipi;
    // console.log( this.userpratilipi, this.pratilipi );

    /* Constants */
    this.maxRating = 5;    
    this.firstLoadReviewCount = 5;
    this.subsequentLoadReviewCount = 10; 
    this.isReviewsLoaded = ko.observable( false );
    this.areMoreReviesLoading = ko.observable( false );


    this.reviewList = ko.observableArray( [] );

    this.dataAccessor = new DataAccessor();

    this.isSaveInProgress = ko.observable( false );
    this.totalReviewsPresent = ko.observable( 0 );
    this.selectedReviewRating = ko.observable( 0 ); /* init the initially until we get userpratiipi obj */
    this.newReviewContent = ko.observable( "" ); /* init the initially until we get userpratiipi obj */
    this.hasUserAlreadyPostedReview = ko.observable( false );
   

    
    this.isGuest = ko.computed( function() {
        return appViewModel.user.isGuest();
    }, this);

    this.totalReviewsShown = ko.computed(function() {
        return this.reviewList().length;
    }, this);

    this.areMoreReviews = ko.computed(function() {
        return this.totalReviewsShown() < this.totalReviewsPresent(); /* can change this to directly have reviewlist.length() */
    }, this);

    /* helper function */
    this.isNewReviewRatingZero = function() {
      return this.selectedReviewRating() == 0;
    }; 
    this.pushToReviewList = function( revList ) {
        for( var i=0; i< revList.length; i++ ) {
            this.reviewList.push( revList[i] );
        }
    };
    
    this.addToReviewList = function( review ) {
        if( this.hasUserAlreadyPostedReview() ) {
            this.deleteAlreadyPostedReview();
        }
        this.reviewList.unshift( review );
        this.settotalReviewsPresent( this.totalReviewsPresent() + 1 );
    }; 
    this.settotalReviewsPresent = function( count ) {
        this.totalReviewsPresent( count );
    };

    this.getReviewListCallback = function( response ) {
        if( response ) {
            this.pushToReviewList( response[ "reviewList" ] );
            this.settotalReviewsPresent( response["numberFound"] );            
        }
        this.isReviewsLoaded( true );

    };

    this.loadMoreReviewsCallback = function( response ) {
        if( response ) {
            this.pushToReviewList( response[ "reviewList" ] );           
        }
        this.areMoreReviesLoading( false );
    };
    
    this.getReviewList = function() {
        this.dataAccessor.getReviewList( this.pratilipiId, null, null, this.firstLoadReviewCount, this.getReviewListCallback.bind( this ) );
    };
    
    this.loadMoreReviews = function() {
        this.areMoreReviesLoading( true );
        this.dataAccessor.getReviewList( this.pratilipiId, this.totalReviewsShown(), null, this.subsequentLoadReviewCount, this.loadMoreReviewsCallback.bind( this ) );
    };  

    this.setSelectedReviewRating = function ( rating ) {
        this.selectedReviewRating( rating );
    };   
    
    this.changeReviewRating = function( rating1 ) {
        self.setSelectedReviewRating( rating1 );
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
        self.hasUserAlreadyPostedReview( true );
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
        this.dataAccessor.createOrUpdateReview( self.pratilipiId, self.selectedReviewRating(), self.newReviewContent(), this.postReviewSuccessCallback, this.postReviewErrorCallback );
    }
    
    this.sendReviewPostAjaxRequest = function() { /* delete this after checking */
        

        $.ajax({
            type: 'post',
            url: '/api/userpratilipi/review',
            data: {
                pratilipiId: self.pratilipiId,
                rating: self.selectedReviewRating(),
                review: self.newReviewContent()
            }, 
            success: function( response ) {
                var res = response;   
                self.addToReviewList( response );              
            },
            error: function( response ) {
            },
            complete: function() {
                self.isSaveInProgress( false );
                self.hideReviewModal();
                /* hide add review?*/
//                self.hideReplyState();
            }
        });     
      
    };           

    this.emptyReviewRating = ko.computed(function() {  /* TODO change its name to remptyReviewSTars*/
        return ( this.maxRating - this.selectedReviewRating() );
    }, this);
    
    this.isSaveDisabled = ko.computed( function() {
        return this.isNewReviewRatingZero() ||  this.isSaveInProgress();
    }, this );

    var dialog = $( '#pratilipi-review-dialog' );
    // if ( !dialog.showModal ) {
    //     dialogPolyfill.registerDialog( dialog );
    // }
    
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
        dialog.modal('hide');
    };    







    



/*    this.initializeReviewRating = function() {
        if( appViewModel.user.isGuest() ) {
            return 0;
        } else {
            if( this.userpratilipi.rating )
                return this.userpratilipi.rating();
            else 
                return 0;
        }
    };

    this.initializeReviewContent = function() {
        if( appViewModel.user.isGuest() ) {
            return "";
        } else {
            if( this.userpratilipi.review )
                return this.userpratilipi.review();
            else 
                return "";
        }
    };   */ 


    this.hasAccessToReview = ko.computed( function() { /* can be true if person has already reviewed */
        if( appViewModel.user.isGuest() )
            return  true;
        // else if( !isEmpty( this.userpratilipi ) ) /* if user is not guest, this will never be empty */
        else
            return this.userPratilipi.hasAccessToReview(); /* whenever userPratilipi has accessToReview Changes ths will change */
    }, this);

     
    this.pratilipiIdObserver = ko.computed( function() {
        console.log("inside reviewws");
        if( this.pratilipi.pratilipiId() ) {
          this.pratilipiId = this.pratilipi.pratilipiId();
          this.getReviewList();
        }
    }, this );

    this.userPratilipiRatingObserver = ko.computed( function() {
        if( this.userPratilipi.rating && this.userPratilipi.rating() ) { /* TODO CHnage this to 1 condn*/
            this.selectedReviewRating( this.userPratilipi.rating() );
        }
    }, this);

    this.userPratilipiReviewContentObserver = ko.computed( function() {
        if( this.userPratilipi.review && this.userPratilipi.review() ) {
            this.newReviewContent( this.userPratilipi.review() );
            this.hasUserAlreadyPostedReview( true );
        }
    }, this);      
     
     // if( appViewModel.user.isGuest() ) {
     //     this.hasAccessToReview( true );
     // } else if ( !isEmpty( this.userpratilipi ) ) {
     //      this.hasAccessToReview( this.userpratilipi.hasAccessToReview() );  
     // } 
    this.deleteReview = function( review ) {
        self.reviewList.remove( review );
        self.settotalReviewsPresent( self.totalReviewsPresent() - 1 );
    };   

    this.deleteAlreadyPostedReview = function() {
        self.reviewList.remove(  function(item) {
            return item.userPratilipiId == self.userPratilipi.userPratilipiId();           
        } ); 
        self.settotalReviewsPresent( self.totalReviewsPresent() - 1 );
    };
      
   
}