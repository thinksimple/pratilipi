package com.pratilipi.pagecontent.publisher;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.publisher.gae.PublisherContentEntity;
import com.pratilipi.pagecontent.publisher.shared.PublisherContentData;

public class PublisherContentHelper extends PageContentHelper<
		PublisherContent,
		PublisherContentData,
		PublisherContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Publisher";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	
	public static PublisherContent newPublisherContent( Long publisherId ) {
		return new PublisherContentEntity( publisherId );
	}

}
