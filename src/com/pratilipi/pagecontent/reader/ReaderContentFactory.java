package com.pratilipi.pagecontent.reader;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.reader.gae.ReaderContentEntity;

public class ReaderContentFactory
		implements PageContentFactory<ReaderContent, ReaderContentProcessor> {
	
	public static ReaderContent newReaderContent( PratilipiType pratilipiType ) {
		ReaderContent readerContent = new ReaderContentEntity();
		readerContent.setPratilipiType( pratilipiType );
		return readerContent;
	}
	
}
