$(function(){
var reqProperties = ['title', 'titleEn', 'language', 'summary', 'state', 'listingDateMillis', 'ratingCount', 'readCount'];
var ViewModel = {
    title: ko.observable(),
    titleEn: ko.observable(),
    language: ko.observable(),
    summary: ko.observable(),
    state: ko.observable(),
    listingDateMillis: ko.observable(),
    ratingCount: ko.observable(),
    readCount: ko.observable(),
    pushToViewModel: function( data ){
      var self = this;
      $.each( data, function( key, value ) {
        if( reqProperties.includes( key ) ) {
          self[key]( value );
        }
      });
    }
  };  
  
  ko.applyBindings( ViewModel );
  $.ajax({
    type: 'get',
    url: '/api/pratilipi?_apiVer=2&pratilipiId=5639631493136384',
    data: { 
        // 'language': "${ language }"
    },
    success: function( response ) {
      var res = jQuery.parseJSON( response );
      ViewModel.pushToViewModel( res );
    },
    error: function( response ) {
      console.log( response );
        console.log( typeof(response) );
      }
  });
});