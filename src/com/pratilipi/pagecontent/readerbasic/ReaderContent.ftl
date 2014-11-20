

<!-- This is reader page -->
<div id="Pratilipi-Reader-Basic"  class="paper">

	<table>
		<tr>
			<td>
				<#-- Title and Author Name -->
				<div>
					<h2>
						<a href="${ pratilipiHomeUrl }">${ pratilipi.getTitle() }</a>
						<small>
							<a href="${ authorHomeUrl }">- ${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if></a>
						</small>
					</h2>
		
					<#if showEditOptions>
						<div id="PageContent-Pratilipi-Content-EditOptions" align="right" style="margin: 5px;"></div>
					</#if>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<div style="display: inline;">
					<button type="button" ><span class="glyphicon glyphicon-minus"></span></button>
					<button type="button" ><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<div align="right" style="display: inline-block; float: right;">
					<#if previousPageUrl?? || nextPageUrl??>
						<button type="button" ><span class="glyphicon glyphicon-chevron-left"></span></button>
						<button type="button" ><span class="glyphicon glyphicon-chevron-right"></span></button>
					</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style="padding:0px;">
					<div id="PageContent-Pratilipi-Content" class="well" style="margin-top:10px; margin-bottom:10px; width: 100%">
						${ pageContent }
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<div style="display: inline;">
						<button type="button" ><span class="glyphicon glyphicon-minus"></span></button>
						<button type="button" ><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<div align="right" style="display: inline-block; float: right;">
					<#if previousPageUrl?? || nextPageUrl??>
						<button type="button" ><span class="glyphicon glyphicon-chevron-left"></span></button>
						<button type="button" ><span class="glyphicon glyphicon-chevron-right"></span></button>
					</#if>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

<div id="PageContent-Reader-EncodedData" style="display:none;">${ pratilipiDataEncodedStr }</div>

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
</script>
	<style type="text/css">
		@media print{
			body {display:none;}
		}
</style>

<script type="text/javascript" language="javascript" src="/pagecontent.reader/pagecontent.reader.nocache.js" defer></script>
