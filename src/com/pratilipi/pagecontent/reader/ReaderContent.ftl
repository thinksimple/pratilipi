<!-- PageContent :: Reader :: Start -->


<template is="auto-binding" id="PageContent-Reader">

	<core-scroll-header-panel flex>
		
		<core-toolbar class="bg-green">
			<paper-icon-button icon="arrow-back" title="Exit Reader" on-tap="{{performExit}}"></paper-icon-button>
			<div flex>
				<a href="${ pratilipiData.getPageUrlAlias() }">${ pratilipiData.getTitle() }</a>
			</div>
			<paper-icon-button icon="more-vert" title="Display Options" on-tap="{{displayOptions}}">
				<paper-dialog class="pageContent-Reader-Options">
					<div><b>Text Size</b></div>
					<core-icon-button icon="remove" title="Decrease Text Size"></core-icon-button>
					<core-icon-button icon="add" title="Increase Text Size"></core-icon-button>
				</paper-dialog>
			</paper-icon-button>
		</core-toolbar>
		
		<div horizontal center-justified layout class="bg-gray">
			<div id="PageContent-Reader-Content" class="paper" style="margin-bottom:65px;"></div>
		</div>
		
	</core-scroll-header-panel>
	
	
	<div center horizontal layout style="position:fixed; bottom:10px; width:100%;">
		<paper-slider flex pin="true" snaps="false" min="1" max="{{ pageCount }}" value="{{ pageNo }}" class="bg-green" style="width:100%" on-core-change="{{displayPage}}"></paper-slider>
		<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" on-tap="{{displayPrevious}}"></paper-fab>
		<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:25px;" on-tap="{{displayNext}}"></paper-fab>
	</div>

</template>


<script>

	var scope = document.querySelector( "#PageContent-Reader" );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };

	
	scope.performExit = function(e) {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};

	scope.displayOptions = function(e) {
		var dialog = e.target.querySelector( 'paper-dialog' );
		if( dialog ) {
			dialog.toggle();
		}
	};

	scope.displayPage = function(e) {
		updateContent();
	};
	
	scope.displayPrevious = function(e) {
		if( scope.pageNo > 1 ) {
			scope.pageNo = scope.pageNo - 1;
			updateContent();
		}
	};

	scope.displayNext = function(e) {
		if( scope.pageNo < scope.pageCount ) {
			scope.pageNo = scope.pageNo + 1;
			updateContent();
		}
	};

	function updateContent() {
		document.querySelector( "#PageContent-Reader-Content" ).innerHTML = "<img src='${ pratilipiData.getImageContentUrl() }/" + scope.pageNo + "'/>"
	}

</script>


<style shim-shadowdom>
	
	html /deep/ .pageContent-Reader-Options {
		position: fixed;
		top: 0px;
		right: 0px;
	}
	
</style>


<!-- PageContent :: Reader :: End -->