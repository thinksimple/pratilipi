function( params ) {
    var self = this;
    this.isGuest = params.isGuest;
    this.pratilipiId = getQueryVariable( "id" );
    this.maxRating = 5;    
    this.firstLoadReviewCount = 5;
    this.subsequentLoadReviewCount = 10;  
    
    this.reviewList = ko.observableArray( [] );
    this.selectedReviewRating = ko.observable( 0 );
    this.newReviewContent = ko.observable( "" );
    this.isSaveInProgress = ko.observable( false );
    this.totalReviewsPresent = ko.observable( 0 );

    this.totalReviewsShown = ko.computed(function() {
        return this.reviewList().length;
    }, this);
    
    this.areMoreReviews = ko.computed(function() {
        return this.totalReviewsShown() < this.totalReviewsPresent();
    }, this);
    
    this.isNewReviewRatingZero = function() {
      return this.selectedReviewRating() == 0;
    }    
    
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

//    this.getUser = function() {
//      $.ajax({
//        type: 'get',
//        url: '/api/user',
//        success: function( response ) {
//          var res = jQuery.parseJSON( response );
//          self.userIsGuest( res["isGuest"] );
//        },
//        error: function( response ) {
//            console.log( response );
//            console.log( typeof( response ) );
//        }
//      });      
//    };
    this.settotalReviewsPresent = function( count ) {
        this.totalReviewsPresent( count );
    };
    
    this.getReviewList = function() {
        $.ajax({
            type: 'get',
            url: '/api/userpratilipi/review/list',
            data: {
                pratilipiId: self.pratilipiId,
                resultCount: self.firstLoadReviewCount
            },
            success: function( response ) {
                var res = response;          
                self.pushToReviewList( res[ "reviewList" ] );
                self.settotalReviewsPresent( res["numberFound"] );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof( response ) );
            }
        });
    };
    
    this.loadMoreReviews = function() {
        $.ajax({
            type: 'get',
            url: '/api/userpratilipi/review/list',
            data: {
                pratilipiId: self.pratilipiId,
                resultCount: self.subsequentLoadReviewCount,
                cursor: self.totalReviewsShown
            },
            success: function( response ) {
                var res = response;          
                self.pushToReviewList( res[ "reviewList" ] );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof( response ) );
            }
        });
    };    
//    this.getUser();
    this.getReviewList();
    
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
    
    this.postNewReview = function() {
        this.sendReviewPostAjaxRequest();
    }
    
    this.sendReviewPostAjaxRequest = function() {
        this.isSaveInProgress( true );
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
   
}