
<script>
	function pageNoChanged() {
		var pageNo = document.getElementById( 'pageNoInput' ).value;
		if( pageNo >=1 && pageNo <= ${ pageCount } ) {
			// Redirect to that page.
			window.location.href = "${ pratilipi.getReadPageUrl() }" +
			( "${ pratilipi.getReadPageUrl() }".indexOf( "?" ) == -1 ? "?" : "&" ) + 
			"pageNo=" + pageNo;
		} else {
			// Set the value to page Number
			document.getElementById( 'pageNoInput' ).value = ${ pageNo };
		}
	}
	$( document ).ready(function() {
		$( "#pageNoInput" ).after( " / ${ pageCount }" );
	});
</script>

<div style="border: 1px solid #000; width: 98px; line-height: 1.4; margin: 0 auto;">
	<input id="pageNoInput" type="number" style="width: 48px;" value="${ pageNo }" onChange="pageNoChanged()">
</div>
