package com.claymus.module.pagecontent.fileupload;

import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.fileupload.gae.FileUploadEntity;

public class FileUploadFactory
		implements PageContentFactory<FileUpload, FileUploadProcessor> {
	
	public static FileUpload newHtmlContent() {
		return new FileUploadEntity();
	}

}
