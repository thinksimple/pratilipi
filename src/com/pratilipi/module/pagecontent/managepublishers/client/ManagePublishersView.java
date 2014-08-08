package com.pratilipi.module.pagecontent.managepublishers.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.PublisherData;

public abstract class ManagePublishersView extends Composite {
	public abstract void setPublisher( PublisherData author);
	
	public abstract PublisherData getPublisher();
	
	
	//All get text functions are used for input validation
	public abstract String getName();
	
	public abstract String getEmail();
	
	//Error Styling function
	public abstract void setNameErrorStyle();
	
	public abstract void setEmailErrorStyle();
	
	//Accept Style Function
	public abstract void setNameAcceptStyle();
	
	public abstract void setEmailAcceptStyle();

}
