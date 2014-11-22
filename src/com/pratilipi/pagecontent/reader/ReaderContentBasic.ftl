
<!-- This is reader page -->
<div id="Pratilipi-Reader-Basic" style="background-color: #f5f5f5;">

	<table>
		<tr>
			<td>
				<#-- Title and Author Name -->
				<div style="margin-left: 5px; margin-right:5px;">
					<h2>
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
							<button type="button" onclick="window.location.href='${ previousPageUrl }'" ><img src="/theme.pratilipi/images/previous.png" title="Previous Page" /></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="window.location.href='${ nextPageUrl }'" ><img src="/theme.pratilipi/images/next.png" title="Next Page" /></button>
						</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td id="Pratilipi-Content-td">
				<div id="paper" class="paper" style="padding:0px;">
					<div id="PageContent-Pratilipi-Content" style="width: 100%">
						${ pageContent }
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style="margin: 5px; text-align: center;">
					<button type="button" onclick="window.location.href='${ returningUrl }'" style="float: left;" ><img src="/theme.pratilipi/images/left.png" /></button>
					<p style="display: inline; line-height: 28px; ">${ pageNumber }</p>
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

<script language="javascript" defer>
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
	
	window.onload = function() {
	    
		//var $reader = $( '#PageContent-Pratilipi-Content' );
		//if( $reader.height() < 800 )
		//	$reader.height( 800 ); 
		
		$( '#PageContent-Pratilipi-Content' ).on("contextmenu",function(e){ return false; });
			
		//pkey = 80; ckey = 67; vkey = 86
		$( document ).bind("keyup keydown", function(e){
	    if( e.ctrlKey && ( e.keyCode == 80 ) ){
		        return false;
		    }
		});
		
	}
	
	/* Zoom Support */
	
	function increaseSize(){
		var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var imageContent = document.getElementById( "imageContent" );
		var contentTd = document.getElementById( "Pratilipi-Content-td" );
		var basicReader = document.getElementById("Pratilipi-Reader-Basic");
		var pTags = document.getElementById( "PageContent-Pratilipi-Content" ).getElementsByTagName( "p" );
			
		if( imageContent ){
			/* For Image content */
			imageContent.height = imageContent.height + 50;
			imageContent.style.width = 'auto';
			setCookie( "image-height", imageContent.height, 365 );
			contentTd.width = imageContent.offsetWidth;
			var padding = ( windowSize - imageContent.offsetWidth )/2;
			basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;"); 
		}
		else if( pTags ) {
			/* For word content */
			/* Converting string of tag element list to array */
			var tagList = Array.prototype.slice.call( pTags );
			var fontSizeStr = tagList[0].style.fontSize;
			
			/* By default font size of paragraphs in content is not set. Hence initializing fontSizeStr to default font-size of p tags across website */
			if( !fontSizeStr )
				fontSizeStr = "16px";
				
			var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
			tagList.forEach( function( value, index, p ) {
				p[index].style.fontSize = ( fontSize + 2 ) + "px";
			} );
			setCookie( "font-size", ( fontSize + 2 ), 365 );
		}

	}
		
	function decreaseSize(){
		var windowSize = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
		var imageContent = document.getElementById( "imageContent" );
		var contentTd = document.getElementById( "Pratilipi-Content-td" );
		var basicReader = document.getElementById("Pratilipi-Reader-Basic");
		var pTags = document.getElementById( "PageContent-Pratilipi-Content" ).getElementsByTagName( "p" );
		
		if( imageContent ){
			/* For Image content */
			imageContent.height = imageContent.height - 50;
			imageContent.style.width = 'auto';
			setCookie( "image-height", imageContent.height, 365 );
			contentTd.width = imageContent.offsetWidth;
			var padding = ( windowSize - imageContent.offsetWidth )/2;
			basicReader.setAttribute("style", "padding-left:" + padding.toString() + "px; background-color: #f5f5f5;");
			
		} 
		else if( pTags ){
			/* For word content */
			var tagList = Array.prototype.slice.call( pTags );
			var fontSizeStr = tagList[0].style.fontSize;
			
			if( !fontSizeStr )
				fontSizeStr = "16px";
				
			var fontSize = parseInt( fontSizeStr.substring( 0, fontSizeStr.indexOf( 'p' )) );
			tagList.forEach( function( value, index, p ) {
				p[index].style.fontSize = ( fontSize - 2 ) + "px";
			} );
			setCookie( "font-size", ( fontSize - 2 ), 365 );
		}
	}
	
</script>
	<style type="text/css">
		@media print{
			body {display:none;}
		}
</style>

