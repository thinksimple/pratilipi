package com.pratilipi.pagecontent.writer;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.writer.gae.WriterContentEntity;
import com.pratilipi.pagecontent.writer.shared.WriterContentData;

public class WriterContentHelper extends PageContentHelper<
		WriterContent,
		WriterContentData,
		WriterContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Writer";
	}

	@Override
	public Double getModuleVersion() {
		return 5.0;
	}

	
	public static WriterContent newReaderContent() {
		return new WriterContentEntity();
	}

}
