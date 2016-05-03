
<script>
	function pageNoChanged() {
		console.log( document.getElementById( 'pageNoInput' ).value );
	}
</script>

<div style="margin: 0 auto; text-align: center;">
	<input id="pageNoInput" type="number" value="${ pageNo }" onChange="pageNoChanged()">
</div>
