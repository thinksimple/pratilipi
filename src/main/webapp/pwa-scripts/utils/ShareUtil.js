/* Global Variables */
var ga_category = null;
var shareUrl = null;
var shareWhatsappText = null;

/* Share Modal */
function viewModel() {

	var self = this;

	self.shareOnFacebook = function() {
		ga_CA( ga_category, 'FB-Share' );
		window.open( "http://www.facebook.com/sharer.php?u=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnTwitter = function() {
		ga_CA( ga_category, 'Twitter-Share' );
		window.open( "http://twitter.com/share?url=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnGplus = function() {
		ga_CA( ga_category, 'G+-Share' );
		window.open( "https://plus.google.com/share?url=" + shareUrl, "share", "width=1100,height=500,left=70px,top=60px" );
	};

	self.shareOnWhatsapp = function() {
		ga_CA( ga_category, 'Whatsapp-Share' );
		window.open( "whatsapp://send?text=" + shareWhatsappText );
	};

}

ko.components.register( 'pratilipi-share-dialog', {
	viewModel: viewModel,
    template: <@add_backslashes><#include "../../pwa-elements/pratilipi-share-dialog/pratilipi-share-dialog.html"></@add_backslashes>
});

var shareDialog = document.createElement( 'pratilipi-share-dialog' );
shareDialog.style.zIndex = "1051";
shareDialog.style.display = "none";
document.body.appendChild( shareDialog );

var ShareUtil = (function() {
	return {
		sharePratilipi: function( pratilipi, utmParams ) {
			var getShareUrl = function() {
				return window.location.origin + pratilipi.pageUrl;
			};
			var getWhatsappText = function() {
				return '"' + pratilipi.title + '"${ _strings.whatsapp_read_story } ' + getShareUrl() +" ${ _strings.whatsapp_read_unlimited_stories }";
			};
			MetaTagUtil.setMetaTagsForPratilipi( pratilipi );
			ga_category = "Pratilipi";
			this.share( getShareUrl(), getWhatsappText(), utmParams );
		},
		shareAuthor: function( author, utmParams ) {
			var getShareUrl = function() {
				return window.location.origin + author.pageUrl;
			};
			var getWhatsappText = function() {
				return '"' + author.name + '"${ _strings.whatsapp_read_story } ' + getShareUrl() +" ${ _strings.whatsapp_read_unlimited_stories }";
			};
			MetaTagUtil.setMetaTagsForAuthor( author );
			ga_category = "Author";
			this.share( getShareUrl(), getWhatsappText(), utmParams );
		},
		share: function( url, whatsappText, utmParams ) {
			shareDialog.style.display = "block";
			shareUrl = encodeURIComponent( url + ( utmParams != null ? "?" + new HttpUtil().formatParams( utmParams ) : "" ) );
			shareWhatsappText = whatsappText != null ? encodeURIComponent( whatsappText ) : null;
			$( "#pratilipi-share-dialog" ).modal();
		}
	};
})();
