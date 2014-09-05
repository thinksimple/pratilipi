package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewImpl extends PratilipiDataInputView {
	
	private Panel panel = new FlowPanel();

	private Panel titleInputRow = new FlowPanel();
	private Panel authorInputRow = new FlowPanel();
	private Panel languageInputRow = new FlowPanel();
	
	private Panel titleInputCol = new SimplePanel();
	private Panel authorInputCol = new SimplePanel();
	private Panel languageInputCol = new SimplePanel();
	
	private TextInputFormField titleInput = new TextInputFormField();
	private ListBoxFormField authorList = new ListBoxFormField();
	private ListBoxFormField languageList = new ListBoxFormField();;
	private Button addButton = new Button();
	
	
	private final PratilipiType pratilipiType;
	
	
	public PratilipiDataInputViewImpl( PratilipiType pratilipiType ) {
		
		this.pratilipiType = pratilipiType;
		
	    titleInput.setPlaceholder( "Title" );
	    titleInput.setRequired( true );
	    authorList.addItem( "Select Author", "" );
	    authorList.setRequired( true );
	    languageList.addItem( "Select Language", "" );
	    languageList.setRequired( true );
		addButton.setText(  "Add " + pratilipiType.getName() );
		
	
		// Composing the widget
		panel.add( titleInputRow );
		panel.add( authorInputRow );
		panel.add( languageInputRow );
		panel.add( addButton );

		titleInputRow.add( titleInputCol );
		authorInputRow.add( authorInputCol );
	    languageInputRow.add( languageInputCol );
	    
		titleInputCol.add( titleInput );
	    authorInputCol.add( authorList );
	    languageInputCol.add( languageList );

		
		// Setting required style classes
		titleInputRow.setStyleName( "row" );
		authorInputRow.setStyleName( "row" );
		languageInputRow.setStyleName( "row" );
	    
		titleInputCol.setStyleName( "col-sm-4" );
	    authorInputCol.setStyleName( "col-sm-4" );
	    languageInputCol.setStyleName( "col-sm-4" );
	    
	    addButton.setStyleName( "btn btn-default" );

		
		initWidget( panel );
	}
	
	
	@Override
	public HandlerRegistration addAddButtonClickHandler(ClickHandler clickHandler) {
		return addButton.addClickHandler( clickHandler );
	}

	@Override
	public void addAuthorListItem( String item, String value ) {
		authorList.addItem( item, value );
	}

	@Override
	public void addLanguageListItem( String item, String value ) {
		languageList.addItem( item, value );
	}
		
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = titleInput.validate() && validated;
		validated = authorList.validate() && validated;
		validated = languageList.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		titleInput.setEnabled( enabled );
		authorList.setEnabled( enabled );
		languageList.setEnabled( enabled );		
	}

	@Override
	public PratilipiData getPratilipiData() {
		PratilipiData pratilipiData = pratilipiType.newPratilipiData();
		pratilipiData.setTitle( titleInput.getText() );
		pratilipiData.setAuthorId( Long.valueOf(authorList.getValue()) );
		pratilipiData.setLanguageId( Long.parseLong( languageList.getValue() ) );
		return pratilipiData;
	}

	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		titleInput.setText( pratilipiData.getTitle() );
		authorList.setValue( pratilipiData.getAuthorId().toString() );
		languageList.setValue( pratilipiData.getLanguageId().toString() );
	}

}
