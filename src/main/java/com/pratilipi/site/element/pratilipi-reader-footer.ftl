<script>
	function pageNoChanged() {
		console.log( document.getElementById( 'pageNoInput' ).value );
	}
</script>

<input id="pageNoInput" type="number" value="${ pageNo }" onChange="pageNoChanged()">
