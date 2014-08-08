package com.pratilipi.module.pagecontent.managepublishers;

import com.claymus.module.pagecontent.PageContentFactory;

public class ManagePublishersFactory
		implements PageContentFactory<ManagePublishers, ManagePublishersProcessor> {
	
	public static ManagePublishers newPublisherDataInput() {
		
		return new ManagePublishers() {
			
			@Override
			public Long getId() {
				return null;
			}

			@Override
			public Long getPageId() {
				return null;
			}
			
			@Override
			public void setPageId( Long pageId ) { }
			
			@Override
			public String getPosition() {
				return null;
			}
			
			@Override
			public void setPosition( String position ) { }

		};
		
	}
	
}
