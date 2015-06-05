package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.GenreData;

public abstract class GenreDataInputView extends Composite {

	public abstract HandlerRegistration addAddButtonClickHandler(
			ClickHandler clickHandler );
	
	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract GenreData getGenreData();

	public abstract void setGenreData( GenreData genreData );

}
