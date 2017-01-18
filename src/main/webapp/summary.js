$(document).ready(function() {  
  var pratilipiSummaryControllerObject = new pratilipiSummaryController();
  pratilipiSummaryControllerObject.init();
});

var pratilipiSummaryController = function() {
  this.ViewModel = {
    title: ko.observable(),
    titleEn: ko.observable(),
    coverImageUrl: ko.observable(),
    author_name: ko.observable(),
    language: ko.observable(),
    summary: ko.observable(),
    state: ko.observable(),
    publish_date: ko.observable(),
    averageRating: ko.observable(),
    ratingCount: ko.observable(),
    readCount: ko.observable(),
    type: ko.observable()
  };
  ko.applyBindings( this.ViewModel );
};

pratilipiSummaryController.prototype.init = function() {
  this.getData();
  // var dummy = {"pratilipiId":5187823242051584,"title":"देवांश","titleEn":"devansh","language":"HINDI","author":{"authorId":5714053011865600,"name":"वीणा वत्सल सिंह","pageUrl":"/veena-vatsal-singh"},"pageUrl":"/veena-vatsal-singh/devansh","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5187823242051584\u0026version\u003d1462536818515","readPageUrl":"/read?id\u003d5187823242051584","writePageUrl":"/write?id\u003d5187823242051584","oldContent":true,"type":"STORY","state":"PUBLISHED","listingDateMillis":1439032842890,"reviewCount":13,"ratingCount":16,"averageRating":4.4375,"readCount":17052,"fbLikeShareCount":1729,"hasAccessToUpdate":false};
  // this.pushToViewModel( dummy );
};

pratilipiSummaryController.prototype.getData = function() {
  var self = this;
  $.ajax({
      type: 'get',
      url: '/api/pratilipi?_apiVer=2&pratilipiId=5713444722442240',
      data: { 
          // 'language': "${ language }"
      },
      success: function( response ) {
        var res = jQuery.parseJSON( response );
        self.pushToViewModel( res );
      },
      error: function( response ) {
        console.log( response );
        console.log( typeof(response) );
      }
  });
};


pratilipiSummaryController.prototype.pushToViewModel = function( data ) {
  this.ViewModel.title(data["title"]).titleEn(data["titleEn"]).coverImageUrl("https://d3cwrmdwk8nw1j.cloudfront.net/pratilipi/cover?pratilipiId=5713444722442240&version=1454565632964&width=150").author_name(data.author.name).language(data["language"]).summary(data["summary"]).state(data["state"]).publish_date(data["listingDateMillis"]).averageRating(data["averageRating"]).ratingCount(data["ratingCount"]).readCount(data["readCount"]).type(data["type"]);  
};
