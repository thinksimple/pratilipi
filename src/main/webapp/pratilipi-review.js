function( params ) {
    var self = this;
    this.reviewObject = params.value;
    console.log( this.reviewObject );
    this.likeCount = ko.observable( this.reviewObject.likeCount );
    this.commentCount = ko.observable( this.reviewObject.commentCount );
    this.isLiked = ko.observable( false );
    this.maxRating = 5;
    this.filledStars = this.reviewObject.rating;
    this.emptyStars = this.maxRating - this.filledStars;
    this.userImageUrl = this.reviewObject.userImageUrl + "&width=40";
    
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
        debugger
        if( this.isLiked() ) {
            debugger
            this.isLiked( false );
            this.likeCount( this.likeCount() - 1 );
        } else {
            debugger
            this.isLiked( true );
            this.likeCount( this.likeCount() + 1 );
        }
    }

    this.generateLikeAjaxRequest = function() {
        $.ajax({
            type: 'post',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/vote',
            data: {
                parentType: "REVIEW",
                parentId: self.reviewObject.userPratilipiId,
                type: self.isLiked ? "LIKE" : "NONE"
            }, 
            success: function( response ) {
        //      var res = jQuery.parseJSON( response );
                <#if stage == "alpha">
                    var res = response;
                <#else>
                    var res = jQuery.parseJSON( response );
                </#if>           
                console.log(res);
            },
            error: function( response ) {
                /* revert changes */
                self.updateLikeCount();
            }
        });      
    };    
}