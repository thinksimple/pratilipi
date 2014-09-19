<div class="container">

	<div class="row">
	
		<#-- Title and Author Name -->
		<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
			<h2>
				<a href="${ pratilipiHomeUrl }">${ pratilipi.getTitle() }</a>
			</h2>
			<h4>
				<a href="${ authorHomeUrl }">${ author.getFirstName() }<#if author.getLastName()??> ${ author.getLastName() }</#if></a>
			</h4>

			<#if showEditOptions>
				<div id="PageContent-Pratilipi-Content-EditOptions"></div>
			</#if>
		</div>

	</div> <#-- END of row -->


	<div class="row">

		<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3" style="padding:0px;">
			<div id="PageContent-Pratilipi-Content" class="well" style="margin-bottom:10px;">
				${ pageContent }
			</div>
		</div>
		
		<div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3" style="padding:0px;">
			<#if previousPageUrl??>
				<button type="button" class="btn btn-default" onclick="window.location.href='${ previousPageUrl }'">
					<span class="glyphicon glyphicon-chevron-left"></span>&nbsp;&nbsp;Previous
				</button>
			</#if>
				
			<#if nextPageUrl??>
				<button type="button" class="btn btn-default pull-right" onclick="window.location.href='${ nextPageUrl }'">
					Next&nbsp;&nbsp;<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
			</#if>
		</div>

	</div> <#-- END of second row -->

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
		var $reader = $( '#PageContent-Pratilipi-Content' );
		if( $reader.height() < 800 )
			$reader.height( 800 ); 
		
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

<#if showEditOptions>
	<script type="text/javascript" language="javascript" src="/pagecontent.reader/pagecontent.reader.nocache.js" defer></script>
</#if>