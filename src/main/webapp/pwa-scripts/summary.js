var AppViewModel = function() {
	this.user = ko.observable( {} );
};
var appViewModel = new AppViewModel();

var updateUser = function() {
	var dataAccessor = new DataAccessor();
	dataAccessor.getUser( function( user ) {
		ko.mapping.fromJS( user, {}, appViewModel.user );
		appViewModel.user.valueHasMutated();
	});
}

ko.applyBindings( appViewModel );
updateUser();

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