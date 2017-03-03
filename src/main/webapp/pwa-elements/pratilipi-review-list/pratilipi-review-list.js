function( params ) {
    var self = this;
//    console.log( "params", params );
//    console.log( "pobj", params.pratilipiObj );
//    console.log( "upobj", params.userPratilipiObj );
    this.pratilipiObj = params.pratilipiObj;
    this.userPratilipiObj = params.userPratilipiObj;
    console.log( this.userPratilipiObj, this.pratilipiObj );
    
    this.hasAccessToReview = ko.computed( function() { /* test this for changes in review access */
        if( appViewModel.user.isGuest() )
            return  true;
        else if( !isEmpty( this.userPratilipiObj ) ) /* if user is not guest, this will never be empty */
            return this.userPratilipiObj.hasAccessToReview();
    }, this);
    
    this.isGuest = ko.observable( appViewModel.user.isGuest() );
    this.dataAccessor = new DataAccessor();
    // this.pratilipiId = "5093925535612928";
    this.maxRating = 5;    
    this.firstLoadReviewCount = 5;
    this.subsequentLoadReviewCount = 10;  
    
    this.reviewList = ko.observableArray( [] );

    this.isSaveInProgress = ko.observable( false );
    this.totalReviewsPresent = ko.observable( 0 );

    this.initializeReviewRating = function() {
        if( appViewModel.user.isGuest() ) {
            return 0;
        } else {
            return this.userPratilipiObj.rating();
        }
    };

    this.initializeReviewContent = function() {
        if( appViewModel.user.isGuest() ) {
            return "";
        } else {
            return this.userPratilipiObj.review();
        }
    };    

    this.selectedReviewRating = ko.observable( this.initializeReviewRating() );
    this.newReviewContent = ko.observable( this.initializeReviewContent() );

    this.totalReviewsShown = ko.computed(function() {
        return this.reviewList().length;
    }, this);
    
    this.areMoreReviews = ko.computed(function() {
        return this.totalReviewsShown() < this.totalReviewsPresent();
    }, this);
    
    this.isNewReviewRatingZero = function() {
      return this.selectedReviewRating() == 0;
    };   
    
    this.emptyReviewRating = ko.computed(function() {
        return ( this.maxRating - this.selectedReviewRating() );
    }, this);
    
    this.isSaveDisabled = ko.computed( function() {
        return this.isNewReviewRatingZero() ||  this.isSaveInProgress();
    }, this ); 
    

    
//    this.userIsGuest = ko.observable();
    this.pushToReviewList = function( revList ) {
        for( var i=0; i< revList.length; i++ ) {
            this.reviewList.push( revList[i] );
        }
    };
    
    this.addToReviewList = function( review ) {
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
    };

    this.loadMoreReviewsCallback = function( response ) {
        if( response ) {
            this.pushToReviewList( response[ "reviewList" ] );           
        }
    };
    
    this.getReviewList = function() {
        this.dataAccessor.getReviewList( this.pratilipiId, null, null, this.firstLoadReviewCount, this.getReviewListCallback.bind( this ) );
    };
    
    this.loadMoreReviews = function() {
        this.dataAccessor.getReviewList( this.pratilipiId, this.totalReviewsShown(), null, this.subsequentLoadReviewCount, this.loadMoreReviewsCallback.bind( this ) );
    };    
//    this.getUser();
    
    var dialog = document.querySelector( '#pratilipi-review-dialog' );
    if ( !dialog.showModal ) {
        dialogPolyfill.registerDialog( dialog );
    }
    
    this.openReviewModal = function() {        
        if( self.isGuest() ) {
            goToLoginPage();
            return;
        }
        else {
            componentHandler.upgradeDom();
            dialog.showModal();
        }
    };
   
    this.hideReviewModal = function() {
        dialog.close();
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
    
     if( !isEmpty( this.pratilipiObj ) ) {
        this.pratilipiId = this.pratilipiObj.pratilipiId();
        this.getReviewList();
     }  
     
     // if( appViewModel.user.isGuest() ) {
     //     this.hasAccessToReview( true );
     // } else if ( !isEmpty( this.userPratilipiObj ) ) {
     //      this.hasAccessToReview( this.userPratilipiObj.hasAccessToReview() );  
     // } 
     this.deleteReview = function( review ) {
         self.reviewList.remove( review );
         self.settotalReviewsPresent( self.totalReviewsPresent() - 1 );
     };     
      
   
}