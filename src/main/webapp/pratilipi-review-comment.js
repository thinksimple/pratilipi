function( params ) {
    var self = this;
    this.reviewCommentObject = params.value;

    this.likeCount = ko.observable( this.reviewCommentObject.likeCount );
    this.isLiked = ko.observable( this.reviewCommentObject.isLiked );
    this.userImageUrl = this.reviewCommentObject.user.profileImageUrl + "&width=40";
    this.userProfilePageUrl = this.reviewCommentObject.user.profilePageUrl;
    console.log( this.userImageUrl );
    
    this.likeDislikeReview = function( item ) {
//        if( self.userIsGuest() ) {
//            /* change url to login page */
//        }
//        else {
          /* increase or decrease like count and change boolean and also change the like button*/
        this.updateLikeCount();
        this.generateLikeAjaxRequest();
//        }
//        console.log( item );      
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

    this.generateLikeAjaxRequest = function() {
        $.ajax({
            type: 'post',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/vote',
            data: {
                parentType: "COMMENT",
                parentId: self.reviewCommentObject.commentId,
                type: self.isLiked ? "LIKE" : "NONE"
            },
            success: function( response ) {
        //      var res = jQuery.parseJSON( response );
                var res = response;          
                console.log(res);
            },
            error: function( response ) {
                /* revert changes */
                self.updateLikeCount();
            }
        });      
    };
       
}