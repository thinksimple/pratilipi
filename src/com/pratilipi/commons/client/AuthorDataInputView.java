package com.pratilipi.commons.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public abstract class AuthorDataInputView extends Composite {
	
	public abstract void addLanguageListItem( LanguageData languageData );

	public abstract void add( Button child );

	
	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract AuthorData getAuthorData();

	public abstract void setAuthorData( AuthorData authorData );

}
