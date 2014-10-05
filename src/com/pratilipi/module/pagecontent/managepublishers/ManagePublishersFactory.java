package com.pratilipi.module.pagecontent.managepublishers;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.module.pagecontent.managepublishers.gae.ManagePublishersEntity;

public class ManagePublishersFactory
		implements PageContentFactory<ManagePublishers, ManagePublishersProcessor> {
	
	public static ManagePublishers newPublisherDataInput() {
		
		return new ManagePublishersEntity();
		
	}
	
}
