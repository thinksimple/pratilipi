<!-- PageContent :: Reader :: Start -->

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
			<td valign="middle" style="margin: 0px;">
				<div style="float: right; margin-right: 10px; width: 100%">
					<#if pratilipiData.getIndex()??>
						<div id="Pratilipi-Reader-Basic-ContentTable-Button" class="PratilipiContent-ReaderBasic-button" onclick="showIndexDiv( event )">
							<img src="/theme.pratilipi/images/buttons/menu-white.png" title="Table of Content">
						</div>
					</#if>
					<div class="PratilipiContent-ReaderBasic-button" onclick="increaseSize()">
						<img src="/theme.pratilipi/images/plus.png" title="Increase size">
					</div>
					<div class="PratilipiContent-ReaderBasic-button" onclick="decreaseSize()">
						<img src="/theme.pratilipi/images/minus.png" title="Decrease size">
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
<div id="Pratilipi-Reader-Basic-ContentTable">
		<h3><img src='/theme.pratilipi/images/index.png' style="margin-bottom: 4px; margin-right: 5px;"/>Index</h3>
</div>
<div id="Pratilipi-Reader-Basic" class="bg-gray">
	<table style="margin-left: auto; margin-right: auto;">
		<tr>
			<td>
				<#if pratilipiData.getContentType() == "PRATILIPI" >
					<div class="paper">
						<div style="position:relative">
							<#if contentSize??>
								<div id="PratilipiContent-Reader-Content" style="font-size:${ contentSize }"> ${ pageContent } </div>
							<#else>
								<div id="PratilipiContent-Reader-Content"> ${ pageContent } </div>
							</#if>
							<div id="PratilipiContent-Reader-Overlay"></div>
						</div>
					</div>
				<#elseif pratilipiData.getContentType() == "IMAGE" >			
	
					<div class="paper" style="width:inherit; max-width:none; min-height:inherit; overflow-x:auto;">
						<div style="position:relative">
							<#if contentSize??>
								<div id="PratilipiContent-Reader-Content" style="width: ${ contentSize };">
									<img id="imageContent" src='/api.pratilipi/pratilipi/content/image?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }' style="width:100%"/>
								</div>
								<div id="PratilipiContent-Reader-Overlay" style="width: ${ contentSize };"></div>
							<#else>
								<div id="PratilipiContent-Reader-Content">
									<img id="imageContent" src='/api.pratilipi/pratilipi/content/image?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }' style="width:100%"/>
								</div>
								<div id="PratilipiContent-Reader-Overlay"></div>
							</#if>
						</div>
					</div>
	
				</#if>
			</td>
		</tr>
		<tr>
			<td>
				<div id="PratilipiContent-Reader-PageNumber-Div" class="green" style="margin: 5px;">
					<div id="PratilipiContent-Reader-PageNumber-Display" style="text-align: center; width:70px; border: 1px solid gray; padding: 0px 5px; background-color: white; cursor:pointer;" title="Click To Set Page Number">
						<span id="PratilipiContent-Reader-PageNumber" style="display: inline; line-height: 28px;">${ pageNo }</span> /
						<span id="PratilipiContent-Reader-PageCount" style="display: inline; line-height: 28px; ">${ pageCount }</span>
					</div>
					<div id="PratilipiContent-Reader-PageNumber-Edit" style="display: none;">
						<span style="margin-right: 10px;">Page No. </span>
						<input id="PratilipiContent-Reader-PageNumber-Edit-InputBox" tabindex=0 type="text" style="width: 40px; text-align: center;">
						<div id="PratilipiContent-Reader-PageNumber-Edit-Button" type="submit" style="background-color: #259b24; color: white; display:inline;padding: 2px 5px; cursor: pointer;">Set</div>
					</div>
				</div>
			</td>
		</tr>	
	</table>
</div>
<div style="float: right; position: fixed; bottom: 0px; right: 0px;padding-right: 1.5%; padding-bottom: 10px; padding-top: 10px; ">
	<#if showEditOption && pratilipiData.getContentType() == 'PRATILIPI'>
		<div class="bg-green PratilipiContent-ReaderBasic-button" onclick="goToWriter();" style="margin-left:10px;">
			<img src="/theme.pratilipi/images/buttons/edit-white.png" title="Edit" />
		</div>
	</#if>
	<div class="bg-green PratilipiContent-ReaderBasic-button" onclick="displayNextPage();" style="margin-left:10px;">
		<img src="/theme.pratilipi/images/next.png" title="Next Page" />
	</div>
	<div class="bg-green PratilipiContent-ReaderBasic-button" onclick="displayPreviousPage();">
		<img src="/theme.pratilipi/images/previous.png" title="Previous Page" />
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
		
		#PratilipiContent-Reader-Overlay {
			position: absolute;
			top: 0px;
			left: 0px;
			height: 100%;
			width: 100%;
		}
		
		#Pratilipi-Reader-Basic-ContentTable{
			display: none;
			width:250px;
			height: 90%;
		    z-index:2;
		    float: right;
		    position:absolute;
		    right:-250px;
		    opacity: 1;
		    background-color: #eeeeee;
		    overflow-y: scroll;
		    padding: 10px;
		}
		
		.menuItem {
			padding-left: 10px;
			cursor: pointer;
		}
		
		.menuItem:hover {
			background-color: #CCC;
		}
		
		.indexItem {
			padding: 10px;
			padding-left: 20px;
		}
		
		.indexSubItem {
			padding-left: 30px;
		}

		.selectedIndex {
			background-color: #DDD;
		}
</style>


<script language="javascript">

var pageNo = ${ pageNo };
var pageCount = ${ pageCount };
var pageNoDisplayed = ${ pageNo };
var contentArray = [];
var pageStartTime = new Date();
var campaign = '${ pratilipiData.getType() }' + ":" + '${ pratilipiData.getId()?c }';

function setPageNo(){
	jQuery( "#PratilipiContent-Reader-PageNumber" ).html( pageNo );
}


function updateDisplay() {
	jQuery('html, body').animate({scrollTop: '0px'}, 300);
	updateContent();
	setPageNo();
	markSelectedIndex();
	prefetchContent();
	setCookie( '${ pageNoCookieName }', pageNo, 365, '${ pratilipiData.getReaderPageUrl() }' );
	setCookie( '${ pageNoCookieName }', pageNo, 365, '${ pratilipiData.getWriterPageUrl() }' );
}



<#if pratilipiData.getContentType() == 'PRATILIPI'>
	function updateContent() {
		if( pageNoDisplayed == pageNo )
			return;
		
		if( contentArray[ pageNo ] == null){
			loading( true );
			getPage( pageNo );
		}
		else {
			jQuery( '#PratilipiContent-Reader-Content' ).html( contentArray[ pageNo ] );
			loading( false );
			pageNoDisplayed = pageNo;
		}
	}
	
	function prefetchContent() {
		if( pageNo > 1 && contentArray[ pageNo - 1 ] == null )
			getPage( pageNo - 1 );
		
		if( pageNo < pageCount && contentArray[ pageNo + 1 ] == null )
			getPage( pageNo + 1 );
	}
	function getPage( pageNumber ) {
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
	
	function handleAjaxGetResponse( response ){
		if( contentArray[response['pageNo']] == null ) {
			contentArray[response['pageNo']] = response['pageContent'];
			updateContent();
		}
	}
	
	
	function increaseSize(){
		var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
		var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
			
		var fontSizeStr = wordContent.style.fontSize;
		
		/* By default font size of paragraphs in content is not set. Hence initializing fontSizeStr to default font-size of p tags across website */
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		wordContent.style.fontSize = ( fontSize + 2 ) + "px";
		
		setCookie( '${ contentSizeCookieName }', ( fontSize + 2 ) + 'px', 365 );
	}
	
	function decreaseSize(){
		var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
		var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
			
		var fontSizeStr = wordContent.style.fontSize;
		
		/* By default font size of paragraphs in content is not set. Hence initializing fontSizeStr to default font-size of p tags across website */
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		wordContent.style.fontSize = ( fontSize - 2 ) + "px";
		
		setCookie( '${ contentSizeCookieName }', ( fontSize - 2 ) + 'px', 365 );
	}
	
	function setMinReaderWidth(){
		var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var displayPageNumberDiv = document.getElementById( "PratilipiContent-Reader-PageNumber-Display" );
		var editPageNumberDiv = document.getElementById( "PratilipiContent-Reader-PageNumber-Edit" )
		if( windowsize > 800 ){
			jQuery( ".paper" ).width( "1000px" );
			displayPageNumberDiv.style.marginLeft = "auto";
			displayPageNumberDiv.style.marginRight = "auto";
			editPageNumberDiv.style.textAlign = "center";
		}
		else
			jQuery( ".paper" ).width( ( windowsize - 10 ) + "px" );
	}
	
<#elseif pratilipiData.getContentType() == 'IMAGE'>

	function loadImage( pageNumber ){
		var img = "<img id='imageContent' src='/api.pratilipi/pratilipi/content/image?pratilipiId=${ pratilipiData.getId()?c }&pageNo=" + pageNo + "' style='width:100%' />";
		jQuery(img).on( 'load', function() {
			contentArray[pageNo] = img;
			updateContent();
		});
	}
	
	function updateContent() {
		if( pageNoDisplayed == pageNo )
			return;
		
		if( contentArray[ pageNo ] == null){
			loading( true );
			loadImage( pageNo );
		}
		else {
			jQuery( '#PratilipiContent-Reader-Content' ).html( contentArray[ pageNo ] );
			loading( false );
			pageNoDisplayed = pageNo;
		}
	}
	
	function prefetchContent() {
		if( pageNo > 1 && contentArray[ pageNo - 1 ] == null )
			loadImage( pageNo - 1 );
		
		if( pageNo < pageCount && contentArray[ pageNo + 1 ] == null )
			loadImage( pageNo + 1 );
	}
	
	
	function increaseSize(){
		var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var imageContent = document.getElementById( "imageContent" );
		var basicReader = document.getElementById( "PratilipiContent-Reader-Content" );
		var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
		var widthStr = basicReader.style.width;
		
		if( !widthStr )
			widthStr = "1000px"; 
		
		var width = parseInt( widthStr.substring( 0, widthStr.indexOf( 'p' )) );
		 
		/* For Image content */
		basicReader.style.width = ( width + 50 ) + "px";
		overlay.style.width = basicReader.style.width;
		basicReader.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', basicReader.style.width, 365 );
	}
	
	function decreaseSize(){
		var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var imageContent = document.getElementById( "imageContent" );
		var basicReader = document.getElementById( "PratilipiContent-Reader-Content" );
		var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
		var widthStr = basicReader.style.width;
		
		if( !widthStr )
			widthStr = "1000px"; 
		
		var width = parseInt( widthStr.substring( 0, widthStr.indexOf( 'p' )) );
		 
		/* For Image content */
		basicReader.style.width = ( width - 50 ) + "px";
		overlay.style.width = basicReader.style.width;
		basicReader.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', basicReader.style.width, 365 );
	}
	
	function setMinReaderWidth(){}
</#if>

/* CONTENT DIV FUNCTIONS */
function showIndexDiv( event ){
	var event = event || window.event;
	event.stopPropagation();
    var hidden = $('#Pratilipi-Reader-Basic-ContentTable');
    if (hidden.hasClass('visible')){
        hidden.animate({"right":"-250px"}, "slow", function(){ hidden.css( "display", "none" ); }).removeClass( 'visible' );
    } else {
    	hidden.css( "display", "block" );
        hidden.animate({"right":"0px"}, "slow").addClass( 'visible' );
    }
}

function setIndexDiv( index ){
	var contentTableDiv = jQuery( "#Pratilipi-Reader-Basic-ContentTable" );
	for( var i = 0; i < index.length; i++ ){
		var div = document.createElement( "div" );
		if( index[i].title != '' && index[i].pageNo != '' && index[i].level == 1 ){
			var item = "<div class='menuItem indexItem indexSubItem' data-pageNo=" + index[i].pageNo + " onclick='indexClicked(" + index[i].pageNo + ");'>" + 
							index[i].title + 
							"</div>";
			contentTableDiv.append( item ); 
		}
		
		if( index[i].title != '' && index[i].pageNo != '' ){
			var item = "<div class='menuItem indexItem' data-pageNo=" + index[i].pageNo + " onclick='indexClicked(" + index[i].pageNo + ");'>" + 
							index[i].title + 
							"</div>";
			contentTableDiv.append( item ); 
		}
	}
	
	markSelectedIndex();
}

function markSelectedIndex(){
	jQuery( "#Pratilipi-Reader-Basic-ContentTable .menuItem" ).each( function(){
		if( jQuery( this ).attr( "data-pageNo" ) == pageNo ){
			jQuery( this ).addClass( "selectedIndex" );
		} else
			jQuery( this ).removeClass( "selectedIndex" );
	});
}

function indexClicked( pageNumber ){
	if( pageNumber != pageNo && pageNumber <= pageCount ){
		recordPageTime();
		pageNo = parseInt( pageNumber );
		pageNoDisplayed = 0;
		updateDisplay();
	}
}



function displayNextPage() {
	if( pageNo < pageCount ){
		recordPageTime();
		pageNo = parseInt( pageNo ) + 1;
		pageNoDisplayed = 0;
		updateDisplay();
	}
	else
		alert( "You have reached last page" );
}

function displayPreviousPage() {
	if( pageNo > 1 ){
		recordPageTime();
		pageNo = parseInt( pageNo ) - 1;
		pageNoDisplayed = 0;
		updateDisplay();
	}
	else
		alert( "This is first page" );
}



function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365, '${ pratilipiData.getReaderPageUrl() }' );
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365, '${ pratilipiData.getWriterPageUrl() }' );
}

function goToWriter(){
	window.location.href="${ pratilipiData.getWriterPageUrl() }";
}

function recordPageTime(){
	var currentTime = new Date();
	var readTimeSec = parseInt( ( currentTime.getTime() - pageStartTime.getTime() )/1000 );
	pageStartTime = new Date();
	var pageNumber = 'Page ' + pageNo;
	console.log( "Record Page Time : " + readTimeSec );
	if( readTimeSec < 2 )
		return;
	
	if( readTimeSec > 900 ) // 15 Min
		readTimeSec = 900;
	
	var eventCategory = 'Pratilipi:' + '${ pratilipiData.getId()?c }';
	var eventAction = 'ReadTimeSec:Page ' + pageNoDisplayed;
	var eventLabel = '${ pratilipiData.getType() }';
	
	ga( 'send', 'event', eventCategory, eventAction, eventLabel, readTimeSec );
}

var pageNumberDiv = document.getElementById( "PratilipiContent-Reader-PageNumber-Div" );
var displayPageNumberDiv = document.getElementById( "PratilipiContent-Reader-PageNumber-Display" );
var editPageNumberDiv = document.getElementById( "PratilipiContent-Reader-PageNumber-Edit" );
var pageNoInputBox = document.getElementById( "PratilipiContent-Reader-PageNumber-Edit-InputBox" );
var pageNoSubmitButton = document.getElementById( "PratilipiContent-Reader-PageNumber-Edit-Button" );

displayPageNumberDiv.onclick = function( e ){
	pageNoInputBox.value = "";
	pageNoInputBox.focus();
	displayPageNumberDiv.style.display = 'none';
	editPageNumberDiv.style.display = 'block';
	e.stopPropagation();
};

pageNoInputBox.onclick = function( e ){
	e.stopPropagation();
};
pageNoInputBox.onkeydown = function( e ){
	if( e.keyCode == 13 ){
		e.preventDefault();
		var pageNumber = this.value;
		displayPageNumberDiv.style.display = 'block';
		editPageNumberDiv.style.display = 'none';
		if( !isNaN( pageNumber ) && pageNumber >= 1 && pageNumber <= pageCount ){
			recordPageTime();
			pageNo = parseInt( this.value );
			updateDisplay();
		}
	}
	
	if( e.keyCode == 27 ){
		displayPageNumberDiv.style.display = 'block';
		editPageNumberDiv.style.display = 'none';
	}
};

document.onclick = function( e ) {
	editPageNumberDiv.style.display = 'none';
	displayPageNumberDiv.style.display = 'block';
	var indexDiv = $('#Pratilipi-Reader-Basic-ContentTable');
    if ( indexDiv.hasClass('visible') )
		showIndexDiv( e );
};

pageNoSubmitButton.onclick = function( e ){
	var pageNumber  = pageNoInputBox.value;
	if( !isNaN( pageNumber ) && pageNumber >= 1 && pageNumber <= pageCount ){
		recordPageTime();
		pageNo = parseInt( pageNumber );
		updateDisplay();
	}
};


if( window.attachEvent) {//for IE8 and below
	window.attachEvent( 'onload', function( event ){
		var isCtrl = false;
		document.onkeyup=function(e)
		{
			if(e.which == 17)
			isCtrl = false;
		}
		document.onkeydown = function(e)
		{
			if(e.which == 17)
			isCtrl = true;
			if(( ( e.which == 67 ) || ( e.which == 80 ) ) && isCtrl == true)
			{
				return false;
			}
		}
		
		document.oncontextmenu =  function( event ){
			var event = event || window.event;
			event.preventDefault();
		}
		
		setMinReaderWidth();
		saveAutoBookmark();
		setIndexDiv( JSON.parse( '${ pratilipiData.getIndex() ! '[]' }' ) );
	});
	
	window.attachEvent( 'onunload', function( event ){
		recordPageTime();
	});
}
else {
	window.addEventListener( 'load', function( event ){
		var isCtrl = false;
		document.onkeyup=function(e)
		{
			if(e.which == 17)
			isCtrl = false;
		}
		document.onkeydown = function(e)
		{
			if(e.which == 17)
			isCtrl = true;
			if(( ( e.which == 67 ) || ( e.which == 80 ) ) && isCtrl == true)
			{
				return false;
			}
		}
		
		document.oncontextmenu =  function( event ){
			event.preventDefault();
		}
		
		setMinReaderWidth();
		saveAutoBookmark();
		setIndexDiv( JSON.parse( '${ pratilipiData.getIndex() ! '[]' }' ) );
	});
	
	window.addEventListener( 'unload', function( event ) {
		recordPageTime();
	});
}


</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Reader :: End -->
