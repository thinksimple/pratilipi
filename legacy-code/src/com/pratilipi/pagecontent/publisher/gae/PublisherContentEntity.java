package com.pratilipi.pagecontent.publisher.gae;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.publisher.PublisherContent;

@SuppressWarnings("serial")
public class PublisherContentEntity extends PageContentEntity
		implements PublisherContent {

	public PublisherContentEntity( Long publisherId ) {
		super.setId( publisherId );
	}
	
}
