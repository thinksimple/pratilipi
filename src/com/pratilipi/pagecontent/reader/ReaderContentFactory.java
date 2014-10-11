package com.pratilipi.pagecontent.reader;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentFactory;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.reader.gae.ReaderContentEntity;
import com.pratilipi.pagecontent.reader.shared.ReaderContentData;

public class ReaderContentFactory extends PageContentFactory<
		ReaderContent,
		ReaderContentData,
		ReaderContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Reader";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static ReaderContent newReaderContent( PratilipiType pratilipiType ) {
		ReaderContent readerContent = new ReaderContentEntity();
		readerContent.setPratilipiType( pratilipiType );
		return readerContent;
	}
	
}
