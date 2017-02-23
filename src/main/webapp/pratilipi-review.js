function( params ) {
    var self = this;
    this.reviewObject = params.value;
    this.isGuest = params.isGuest;
    console.log( this.reviewObject );
    this.likeCount = ko.observable( this.reviewObject.likeCount );
    this.commentCount = ko.observable( this.reviewObject.commentCount );
    this.isLiked = ko.observable( false );
    this.maxRating = 5;
    this.filledStars = this.reviewObject.rating;
    this.emptyStars = this.maxRating - this.filledStars;
    this.userImageUrl = this.reviewObject.userImageUrl + "&width=40";
    this.review_date = convertDate( this.reviewObject.reviewDateMillis );
    this.isCommentsShown = ko.observable( false );
    this.isReplyStateOn = ko.observable( false );
    
    this.isSubreviewListVisible = ko.computed( function() {
        return this.isCommentsShown() || this.isReplyStateOn();
    }, this );
    
    this.showRepliesText = ko.computed( function() {
        return this.isCommentsShown() ? "Hide all replies" : "Show all replies";
    }, this );
    
    this.comments = ko.observableArray([]);
    
    this.isCommentsLoaded = ko.computed( function() {
        return this.comments().length ? true : false;
    }, this );
    
    
    this.likeDislikeReview = function( item ) {
        if( self.isGuest() ) {
            goToLoginPage();
        }
        else {
          /* increase or decrease like count and change boolean and also change the like button*/
            this.updateLikeCount();
            this.generateLikeAjaxRequest();
        }
//        console.log( item );      
    }; 
    
    this.addComment = function( comment ) {
        this.generatePostCommentAjaxRequest( comment );
    }
    
    this.showReplyState = function( item ) {
       if( self.isGuest() ) {
           goToLoginPage();
       }
       else {
           this.isReplyStateOn( true );
           componentHandler.upgradeDom();
       }       
    }; 
    
    this.hideReplyState = function() {
      this.isReplyStateOn( false );
    }
    
    this.updateLikeCount = function() {
        if( this.isLiked() ) {
            this.isLiked( false );
            this.likeCount( this.likeCount() - 1 );
        } else {
            this.isLiked( true );
            this.likeCount( this.likeCount() + 1 );
        }
    }
    
    this.toggleShowRepliesState = function() {
        this.isCommentsShown( !this.isCommentsShown() );
        if( this.isCommentsLoaded() == false && this.isCommentsShown() ) {
          this.generateGetCommentsAjaxRequest();
        }
    };

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
    
    this.generateGetCommentsAjaxRequest = function() {
        $.ajax({
            type: 'get',
            url: '<#if stage == "alpha">${ prefix }</#if>/api/comment/list',
            data: {
                parentType: "REVIEW",
                parentId: self.reviewObject.userPratilipiId,
            }, 
            success: function( response ) {
        //      var res = jQuery.parseJSON( response );
                var res = response;        
                self.populateCommentsList( response["commentList"] );
            },
            error: function( response ) {
                /* revert changes */
//                self.updateLikeCount();
            }
        });             
    }  
    
    this.generatePostCommentAjaxRequest = function( comment ) {
      comment.saveInProgress( true );
      $.ajax({
          type: 'post',
          url: '<#if stage == "alpha">${ prefix }</#if>/api/comment',
          data: {
              parentType: "REVIEW",
              parentId: self.reviewObject.userPratilipiId,
              content: comment.reply()
          }, 
          success: function( response ) {
              var res = response;   
              comment.reply("");
              self.addToCommentsList( response );              
          },
          error: function( response ) {
          },
          complete: function() {
              comment.saveInProgress( false );
              self.hideReplyState();
          }
      });             
   }    
    
    this.populateCommentsList = function( commentList ) {
        for(var i = 0; i < commentList.length; i++) {
            this.comments.push( commentList[i] );
        }        
    }  
    
    this.addToCommentsList = function( comment ) {
        this.comments.push( comment );
    } 
    
    componentHandler.upgradeDom();
}