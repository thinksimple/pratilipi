
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
					<button type="button" onclick="decreaseSize()"><span class="glyphicon glyphicon-minus"></span></button>
					<button type="button" onclick="increaseSize()"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<div align="right" style="display: inline-block; float: right;">
						<#if previousPageUrl?? >
							<button type="button" onclick="window.location.href='${ previousPageUrl }'" ><span class="glyphicon glyphicon-chevron-left"></span></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="window.location.href='${ nextPageUrl }'" ><span class="glyphicon glyphicon-chevron-right"></span></button>
						</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="paper" class="paper" style="padding:0px;">
					<div id="PageContent-Pratilipi-Content" style="width: 100%">
						${ pageContent }
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style="margin: 5px;">
					<p style="display: inline; line-height: 28px;">${ pageNumber }</p>
					<div align="right" style="display: inline-block; float: right;">
						<#if previousPageUrl?? >
							<button type="button" onclick="window.location.href='${ previousPageUrl }'" ><span class="glyphicon glyphicon-chevron-left"></span></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="window.location.href='${ nextPageUrl }'" ><span class="glyphicon glyphicon-chevron-right"></span></button>
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
	var zoomNo = 0;
	function increaseSize(){
		zoomNo++;
		var windowsize = window.innerHeight || document.documentElement.clientHeight || document.body.clientheight;
		var imageContent = document.getElementById( "imageContent" );
		var imageContainer = document.getElementById( "PageContent-Pratilipi-Content" );
		imageContainer.setAttribute( "style", "overflow: auto;" );
		
		/*Fixing height of the container. NOT WORKING TILL NOW*/
		if( zoomNo == 1)
			imageContainer.height = windowsize;
			
		if( imageContent  ){
			imageContent.height = imageContent.height + 100;
			imageContent.style.width = 'auto';
		}

	}
	
	function decreaseSize(){
		zoomNo--;
		var imageContent = document.getElementById( "imageContent" );
		var imageContainer = document.getElementById( "PageContent-Pratilipi-Content" );
		if( imageContent && zoomNo >= 0 ){
			imageContent.height = imageContent.height - 100;
			imageContent.style.width = 'auto';
		}
		else{
			zoomNo = 0;
		}
	}
	
</script>
	<style type="text/css">
		@media print{
			body {display:none;}
		}
</style>

