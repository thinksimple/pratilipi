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
			<div class="paper" style="margin-bottom:65px;">
				Start Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
				<br/>
				<br/>
				Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
			</div>
		</div>
		
	</core-scroll-header-panel>
	
	
	<div center horizontal layout style="position:fixed; bottom:10px; width:100%;">
		<paper-slider flex pin="true" snaps="false" min="1" max="10" value="5" class="bg-green" style="width:100%" on-core-change="{{displayPage}}"></paper-slider>
		<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" on-tap="{{displayPrevious}}"></paper-fab>
		<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:25px;" on-tap="{{displayNext}}"></paper-fab>
	</div>

</template>


<script>

	var scope = document.querySelector( "#PageContent-Reader" );
	
	scope.performExit = function(e) {
		alert( "Exit functionality is not yet implemented !" );
	};

	scope.displayOptions = function(e) {
		var dialog = e.target.querySelector( 'paper-dialog' );
		if( dialog ) {
			dialog.toggle();
		}
	};

	scope.displayPage = function(e) {
		alert( "New page number is " + e.target.value  );
	};
	
	scope.displayPrevious = function(e) {
		alert( "Display previous functionality is not yet implemented !" );
	};

	scope.displayNext = function(e) {
		alert( "Display next functionality is not yet implemented !" );
	};

</script>


<style shim-shadowdom>
	
	html /deep/ .pageContent-Reader-Options {
		position: fixed;
		top: 0px;
		right: 0px;
	}
	
</style>
