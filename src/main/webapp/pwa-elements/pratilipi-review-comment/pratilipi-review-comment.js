function( params ) {
    var self = this;
    var dataAccessor = new DataAccessor();
    this.reviewCommentObject = params.value;
    this.isGuest = ko.observable( appViewModel.user.isGuest() );    

    this.likeCount = ko.observable( this.reviewCommentObject.likeCount );
    this.isLiked = ko.observable( this.reviewCommentObject.isLiked );
    this.userImageUrl = this.reviewCommentObject.user.profileImageUrl + "&width=40";
    this.comment_date = convertDate( this.reviewCommentObject.creationDateMillis );
    this.userProfilePageUrl = this.reviewCommentObject.user.profilePageUrl;
    console.log( this.userImageUrl );
    
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
        if( this.isLiked() ) {
            this.isLiked( false );
            this.likeCount( this.likeCount() - 1 );
        } else {
            this.isLiked( true );
            this.likeCount( this.likeCount() + 1 );
        }
    }

    this.getLikeParam = function() {
        return this.isLiked() ? "LIKE" : "NONE";
    }

    this.likeSuccessCallback = function() {

    };

    this.likeErrorCallback = function() {
        self.updateLikeCount();
    };

    this.generateLikeAjaxRequest = function() {
        dataAccessor.likeOrDislikeComment( self.reviewCommentObject.commentId, this.getLikeParam(), this.likeSuccessCallback, this.likeErrorCallback );      
    };
       
}