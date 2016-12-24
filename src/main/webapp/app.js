$(document).ready(function() {  
  var pratilipiSummaryControllerObject = new pratilipiSummaryController();
  pratilipiSummaryControllerObject.init();
});

var pratilipiSummaryController = function() {
  this.ViewModel = {};
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
      url: '/api/pratilipi?_apiVer=2&pratilipiId=5187823242051584',
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
  var self = this;
  $.each( data, function( key, value ) {
    self.ViewModel[ key ] = ko.observable( value );
  });
  ko.applyBindings( self.ViewModel );
};