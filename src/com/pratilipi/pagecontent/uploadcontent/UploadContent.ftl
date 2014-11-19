<!-- PageContent :: UploadContent :: Start -->

<div class="container">
	<p style="margin-top: 10px;">Click Browse to select file to upload</p>
	<#if hasImageContent>
		<p>
			1.Please name image corresponding to it's page number as 1.jpg, 2.jpg, 3.jpg etc. <br/>
			2.Select images corresponding to a book at the same  time.
			3.If upload of a particular image fails, try uploading only failed page next time. <br/>
		</p>
		<div id="PageContent-UploadContent-Image" style="margin-top: 10px;"></div>
	<#else>
		<div id="PageContent-UploadContent-Word" style="margin-top: 10px;"></div>
	</#if>
	<div id="status-div" style="margin-top: 20px;"></div>
	<div id="PageContent-UploadContent-button" class="col-xs-12"></div>
</div>

<div id="PageContent-UploadContent-EncodedData" style="display:none;">${ pratilipiDataEncodedStr }</div>
<script type="text/javascript" language="javascript" src="/pagecontent.upload/pagecontent.upload.nocache.js" async></script>

<!-- PageContent :: UploadContent :: End -->