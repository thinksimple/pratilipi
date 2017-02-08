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
//      var response = {"reviewList": [] };
//      var response = {"reviewList":[{"userPratilipiId":"5219799709777920-6321516031508480","userName":"Suresh","userImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2","userProfilePageUrl":"/suresh-yadav","user":{"userId":5219799709777920,"displayName":"Suresh","profilePageUrl":"/suresh-yadav","profileImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2"},"rating":0,"review":"Kahani acchi thi","reviewDateMillis":1465170931889,"likeCount":0,"commentCount":0,"isLiked":false},{"userPratilipiId":"5658881667629056-6321516031508480","userName":"दिशा","userImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2","userProfilePageUrl":"/disha-minda","user":{"userId":5658881667629056,"displayName":"दिशा","profilePageUrl":"/disha-minda","profileImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2"},"rating":5,"review":"Kahanai basherte achi h parantu jab hum kahani padhte hain toh yahi ummed rakhte hain ki lekhak ki bhasha par pakad h aur ye itni trutiya (spelling mistakes ) ki asha kam hi rakhte h kripya is par thora dhyan de","reviewDateMillis":1465045038418,"likeCount":0,"commentCount":1,"isLiked":false},{"userPratilipiId":"6404625446993920-6321516031508480","userName":"rdx","userImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2","userProfilePageUrl":"/author/6329221793185792","user":{"userId":6404625446993920,"displayName":"rdx","profilePageUrl":"/author/6329221793185792","profileImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?version\u003d2"},"rating":5,"review":"hgff","reviewDateMillis":1485743717361,"likeCount":0,"commentCount":0,"isLiked":false}],"cursor":"3"};
//      this.pushToReviewList( response["reviewList"] );
      $.ajax({
        type: 'get',
        url: '/api/userpratilipi/review/list?pratilipiId=' + self.pratilipiId + "&count=3",
        success: function( response ) {
          var res = jQuery.parseJSON( response );
          self.pushToReviewList( res["reviewList"] ); 
        },
        error: function( response ) {
            console.log( response );
            console.log( typeof(response) );
        }
      });
    };
    this.getReviewList();
}