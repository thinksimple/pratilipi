package com.pratilipi.module.pagecontent.manageauthors.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.AuthorData;

public abstract class ManageAuthorsView extends Composite {
	public abstract void setAuthor( AuthorData author);
	
	public abstract AuthorData getAuthor();
	
	
	//All get text functions are used for input validation
	public abstract String getFirstName();
	
	public abstract String getLastName();
	
	public abstract String getPenName();
	
	public abstract String getEmail();
	
	//Error Styling function
	public abstract void setFirstNameErrorStyle();
	
	public abstract void setLastNameErrorStyle();
	
	public abstract void setPenNameErrorStyle();
	
	public abstract void setEmailErrorStyle();
	
	//Accept Style Function
	public abstract void setFirstNameAcceptStyle();
	
	public abstract void setLastNameAcceptStyle();
	
	public abstract void setPenNameAcceptStyle();
	
	public abstract void setEmailAcceptStyle();

}
