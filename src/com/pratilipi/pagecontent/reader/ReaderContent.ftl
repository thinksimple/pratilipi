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


	<#if pratilipiData.getContentType() == "PRATILIPI" >
		<core-ajax
				id="PageContent-Reader-Ajax"
				url="/service.pratilipi/json?api=getPratilipiContent"
				contentType="application/json"
				method="POST"
				handleAs="json"
				on-core-response="{{handleAjaxResponse}}" ></core-ajax>
	</#if>

</template>


<script>

	var scope = document.querySelector( "#PageContent-Reader" );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	
	var contentArray = [];
	
	jQuery( "body" ).keydown( function( event ) {
		if( event.which == 37 && scope.pageNo > 1 ) {
			scope.pageNo--;
		} else if( event.which == 39 && scope.pageNo < scope.pageCount ) {
			scope.pageNo++;
		}
	});
	
	scope.performExit = function( e ) {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};

	scope.displayOptions = function( e ) {
		var dialog = e.target.querySelector( 'paper-dialog' );
		if( dialog ) {
			dialog.toggle();
		}
	};

	scope.displayPage = function( e ) {
		updateContent();
		document.querySelector( 'core-scroll-header-panel' ).scroller.scrollTop = 0;
		prefetchContent();
	};
	
	scope.displayPrevious = function( e ) {
		if( scope.pageNo > 1 ) {
			scope.pageNo--;
		}
	};

	scope.displayNext = function( e ) {
		if( scope.pageNo < scope.pageCount ) {
			scope.pageNo++;
		}
	};
	
    
	<#if pratilipiData.getContentType() == "PRATILIPI" >
    
		contentArray[scope.pageNo] = ${ pageContent }
		
		scope.handleAjaxResponse = function( event, response ) {
			contentArray[response.response['pageNo']] = response.response['pageContent'];
			updateContent();
	    };
	    
		function updateContent() {
			if( contentArray[scope.pageNo] == null ) {
				document.querySelector( "#PageContent-Reader-Content" ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
				var ajax = document.querySelector( "#PageContent-Reader-Ajax" );
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo } );
				ajax.go();
			} else {
				document.querySelector( "#PageContent-Reader-Content" ).innerHTML = contentArray[scope.pageNo];
			}
		}
		
		function prefetchContent() {
			var ajax = document.querySelector( "#PageContent-Reader-Ajax" );
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo - 1 } );
				ajax.go();
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1 } );
				ajax.go();
			}
		}
		
	<#elseif pratilipiData.getContentType() == "IMAGE" >
		
		function loadImage( pageNo ) {
			var img = "<img src='${ pratilipiData.getImageContentUrl() }/" + pageNo + "' />";
			$(img).on( 'load', function() {
				contentArray[pageNo] = img;
				updateContent();
			});
		}
		
		function updateContent() {
			if( contentArray[scope.pageNo] == null ){
				document.querySelector( "#PageContent-Reader-Content" ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
				loadImage( scope.pageNo );
			} else {
				document.querySelector( "#PageContent-Reader-Content" ).innerHTML = contentArray[scope.pageNo];
			}
		}

		function prefetchContent() {
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				loadImage( scope.pageNo - 1 );
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				loadImage( scope.pageNo + 1 );
			}
		}
		
	</#if>
	
</script>


<style shim-shadowdom>
	
	html /deep/ .pageContent-Reader-Options {
		position: fixed;
		top: 0px;
		right: 0px;
	}
	
</style>


<style>

	#PageContent-Reader-Content img {
		width:100%;
	}
	
</style>


<!-- PageContent :: Reader :: End -->