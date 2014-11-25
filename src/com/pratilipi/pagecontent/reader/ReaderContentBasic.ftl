<!-- PageContent :: Reader :: Start -->
<#assign pageNumber = pageNo?number>
<#assign totalPageCount = pageCount?number>
<#if ( pageNumber > 1 )>
	<#assign previousPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNumber -1) >
</#if>
<#if ( pageNo < totalPageCount )>
	<#assign nextPageUrl= pratilipiData.getReaderPageUrl()+'&page='+(pageNumber+1) >
</#if>
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
							<button type="button" onclick="newPage( '${ previousPageUrl }' )" ><img src="/theme.pratilipi/images/previous.png" title="Previous Page" /></button>
						</#if>
						<#if nextPageUrl?? >
							<button type="button" onclick="newPage( '${ nextPageUrl }' )" ><img src="/theme.pratilipi/images/next.png" title="Next Page" /></button>
						</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td id="Pratilipi-Content-td">
				<div id="paper" class="paper" style="padding:0px;">
					<div id="PageContent-Pratilipi-Content" style="width: 100%">
						<#if pratilipiData.getContentType() == "PRATILIPI" >
							${ pageContent }
						<#elseif pratilipiData.getContentType() == "IMAGE" >
							<img id="imageContent" src='${ pratilipiData.getImageContentUrl() }/${ pageNo }' style="width: 100%;"/>
						</#if>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style="margin: 5px; text-align: center;">
					<button type="button" onclick="window.location.href='${ exitUrl ! pratilipiData.getPageUrl() }'" style="float: left;" ><img src="/theme.pratilipi/images/left.png" /></button>
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
	
</script>
	<style type="text/css">
		@media print{
			body {display:none;}
		}
</style>


<!-- PageContent :: Reader :: End -->