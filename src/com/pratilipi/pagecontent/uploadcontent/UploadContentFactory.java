package com.pratilipi.pagecontent.uploadcontent;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.uploadcontent.gae.UploadContentEntity;
import com.pratilipi.pagecontent.uploadcontent.shared.UploadContentData;


public class UploadContentFactory extends PageContentHelper<UploadContent, 
											UploadContentData, UploadContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "File Browser";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static UploadContent newUploadContent() {
		return new UploadContentEntity();
	}
}
