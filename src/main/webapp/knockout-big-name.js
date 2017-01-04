alert("When i am not ready");
$(function(){
  alert("i am ready");
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
  alert("going to make ajax req");
  $.ajax({
    type: 'get',
    url: '/api/pratilipi?_apiVer=2&pratilipiId=5639631493136384',
    data: { 
        // 'language': "${ language }"
    },
    success: function( response ) {
      var res = jQuery.parseJSON( response );
      alert("ajax succeeded");
      ViewModel.pushToViewModel( res );
    },
    error: function( response ) {
      console.log( response );
      alert("ajax failed");
        console.log( typeof(response) );
      }
  });
});