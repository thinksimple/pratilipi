package com.pratilipi.pagecontent.reader;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.reader.gae.ReaderContentEntity;
import com.pratilipi.pagecontent.reader.shared.ReaderContentData;

public class ReaderContentHelper extends PageContentHelper<
		ReaderContent,
		ReaderContentData,
		ReaderContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Reader";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static ReaderContent newReaderContent() {
		return new ReaderContentEntity();
	}
	
}
