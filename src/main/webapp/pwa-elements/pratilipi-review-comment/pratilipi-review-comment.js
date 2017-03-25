function( params ) {
    var self = this;
    this.dataAccessor = new DataAccessor();
    this.reviewCommentObject = ko.mapping.fromJS( params.value, {}, self.reviewCommentObject );
    this.isGuest = ko.observable( appViewModel.user.isGuest() ); 
    this.isEditModeOn = ko.observable( false );   

     this.likeCount = ko.observable( this.reviewCommentObject.likeCount );
    // this.isLiked = ko.observable( this.reviewCommentObject.isLiked );
    // this.userImageUrl = this.reviewCommentObject.user.profileImageUrl + "&width=40";
    // this.comment_date = convertDate( this.reviewCommentObject.creationDateMillis );
    // this.userProfilePageUrl = this.reviewCommentObject.user.profilePageUrl;
//    console.log( this.userImageUrl );
    
    this.likeDislikeReview = function( item ) {
        if( self.isGuest() ) {
            goToLoginPage();
        } else {
            this.updateLikeCount();
            this.generateLikeAjaxRequest();
        }
        /* increase or decrease like count and change boolean and also change the like button*/     
    }; 
    
    this.updateLikeCount = function() {
        if( this.reviewCommentObject.isLiked() ) {
            // this.isLiked( false );
            var updatedLikeCount = this.reviewCommentObject.likeCount() - 1;
            ko.mapping.fromJS( { isLiked: false, likeCount: updatedLikeCount }, {}, self.reviewCommentObject );
             this.likeCount( this.likeCount() - 1 );
        } else {
            var updatedLikeCount = this.reviewCommentObject.likeCount() + 1;
            ko.mapping.fromJS( { isLiked: true, likeCount: updatedLikeCount }, {}, self.reviewCommentObject );
        }
    };

    this.likeSuccessCallback = function() {

    };

    this.likeErrorCallback = function() {
        self.updateLikeCount();
    };

    this.generateLikeAjaxRequest = function() {
        this.dataAccessor.likeOrDislikeComment( self.reviewCommentObject.commentId(), this.reviewCommentObject.isLiked(), this.likeSuccessCallback, this.likeErrorCallback );      
    };
    
    this.deleteSuccessCallback = function() {
      params.deleteComment( params.value );
    };
    
    this.deleteErrorCallback = function() {
      //  params.deleteComment( params.value ); <#-- only  localhost -->
    };    
    
    this.deleteSelf = function() {
        this.dataAccessor.deleteComment( this.reviewCommentObject.commentId(), this.deleteSuccessCallback.bind( this ), this.deleteErrorCallback.bind( this ) )
    };    
    componentHandler.upgradeDom();  

    this.updateCommentObject = function( comment ) {
        ko.mapping.fromJS( comment, {}, self.reviewCommentObject );
    };

    this.editComment = function( comment ) {
        self.hideEditState();
        self.updateCommentObject( comment );
        //update comment object
    };

    this.showEditState = function() {
        this.isEditModeOn( true );
    };

    this.hideEditState = function() {
        this.isEditModeOn( false );
    };    
}