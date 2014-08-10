package com.claymus.module.pagecontent.fileupload;

import com.claymus.data.transfer.PageContent;

public interface FileUpload extends PageContent {
	
	String getFileName();
	
	void setFileName( String fileName );
	
	String getUploadUrl();
	
}
