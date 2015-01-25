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
									<img id="imageContent" src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }&contentType=IMAGE' style="width:100%"/>
								</div>
								<div id="PratilipiContent-Reader-Overlay" style="width: ${ contentSize };"></div>
							<#else>
								<div id="PratilipiContent-Reader-Content">
									<img id="imageContent" src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }&contentType=IMAGE' style="width:100%"/>
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
				<div class="green" style="margin: 5px; text-align: center;">
					<span id="PratilipiContent-Reader-PageNumber" contenteditable="true" style="display: inline; line-height: 28px; border: 1px solid gray; padding: 0px 5px; background-color: white; ">${ pageNo }</span> /
					<span id="PratilipiContent-Reader-PageCount" style="display: inline; line-height: 28px; ">${ pageCount }</span>
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
</style>


<script language="javascript">

var pageNo = ${ pageNo };
var pageCount = ${ pageCount };
var pageNoDisplayed;
var contentArray = [];
var pageStartTime;		//Initialized in recordPageChangeEvent
var campaign = '${ pratilipiData.getType() }' + ":" + '${ pratilipiData.getId()?c }';

function setPageNo(){
	jQuery( "#PratilipiContent-Reader-PageNumber" ).html( pageNo );
}


function updateDisplay() {
	jQuery('html, body').animate({scrollTop: '0px'}, 300);
	updateContent();
	setPageNo();
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
			data: 'pratilipiId=${ pratilipiData.getId()?c }&pageNo=' + pageNumber + '&contentType=PRATILIPI',
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
		if( windowsize > 800 )
			jQuery( ".paper" ).width( "1000px" );
		else
			jQuery( ".paper" ).width( ( windowsize - 10 ) + "px" );
	}
	
<#elseif pratilipiData.getContentType() == 'IMAGE'>

	function loadImage( pageNumber ){
		var img = "<img id='imageContent' src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=" + pageNo + "&contentType=IMAGE' style='width:100%' />";
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


function displayNextPage() {
	if( pageNo < pageCount ){
		recordPageTime( 'PageDurationInSec' );
		pageNo = parseInt( pageNo ) + 1;
		pageNoDisplayed = 0;
		updateDisplay();
		recordPageChangeEvent( 'NextPage' );
	}
	else
		alert( "You have reached last page" );
}

function displayPreviousPage() {
	if( pageNo > 1 ){
		recordPageTime( 'PageDurationInSec' );
		pageNo = parseInt( pageNo ) - 1;
		pageNoDisplayed = 0;
		updateDisplay();
		recordPageChangeEvent( 'PreviousPage' );
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


function recordPageChangeEvent( eventAction ) {
	pageStartTime = new Date();
	var pageNumber = 'Page ' + pageNo;
	ga( 'send', 'event', campaign, eventAction, pageNumber );
}

function recordPageTime( eventAction ){
	var currentTime = new Date();
	var timeDiff = currentTime.getTime() - pageStartTime.getTime();
	var pageNumber = 'Page ' + pageNo;
	if( timeDiff > 100 )
		timeDiff = 0;
	
	if( timeDiff < 900000 )
		timeDiff = 900000;
	
	ga( 'send', 'event', campaign, eventAction, pageNumber, parseInt( timeDiff/1000 ));
}

var goTo = document.getElementById( "PratilipiContent-Reader-PageNumber" );
goTo.onkeydown = function( e ){
	if( !( e.keyCode == 8 || e.keyCode == 37 || e.keyCode == 39 || e.keyCode == 46 || ( e.keyCode >= 48 && e.keyCode <= 57 ) || ( e.keyCode >= 96 && e.keyCode <= 105 ) ) )
		return false;
};
goTo.onkeyup = function( e ){
	if( ( e.keyCode >= 48 && e.keyCode <= 57 ) || ( e.keyCode >= 96 && e.keyCode <= 105 )){
		var pageNumber = this.innerHTML;
		if( pageNumber < 1 && pageNumber > pageCount ){
			this.innerHTML = pageNo;
		}
	}
	if( e.keyCode == 13 ){
		e.preventDefault();
		pageNo = this.innerHTML;
		updateDisplay();
		recordPageChangeEvent( 'GOTO Page' );
	}
};
goTo.onfocus = function( e ) {
	this.innerHTML = "";
};
goTo.onblur = function( e ) {
	this.innerHTML = pageNo;
};


if( window.attachEvent) {//for IE8 and below
	window.attachEvent( 'onload', function( event ){
		recordPageChangeEvent( 'PageLoad' );
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
	});
	
	window.attachEvent( 'onunload', function( event ){
		recordPageTime( 'PageDurationInSec' );
	});
}
else {
	window.addEventListener( 'load', function( event ){
		recordPageChangeEvent( 'PageLoad' );
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
	});
	
	window.addEventListener( 'unload', function( event ) {
		recordPageTime( 'PageDurationInSec' );
	});
}
</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Reader :: End -->
