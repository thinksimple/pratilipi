package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.service.shared.data.LanguageData;

public class LanguageDataInputViewImpl extends LanguageDataInputView {

	private final Panel panel = new FlowPanel();
	private final TextInputFormField nameInput = new TextInputFormField();
	private final TextInputFormField enNameInput = new TextInputFormField();
	private final Button addButton = new Button( "Add" );
	
	
	public LanguageDataInputViewImpl() {
		nameInput.setPlaceholder( "Language" );
		enNameInput.setPlaceholder( "Language (English)" );
		nameInput.setRequired( true );
		enNameInput.setRequired( true );
		addButton.setStyleName( "btn btn-default" );

		panel.add( nameInput );
		panel.add( enNameInput );
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
		validated = enNameInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled( boolean enabled ) {
		nameInput.setEnabled( enabled );
		enNameInput.setEnabled( enabled );
		addButton.setEnabled( enabled );
	}

	@Override
	public LanguageData getLanguageData() {
		LanguageData languageData = new LanguageData();
		languageData.setName( nameInput.getText() );
		languageData.setNameEn( enNameInput.getText() );
		return languageData;
	}
	
	@Override
	public void setLanguageData( LanguageData languageData ) {
		nameInput.setText( languageData.getName() );
		enNameInput.setText( languageData.getNameEn() );
	}

}
