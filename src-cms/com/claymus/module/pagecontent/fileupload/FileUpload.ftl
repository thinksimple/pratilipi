<div>
	<form action="${ getUploadUrl() }" method="post" enctype="multipart/form-data">
		<input type="file" name="file" />
		<input type="submit" value="Submit" />
	</form>
	<a href="/service.upload/${ getFileName() }">File Path</a>
</div>