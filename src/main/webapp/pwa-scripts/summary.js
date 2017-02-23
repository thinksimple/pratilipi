function AppViewModel() {
    var self = this;
    this.user = ko.observable("Bert");
    this.userIsGuest = ko.observable();
    this.getUser = function() {
        $.ajax({
            type: 'get',
            url: '/api/user',
            success: function( response ) {
                var res = jQuery.parseJSON( response );   
                self.userIsGuest( res["isGuest"] );
//                  self.userIsGuest( false );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof( response ) );
            }
        });      
    };  
    this.getUser();
}

$(document).ready(function() {  
 /* var pratilipiSummaryControllerObject = new pratilipiSummaryController();
  pratilipiSummaryControllerObject.init(); */
  
    ko.applyBindings( new AppViewModel() );
});

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
      var pair = vars[i].split("=");
      if (pair[0] == variable) {
        return pair[1];
      }
    } 
    alert('Query Variable ' + variable + ' not found');
}

function goToLoginPage() {
    window.location.href = "/login-pwa";
}

function sharePratilipiOnFacebook( url ) {
    window.open( "http://www.facebook.com/sharer.php?u=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnTwitter( url ) {
    window.open( "http://twitter.com/share?url=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnGplus( url ) {
    window.open( "https://plus.google.com/share?url=" + url, "share", "width=1100,height=500,left=70px,top=60px" );
}

function sharePratilipiOnWhatsapp( text ) {
    window.open( "whatsapp://send?text=" + text );
}