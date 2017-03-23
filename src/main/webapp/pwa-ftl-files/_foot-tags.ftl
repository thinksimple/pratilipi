<#macro foot_tags>
	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/js/jquery.js"></script>

	<link rel="stylesheet" href="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/css/material-icons.materialise.materialise_dropdown.bootstrap_modal.css"/>
	<link rel="stylesheet" type="text/css" href="/pwa-stylesheets/css/bootstrap-dropdown.css" />
	<link rel="stylesheet" type="text/css" href="/pwa-stylesheets/css/material.css?1" />
	<link rel="stylesheet" href="/pwa-stylesheets/css/styles-new.css?76" />

	<script src="/pwa-scripts/bootstrap-dropdown.min.js"></script>
	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/rangy_core.rangy_selectionsaverestore.min.js"></script>
	<script src="/pwa-scripts/transliteration-suggester-${ lang }.js?1"></script>
	<script src="/pwa-scripts/transliteration-app-${ lang }.js?2"></script>
	<script>
	$( "*[make-transliterable]" ).each( function() {
		var $content_object = $( this );
		this.content_transliteration_object = new transliterationApp( $content_object, "${ lang }" );
		this.content_transliteration_object.init();
	});
	</script>

	<script src="/pwa-scripts/scripts-${ lang }.js?68"></script>
	<script src="/pwa-scripts/app-${ lang }.js?53"></script>

	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/js/materialise.materialise_dropdown.bootstrap_modal.js"></script>

	<style>
		.word-suggester {
			position: absolute;
			padding: 10px;
			border: whitesmoke 1px solid;
			display: inline-block;
			min-width: 100px;
			display: none;
			background: white;
			z-index: 65538;
			font-size: 16px;
		}
		
		.word-input {
			border: none;
			display: inline-block;
			border-right: 2px grey solid;
		}
		.suggestion {
			cursor: pointer;
			line-height: 24px;
		}
		.highlight-suggestion {
			background: lightgrey;
			font-weight: 700;
		}
	</style>
</#macro>
