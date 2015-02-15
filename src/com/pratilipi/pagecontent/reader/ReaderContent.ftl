<!-- PageContent :: Reader :: Start -->


<core-drawer-panel rightDrawer>

	<pagecontent-reader-menu
			drawer style="background-color:white; overflow-y:auto"
			pageNo="{{ pageNo }}"
			pageCount="{{ pageCount }}"
			on-change="{{ displayPage }}"
			on-dec-text-size="{{ decTextSize }}"
			on-inc-text-size="{{ incTextSize }}"></pagecontent-reader-menu>
	
	
	<div main vertical layout>
		<core-scroll-header-panel flex id="Polymer-Window">


			<core-toolbar class="bg-green">
				<paper-icon-button icon="arrow-back" title="Exit Reader" on-tap="{{performExit}}"></paper-icon-button>
				<div flex>
					${ pratilipiData.getTitle() }
				</div>
				<paper-icon-button core-drawer-toggle icon="reorder" title="Display Options"></paper-icon-button>
			</core-toolbar>


			<div horizontal center-justified layout class="bg-gray">
				<#if pratilipiData.getContentType() == "PRATILIPI" >
			
					<div class="paper">
						<div style="position:relative">
							<#if contentSize??>
								<div id="PageContent-Reader-Content" style="font-size:${ contentSize }"></div>
							<#else>
								<div id="PageContent-Reader-Content" ></div>
							</#if>
							<div id="PageContent-Reader-Overlay"></div>
						</div>
					</div>
			
				<#elseif pratilipiData.getContentType() == "IMAGE" >			
			
					<div class="paper" style="width:inherit; max-width:none; min-height:inherit; overflow-x:auto;">
						<div style="position:relative">
							<#if contentSize??>
								<div id="PageContent-Reader-Content" style="width:${ contentSize }"></div>
								<div id="PageContent-Reader-Overlay" style="width:${ contentSize }"></div>
							<#else>
								<div id="PageContent-Reader-Content"></div>
								<div id="PageContent-Reader-Overlay"></div>
							</#if>
						</div>
					</div>
			
				</#if>
			</div>
			
			
			<div class="bg-gray green" style="text-align:center;padding-bottom:16px;margin-bottom:65px;">
				<b>{{ pageNo }} / ${ pageCount }</b>
			</div>
		
		
		</core-scroll-header-panel>
		
		
		<div style="position:relative; margin-right:15px">
			<pagecontent-reader-navigation
					style="position:absolute; bottom:10px; width:100%"
					pageNo="{{ pageNo }}"
					pageCount="{{ pageCount }}"
					on-change="{{ displayPage }}"></pagecontent-reader-navigation>
		</div>
		
		
		<#if pratilipiData.getContentType() == "PRATILIPI" >
			<core-ajax
					id="PageContent-Reader-Ajax"
					url="/api.pratilipi/pratilipi/content"
					contentType="application/json"
					method="GET"
					handleAs="json"
					on-core-response="{{handleAjaxResponse}}"
					on-core-error="{{handleAjaxError}}" ></core-ajax>
		</#if>
	
	</div>


</core-drawer-panel>


<script>

	var scope = document.querySelector( '#Polymer' );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	var pageNoDisplayed = 0;
	
	var contentArray = [];
	var isCtrl = false;
	
	var pageStartTime;
	var campaign = '${ pratilipiData.getType() }' + ":" + '${ pratilipiData.getId()?c }';
	
	
	jQuery( window ).unload( function() {
		recordPageTime( 'PageDurationInSec' );
	} ); 
	
	jQuery( 'body' ).bind( 'contextmenu', function( event ) {
		event.preventDefault();
	});
	
	jQuery( 'body' ).keyup( function( event) {
		if( event.which == 17 )
			isCtrl = false;
	});
	
	jQuery( 'body' ).keydown( function( event ) {
		if( event.which == 37 ) {
			scope.displayPrevious();
		} else if( event.which == 39 ) {
			scope.displayNext();
		} else if( event.which == 17 ) {
			isCtrl = true;
		} else if( event.which == 67 || event.which == 80 ) {
			if( isCtrl)
				return false; // Disabled Ctrl + C/P
		}
	});
	
	
	jQuery( '#Polymer-Window' ).bind( 'scroll', function( e ) {
		var navigation = jQuery( document.querySelector( 'pagecontent-reader-navigation' ) );
		var bottom = jQuery( '.paper' ).position().top
				+ jQuery( '.paper' ).outerHeight( true )
				+ 66;
		if( e.target.y > 60 && bottom > e.target.scrollHeight && navigation.is( ':visible' ) )
			navigation.fadeOut( 'fast' );
		else if( ( e.target.y <= 60 || bottom <= e.target.scrollHeight ) && !navigation.is( ':visible' ) )
			navigation.fadeIn( 'fast' );
	});
	
	scope.displayOptions = function( e ) {
		var dialog = document.querySelector( '#PageContent-Reader-Options' );
		if( dialog ) {
			dialog.open();
		}
	};

	scope.goToWriter = function( e ) {
		window.location.href="${ pratilipiData.getWriterPageUrl() }";
	};

	scope.performExit = function( e ) {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};


	scope.displayPage = function( e ) {
		recordPageTime( 'PageDurationInSec' );
		updateAndPrefetchContent();
		recordPageChangeEvent( 'SetPage' );
	};
	
	scope.displayPrevious = function( e ) {
		if( scope.pageNo > 1 ) {
			recordPageTime( 'PageDurationInSec' );
			scope.pageNo--;
			updateAndPrefetchContent();
			recordPageChangeEvent( 'PreviousPage' );
		}
	};

	scope.displayNext = function( e ) {
		if( scope.pageNo < scope.pageCount ) {
			recordPageTime( 'PageDurationInSec' );
			scope.pageNo++;
			updateAndPrefetchContent();
			recordPageChangeEvent( 'NextPage' );
		}
	};
    
	function updateAndPrefetchContent() {
		updateContent();
		document.querySelector( '#Polymer-Window' ).scroller.scrollTop = 0;
		prefetchContent();
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getReaderPageUrl() }' );
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getWriterPageUrl() }' );
    }
    
    
	<#if pratilipiData.getContentType() == "PRATILIPI" >
    
		contentArray[scope.pageNo] = ${ pageContent }
		
		scope.handleAjaxResponse = function( event, response ) {
			contentArray[response.response['pageNo']] = response.response['pageContent'];
			updateContent();
	    };
	    
	    scope.handleAjaxError = function( event, response ) {
			updateContent();
		};
	    
	    
		function updateContent() {
			if( pageNoDisplayed == scope.pageNo ) {
				return;
			
			} else if( contentArray[scope.pageNo] == null ) {
				loading( true );
				var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo } );
				ajax.go();
			
			} else {
				jQuery( '#PageContent-Reader-Content' ).fadeOut( 'fast', function() {
						jQuery( '#PageContent-Reader-Content' ).html( contentArray[scope.pageNo] );
						jQuery( '#PageContent-Reader-Content' ).fadeIn( 'fast', function() {
								loading( false );
						});
				});
				pageNoDisplayed = scope.pageNo;
			}
		}
		
		function prefetchContent() {
			var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo - 1 } );
				ajax.go();
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1 } );
				ajax.go();
			}
		}
		
		
		scope.decTextSize = function() {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize - 2;
			if( newFontSize < 10 )
				newFontSize = 10;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
		};

		scope.incTextSize = function() {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize + 2;
			if( newFontSize > 30 )
				newFontSize = 30;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
		};
		
	<#elseif pratilipiData.getContentType() == "IMAGE" >
		
		function loadImage( pageNo ) {
			var img = "<img src='/api.pratilipi/pratilipi/content/image?pratilipiId=${ pratilipiData.getId()?c }&pageNo=" + pageNo + "' />";
			$( img ).on( 'load', function() {
				contentArray[pageNo] = img;
				updateContent();
			});
			$( img ).on( 'error', function() {
				updateContent();
			});
		}
		
		function updateContent() {
			if( pageNoDisplayed == scope.pageNo ) {
				return;
			
			} else if( contentArray[scope.pageNo] == null ){
				loading( true );
				loadImage( scope.pageNo );
			
			} else {
				jQuery( '#PageContent-Reader-Content' ).fadeOut( 'fast', function() {
						jQuery( '#PageContent-Reader-Content' ).html( contentArray[scope.pageNo] );
						jQuery( '#PageContent-Reader-Content' ).fadeIn( 'fast', function() {
								loading( false );
						});
				});
				pageNoDisplayed = scope.pageNo;
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
			jQuery( '#PageContent-Reader-Overlay' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };

		scope.incTextSize = function( e ) {
			var width = jQuery( '#PageContent-Reader-Content' ).width();
			var newWidth = width + 50;
			jQuery( '#PageContent-Reader-Content' ).css( 'width', newWidth + 'px' );
			jQuery( '#PageContent-Reader-Overlay' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };
		
	</#if>
	
	
	recordPageChangeEvent = function( eventAction ){
		pageStartTime = jQuery.now()
		ga( 'send', 'event', campaign, eventAction, 'Page ' + scope.pageNo );
	};
	
	recordPageTime = function( eventAction ){
		var currentTime = jQuery.now();
		var timeDiff = currentTime - pageStartTime;
		var pageNumber = 'Page ' + scope.pageNo;
		if( timeDiff > 100 )
			timeDiff = 0;
		
		if( timeDiff < 900000 )
			timeDiff = 900000;
		ga( 'send', 'event', campaign, eventAction, pageNumber, parseInt( timeDiff/1000 ));
	}


	addEventListener( 'template-bound', function( e ) {
		if( e.target != scope )
			return;
		updateContent();
		prefetchContent();
		recordPageChangeEvent( 'PageLoad' );
		document.querySelector( 'pagecontent-reader-menu' ).setIndex( JSON.parse( '${ pratilipiData.getIndex() ! '[]' }' ) );
	});
	
</script>


<style>

	<#if pratilipiData.getContentType() == "IMAGE" >
		#PageContent-Reader-Content img {
			width:100%;
		}
	</#if>
	
	#PageContent-Reader-Overlay {
		position: absolute;
		top: 0px;
		left: 0px;
		height: 100%;
		width: 100%;
	}

</style>


<!-- PageContent :: Reader :: End -->
