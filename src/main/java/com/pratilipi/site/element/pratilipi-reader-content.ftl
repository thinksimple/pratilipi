<style>		
	#reader-content * {
		font-size: ${ fontSize }px!important;
		text-align: justify;
		-webkit-font-smoothing: antialiased;
		line-height: 1.6em!important;
	}
	
	#reader-content h1, #reader-content h2 {
		text-align: center;
	}
	
	#reader-content img {
		max-width: 100%!important;
	}
	
	#reader-content h2 {
		font-size: ${ fontSize + 4 }px!important;
	}
	#reader-content h1 {
		font-size: ${ fontSize + 8 }px!important;
	}
		
</style>
	
<div class="secondary-500 pratilipi-shadow box" style="margin-bottom: 5px; padding: 16px 22px; width: 100%;">
	<div id="reader-content">
		${ content }
	</div>
</div>