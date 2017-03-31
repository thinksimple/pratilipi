function() {
	$( ".js-navigation-drawer a" ).each( function( index, element ) {
		if( $( element ).attr( 'href' ) == window.location.pathname )
			$( element ).css( "font-weight", "700" );
	});
}