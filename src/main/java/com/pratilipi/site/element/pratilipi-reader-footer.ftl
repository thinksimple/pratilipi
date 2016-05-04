
<script>
	function pageNoChanged() {
		var pageNo = document.getElementById( 'pageNoInput' ).value;
		if( pageNo >=1 && pageNo <= ${ pageCount } )
			gotoPage( pageNo );
		else
			document.getElementById( 'pageNoInput' ).value = ${ pageNo };
	}
	$( document ).ready(function() {
		$( "#pageNoInput" ).after( " / ${ pageCount }" );
	});
</script>

<div style="border: 1px solid #000; width: 98px; line-height: 1.4; margin: 0 auto;">
	<input id="pageNoInput" type="number" style="width: 48px;" value="${ pageNo }" onChange="pageNoChanged()">
</div>
