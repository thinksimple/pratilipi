package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.service.shared.data.AuthorData;

public class AuthorsDataInputViewImpl extends AuthorsDataInputView {
	
	private Panel panel = new FlowPanel();
	
	private Panel firstNameRow = new FlowPanel();
	private Panel lastNameRow = new FlowPanel();
	private Panel penNameRow = new FlowPanel();
	private Panel languageEmailRow = new FlowPanel();
	
	private Panel firstNameCol = new SimplePanel();
	private Panel lastNameCol = new SimplePanel();
	private Panel penNameCol = new SimplePanel();
	private Panel firstNameEnCol = new SimplePanel();
	private Panel lastNameEnCol = new SimplePanel();
	private Panel penNameEnCol = new SimplePanel();
	private Panel languageCol = new SimplePanel();
	private Panel emailCol = new SimplePanel();
	
	private TextInputFormField firstNameInput = new TextInputFormField();
	private TextInputFormField lastNameInput = new TextInputFormField();
	private TextInputFormField penNameInput = new TextInputFormField();
	private TextInputFormField firstNameEnInput = new TextInputFormField();
	private TextInputFormField lastNameEnInput = new TextInputFormField();
	private TextInputFormField penNameEnInput = new TextInputFormField();	
	private ListBoxFormField languageList = new ListBoxFormField();
	private TextInputFormField emailInput = new TextInputFormField();
	private Button addButton = new Button( "Add" );
	
	
	public AuthorsDataInputViewImpl(){
		firstNameInput.setPlaceholder( "First Name" );
		firstNameInput.setRequired( true );
		
		lastNameInput.setPlaceholder( "Last Name" );
		
		penNameInput.setPlaceholder( "Pen Name" );

		firstNameEnInput.setPlaceholder( "First Name (English)" );
		firstNameEnInput.setRequired( true );
		
		lastNameEnInput.setPlaceholder( "Last Name (English)" );
		
		penNameEnInput.setPlaceholder( "Pen Name (English)" );

		languageList.setRequired( true );
		emailInput.setPlaceholder( "Email" );

		
		// Composing the widget
		panel.add( firstNameRow );
		panel.add( lastNameRow );
		panel.add( penNameRow);
		panel.add( languageEmailRow );
		panel.add( addButton );

		firstNameRow.add( firstNameCol );
		firstNameRow.add( firstNameEnCol );

		lastNameRow.add( lastNameCol );
		lastNameRow.add( lastNameEnCol );
		
		penNameRow.add( penNameCol );
		penNameRow.add( penNameEnCol );
		
		languageEmailRow.add( languageCol );
		languageEmailRow.add( emailCol );
		
		firstNameCol.add( firstNameInput );
		firstNameEnCol.add( firstNameEnInput );
		
		lastNameCol.add( lastNameInput );
		lastNameEnCol.add( lastNameEnInput );
		
		penNameCol.add( penNameInput );
		penNameEnCol.add( penNameEnInput );
		
		languageCol.add( languageList );
		emailCol.add( emailInput );
		
		
		// Setting required style classes
		panel.addStyleName( "container-fluid" );
		
		firstNameRow.addStyleName( "row" );
		lastNameRow.addStyleName( "row" );
		penNameRow.addStyleName( "row" );
		languageEmailRow.addStyleName( "row" );
		
		firstNameCol.addStyleName( "col-sm-4" );
		firstNameEnCol.addStyleName( "col-sm-4" );
		
		lastNameCol.addStyleName( "col-sm-4" );
		lastNameEnCol.addStyleName( "col-sm-4" );
		
		penNameCol.addStyleName( "col-sm-4" );
		penNameEnCol.addStyleName( "col-sm-4" );
		
		emailCol.addStyleName( "col-sm-4" );
		languageCol.addStyleName( "col-sm-4" );
		
		addButton.addStyleName( "btn btn-default" );
				
		initWidget( panel );
	}


	@Override
	public HandlerRegistration addAddButtonClickHandler(ClickHandler clickHandler) {
		return addButton.addClickHandler( clickHandler );
	}

	@Override
	public void addLanguageListItem( String item, String value ) {
		languageList.addItem( item, value );
	}
		
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = firstNameInput.validate() && validated;
		validated = lastNameInput.validate() && validated;
		validated = penNameInput.validate() && validated;
		validated = firstNameEnInput.validate() && validated;
		validated = lastNameEnInput.validate() && validated;
		validated = penNameEnInput.validate() && validated;
		validated = emailInput.validate() && validated;
		validated = languageList.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		firstNameInput.setEnabled( enabled );
		lastNameInput.setEnabled( enabled );
		penNameInput.setEnabled( enabled );
		firstNameEnInput.setEnabled( enabled );
		lastNameEnInput.setEnabled( enabled );
		penNameEnInput.setEnabled( enabled );
		emailInput.setEnabled( enabled );
		languageList.setEnabled( enabled );
	}

	@Override
	public AuthorData getAuthorData() {
		AuthorData authorData = new AuthorData();
		authorData.setLanguageId( Long.parseLong( languageList.getValue() ) );
		authorData.setFirstName( firstNameInput.getText() );
		authorData.setLastName( lastNameInput.getText() );
		authorData.setPenName( penNameInput.getText() );
		authorData.setFirstNameEn( firstNameEnInput.getText() );
		authorData.setLastNameEn( lastNameEnInput.getText() );
		authorData.setPenNameEn( penNameEnInput.getText() );
		authorData.setEmail( emailInput.getText() );
		return authorData;
	}

	@Override
	public void setAuthorData( AuthorData authorData ) {
		languageList.setValue( authorData.getLanguageId().toString() );
		firstNameInput.setText( authorData.getFirstName() );
		lastNameInput.setText( authorData.getLastName() );
		penNameInput.setText( authorData.getPenName() );
		firstNameEnInput.setText( authorData.getFirstNameEn() );
		lastNameEnInput.setText( authorData.getLastNameEn() );
		penNameEnInput.setText( authorData.getPenNameEn() );
		emailInput.setText( authorData.getEmail() );
	}

}
