package com.claymus.module.pagecontent.fileupload;

import com.claymus.module.pagecontent.PageContentProcessor;

public class FileUploadContentProcessor extends PageContentProcessor<FileUploadContent> {

	@Override
	protected String getTemplateName() {
		return "com/claymus/module/pagecontent/fileupload/FileUploadContent.ftl";
	}
	
}
