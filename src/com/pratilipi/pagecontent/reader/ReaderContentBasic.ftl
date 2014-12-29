<!-- PageContent :: Reader :: Start -->
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
								<div id="PratilipiContent-Reader-Content">
									<img id="imageContent" src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }&contentType=IMAGE' width=${ contentSize }/>
								</div>
								<div id="PratilipiContent-Reader-Overlay"></div>
							<#else>
								<div id="PratilipiContent-Reader-Content">
									<img id="imageContent" src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=${ pageNo }&contentType=IMAGE' width="100%"/>
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
					<p style="display: inline; line-height: 28px; ">${ pageNo } / ${ pageCount }</p>
				</div>
			</td>
		</tr>
	</table>
</div>
<div style="width: 100%; position: fixed; bottom: 0px; right: 0px;padding-right: 3%; padding-bottom: 10px; padding-top: 10px; ">
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

/* SAVES PRATILIPI ID AND PAGE NUMBER IN COOKIE */
function saveAutoBookmark(){
	setCookie( '${ pageNoCookieName }', ${ pageNo }, 365 );
}

function setMinReaderWidth(){
	var windowsize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	if( windowsize > 800 )
		document.getElementById( "PratilipiContent-Reader-Content" ).style.width = "1000px" ;
	else
		document.getElementById( "PratilipiContent-Reader-Content" ).style.width = windowsize + "px";
}

/* Zoom Support */

function increaseSize(){
	var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var imageContent = document.getElementById( "imageContent" );
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
	var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
		
	if( imageContent ){
		/* For Image content */
		imageContent.width = imageContent.width + 50;
		overlay = imageContent.width;
		imageContent.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', imageContent.width + 'px', 365 );
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
	var basicReader = document.getElementById("Pratilipi-Reader-Basic");
	var wordContent = document.getElementById( "PratilipiContent-Reader-Content" );
	var overlay = document.getElementById( "PratilipiContent-Reader-Overlay" );
	
	if( imageContent ){
		/* For Image content */
		imageContent.width = imageContent.width - 50;
		overlay.width = imageContent.width;
		imageContent.style.height = 'auto';
		setCookie( '${ contentSizeCookieName }', imageContent.width + 'px', 365 );
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



if( window.attachEvent) //for IE8 and below
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
	});
else 
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
	});

</script>

<!-- JAVASCRIPT END -->
<!-- PageContent :: Reader :: End -->