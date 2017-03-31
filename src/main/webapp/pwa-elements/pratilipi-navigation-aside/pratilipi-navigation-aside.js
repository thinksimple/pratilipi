function() {
	$( ".js-navigation-aside a" ).each( function( index, element ) {
		if( $( element ).attr( 'href' ) == window.location.pathname )
			$( element ).css( "font-weight", "700" );
	});
}