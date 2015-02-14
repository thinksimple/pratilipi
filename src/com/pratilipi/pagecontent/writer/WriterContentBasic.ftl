<!-- PageContent :: Writer :: Start -->

<div class="bg-green">	
	<table style="width: 100%;color: white; height: 64px;">
		<tr>
			<td style="width: 90%;">
				<div style="margin-left: 5px; margin-right:5px;">
					<div id="PratilipiContent-WriterBasic-ExitButton" class="writer-Button" onclick="exitReader();" style="float: left; margin-right: 10px; padding-top: 19px;" >
						<img src="/theme.pratilipi/images/left.png" title="Exit" />
					</div>
					<p style="margin : 0px; font-color: white;font-size: 1.3em; line-height: 64px;">
						${ pratilipiData.getTitle() }
					</p>
				</div>
			</td>
			<td valign="middle" style="margin: 0px; position:relative;">
				<div style="float: right; margin-right: 10px; position:relative;text-align: right; width: 200px;">
					<a href='#' id="PratilipiContent-writer-dropdown" onclick="dropDown( event, this );" style="color: white; position:relative; text-decoration: none;">
						<img src="/theme.pratilipi/images/buttons/menu-white.png" title="Options" />
					</a>
					<ul id="PratilipiContent-writer-menu" style="padding-left: 0px; position:absolute; width:100%; z-index:1; ">
						<li class="menuItem" onclick="decreaseSize( event );">Decrease Text Size</li>
						<li class="menuItem" onclick="increaseSize( event );">Increase Text Size</li>
						<li class="menuItem" onclick="addPageBefore();">Add Page Before This Page</li>
						<li class="menuItem" onclick="addPageAfter();">Add Page  After This Page</li>
						<li class="menuItem" onclick="deletePage();">Delete Page</li>
						<div id="PratilipiContent-writer-Languages"  onclick="subMenu( event, this );" style="position: relative;">
							<li id="PratilipiContent-writer-Languages-Text" class="menuItem">Typing Language</li>
							<ul id="PratilipiContent-writer-Languages-SubMenu" style="position: absolute; width: 60%; top: 0px; right: 100%;">
								<li class="subMenuItem" onclick="enableTransliteration( this );">Gujarati</li>
								<li class="subMenuItem" onclick="enableTransliteration( this );">Hindi</li>
								<li class="subMenuItem" onclick="enableTransliteration( this );">Tamil</li>
							</ul> 
						</div>
						<li class="menuItem">Upload Word Document</li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</div>
<div id="Pratilipi-Write-Basic" class="bg-gray">
	<table style="margin-left: auto; margin-right: auto;">
		<tr>
			<td>
			<#if pratilipiData.getContentType() == "PRATILIPI" >
				<#if contentSize??>
					<div id="PratilipiContent-Writer-Content" class="paper" contenteditable="true" style="font-size:${ contentSize }"> ${ pageContent } </div>
				<#else>
					<div id="PratilipiContent-Writer-Content" class="paper" contenteditable="true"> ${ pageContent } </div>
				</#if>
			</#if>
			</td>
		</tr>
		<tr>
			<td>
				<div class="green" style="margin: 5px; text-align: center;">
					<span id="PratilipiContent-Writer-PageNumber" style="display: inline; line-height: 28px; ">${ pageNo }</span> /
					<span id="PratilipiContent-Writer-PageCount" style="display: inline; line-height: 28px; ">${ pageCount }</span>
				</div>
			</td>
		</tr>
	</table>
</div>
<div style="width: 100%; position: fixed; bottom: 0px; right: 0px;padding-right: 3%; padding-bottom: 10px; padding-top: 10px; padding-left: 4%;">
	<div id="PratilipiContent-WriterBasic-NextPageButton" class="bg-green writer-Button" onclick="displayNextPage();" style="margin-left:10px;">
		<img src="/theme.pratilipi/images/next.png" title="Next Page" />
	</div>
	<div id="PratilipiContent-WriterBasic-PreviousPageButton" class="bg-green writer-Button" onclick="displayPreviousPage();">
		<img src="/theme.pratilipi/images/previous.png" title="Previous Page" />
	</div>
	<div id="PratilipiContent-WriterBasic-SaveButton" class="writer-Button" onclick="savePage();" style="float:left;">
		<img src="/theme.pratilipi/images/buttons/save-white.png" title="Save Changes" />
	</div>
	<div id="PratilipiContent-WriterBasic-SaveButton" class="bg-green writer-Button" onclick="goToReader();" style="float:left; margin-left:10px;">
		<img src="/theme.pratilipi/images/buttons/launch-white.png" title="View on Reader" />
	</div>
</div>

<!-- JAVASCRIPT START -->
<style type="text/css">
		@media print{
			body {display:none;}
		}
		
		.writer-Button {
			float: right;
			width: 40px;
			height: 40px;
			text-align: center;
			padding-top: 7px;
			cursor: pointer;
		}
		
		.menuItem, .subMenuItem {
			display: none;
			color: white;
			background-color : #a7d7a7;
			width: 100%;
			padding: 2px 0px 2px 5px;
			text-align: left;
			font-size: 15px;
			cursor: pointer; 
		}
		
		.menuItem:hover, .subMenuItem:hover {
			background-color: #259B24;
			color: white;
			text-decoration: none;
		}
		
		.disableYtimBtn {
			height: 27px !important;
			width: 27px !important;
		}
		
</style>


<script language="javascript">

<!-- TRANSLITERATION SCRIPT STARTS -->
function enableTransliteration( object ) {

	jQuery( ".menuItem" ).slideToggle();
	var language = jQuery( object ).text();
	var s = document.createElement('script');
	var p = document.location.protocol;
	s.setAttribute( 'src', p + '//ytranslitime-widgets.zenfs.com/ytimanywhere/YTimAnywhere_' + language + '.js' );
	s.setAttribute( 'type', 'text/javascript' );
	document.getElementsByTagName( 'head' )[0].appendChild( s ); 
}
<!-- TRANSLITERATION SCRIPT ENDS -->

var pageNo = ${ pageNo };
var pageCount = ${ pageCount };
var pageNoDisplayed;
var contentArray = [];

var ckEditor; // Initialized in initWriter()
var isEditorDirty;

function initWriter() {
	try {
		ckEditor = CKEDITOR.replace( document.getElementById( 'PratilipiContent-Writer-Content' ), { 
				height: 450
				filebrowserImageBrowseUrl: '/filebrowser?folder=${ resourceFolder }&urlPrefix=/api.pratilipi/pratilipi/resource?pratilipiId=${ pratilipiData.getId()?c }&name=',
				filebrowserImageUploadUrl: '/api.pratilipi/pratilipi/resource?pratilipiId=${ pratilipiData.getId()?c }',
				} ); 
		CKEDITOR.config.toolbar = [
				['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
				['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
				['NumberedList','BulletedList'],
				['Blockquote','Smiley','HorizontalRule'],
				['Image'],
				['Link','Unlink'],
				['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
				['ShowBlocks','Maximize']
		];
		ckEditor.on('instanceReady', function(){ 
			updateContent();
			prefetchContent();
		});
		CKEDITOR.on( 'dialogDefinition', function( ev ){
			var dialogName = ev.data.name;
			var dialogDefinition = ev.data.definition;
			if ( dialogName == 'image' )
			{
				dialogDefinition.removeContents( 'advanced' );
				dialogDefinition.removeContents( 'Link' );
			}
		});
		ckEditor.on('change', function(){
			isEditorDirty = ckEditor.checkDirty();
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).removeClass( "bg-green" );
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).addClass( "bg-red" );
		});
	} catch( err ) {
		console.log( 'Writer initialization failed with error - ' + '\"' + err.message + '\". Retrying in 100ms ...' );
		window.setTimeout( initWriter, 100 );
	}
}
initWriter();



function dropDown( event, object ){
	var event = event || window.event;
    event.stopPropagation();
    jQuery( object ).parent().find( ".menuItem" ).slideToggle();
    if( jQuery( "#PratilipiContent-writer-Languages-SubMenu" ).find( ".subMenuItem" ).is( ":visible" ))
    	jQuery( "#PratilipiContent-writer-Languages-SubMenu" ).find( ".subMenuItem" ).slideToggle();
}

function subMenu( event, object ){
	var event = event || window.event;
	event.stopPropagation();
	jQuery( object ).find( ".subMenuItem" ).slideToggle();
}

/* AJAX FUNCTIONS START */ 

function setPageNo(){
	jQuery( "#PratilipiContent-Writer-PageNumber" ).html( pageNo );
}

function setPageCount(){
	jQuery( "#PratilipiContent-Writer-PageCount" ).html( pageCount );
}

function prefetchContent(){
	if( pageNo > 1 && contentArray[ pageNo - 1 ] == null )
		getPage( pageNo - 1 );
	
	if( pageNo < pageCount && contentArray[ pageNo + 1 ] == null )
		getPage( pageNo + 1 );
}

function updateContent(){
	if( pageNoDisplayed == pageNo )
		return;
	
	if( contentArray[ pageNo ] == null){
		loading( true );
		getPage( pageNo );
	}
	else {
		jQuery( '#PratilipiContent-Writer-Content' ).html( contentArray[ pageNo ] );
		loading( false );
		pageNoDisplayed = pageNo;
	}
	
	ckEditor.resetDirty();
	ckEditor.resetUndo();
	isEditorDirty = false; 
}



function getPage( pageNumber ){
	jQuery.ajax({
		url: "/api.pratilipi/pratilipi/content",
		type: "GET",
		contentType: "application/json",
		dataType: "json",
		handleAs: "json",
		data: 'pratilipiId=${ pratilipiData.getId()?c }&pageNo=' + pageNumber,
		beforeSend: function( data, object ){
		},
		success: function( response, status, xhr ) {
			handleAjaxGetResponse( response );
		}, 
		error: function( xhr, status, error) {
			alert( status + " : " + error );
		},
		complete: function( event, response ){
			console.log( response );
		}
	});
}

function updateDisplay(){
	jQuery('html, body').animate({scrollTop: '0px'}, 300);
	setMinWriterWidth();
	updateContent();
	setPageNo();
	setPageCount();
	jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).removeClass( "bg-red" );
	jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).removeClass( "bg-green" );
	prefetchContent();
	setCookie( '${ pageNoCookieName }', pageNo, 365, '${ pratilipiData.getReaderPageUrl() }' );
	setCookie( '${ pageNoCookieName }', pageNo, 365, '${ pratilipiData.getWriterPageUrl() }' );
}

function savePage(){
	jQuery.ajax({
		url: "/api.pratilipi/pratilipi/content",
		type: "PUT",
		contentType: "application/json",
		dataType: "json",
		handleAs: "json",
		data: 
			JSON.stringify({
				pratilipiId: ${ pratilipiData.getId()?c }, 
				pageNo: pageNo, 
				pageContent:ckEditor.getData() 
			}),
		beforeSend: function( data, object ){
			setReadOnly( true );
		},
		success: function( response, status, xhr ) {
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).removeClass( "bg-red" );
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).addClass( "bg-green" ).delay( 4000 );
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).fadeOut( "slow", function(){
				jQuery( this ).removeClass( "bg-green" );
			});
			jQuery( "#PratilipiContent-WriterBasic-SaveButton" ).fadeIn( "fast" );
		}, 
		error: function( xhr, status, error) {
			alert( status + " : " + error );
		},
		complete: function( event, response ){
			setReadOnly( false );
			console.log( response );
		}
	});
}

function addPageBefore(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {	
		jQuery.ajax({
			url: "/api.pratilipi/pratilipi/content",
			type: "PUT",
			contentType: "application/json",
			dataType: "json",
			handleAs: "json",
			data: 
				JSON.stringify({
					pratilipiId: ${ pratilipiData.getId()?c }, 
					pageNo: pageNo,
					pageContent:'',
					insertNew: true 
				}),
			beforeSend: function( data, object ){
				setReadOnly( true );
			},
			success: function( response, status, xhr ) {
				handleAjaxPutResponse( response );
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				setReadOnly( false );
				console.log( response );
			}
		});
	}
}

function addPageAfter(){
	if( isEditorDirty && 
			confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {	
		jQuery.ajax({
			url: "/api.pratilipi/pratilipi/content",
			type: "PUT",
			contentType: "application/json",
			dataType: "json",
			handleAs: "json",
			data: 
				JSON.stringify({
					pratilipiId: ${ pratilipiData.getId()?c }, 
					pageNo: pageNo + 1, 
					pageContent:'',
					insertNew: true
				}),
			beforeSend: function( data, object ){
				setReadOnly( true );
			},
			success: function( response, status, xhr ) {
				handleAjaxPutResponse( response );
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				setReadOnly( false );
				console.log( response );
			}
		});
	}
}

function deletePage(){
	if( confirm( "Are you sure you want to delete this page ?" )){
		jQuery.ajax({
			url: "/api.pratilipi/pratilipi/content",
			type: "PUT",
			contentType: "application/json",
			dataType: "json",
			handleAs: "json",
			data: 
				JSON.stringify({
					pratilipiId: ${ pratilipiData.getId()?c }, 
					pageNo: pageNo, 
					pageContent:''
				}),
			beforeSend: function( data, object ){
				setDisabled( true );
			},
			success: function( response, status, xhr ) {
				handleAjaxPutResponse( response );
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				setReadOnly( false );
				console.log( response );
			}
		});
	}
}

function handleAjaxPutResponse( response ){
	pageNo = response[ 'pageNo' ];
	if( pageCount != response[ 'pageCount' ] ){
		contentArray = [];
		pageNoDisplayed = 0;
		pageCount = response[ 'pageCount' ];
		if( pageNo > pageCount )
			pageNo = pageCount;
		updateDisplay();
	} else {
		alert( "No new pages added" );
		contentArray[pageNo] = ckEditor.getData();
		pageNoDisplayed = 0;
		updateDisplay();
	}
}

function handleAjaxGetResponse( response ){
	if( contentArray[response['pageNo']] == null ) {
		contentArray[response['pageNo']] = response['pageContent'];
		updateContent();
	}
}

function displayNextPage(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		if( pageNo < pageCount ){
			pageNo = pageNo + 1;
			pageNoDisplayed = 0;
			updateDisplay();
		}
		else
			alert( "You have reached last page" );
	}
}

function displayPreviousPage(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		if( pageNo > 1 ){
			pageNo = pageNo - 1;
			pageNoDisplayed = 0;
			updateDisplay();
		}
		else
			alert( "This is first page" );
	}
}

/* AJAX FUNCTIONS END */


function exitReader(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	} 
}

/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIE */
function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365 );
}

function setReadOnly( bool ){
	loading( bool );
	ckEditor.setReadOnly( bool );
}

function goToReader(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		window.location.href="${ pratilipiData.getReaderPageUrl() }";
	}
}

function setMinWriterWidth(){
	var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	if( windowsize > 800 )
		jQuery( "#PratilipiContent-Writer-Content" ).width( 1000 );
	else
		jQuery( "#PratilipiContent-Writer-Content" ).width( windowsize - 10 );
}

/* Zoom Support */

function increaseSize( event ){
	event.stopPropagation();
	var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
	if( wordContent ) {
		/* For word content */
		var fontSizeStr = wordContent.style.fontSize;
		
		/* By default font size of paragraphs in content is not set. Hence initializing fontSizeStr to default font-size of p tags across website */
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		wordContent.style.fontSize = ( fontSize + 2 ) + "px";
		
		setCookie( '${ contentSizeCookieName }', ( fontSize + 2 ) + 'px', 365 );
	}

}
	
function decreaseSize( event ){
	event.stopPropagation();
	var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
	if( wordContent ){
		/* For word content */
		var fontSizeStr = wordContent.style.fontSize;
		
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		wordContent.style.fontSize = ( fontSize - 2 ) + "px";

		setCookie( '${ contentSizeCookieName }', ( fontSize - 2 ) + 'px', 365 );
	}
}

if( window.attachEvent){ //for IE8 and below
	window.attachEvent( 'onload', function( event ){
		document.attachEvent("onkeyup onkeypress", function(e){
	    if( e.ctrlKey && ( e.keyCode == 80 ) ){
		        return false;
		    }
		});
		setMinWriterWidth();
		saveAutoBookmark();
	});
	
	document.attachEvent( 'onclick', function( event ){
		var elements = document.getElementById( "PratilipiContent-writer-menu" ).getElementsByTagName( "li" );
		for( i=0; i< elements.length; i++ )
			elements[i].style.display = 'none';
	});
} else { 
	window.addEventListener( 'load', function( event ){
		document.addEventListener("keyup keypress", function(e){
	    if( e.ctrlKey && ( e.keyCode == 80 ) ){
		        return false;
		    }
		});
		setMinWriterWidth();
		saveAutoBookmark();
	});
	
	document.addEventListener( 'click', function( event ){
		var elements = document.getElementById( "PratilipiContent-writer-menu" ).getElementsByTagName( "li" );
		for( i=0; i< elements.length; i++ )
			elements[i].style.display = 'none';
	});
}
</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Writer :: End -->