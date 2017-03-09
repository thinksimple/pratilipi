/* Global Variables */
var shareUrl = null;
var shareWhatsappText = null;

/* Share Modal */
function viewModel() {

	var self = this;

	self.shareOnFacebook = function() {
		window.open( "http://www.facebook.com/sharer.php?u=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnTwitter = function() {
		window.open( "http://twitter.com/share?url=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnGplus = function() {
		window.open( "https://plus.google.com/share?url=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnWhatsapp = function() {
		window.open( "whatsapp://send?text=" + shareWhatsappText );
	};

}

ko.components.register( 'pratilipi-share-dialog', {
	viewModel: viewModel,
    template: <@add_backslashes><#include "../../pwa-elements/pratilipi-share-dialog/pratilipi-share-dialog.html"></@add_backslashes>
});

var shareDialog = document.createElement( 'pratilipi-share-dialog' );
shareDialog.style.zIndex = "1051";
document.body.appendChild( shareDialog );

var ShareUtil = (function() {
	return {
		share: function( url, whatsappText, utmParams ) {
			shareUrl = encodeURIComponent( url + ( utmParams != null ? "?" + new HttpUtil().formatParams( utmParams ) : "" ) );
			shareWhatsappText = whatsappText != null ? encodeURIComponent( whatsappText ) : null;
			$( "#pratilipi-share-dialog" ).modal();
		}
	};
})();
