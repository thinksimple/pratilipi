function() {
    var self = this;
    this.reviewList = ko.observableArray( [] );
    this.pratilipiId = getQueryVariable( "id" );
    this.maxRating = 5;
    this.selectedReviewRating = ko.observable( 0 );
    this.newReviewContent = ko.observable( "" );
    this.isSaveInProgress = ko.observable( false );
    
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
    }
    this.addToReviewList = function( review ) {
        this.reviewList.unshift( review );
    }   

//    this.getUser = function() {
//      $.ajax({
//        type: 'get',
//        url: '<#if stage == "alpha">${ prefix }</#if>/api/user',
//        success: function( response ) {
//          <#if stage == "alpha" || stage == "gamma">
//              var res = response;
//          <#else>
//              var res = jQuery.parseJSON( response );
//          </#if>        
//          self.userIsGuest( res["isGuest"] );
//        },
//        error: function( response ) {
//            console.log( response );
//            console.log( typeof( response ) );
//        }
//      });      
//    };
    
    this.getReviewList = function() {
        $.ajax({
            type: 'get',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/userpratilipi/review/list?pratilipiId=' + self.pratilipiId + "&resultCount=3",
            success: function( response ) {
    //          var res = jQuery.parseJSON( response );
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
    
    var dialog = document.querySelector( 'dialog' );
    if ( !dialog.showModal ) {
        dialogPolyfill.registerDialog( dialog );
    }
    
    this.openReviewModal = function() {
        componentHandler.upgradeDom();
        dialog.showModal();
    };
   
    this.hideReviewModal = function() {
        dialog.close();
    };
    
    this.setSelectedReviewRating = function ( rating ) {
        this.selectedReviewRating( rating );
    };   
    
    this.wassup = function( rating1 ) {
        self.setSelectedReviewRating( rating1 );
    };
    
    this.openReviewModalWithRating = function( rating ) {
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
            url: '<#if stage == "alpha">${ prefix }</#if>/api/userpratilipi/review',
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
                /* hide add review?*/
//                self.hideReplyState();
            }
        });     
      
    };  
   
}