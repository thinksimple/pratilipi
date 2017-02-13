function() {
    this.reviewList = ko.observableArray([]);
    this.pratilipiId = getQueryVariable( "id" );
    this.userIsGuest = ko.observable();
    this.pushToReviewList = function( revList ) {
      for( var i=0; i< revList.length; i++ ) {
        this.reviewList.push( revList[i] );
      }
    }
    var self = this;
    
    this.getUser = function() {
      $.ajax({
        type: 'get',
        url: '<#if stage == "alpha">${ prefix }</#if>/api/user',
        success: function( response ) {
          <#if stage == "alpha" || stage == "gamma">
              var res = response;
          <#else>
              var res = jQuery.parseJSON( response );
          </#if>        
          self.userIsGuest( res["isGuest"] );
        },
        error: function( response ) {
            console.log( response );
            console.log( typeof( response ) );
        }
      });      
    };
    
    this.getReviewList = function() {

      $.ajax({
        type: 'get',
        url: '<#if stage == "alpha">${ prefix }</#if>/api/userpratilipi/review/list?pratilipiId=' + self.pratilipiId + "&resultCount=3",
        success: function( response ) {
//          var res = jQuery.parseJSON( response );
              var res = response;          
          self.pushToReviewList( res["reviewList"] );
        },
        error: function( response ) {
            console.log( response );
            console.log( typeof( response ) );
        }
      });
    };
    
    this.likeDislikeReview = function( item ) {
//        console.log( this.userIsGuest() );
        if( self.userIsGuest() ) {
            /* change url to login page */
        }
        else {
          /* increase or decrease like count and change boolean and also change the like button*/
          if( item.isLiked ) {
              item.isLiked = false;
              item.likeCount--;
          } else {
              item.isLiked = true;
              item.likeCount++;
          }
          self.generateLikeAjaxRequest( item );
        }
        console.log( item );      
    };
    
    this.generateLikeAjaxRequest = function( item ) {
      $.ajax({
        type: 'post',
        url: '<#if stage == "alpha">${ prefix }</#if>/api/vote',
        data: {
          parentType: "REVIEW",
          parentId: item.userPratilipiId,
          type: item.isLiked ? "LIKE" : "NONE"
        }, 
        success: function( response ) {
//          var res = jQuery.parseJSON( response );
          <#if stage == "alpha">
              var res = response;
          <#else>
              var res = jQuery.parseJSON( response );
          </#if>           
              console.log(res);
        },
        error: function( response ) {
            /* revert changes */
          if( item.isLiked ) {
            item.isLiked = false;
            item.likeCount--;
          } else {
              item.isLiked = true;
              item.likeCount++;
          }                    
        }
      });      
    };
    this.getUser();
    this.getReviewList();
}