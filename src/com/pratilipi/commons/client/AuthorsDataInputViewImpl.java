package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public class AuthorsDataInputViewImpl extends AuthorsDataInputView {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private FormPanel form = new FormPanel();
	private Panel panel = new FlowPanel();
	private Panel firstNamePanel = new FlowPanel();
	private Panel firstNameCol = new SimplePanel();
	private Panel lastNameCol = new SimplePanel();
	private Panel lastNamePanel = new FlowPanel();
	private Panel firstNameEngCol = new SimplePanel();
	private Panel lastNameEngCol = new SimplePanel();
	private Panel penNamePanel = new FlowPanel();
	private Panel penNameCol = new SimplePanel();
	private Panel penNameEngCol = new SimplePanel();
	private Panel otherPanel = new FlowPanel();
	private Panel emailCol = new SimplePanel();
	private Panel languageCol = new SimplePanel();
	
	private TextInputFormField firstNameInput = new TextInputFormField();
	private TextInputFormField firstNameEngInput = new TextInputFormField();
	private TextInputFormField lastNameInput = new TextInputFormField();
	private TextInputFormField lastNameEngInput = new TextInputFormField();
	private TextInputFormField emailInput = new TextInputFormField();
	private TextInputFormField penNameInput = new TextInputFormField();
	private TextInputFormField penNameEngInput = new TextInputFormField();	
	private ListBoxFormField languageInput = new ListBoxFormField();
	private Button addButton = new Button( "Add" );
	
	public AuthorsDataInputViewImpl(){
		firstNameInput.setPlaceholder( "First Name" );
		firstNameInput.setRequired( true );
		firstNameInput.addStyleName( "form-group" );
		
		lastNameInput.setPlaceholder( "Last Name" );
		lastNameInput.setRequired( true );
		lastNameInput.addStyleName( "form-group" );
		
		firstNameEngInput.setPlaceholder( "First Name (English)" );
		firstNameEngInput.setRequired( true );
		firstNameEngInput.addStyleName( "form-group" );
		
		lastNameEngInput.setPlaceholder( "Last Name (English)" );
		lastNameEngInput.setRequired( true );
		lastNameEngInput.addStyleName( "form-group" );
		
		emailInput.setPlaceholder( "Email" );
		emailInput.addStyleName( "form-group" );
		
		penNameInput.setPlaceholder( "Pen Name" );
		penNameInput.setRequired( true );
		penNameInput.addStyleName( "form-group" );
		
		penNameEngInput.setPlaceholder( "Pen Name (English)" );
		penNameEngInput.setRequired( true );
		penNameEngInput.addStyleName( "form-group" );
		
		languageInput.setRequired( true );
		languageInput.addStyleName( "form-group" );
		pratilipiService.getLanguageList(new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(GetLanguageListResponse response) {
					for( LanguageData languageData : response.getLanguageList())
						languageInput.addItem(languageData.getName(), languageData.getId().toString());
					
				}});
		
		addButton.addStyleName( "btn btn-default" );
		
		firstNameCol.add( firstNameInput );
		firstNameCol.addStyleName( "col-sm-4" );
		firstNameEngCol.add( firstNameEngInput );
		firstNameEngCol.addStyleName( "col-sm-4" );
		firstNamePanel.add( firstNameCol );
		firstNamePanel.add( firstNameEngCol );
		firstNamePanel.addStyleName( "row" );
		
		lastNameCol.add( lastNameInput );
		lastNameCol.addStyleName( "col-sm-4" );
		lastNameEngCol.add( lastNameEngInput );
		lastNameEngCol.addStyleName( "col-sm-4" );
		lastNamePanel.add( lastNameCol );
		lastNamePanel.add( lastNameEngCol );
		lastNamePanel.addStyleName( "row" );
		
		penNameCol.add( penNameInput );
		penNameCol.addStyleName( "col-sm-4" );
		penNameEngCol.add( penNameEngInput );
		penNameEngCol.addStyleName( "col-sm-4" );
		penNamePanel.add( penNameCol );
		penNamePanel.add( penNameEngCol );
		penNamePanel.addStyleName( "row" );
		
		emailCol.add( emailInput );
		emailCol.addStyleName( "col-sm-4" );
		languageCol.add( languageInput );
		languageCol.addStyleName( "col-sm-4" );
		otherPanel.add( emailCol );
		otherPanel.add( languageCol );
		otherPanel.addStyleName( "row" );
		
		panel.addStyleName( "container-fluid" );
		

		panel.add( firstNamePanel );
		panel.add( lastNamePanel );
		panel.add( penNamePanel);
		panel.add( otherPanel );
		panel.add( addButton );
		
		form.getElement().setAttribute( "role", "form" );
		form.add( panel );
		
		
		initWidget( panel );
	}

	@Override
	public HandlerRegistration addAddButtonClickHandler(
			ClickHandler clickHandler) {
		return addButton.addClickHandler( clickHandler );
	}	

	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = firstNameInput.validate() && validated;
		validated = lastNameInput.validate() && validated;
		validated = firstNameEngInput.validate() && validated;
		validated = lastNameEngInput.validate() && validated;
		validated = penNameInput.validate() && validated;
		validated = penNameEngInput.validate() && validated;
		validated = emailInput.validate() && validated;
		validated = languageInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		firstNameInput.setEnabled( enabled );
		lastNameInput.setEnabled( enabled );
		firstNameEngInput.setEnabled( enabled );
		lastNameEngInput.setEnabled( enabled );
		penNameInput.setEnabled( enabled );
		penNameEngInput.setEnabled( enabled );
		emailInput.setEnabled( enabled );
		languageInput.setEnabled( enabled );
	}

	@Override
	public AuthorData getAuthorData() {
		AuthorData authorData = new AuthorData();
		authorData.setFirstName( firstNameInput.getText() );
		authorData.setLastName( lastNameInput.getText() );
		authorData.setFirstNameEn( firstNameEngInput.getText() );
		authorData.setLastNameEn( lastNameEngInput.getText() );
		authorData.setPenName( penNameInput.getText() );
		authorData.setPenNameEn( penNameEngInput.getText() );
		authorData.setEmail( emailInput.getText() );
		authorData.setLanguageName( languageInput.getItemText() );
		authorData.setLanguageId( Long.valueOf( languageInput.getValue() ));
		return authorData;
	}

	@Override
	public void setAuthorData(AuthorData authorData) {
		firstNameInput.setText( authorData.getFirstName() );
		lastNameInput.setText( authorData.getLastName() );
		firstNameEngInput.setText( authorData.getFirstNameEn() );
		lastNameEngInput.setText( authorData.getLastNameEn() );
		penNameInput.setText( authorData.getPenName() );
		penNameEngInput.setText( authorData.getPenNameEn() );
		emailInput.setText( authorData.getEmail() );
		languageInput.setValueText( authorData.getLanguageName() );
	}
	
}
