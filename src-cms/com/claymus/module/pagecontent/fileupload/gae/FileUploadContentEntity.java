package com.claymus.module.pagecontent.fileupload.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.module.pagecontent.fileupload.FileUploadContent;

@PersistenceCapable
public class FileUploadContentEntity extends PageContentEntity implements FileUploadContent {
	
	@Persistent( column = "X_COL_0" )
	private String fileName;

	
	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}
	
	@Override
	public String getUploadUrl() {
		return DataAccessorFactory
				.getBlobAccessor()
				.createUploadUrl( fileName );
	}
	
}
