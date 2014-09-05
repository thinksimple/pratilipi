package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.PratilipiData;

public abstract class PratilipiDataInputView extends Composite {
	
	public abstract HandlerRegistration addAddButtonClickHandler(
			ClickHandler clickHandler );
	
	public abstract void addAuthorListItem( String item, String value );
	
	public abstract void addLanguageListItem( String item, String value );
	

	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract PratilipiData getPratilipiData();

	public abstract void setPratilipiData( PratilipiData pratilipiData );
	
}
