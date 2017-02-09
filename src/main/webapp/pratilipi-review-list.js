function() {
    this.reviewList = ko.observableArray([]);
    this.pratilipiId = getQueryVariable( "id" );
    this.pushToReviewList = function( revList ) {
      for( var i=0; i< revList.length; i++ ) {
        this.reviewList.push( revList[i] );
      }
    }
    var self = this;
    this.getReviewList = function() {

      $.ajax({
        type: 'get',
        url: '<#if stage == "alpha">${ prefix }</#if>/api/userpratilipi/review/list?pratilipiId=' + self.pratilipiId + "&resultCount=3",
        success: function( response ) {
//          var res = jQuery.parseJSON( response );
          <#if stage == "alpha">
              var res = response;
          <#else>
              var res = jQuery.parseJSON( response );
          </#if>           
          self.pushToReviewList( res["reviewList"] );
        },
        error: function( response ) {
            console.log( response );
            console.log( typeof( response ) );
        }
      });
    };
    this.getReviewList();
}