<!-- PageContent :: Writer :: Start -->

<script src="//cdn-asia.pratilipi.com/third-party/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8"></script>

<#if ( pageNo > 1 )>
	<#assign previousPageUrl= "/write?id=${ pratilipiData.getId()?c }&page=" +(pageNo -1) >
</#if>
<#if ( pageNo < pageCount )>
	<#assign nextPageUrl= "/write?id=${ pratilipiData.getId()?c }&page=" +(pageNo+1) >
</#if>


<div class="bg-green">
	<table style="width: 100%;color: white; height: 64px;">
		<tr>
			<td>
				<div style="margin-left: 5px; margin-right:5px;">
					<div id="PratilipiContent-WriterBasic-ExitButton" class="writer-Button" onclick="window.location.href='${ exitUrl ! pratilipiData.getPageUrl() }'" style="float: left; margin-right: 10px; padding-top: 19px;" >
						<img src="/theme.pratilipi/images/left.png" />
					</div>
					<p style="margin : 0px; font-color: white;font-size: 1.3em; line-height: 64px;">
						${ pratilipiData.getTitle() }
					</p>
				</div>
			</td>
			<td valign="middle" style="margin: 0px; position:relative;">
				<div style="float: right; margin-right: 10px; padding-right:10px; position:relative;text-align: right; width: 250px;">
					<a href='#' id="PratilipiContent-writer-dropdown" style="color: white; position:relative; text-decoration: none;">Menu</a>
					<ul id="PratilipiContent-writer-menu" style="padding-left: 50px; position:absolute; width:100%; z-index:1;">
						<li class="menuItem" onclick="decreaseSize( event );">Decrease Text Size</li>
						<li class="menuItem" onclick="increaseSize( event );">Increase Text Size</li>
						<li class="menuItem" onclick="addPageBefore();">Add Page Before This Page</li>
						<li class="menuItem" onclick="addPageAfter();">Add Page  After This Page</li>
						<li class="menuItem" onclick="deletePage();">Delete Page</li>
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
					<div id="PratilipiContent-Reader-Content" class="paper" contenteditable="true" style="font-size:${ contentSize }"> ${ pageContent } </div>
				<#else>
					<div id="PratilipiContent-Reader-Content" class="paper" contenteditable="true"> ${ pageContent } </div>
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
	<#if nextPageUrl?? >
		<div id="PratilipiContent-WriterBasic-NextPageButton" class="bg-green writer-Button" onclick="displayNextPage();" style="margin-left:10px;">
			<img src="/theme.pratilipi/images/next.png" title="Next Page" />
		</div>
	</#if>
	<#if previousPageUrl?? >
		<div id="PratilipiContent-WriterBasic-PreviousPageButton" class="bg-green writer-Button" onclick="displayPreviousPage();">
			<img src="/theme.pratilipi/images/previous.png" title="Previous Page" />
		</div>
	</#if>
	<div id="PratilipiContent-WriterBasic-SaveButton" class="writer-Button" onclick="savePage();" style="float:left;">
		<img src="/theme.pratilipi/images/buttons/save-white.png" title="Save Changes" />
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
		
		.menuItem {
			display: none;
			color: white;
			background-color : #a7d7a7;
			width: 100%;
			padding-right: 10px;
			pointer: cursor;
		}
		
		.menuItem:hover {
			background-color: #259B24;
			color: white;
			text-decoration: none;
		}
		
</style>


<script language="javascript">

var pageNo = ${ pageNo };
var pageCount = ${ pageCount };
var pageNoDisplayed;
var contentArray = [];

var ckEditor; // Initialized in initWriter()
var isEditorDirty;
CKEDITOR.disableAutoInline = true;

function initWriter() {
	try {
		ckEditor = CKEDITOR.inline( document.getElementById( 'PratilipiContent-Reader-Content' ) ); 
		CKEDITOR.config.toolbar = [
				['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
				['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
				['NumberedList','BulletedList'],
				['Blockquote','Smiley','HorizontalRule'],
				['Link','Unlink'],
				['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
				['ShowBlocks','Maximize']
		];
		ckEditor.on('instanceReady', function(){ 
			//updateContent();
			//prefetchContent();
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

var dropDown = document.getElementById( "PratilipiContent-writer-dropdown" );
dropDown.addEventListener('click', function( event ) {
	var event = event || window.event;
    event.stopPropagation();
    jQuery( this ).parent().find( ".menuItem" ).slideToggle();
});


/* AJAX FUNCTIONS START 
 * Ajax functionality is still not working. $.ajax() GET call is not working properly.	
 */ 

function setPageNo(){
	alert( "SET PAGENO CALLED" );
	jQuery( "#PratilipiContent-Writer-PageNumber" ).innerHTML = pageNo;
}

function setPageCount(){
	alert( "SET PAGE COUNT CALLED" );
	jQuery( "#PratilipiContent-Writer-PageCount" ).innerHTML = pageCount;
}

function prefetchContent(){
	alert( "PREFETCH CONTENT CALLED" );
	if( pageNo > 1 && contentArray[ pageNo - 1 ] == null )
		getPage( pageNo - 1 );
	
	if( pageNo < pageCount && contentArray[ pageNo + 1 ] == null )
		getPage( pageNo + 1 );
}

function updateContent(){
	alert( "UPDATE CONTENT" );
	if( pageNoDisplayed == pageNo )
		return;
	
	if( contentArray[ pageNo ] == null){
		jQuery( '#PratilipiContent-Writer-Content' ).innerHTML = "<div style='text-align:center'>Loading ...</div>";
		getPage( pageNo );
	}
	else {
		jQuery( '#PratilipiContent-Writer-Content' ).innerHTML = contentArray[ pageNo ];
		pageNoDisplayed = pageNo;
	}
	
	ckEditor.resetDirty();
	ckEditor.resetUndo();
	isEditorDirty = false; 
}


function checkDirtyAndUpdatePage( pageNumber ){
	if( isEditorDirty && 
			confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		alert( "CHECKDIRTY AND UPDATE PAGE CALLED" );
		pageNo = pageNumber;
		updatePage();
		setCookie( '${ pageNoCookieName }', pageNo );
	}
	else{
		alert( "CHECKDIRTYANDUPDATEPAGE CALLED" );
		pageNo = pageNumber;
		updatePage();
		setCookie( '${ pageNoCookieName }', pageNo );
	}		
}

function getPage( pageNumber ){
	jQuery.ajax({
		url: "/api.pratilipi/pratilipi/content",
		type: "GET",
		dataType: "json",
		handleAs: "json",
		data: 
			JSON.stringify({
				pratilipiId: ${ pratilipiData.getId()?c }, 
				pageNo: pageNumber, 
				contentType:'PRATILIPI'
			}),
		beforeSend: function( data, object ){
			readOnlyMode();
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

function updatePage(){
	alert( "UPDATE PAGE CALLED" );
	updateContent();
	setPageNo();
	setPageCount();
	preFetchContent();
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
				contentType:'PRATILIPI', 
				pageContent:ckEditor.getData() 
			}),
		beforeSend: function( data, object ){
			readOnlyMode();
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
			console.log( response.response );
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
					contentType:'PRATILIPI', 
					pageContent:'',
					insertNew: true 
				}),
			beforeSend: function( data, object ){
				readOnlyMode();
			},
			success: function( response, status, xhr ) {
				location.reload();
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				alert( response );
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
					contentType:'PRATILIPI', 
					pageContent:'',
					insertNew: true
				}),
			beforeSend: function( data, object ){
				readOnlyMode();
			},
			success: function( response, status, xhr ) {
				pageNo = response[ 'pageNo' ];
				window.location.href="/write?id=${ pratilipiData.getId()?c }&page=" + pageNo;
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				alert( response[ 'pageNo' ] + "/" +  response[ 'pageCount' ] );
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
					contentType:'PRATILIPI', 
					pageContent:''
				}),
			beforeSend: function( data, object ){
				readOnlyMode();
			},
			success: function( response, status, xhr ) {
				location.reload();
			}, 
			error: function( xhr, status, error) {
				alert( status + " : " + error );
			},
			complete: function( event, response ){
				console.log( response );
			}
		});
	}
}

function handleAjaxPutResponse( response ){
	pageNo = response[ 'pageNo' ];
	alert( pageNo + "/" + response[ 'pageNo' ] + "/" + pageCount + "/" + response[ 'pageCount' ] );
	if( pageCount != response[ 'pageCount' ] ){
		alert( "Page added or deleted" );
		contentArray = [];
		pageNoDisplayed = 0;
		pageCount = response[ 'pageCount' ];
		if( pageNo > pageCount )
			pageNo = pageCount;
		checkDirtyAndUpdatePage( pageNo );
	} else {
		alert( "No new pages added" );
		contentArray[pageNo] = ckEditor.getData();
		pageNoDisplayed = 0;
		updatePage();
	}
}

function handleAjaxGetResponse( response ){
	if( contentArray[response['pageNo']] == null ) {
		contentArray[response['pageNo']] = response['pageContent'];
		updatePage();
	}
}

/* AJAX FUNCTIONS END */


/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIE */
function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365 );
}

function displayNextPage(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		window.location.href= '${ nextPageUrl }';
	}
}

function displayPreviousPage(){
	if( isEditorDirty && 
			!confirm( "You haven't saved your changes yet ! Press 'Cancel' to go back and save your changes. Press 'Ok' to discard your changes and continue." )) {
		return;
	} else {
		window.location.href= '${ previousPageUrl }';
	}
}

function readOnlyMode(){
	jQuery( "#PratilipiContent-WriterBasic-ExitButton").attr('disabled', 'disabled');
	jQuery( "#PratilipiContent-writer-dropdown").attr('disabled', 'disabled');
	jQuery( "#Pratilipi-Write-Basic").attr('disabled', 'disabled');
	jQuery( "#PratilipiContent-WriterBasic-NextPageButton").attr('disabled', 'disabled');
	jQuery( "#PratilipiContent-WriterBasic-PreviousPageButton").attr('disabled', 'disabled');
	jQuery( "#PratilipiContent-WriterBasic-SaveButton").attr('disabled', 'disabled');
}

function setMinWriterWidth(){
	var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var writeDiv = document.getElementById( '' );
	if( windowsize > 800 )
		jQuery( "#PratilipiContent-Reader-Content" ).width( 1000 );
	else
		jQuery( "#PratilipiContent-Reader-Content" ).width( windowsize );
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

<!-- JAVASCRIPT END ->
<!-- PageContent :: Writer :: End -->