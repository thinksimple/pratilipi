<#macro foot_tags>
	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/js/jquery.js"></script>

	<link rel="stylesheet" href="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/pwa/css/material-icons.materialise.materialise_dropdown.bootstrap_modal.css"/>
	<link rel="stylesheet" type="text/css" href="http://public.pratilipi.com/resource-pwa/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/pwa-stylesheets/css/material.css?1" />
	<link rel="stylesheet" href="/pwa-stylesheets/css/styles-new.css?115" />

	<script src="https://d3cwrmdwk8nw1j.cloudfront.net/resource-all/rangy_core.rangy_selectionsaverestore.min.js"></script>
	<script src="/pwa-scripts/transliteration-suggester-${ lang }.js?1"></script>
	<script src="/pwa-scripts/transliteration-app-${ lang }.js?3"></script>

	<script src="/pwa-scripts/scripts-${ lang }.js?83"></script>
	<script src="/pwa-scripts/app-${ lang }.js?55"></script>

	<script src="http://public.pratilipi.com/resource-pwa/js/bootstrap.min.js"></script>
	<script src="http://public.pratilipi.com/resource-pwa/js/material.getmdlselect.min.js"></script>

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
