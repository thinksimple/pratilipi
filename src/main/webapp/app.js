$(document).ready(function() {  
  var pratilipiSummaryControllerObject = new pratilipiSummaryController();
  pratilipiSummaryControllerObject.init();
});

var pratilipiSummaryController = function() {
  this.reqProperties = ['title', 'titleEn', 'language', 'summary', 'state', 'listingDateMillis', 'ratingCount', 'readCount']
  this.ViewModel = {
    title: ko.observable(),
    titleEn: ko.observable(),
    language: ko.observable(),
    summary: ko.observable(),
    state: ko.observable(),
    listingDateMillis: ko.observable(),
    ratingCount: ko.observable(),
    readCount: ko.observable(),
  };
  ko.applyBindings( this.ViewModel );
};

pratilipiSummaryController.prototype.init = function() {
  this.getData();
  this.registerServiceWorker();
  // var dummy = {"pratilipiId":5187823242051584,"title":"देवांश","titleEn":"devansh","language":"HINDI","author":{"authorId":5714053011865600,"name":"वीणा वत्सल सिंह","pageUrl":"/veena-vatsal-singh"},"pageUrl":"/veena-vatsal-singh/devansh","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5187823242051584\u0026version\u003d1462536818515","readPageUrl":"/read?id\u003d5187823242051584","writePageUrl":"/write?id\u003d5187823242051584","oldContent":true,"type":"STORY","state":"PUBLISHED","listingDateMillis":1439032842890,"reviewCount":13,"ratingCount":16,"averageRating":4.4375,"readCount":17052,"fbLikeShareCount":1729,"hasAccessToUpdate":false};
  // this.pushToViewModel( dummy );
};

pratilipiSummaryController.prototype.getData = function() {
  this.requestFromCache();
  var self = this;
  $.ajax({
      type: 'get',
      url: '/api/pratilipi?_apiVer=2&pratilipiId=5639631493136384',
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

pratilipiSummaryController.prototype.requestFromCache = function() {
  var self = this;
  var url = '/api/pratilipi?_apiVer=2&pratilipiId=5639631493136384';  
  if ('caches' in window) {
    /*
     * Check if the service worker has already cached this books
     * data. If the service worker has the data, then display the cached
     * data while the app fetches the latest data.
     */
    caches.match(url).then(function(response) {
      if (response) {
        response.json().then(function updateFromCache(json) {
          self.pushToViewModel( json );
        });
        
      }
    });
  } 
};

pratilipiSummaryController.prototype.pushToViewModel = function( data ) {
  this.ViewModel.title(data["title"]).titleEn(data["titleEn"]).language(data["language"]).summary(data["summary"]).state(data["state"]).listingDateMillis(data["listingDateMillis"]).ratingCount(data["ratingCount"]).readCount(data["readCount"]);  
};

pratilipiSummaryController.prototype.registerServiceWorker = function() {
  if ('serviceWorker' in navigator) {
      navigator.serviceWorker
             .register('service-worker.js?6')
             .then(function() { console.log('Service Worker Registered'); });
  }    
}; 