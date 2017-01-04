$(function(){
  alert('Document Ready');
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
//    url: '/api/pratilipi?_apiVer=2&pratilipiId=5639631493136384',
    url: '/dummy.json',
    data: { 
        // 'language': "${ language }"
    },
    success: function( response ) {
      alert('Ajax success')
      ViewModel.pushToViewModel( response );
    },
    error: function( response ) {
      alert('Ajax error')
      console.log( response );
      console.log( typeof(response) );
    }
  });
});