window.fbAsyncInit = function() {
  FB.init({
    appId      : '413366985722072',
    xfbml      : true,
    version    : 'v2.8'
  });
  FB.AppEvents.logPageView();
};

(function(d, s, id){
   var js, fjs = d.getElementsByTagName(s)[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement(s); js.id = id;
   js.src = "//connect.facebook.net/en_US/sdk.js";
   fjs.parentNode.insertBefore(js, fjs);
 }(document, 'script', 'facebook-jssdk'));


function getQueryVariable(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    if (pair[0] == variable) {
      return pair[1];
    }
  } 
  console.log('Query Variable ' + variable + ' not found');
}

function ViewModel() {
    this.userEmail = "";
    this.userPassword = "";
    this.login = function() {
        FB.login( function( response ) {
          if( response == null || response.authResponse == null ) {
//            facebookLoginOnFlight = false;
            return;
          }
//          facebookLoginOnFlight = true;
          $.ajax({
        
            type: 'post',
            url: '/api/user/login/facebook',
        
            data: { 
              'fbUserAccessToken': response.authResponse.accessToken
            },
            
            success: function( response ) {
//              facebookLoginOnFlight = false;
//              if( getUrlParameters().ret != null )
//                window.location.href = decodeURIComponent( getUrlParameters().ret );
//              else
                window.location.href = "/";
            },
            
            error: function( response ) {
              facebookLoginOnFlight = false;
              var message = jQuery.parseJSON( response.responseText );
              var status = response.status;
        
              if( message["message"] != null )
                alert( "Error " + status + " : " + message["message"] ); 
              else
                alert( "Invalid Credentials" );
            }
          });
        }, { scope: 'public_profile,email,user_birthday' } );      
    };
}

ko.applyBindings( new ViewModel() );