package com.claymus.module.pagecontent.fileupload;

import com.claymus.module.pagecontent.PageContentProcessor;

public class FileUploadProcessor extends PageContentProcessor<FileUpload> {

	@Override
	protected String getTemplateName() {
		return "com/claymus/module/pagecontent/fileupload/FileUpload.ftl";
	}
	
}
