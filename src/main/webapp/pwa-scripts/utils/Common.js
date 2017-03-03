/* Helper functions specific to Pratilipi */
function getRetUrl( decoded ) {
	return getUrlParameter( "retUrl" ) == null
			? "/" 
			: ( decoded ? decodeURIComponent( getUrlParameter( "retUrl" ) ) : getUrlParameter( "retUrl" ) );
}

function convertDate( date ) {
	var d = new Date( date );
	function day(d) { return (d < 10) ? '0' + d : d; }
	function month(m) { var months = ['${ _strings.month_jan }','${ _strings.month_feb }','${ _strings.month_mar }',
									'${ _strings.month_apr }','${ _strings.month_may }','${ _strings.month_jun }',
									'${ _strings.month_jul }','${ _strings.month_aug }','${ _strings.month_sep }',
									'${ _strings.month_oct }','${ _strings.month_nov }','${ _strings.month_dec }']; 
						return months[m]; }
	return [ day( d.getDate() ), month( d.getMonth() ), d.getFullYear() ].join(' ');
}

function goToLoginPage() {
	window.location.href = "/login?retUrl=" + encodeURIComponent( window.location.pathname );
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

function getImageUrl( imageUrl, width ) {
	if( imageUrl == null ) return null;
	return imageUrl + ( imageUrl.indexOf( "?" ) > -1 ? "&" : "?" ) + width;
}

function getPratilipiTypeVernacular( pratilipiType ) {
	var pratilipiTypes = {
		<#list pratilipiTypes as pratilipiType>
			"${ pratilipiType.value }": "${ pratilipiType.name }",
		</#list>
	};
	return pratilipiTypes[ pratilipiType ];
}
