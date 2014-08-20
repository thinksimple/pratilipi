package com.pratilipi.module.pagecontent.managebooks;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.module.pagecontent.managebooks.gae.ManageBooksEntity;

public class ManageBooksFactory
		implements PageContentFactory<ManageBooks, ManageBooksProcessor> {
	
	public static ManageBooks newManageBooks() {
		
		return new ManageBooksEntity();
		
	}
	
}
