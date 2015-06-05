<!-- PageContent :: Writer :: Start -->


<div id="PageContent-Writer" class="container">
	<pagecontent-writer-image apiUrl="/api.pratilipi/pratilipi" pratilipiId="${ pratilipiData.getId()?c }"></pagecontent-writer-image>
</div>


<script>

	jQuery( '#Polymer' ).bind( 'template-bound', function( e ) {
		var writer = document.querySelector( 'pagecontent-writer-image' );
		writer.setIndex( JSON.parse( '${ pratilipiData.getIndex() ! "[]" }' ) );
	});

</script>


<!-- PageContent :: Writer :: End -->