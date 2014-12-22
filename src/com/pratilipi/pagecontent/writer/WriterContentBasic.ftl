<!-- PageContent :: Writer :: Start -->

<script src="//cdn-asia.pratilipi.com/third-party/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8"></script>

<#if ( pageNo > 1 )>
	<#assign previousPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNo -1) >
</#if>
<#if ( pageNo < pageCount )>
	<#assign nextPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNo+1) >
</#if>


<div class="bg-green">
	<table style="width: 100%;color: white; height: 64px;">
		<tr>
			<td>
				<#-- Title -->
				<div style="margin-left: 5px; margin-right:5px;">
					<div class="PratilipiContent-ReaderBasic-button" onclick="window.location.href='${ exitUrl ! pratilipiData.getPageUrl() }'" style="float: left; margin-right: 10px; padding-top: 19px;" >
						<img src="/theme.pratilipi/images/left.png" />
					</div>
					<p style="margin : 0px; font-color: white;font-size: 1.3em; line-height: 64px;">
						${ pratilipiData.getTitle() }
					</p>
				</div>
			</td>
			<td valign="middle" style="margin: 0px; position:relative;">
				<div style="float: right; margin-right: 10px; padding-right:10px; position:relative;text-align: right; width: 250px;">
					<a href='#' id="PageContent-writer-dropdown" style="color: white; position:relative; text-decoration: none;">Menu</a>
					<ul id="PageContent-writer-menu" style="padding-left: 50px; position:absolute; width:100%; z-index:1;">
						<li id="PageContent-writer-SubMenu-TextSize" class="menuItem" style="position:relative; height: 22px;" >
							Text Size
							<ul style="position: relative; top: -23px; right: 100%;">
								<li onclick="decreaseSize()" class="subMenuItem">Decrease</li>
								<li onclick="increaseSize()" class="subMenuItem">Increase</li>
							</ul>
						</li>
						<li id="PageContent-writer-SubMenu-AddPage" class="menuItem" style="position:relative; height: 22px;">
							Add Page
							<ul style="position: relative; top: -23px; right: 100%;">
								<li class="subMenuItem">Before This Page</li>
								<li class="subMenuItem">After This Page</li>
							</ul>
						</li>
						<li class="menuItem">Delete Page</li>
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
					<div id="PageContent-Reader-Content" class="paper" contenteditable="true" style="font-size:${ contentSize }"> ${ pageContent } </div>
				<#else>
					<div id="PageContent-Reader-Content" class="paper" contenteditable="true"> ${ pageContent } </div>
				</#if>
			</#if>
			</td>
		</tr>
		<tr>
			<td>
				<div class="green" style="margin: 5px; text-align: center;">
					<p style="display: inline; line-height: 28px; ">${ pageNo } / ${ pageCount }</p>
				</div>
			</td>
		</tr>
	</table>
</div>
<div style="width: 100%; position: fixed; bottom: 0px; right: 0px;padding-right: 3%; padding-bottom: 10px; padding-top: 10px; padding-left: 4%;">
	<#if nextPageUrl?? >
		<div class="bg-green PratilipiContent-ReaderBasic-button" onclick="window.location.href='${ nextPageUrl }'" style="margin-left:10px;">
			<img src="/theme.pratilipi/images/next.png" title="Next Page" />
		</div>
	</#if>
	<#if previousPageUrl?? >
		<div class="bg-green PratilipiContent-ReaderBasic-button" onclick="window.location.href='${ previousPageUrl }'">
			<img src="/theme.pratilipi/images/previous.png" title="Previous Page" />
		</div>
	</#if>
	<div class="PratilipiContent-ReaderBasic-button" style="float:left;">
		<img src="/theme.pratilipi/images/buttons/unsaved.png" title="Save Changes" />
	</div>
</div>

<!-- JAVASCRIPT START -->
<style type="text/css">
		@media print{
			body {display:none;}
		}
		
		.PratilipiContent-ReaderBasic-button {
			float: right;
			width: 40px;
			height: 40px;
			text-align: center;
			padding-top: 7px;
			cursor: pointer;
		}
		
		#PageContent-Reader-Overlay {
			position: absolute;
			top: 0px;
			left: 0px;
			height: 100%;
			width: 100%;
		}
		
		.menuItem, .subMenuItem {
			display: none;
			color: white;
			background-color : #a7d7a7;
			width: 100%;
			padding-right: 10px;
		}
		
		.menuItem:hover , .subMenuItem:hover {
			background-color: #259B24;
			color: white;
			text-decoration: none;
		}
		
</style>


<script language="javascript">

var ckEditor; // Initialized in initWriter()
CKEDITOR.disableAutoInline = true;

function initWriter() {
	try {
		ckEditor = CKEDITOR.inline( document.getElementById( 'PageContent-Reader-Content' ) ); 
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
		
		});
	} catch( err ) {
		console.log( 'Writer initialization failed with error - ' + '\"' + err.message + '\". Retrying in 100ms ...' );
		window.setTimeout( initWriter, 100 );
	}
}
initWriter();

//TODO : Refactoring required
var dropDown = document.getElementById( "PageContent-writer-dropdown" );
dropDown.addEventListener('click', function( event ) {
	var event = event || window.event;
    event.stopPropagation();
    jQuery( this ).find( ".subMenuItem" ).slideUp();
    jQuery( this ).parent().find( ".menuItem" ).slideToggle();
});

var addPageSubMenu = document.getElementById( "PageContent-writer-SubMenu-AddPage" );
addPageSubMenu.addEventListener('click', function( event ) {
	var event = event || window.event;
    event.stopPropagation();
	jQuery( this ).parent().find( ".subMenuItem" ).slideUp();
    jQuery( this ).find( ".subMenuItem" ).slideToggle();
});

var textSizeSubMenu = document.getElementById( "PageContent-writer-SubMenu-TextSize" );
textSizeSubMenu.addEventListener('click', function( event ) {
	var event = event || window.event;
    event.stopPropagation();
    jQuery( this ).parent().find( ".subMenuItem" ).slideUp();
    jQuery( this ).find( ".subMenuItem" ).slideToggle();
});




/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIE */
function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365 );
}

/* Zoom Support */

function increaseSize(){
	var wordContent = document.getElementById( "PageContent-Reader-Content" );
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
	
function decreaseSize(){
	var wordContent = document.getElementById( "PageContent-Reader-Content" );
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
		saveAutoBookmark();
	});
	
	document.attachEvent( 'onclick', function( event ){
		var elements = document.getElementById( "PageContent-writer-menu" ).getElementsByTagName( "li" );
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
		
		saveAutoBookmark();
	});
	
	document.addEventListener( 'click', function( event ){
		var elements = document.getElementById( "PageContent-writer-menu" ).getElementsByTagName( "li" );
		for( i=0; i< elements.length; i++ )
			elements[i].style.display = 'none';
	});
}
</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Writer :: End -->