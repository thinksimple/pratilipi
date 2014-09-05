package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;

public abstract class BookDataInputView extends Composite {
	
	public abstract HandlerRegistration addAddButtonClickHandler(
			ClickHandler clickHandler );
	
	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract PratilipiData getPratilipiData();

	public abstract void setBookData( BookData bookData );
	
	public abstract void setAuthorList ( AuthorData authorData );
	
	public abstract void setLanguageList ( LanguageData languageData );
	
	public abstract void setPublisherList ( PublisherData publisherData );

}
