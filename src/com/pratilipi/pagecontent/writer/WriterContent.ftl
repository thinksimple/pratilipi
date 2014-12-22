<!-- PageContent :: Writer :: Start -->

<script src="//cdn-asia.pratilipi.com/third-party/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8"></script>


<template is="auto-binding" id="PageContent-Writer">

	<core-header-panel flex mode="scroll" on-scroll={{performScrollActions}}>
		
		<core-toolbar class="bg-green">
			<paper-icon-button icon="arrow-back" title="Exit Writer" on-tap="{{performExit}}"></paper-icon-button>
			<div flex>${ pratilipiData.getTitle() }</div>
		</core-toolbar>
		
		<div horizontal center-justified layout class="bg-gray">
			<#if contentSize??>
				<div id="PageContent-Writer-Content" class="paper" contenteditable="true" style="font-size:${ contentSize };" ></div>
			<#else>
				<div id="PageContent-Writer-Content" class="paper" contenteditable="true" ></div>
			</#if>
		</div>
		
		<div class="bg-gray green" style="text-align:center; padding-bottom:16px; margin-bottom:75px;">
			<b>{{ pageNo }} / {{ pageCount }}</b>
		</div>
		
	</core-header-panel>
	
	
	<div center horizontal layout id="PageContent-Writer-Navigation" style="position:fixed; bottom:10px; width:100%;">
		<paper-slider flex pin="true" snaps="false" min="1" max="{{ pageCount > 1 ? pageCount : 2 }}" value="${ pageNo }" class="bg-green" style="width:100%" disabled="{{ pageCount == 1 }}" on-change="{{displayPage}}"></paper-slider>
		<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" disabled="{{ pageNo == 1 }}" on-tap="{{displayPrevious}}"></paper-fab>
		<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:10px;" disabled="{{ pageNo == pageCount }}" on-tap="{{displayNext}}"></paper-fab>
		<paper-fab mini icon="reorder" title="Options" class="bg-green" style="margin-right:10px;" on-tap="{{displayOptions}}"></paper-fab>
		<paper-fab icon="{{ isEditorDirty ? 'save' : 'done' }}" title="{{ isEditorDirty ? 'Save' : 'Saved' }}" class="{{ isEditorDirty ? 'bg-red' : 'bg-green' }}" style="margin-right:25px;" on-tap="{{savePage}}"></paper-fab>
	</div>

	<paper-dialog id="PageContent-Writer-Options" style="color:gray; border:1px solid #EEEEEE;">
		<core-icon-button icon="remove" title="Decrease Text Size" on-tap="{{decTextSize}}"></core-icon-button>
		Text Size
		<core-icon-button icon="add" title="Increase Text Size" on-tap="{{incTextSize}}"></core-icon-button>
		<br/>
		<core-icon-button icon="description" on-tap="{{addPageAfter}}">&nbsp; Add New Page After This Page</core-icon-button>
		<br/>
		<core-icon-button icon="description" on-tap="{{addPageBefore}}">&nbsp; Add New Page Before This Page</core-icon-button>
		<br/>
		<core-icon-button icon="delete" on-tap="{{deletePage}}">&nbsp; Delete This Page</core-icon-button>
		<br/>
		<core-icon-button icon="history">&nbsp; Version History</core-icon-button>
		<br/>
		<core-icon-button icon="file-upload">&nbsp; Upload Word Document</core-icon-button>
	</paper-dialog>

	<core-overlay layered backdrop id="PageContent-Writer-Overlay" autoCloseDisabled="true">
		<h2>Please wait ...</h2>
	</core-overlay>


	<core-ajax
			id="PageContent-Writer-Ajax-Get"
			url="/api.pratilipi/pratilipi/content"
			contentType="application/json"
			method="GET"
			handleAs="json"
			on-core-response="{{handleAjaxGetResponse}}" ></core-ajax>
			
	<core-ajax
			id="PageContent-Writer-Ajax-Put"
			url="/api.pratilipi/pratilipi/content"
			contentType="application/json"
			method="PUT"
			handleAs="json"
			on-core-response="{{handleAjaxPutResponse}}" ></core-ajax>

</template>


<script>

	var scope = document.querySelector( '#PageContent-Writer' );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	var pageNoDisplayed = 0;
	

	var overlay; // Initialized in initWriter()
	var dialog; // Initialized in initWriter()
	var ajaxGet; // Initialized in initWriter()
	var ajaxPut; // Initialized in initWriter()
	
	var contentArray = [];
	contentArray[scope.pageNo] = ${ pageContent }
	
	
	var ckEditor; // Initialized in initWriter()
	CKEDITOR.disableAutoInline = true;
	CKEDITOR.config.toolbar = [
			['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
			['NumberedList','BulletedList'],
			['Blockquote','Smiley','HorizontalRule','PageBreak'],
			['Link','Unlink'],
			['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
			['ShowBlocks','Maximize']
	];	
	

	scope.performScrollActions = function( e ) {
		<#if pageCount gt 1>
			var bottom = jQuery( '.paper' ).position().top
					+ jQuery( '.paper' ).outerHeight( true )
					+ 66;
			if( e.target.y > 60 && bottom > e.target.scrollHeight && jQuery( '#PageContent-Writer-Navigation' ).is( ':visible' ) )
				jQuery( '#PageContent-Writer-Navigation' ).fadeOut( 'fast' );
			else if( ( e.target.y <= 60 || bottom <= e.target.scrollHeight ) && !jQuery( '#PageContent-Writer-Navigation' ).is( ':visible' ) )
				jQuery( '#PageContent-Writer-Navigation' ).fadeIn( 'fast' );
		</#if>
	}
	
	scope.performExit = function( e ) {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};

	scope.displayOptions = function( e ) {
		dialog.open();
	};

	scope.decTextSize = function( e ) {
		var fontSize = parseInt( jQuery( '#PageContent-Writer-Content' ).css( 'font-size' ).replace( 'px', '' ) );
		var newFontSize = fontSize - 2;
		if( newFontSize < 10 )
			newFontSize = 10;
		jQuery( '#PageContent-Writer-Content' ).css( 'font-size', newFontSize + 'px' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px' );
	};

	scope.incTextSize = function( e ) {
		var fontSize = parseInt( jQuery( '#PageContent-Writer-Content' ).css( 'font-size' ).replace( 'px', '' ) );
		var newFontSize = fontSize + 2;
		if( newFontSize > 30 )
			newFontSize = 30;
		jQuery( '#PageContent-Writer-Content' ).css( 'font-size', newFontSize + 'px' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px' );
	};

	scope.displayPage = function( e ) {
		checkDirtyAndUpdatePage( e.target.value );
	};
	
	scope.displayPrevious = function( e ) {
		checkDirtyAndUpdatePage( scope.pageNo - 1 );
	};

	scope.displayNext = function( e ) {
		checkDirtyAndUpdatePage( scope.pageNo + 1 );
	};
    
	function checkDirtyAndUpdatePage( pageNo ) {
		if( CKEDITOR.instances[ 'PageContent-Writer-Content' ].checkDirty()
				&& !confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." ) ) {
			document.querySelector( 'paper-slider' ).value = scope.pageNo;

		} else {
			scope.pageNo = pageNo;
			document.querySelector( 'paper-slider' ).value = pageNo;
			document.querySelector( 'core-header-panel' ).scroller.scrollTop = 0;
			updateContent();
			prefetchContent();
			setCookie( '${ pageNoCookieName }', scope.pageNo );
		}
	}
	
	function updateContent() {
		if( pageNoDisplayed == scope.pageNo )
			return;
			
		if( contentArray[scope.pageNo] == null ) {
			document.querySelector( '#PageContent-Writer-Content' ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
			ajaxGet.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI' } );
			ajaxGet.go();
		} else {
			document.querySelector( '#PageContent-Writer-Content' ).innerHTML = contentArray[scope.pageNo];
			pageNoDisplayed = scope.pageNo;
		}
		
		ckEditor.resetDirty();
		ckEditor.resetUndo();
		scope.isEditorDirty = false;
	}
	
	function prefetchContent() {
		if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
			ajaxGet.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo - 1, contentType:'PRATILIPI' } );
			ajaxGet.go();
		}
		if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
			ajaxGet.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1, contentType:'PRATILIPI' } );
			ajaxGet.go();
		}
	}
	
    scope.savePage = function( e ) {
    	if( scope.isEditorDirty ) {
    		overlay.open();
	    	ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:ckEditor.getData() } );
	    	ajaxPut.go();
		}
    };
    
    scope.addPageAfter = function( e ) {
		dialog.close();
		overlay.open();
    	ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1, contentType:'PRATILIPI', pageContent:'', insertNew:true } );
    	ajaxPut.go();
    };
    
    scope.addPageBefore = function( e ) {
		dialog.close();
		overlay.open();
    	ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:'', insertNew:true } );
    	ajaxPut.go();
    };
    
    scope.deletePage = function( e ) {
		dialog.close();
		overlay.open();
    	ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:'' } );
    	ajaxPut.go();
    };

	scope.handleAjaxGetResponse = function( event, response ) {
		if( contentArray[response.response['pageNo']] == null ) {
			contentArray[response.response['pageNo']] = response.response['pageContent'];
			updateContent();
		}
    };
    
	scope.handleAjaxPutResponse = function( event, response ) {
		overlay.close();
		scope.pageNo = response.response['pageNo'];
		if( scope.pageCount != response.response['pageCount'] ) {
			contentArray = [];
			pageNoDisplayed = 0;
			scope.pageCount = response.response['pageCount'];
			if( scope.pageNo > scope.pageCount )
				scope.pageNo = scope.pageCount;
			checkDirtyAndUpdatePage( scope.pageNo );
		} else {
			contentArray[scope.pageNo] = ckEditor.getData();
			pageNoDisplayed = 0;
			updateContent();
		}
	};

	function initWriter() {
		try {
			overlay = document.querySelector( '#PageContent-Writer-Overlay' );
			dialog = document.querySelector( '#PageContent-Writer-Options' );
			ajaxGet = document.querySelector( '#PageContent-Writer-Ajax-Get' );
			ajaxPut = document.querySelector( '#PageContent-Writer-Ajax-Put' );
			ckEditor = CKEDITOR.inline( 'PageContent-Writer-Content', {
				on:{
					'instanceReady': function() {
						updateContent();
						prefetchContent();
					}, 'change': function() {
						scope.isEditorDirty = ckEditor.checkDirty();
					},
				}
			});
		} catch( err ) {
			console.log( 'Writer initialization failed with error - ' + '\"' + err.message + '\". Retrying in 100ms ...' );
			window.setTimeout( initWriter, 100 );
		}
	}
	initWriter();
	
</script>


<!-- PageContent :: Writer :: End -->