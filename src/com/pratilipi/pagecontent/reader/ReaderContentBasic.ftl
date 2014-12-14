<!-- PageContent :: Reader :: Start -->
<#assign pageNumber = pageNo?number>
<#assign totalPageCount = pageCount?number>
<#if ( pageNumber > 1 )>
	<#assign previousPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNumber -1) >
</#if>
<#if ( pageNo < totalPageCount )>
	<#assign nextPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNumber+1) >
</#if>
<div id="Pratilipi-Reader-Basic" style="background-color: #f5f5f5; visibility: hidden;">
	<table>
		<tr>
			<td>
				<#-- Title and Author Name -->
				<div style="margin-left: 5px; margin-right:5px;">
					<button type="button" onclick="window.location.href='${ exitUrl ! pratilipiData.getPageUrl() }'" style="float: left; margin-right: 10px;" ><img src="/theme.pratilipi/images/left.png" /></button>
					<h2 style="line-height: 27px;">
						<a href="${ pratilipiData.getPageUrlAlias() }">${ pratilipiData.getTitle() }</a>
					</h2>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style="margin: 5px;">
					<div style="display: inline;">
					<button type="button" onclick="decreaseSize()"><img src="/theme.pratilipi/images/minus.png" title="Decrease size" /></button>
					<button type="button" onclick="increaseSize()"><img src="/theme.pratilipi/images/plus.png" title="Increase size"/></button>
					</div>
					<div align="right" style="display: inline-block; float: right;">
						<#if previousPageUrl?? >
							<button type="button" onclick="window.location.href= '${ previousPageUrl }'" ><img src="/theme.pratilipi/images/previous.png" title="Previous Page" /></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="window.location.href= '${ nextPageUrl }'" ><img src="/theme.pratilipi/images/next.png" title="Next Page" /></button>
						</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td id="Pratilipi-Content-td" align="center">
				<#if pratilipiData.getContentType() == "PRATILIPI" >
	
					<#if contentSize??>
						<div id="PageContent-Reader-Content" class="paper" style="font-size:${ contentSize }"> ${ pageContent } </div>
					<#else>
						<div id="PageContent-Reader-Content" class="paper"> ${ pageContent } </div>
					</#if>
	
				<#elseif pratilipiData.getContentType() == "IMAGE" >			
	
					<div class="paper" style="width:inherit; max-width:none; min-height:inherit; overflow-x:auto;">
						<#if contentSize??>
							<div id="PageContent-Reader-Content">
								<img id="imageContent" src='${ pratilipiData.getImageContentUrl() }/${ pageNo }' width=${ contentSize }/>
							</div>
						<#else>
							<div id="PageContent-Reader-Content">
								<img id="imageContent" src='${ pratilipiData.getImageContentUrl() }/${ pageNo }' width="100%"/>
							</div>
						</#if>
					</div>
	
				</#if>
			</td>
		</tr>
		<tr>
			<td>
				<div style="margin: 5px; text-align: center;">
					<p style="display: inline; line-height: 28px; ">${ pageNumber } of ${ pageCount }</p>
					<div align="right" style="display: inline-block; float: right;">
						<#if previousPageUrl?? >
							<button type="button" onclick="window.location.href='${ previousPageUrl }'" ><img src="/theme.pratilipi/images/previous.png" title="Previous Page" /></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="window.location.href='${ nextPageUrl }'" ><img src="/theme.pratilipi/images/next.png" title="Next Page" /></button>
						</#if>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

<!-- JAVASCRIPT START -->
<script language="javascript">
	function disableselect(e){
		return false;
	}
	function reEnable(){
		return true;
	}
	//if IE4+
		document.onselectstart=new Function ("return false");
	//if NS6
	if (window.sidebar){
		document.onmousedown=disableselect;
		document.onclick=reEnable;
	}
	
</script>

<style type="text/css">
		@media print{
			body {display:none;}
		}
</style>


<script language="javascript">

/**
 * THIS FILE CONTAINS ALL JS USED IN BASIC MODE READER.
 */

/* SCRIPT TO KEEP READER CENTER ALIGNED IRRESPECTIVE OF THE SCREEN SIZE */
function centerAlignBasicReader(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var imageContent = document.getElementById( "imageContent" );
	var contentDiv = document.getElementById( "PageContent-Reader-Content" );
	var basicReaderWidth = basicReader.offsetWidth;
	var padding;
	if( basicReaderWidth >= 1000 ){
		if( imageContent ){
			padding = ( windowSize - imageContent.offsetWidth )/2;
		}
		else
			padding = ( windowSize - contentDiv.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
	}
	else{
		padding = ( windowSize - contentDiv.offsetWidth )/2;
		basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
	}
	basicReader.style.visibility = 'visible';
}

/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIE */
function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365 );
}

/* Zoom Support */

function increaseSize(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var imageContent = document.getElementById( "imageContent" );
	var contentTd = document.getElementById( "Pratilipi-Content-td" );
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var wordContent = document.getElementById( "PageContent-Reader-Content" );
		
	if( imageContent ){
		/* For Image content */
		imageContent.width = imageContent.width + 50;
		imageContent.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', imageContent.width + 'px', 365 );
		contentTd.width = imageContent.offsetWidth;
		centerAlignBasicReader(); 
	}
	else if( wordContent ) {
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
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var imageContent = document.getElementById( "imageContent" );
	var contentTd = document.getElementById( "Pratilipi-Content-td" );
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var wordContent = document.getElementById( "PageContent-Reader-Content" );
	
	if( imageContent ){
		/* For Image content */
		imageContent.width = imageContent.width - 50;
		imageContent.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', imageContent.width + 'px', 365 );
		contentTd.width = imageContent.offsetWidth;
		centerAlignBasicReader();
		
	} 
	else if( wordContent ){
		/* For word content */
		var fontSizeStr = wordContent.style.fontSize;
		
		if( !fontSizeStr )
			fontSizeStr = "16px";
			
		var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
		wordContent.style.fontSize = ( fontSize - 2 ) + "px";

		setCookie( '${ contentSizeCookieName }', ( fontSize - 2 ) + 'px', 365 );
	}
}

//EXECUTE ON WINDOW LOAD EVENT
window.addEventListener( 'load', function( event ){
	var pratilipiContent = document.getElementById( "PageContent-Reader-Content" );
	pratilipiContent.addEventListener( 'contextmenu', function( event){
		event.preventDefault();
		return false;
	});
	//pkey = 80; ckey = 67; vkey = 86
	document.addEventListener("keyup keypress", function(e){
    if( e.ctrlKey && ( e.keyCode == 80 ) ){
	        return false;
	    }
	});
	
	centerAlignBasicReader();
	saveAutoBookmark();
});

//EXECUTE ON WINDOW RESIZE EVENT
window.addEventListener( 'resize', function( event ){
	centerAlignBasicReader();
});



</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Reader :: End -->