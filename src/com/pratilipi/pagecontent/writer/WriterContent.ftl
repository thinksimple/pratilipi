<!-- PageContent :: Writer :: Start -->


<core-toolbar class="bg-green">
	<paper-icon-button icon="arrow-back" title="Exit Writer" disabled="{{ disabled }}" on-tap="{{performExit}}"></paper-icon-button>
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


<div center horizontal layout id="PageContent-Writer-Navigation" style="position:fixed; bottom:10px; width:100%;">
	<paper-slider flex pin="true" snaps="false" min="1" max="{{ pageCount > 1 ? pageCount : 2 }}" value="${ pageNo }" class="bg-green" style="width:100%" disabled="{{ disabled || pageCount == 1 }}" on-change="{{displayPage}}"></paper-slider>
	<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" disabled="{{ disabled || pageNo == 1 }}" on-tap="{{displayPrevious}}"></paper-fab>
	<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:10px;" disabled="{{ disabled || pageNo == pageCount }}" on-tap="{{displayNext}}"></paper-fab>
	<paper-fab mini icon="reorder" title="Options" class="bg-green" style="margin-right:10px;" disabled="{{ disabled }}" on-tap="{{displayOptions}}"></paper-fab>
	<paper-fab mini icon="translate" title="Enable Transliteration" class="bg-green" style="margin-right:10px;" disabled="{{ disabled }}" on-tap="{{displayTransliterationOptions}}"></paper-fab>
	<paper-fab mini icon="launch" title="View on Reader" class="bg-green" style="margin-right:10px;" disabled="{{ disabled }}" on-tap="{{goToReader}}"></paper-fab>
	<paper-fab icon="{{ isEditorDirty ? 'save' : 'done' }}" title="{{ isEditorDirty ? 'Save' : 'Saved' }}" class="{{ isEditorDirty ? 'bg-red' : 'bg-green' }}" style="margin-right:25px;" disabled="{{ disabled }}" on-tap="{{savePage}}"></paper-fab>
</div>

<paper-action-dialog
		backdrop
		id="PageContent-Writer-Options"
		heading="Options"
		transition="core-transition-top"
		layered="true">
	<core-icon-button icon="remove" title="Decrease Text Size" on-tap="{{decTextSize}}"></core-icon-button>
	<span>Text Size</span>
	<core-icon-button icon="add" title="Increase Text Size" on-tap="{{incTextSize}}"></core-icon-button>
	<br/>
	<core-icon-button icon="description" on-tap="{{addPageAfter}}">&nbsp; Add New Page After This Page</core-icon-button>
	<br/>
	<core-icon-button icon="description" on-tap="{{addPageBefore}}">&nbsp; Add New Page Before This Page</core-icon-button>
	<br/>
	<core-icon-button icon="delete" on-tap="{{deletePage}}">&nbsp; Delete This Page</core-icon-button>
<#--
	<br/>
	<core-icon-button icon="history">&nbsp; Version History</core-icon-button>
	<br/>
	<core-icon-button icon="file-upload">&nbsp; Upload Word Document</core-icon-button>
-->
	<paper-button affirmative autofocus>Close</paper-button>
</paper-action-dialog>

<paper-action-dialog
		backdrop
		id="PageContent-Writer-TransliterationOptions"
		heading="Enable Transliteration"
		transition="core-transition-top"
		layered="true">
	<core-icon-button icon="translate" on-tap="{{enableHindiTransliteration}}">&nbsp; Enable Hindi Transliteration</core-icon-button>
	<br/>
	<core-icon-button icon="translate" on-tap="{{enableGujaratiTransliteration}}">&nbsp; Enable Gujarati Transliteration</core-icon-button>
	<br/>
	<core-icon-button icon="translate" on-tap="{{enableTamilTransliteration}}">&nbsp; Enable Tamil Transliteration</core-icon-button>
	<paper-button affirmative autofocus>Close</paper-button>
</paper-action-dialog>


<paper-action-dialog
		backdrop autoCloseDisabled
		id="PageContent-Writer-SessionExpired"
		heading="Session Expired !"
		transition="core-transition-top"
		layered="true">
	<p>Your session is expired. You will have to login again to continue.</p>
	<paper-button affirmative autofocus on-tap="{{initLogin}}">log in</paper-button>
</paper-action-dialog>

<paper-action-dialog
		backdrop autoCloseDisabled
		id="PageContent-Writer-ServerError"
		heading="Server Error !"
		transition="core-transition-top"
		layered="true">
	<p>Something unexpected happened at our server. Kindly try again.</p>
	<paper-button affirmative autofocus>Okay</paper-button>
</paper-action-dialog>


<core-ajax
		id="PageContent-Writer-Ajax-Get"
		url="/api.pratilipi/pratilipi/content"
		contentType="application/json"
		method="GET"
		handleAs="json"
		on-core-response="{{handleAjaxGetResponse}}"
		on-core-error="{{handleAjaxGetError}}" ></core-ajax>
		
<core-ajax
		id="PageContent-Writer-Ajax-Put"
		url="/api.pratilipi/pratilipi/content"
		contentType="application/json"
		method="PUT"
		handleAs="json"
		on-core-response="{{handleAjaxPutResponse}}"
		on-core-error="{{handleAjaxPutError}}" ></core-ajax>


<script>

	var scope = document.querySelector( '#Polymer' );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	scope.disabled = false;
	var pageNoDisplayed = 0;
	

	var dialog; // Initialized in initWriter()
	var transliterationDialog; // Initialized in initWriter()
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
			['Blockquote','Smiley','HorizontalRule'],
			['Link','Unlink'],
			['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
			['ShowBlocks','Maximize']
	];	
	

	scope.initLogin = function( e ) {
		window.open( "/" );
	};

	scope.goToReader = function( e ) {
		if( !checkDirty() )
			window.location.href="${ pratilipiData.getReaderPageUrl() }";
	};

	scope.performExit = function( e ) {
		if( !checkDirty() )
			window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};


	scope.displayOptions = function( e ) {
		dialog.open();
	};

	scope.displayTransliterationOptions = function( e ) {
		transliterationDialog.open();
	};
	

	scope.decTextSize = function( e ) {
		var fontSize = parseInt( jQuery( '#PageContent-Writer-Content' ).css( 'font-size' ).replace( 'px', '' ) );
		var newFontSize = fontSize - 2;
		if( newFontSize < 10 )
			newFontSize = 10;
		jQuery( '#PageContent-Writer-Content' ).css( 'font-size', newFontSize + 'px' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
	};

	scope.incTextSize = function( e ) {
		var fontSize = parseInt( jQuery( '#PageContent-Writer-Content' ).css( 'font-size' ).replace( 'px', '' ) );
		var newFontSize = fontSize + 2;
		if( newFontSize > 30 )
			newFontSize = 30;
		jQuery( '#PageContent-Writer-Content' ).css( 'font-size', newFontSize + 'px' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
		setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
	};


	scope.displayPage = function( e ) {
		if( e.target.value == scope.pageNo ) {
			// Do Nothing
		} else if( checkDirty() ) {
			e.target.value = scope.pageNo;
		} else {
			scope.pageNo = e.target.value;
			updateAndPrefetchContent();
		}
	};
	
	scope.displayPrevious = function( e ) {
		if( !checkDirty() ) {
			scope.pageNo--;
			document.querySelector( 'paper-slider' ).value = scope.pageNo;
			updateAndPrefetchContent();
		}
	};

	scope.displayNext = function( e ) {
		if( !checkDirty() ) {
			scope.pageNo++;
			document.querySelector( 'paper-slider' ).value = scope.pageNo;
			updateAndPrefetchContent();
		}
	};

	
	function checkDirty() {
		return CKEDITOR.instances[ 'PageContent-Writer-Content' ].checkDirty()
				&& !confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." );
	}
	
	function updateAndPrefetchContent() {
		document.querySelector( '#Polymer-Window' ).scroller.scrollTop = 0;
		updateContent();
		prefetchContent();
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getReaderPageUrl() }' );
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getWriterPageUrl() }' );
	}
	
	function updateContent() {
		if( pageNoDisplayed == scope.pageNo )
			return;
			
		if( contentArray[scope.pageNo] == null ) {
			loading( true );
			ajaxGet.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI' } );
			ajaxGet.go();
		} else {
			document.querySelector( '#PageContent-Writer-Content' ).innerHTML = contentArray[scope.pageNo];
			loading( false );
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
			setReadOnly( true );
			ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:ckEditor.getData() } );
			ajaxPut.go();
		}
	};
	
	scope.addPageAfter = function( e ) {
		dialog.close();
		if( !checkDirty() ) {
			setReadOnly( true );
			ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1, contentType:'PRATILIPI', pageContent:'', insertNew:true } );
			ajaxPut.go();
		}
	};
	
	scope.addPageBefore = function( e ) {
		dialog.close();
		if( !checkDirty() ) {
			setReadOnly( true );
			ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:'', insertNew:true } );
			ajaxPut.go();
		}
	};
	
	scope.deletePage = function( e ) {
		dialog.close();
		if( confirm( "Are you sure you want to delete this page ?" ) ) {
			setReadOnly( true );
			ajaxPut.body = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI', pageContent:'' } );
			ajaxPut.go();
		}
	};
	

	scope.handleAjaxGetResponse = function( event, response ) {
		if( contentArray[response.response['pageNo']] == null ) {
			contentArray[response.response['pageNo']] = response.response['pageContent'];
			updateContent();
		}
	};
	
	scope.handleAjaxGetError = function( event, response ) {
		updateContent();
	};


	scope.handleAjaxPutResponse = function( event, response ) {
		setReadOnly( false );
		scope.pageNo = response.response['pageNo'];
		if( scope.pageCount != response.response['pageCount'] ) {
			contentArray = [];
			pageNoDisplayed = 0;
			scope.pageCount = response.response['pageCount'];
			if( scope.pageNo > scope.pageCount )
				scope.pageNo = scope.pageCount;
			updateAndPrefetchContent();
		} else {
			contentArray[scope.pageNo] = ckEditor.getData();
			pageNoDisplayed = 0;
			updateContent();
		}
	};

	scope.handleAjaxPutError = function( event, response ) {
		setReadOnly( false );
		if( response.xhr.status == 401 ) {
			document.querySelector( '#PageContent-Writer-SessionExpired' ).open();
		} else if( response.xhr.status == 500 ) {
			document.querySelector( '#PageContent-Writer-ServerError' ).open();
		}
	}


	scope.enableHindiTransliteration = function( e ) {
		enableTransliteration( "Hindi" );
		transliterationDialog.close();
	}

	scope.enableGujaratiTransliteration = function( e ) {
		enableTransliteration( "Gujarati" );
		transliterationDialog.close();
	}

	scope.enableTamilTransliteration = function( e ) {
		enableTransliteration( "Tamil" );
		transliterationDialog.close();
	}
	
	function enableTransliteration( language ) {
		var s = document.createElement('script');
		var p = document.location.protocol;
		s.setAttribute( 'src', p + '//ytranslitime-widgets.zenfs.com/ytimanywhere/YTimAnywhere_' + language + '.js' );
		s.setAttribute( 'type', 'text/javascript' );
		document.getElementsByTagName( 'head' )[0].appendChild( s ); 
	}
	

	function setReadOnly( bool ) {
		scope.disabled = bool;
		ckEditor.setReadOnly( bool );
	}
	
	
	addEventListener( 'template-bound', function( e ) {
		if( e.target != scope )
			return;
		dialog = document.querySelector( '#PageContent-Writer-Options' );
		transliterationDialog = document.querySelector( '#PageContent-Writer-TransliterationOptions' );
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
	});
	
</script>


<style>

	body {
	  overflow: hidden;
	}

</style>


<!-- PageContent :: Writer :: End -->