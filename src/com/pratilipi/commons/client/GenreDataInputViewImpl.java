package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.service.shared.data.GenreData;

public class GenreDataInputViewImpl extends GenreDataInputView {

	private final Panel panel = new FlowPanel();
	private final TextInputFormField nameInput = new TextInputFormField();
	private final Button addButton = new Button( "Add" );
	
	
	public GenreDataInputViewImpl() {
		nameInput.setPlaceholder( "Genre" );
		nameInput.setRequired( true );
		addButton.setStyleName( "btn btn-default" );

		panel.add( nameInput );
		panel.add( addButton );

		initWidget( panel );
	}

	
	@Override
	public HandlerRegistration addAddButtonClickHandler( ClickHandler clickHandler ) {
		return addButton.addClickHandler( clickHandler );
	}
	
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = nameInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled( boolean enabled ) {
		nameInput.setEnabled( enabled );
		addButton.setEnabled( enabled );
	}

	@Override
	public GenreData getGenreData() {
		GenreData genreData = new GenreData();
		genreData.setName( nameInput.getText() );
		return genreData;
	}
	
	@Override
	public void setGenreData( GenreData genreData ) {
		nameInput.setText( genreData.getName() );
	}

}
