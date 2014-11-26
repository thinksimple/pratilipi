<!-- PageContent :: Reader :: Start -->


<template is="auto-binding" id="PageContent-Reader">

	<core-scroll-header-panel flex>
		
		<core-toolbar class="bg-green">
			<paper-icon-button icon="arrow-back" title="Exit Reader" on-tap="{{performExit}}"></paper-icon-button>
			<div flex>
				<a href="${ pratilipiData.getPageUrlAlias() }">${ pratilipiData.getTitle() }</a>
			</div>
			<paper-icon-button icon="more-vert" title="Display Options" on-tap="{{displayOptions}}"></paper-icon-button>
		</core-toolbar>
		
		<div horizontal center-justified layout class="bg-gray" style="margin-bottom:65px;">
			<#if pratilipiData.getContentType() == "PRATILIPI" >
	
				<#if contentSize??>
					<div id="PageContent-Reader-Content" class="paper" style="font-size:${ contentSize }"></div>
				<#else>
					<div id="PageContent-Reader-Content" class="paper"></div>
				</#if>

			<#elseif pratilipiData.getContentType() == "IMAGE" >			

				<div class="paper" style="width:inherit; max-width:none; min-height:inherit; overflow-x:auto;">
					<#if contentSize??>
						<div id="PageContent-Reader-Content" style="width:${ contentSize }"></div>
					<#else>
						<div id="PageContent-Reader-Content"></div>
					</#if>
				</div>

			</#if>
		</div>
				
	</core-scroll-header-panel>
	
	
	<div center horizontal layout style="position:fixed; bottom:10px; width:100%;">
		<paper-slider flex pin="true" snaps="false" min="1" max="{{ pageCount }}" value="{{ pageNo }}" class="bg-green" style="width:100%" on-core-change="{{displayPage}}"></paper-slider>
		<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" on-tap="{{displayPrevious}}"></paper-fab>
		<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:25px;" on-tap="{{displayNext}}"></paper-fab>
	</div>


	<paper-dialog id="PageContent-Reader-Options">
		<div><b>Text Size</b></div>
		<core-icon-button icon="remove" title="Decrease Text Size" on-tap="{{decTextSize}}"></core-icon-button>
		<core-icon-button icon="add" title="Increase Text Size" on-tap="{{incTextSize}}"></core-icon-button>
	</paper-dialog>


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

	var scope = document.querySelector( '#PageContent-Reader' );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	
	var contentArray = [];
	
	jQuery( 'body' ).keydown( function( event ) {
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
		var dialog = document.querySelector( '#PageContent-Reader-Options' );
		if( dialog ) {
			dialog.toggle();
		}
	};

	scope.displayPage = function( e ) {
		updateContent();
		document.querySelector( 'core-scroll-header-panel' ).scroller.scrollTop = 0;
		prefetchContent();
		setCookie( '${ pageNoCookieName }', scope.pageNo );
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
				document.querySelector( '#PageContent-Reader-Content' ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
				var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo } );
				ajax.go();
			} else {
				document.querySelector( '#PageContent-Reader-Content' ).innerHTML = contentArray[scope.pageNo];
			}
		}
		
		function prefetchContent() {
			var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo - 1 } );
				ajax.go();
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				ajax.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1 } );
				ajax.go();
			}
		}
		
		
		scope.decTextSize = function( e ) {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize - 2;
			if( newFontSize < 10 )
				newFontSize = 10;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px' );
		};

		scope.incTextSize = function( e ) {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize + 2;
			if( newFontSize > 30 )
				newFontSize = 30;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px' );
		};
		
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
				document.querySelector( '#PageContent-Reader-Content' ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
				loadImage( scope.pageNo );
			} else {
				document.querySelector( '#PageContent-Reader-Content' ).innerHTML = contentArray[scope.pageNo];
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
		
		
		scope.decTextSize = function( e ) {
			var width = jQuery( '#PageContent-Reader-Content' ).width();
			var newWidth = width - 50;
			if( newWidth < 300 )
				newWidth = 300;
			jQuery( '#PageContent-Reader-Content' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };

		scope.incTextSize = function( e ) {
			var width = jQuery( '#PageContent-Reader-Content' ).width();
			var newWidth = width + 50;
			jQuery( '#PageContent-Reader-Content' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };
		
	</#if>
	
</script>


<style>

	#PageContent-Reader-Content img {
		width:100%;
	}

</style>


<!-- PageContent :: Reader :: End -->