package com.pratilipi.module.pagecontent.managepublishers;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.module.pagecontent.managepublishers.gae.ManagePublishersEntity;
import com.pratilipi.module.pagecontent.managepublishers.shared.ManagePublishersData;

public class ManagePublishersFactory extends PageContentHelper<
		ManagePublishers,
		ManagePublishersData,
		ManagePublishersProcessor> {
	
	@Override
	public String getModuleName() {
		return "Publisher List";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static ManagePublishers newPublisherDataInput() {
		
		return new ManagePublishersEntity();
		
	}
	
}
