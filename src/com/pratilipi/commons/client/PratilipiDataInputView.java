package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;

public abstract class PratilipiDataInputView<T extends PratilipiData> extends Composite {
	
	public abstract HandlerRegistration addAddButtonClickHandler(
			ClickHandler clickHandler );
	
	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract T getPratilipiData();

	public abstract void setPratilipiData( T t );
	
	public abstract void setAuthorList ( AuthorData authorData );
	
	public abstract void setLanguageList ( LanguageData languageData );
	
	public abstract void setPublisherList ( PublisherData publisherData );

}
