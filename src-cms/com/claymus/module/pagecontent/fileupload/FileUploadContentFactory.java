package com.claymus.module.pagecontent.fileupload;

import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.fileupload.gae.FileUploadContentEntity;

public class FileUploadContentFactory
		implements PageContentFactory<FileUploadContent, FileUploadContentProcessor> {
	
	public static FileUploadContent newFileUploadContent() {
		return new FileUploadContentEntity();
	}

}
