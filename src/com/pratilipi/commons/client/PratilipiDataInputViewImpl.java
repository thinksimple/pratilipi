package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.CheckBoxFormField;
import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.NumberInputFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewImpl extends PratilipiDataInputView {
	
	private Panel panel = new FlowPanel();
	private Panel innerPanel = new FlowPanel();
	
	private TextInputFormField titleInput = new TextInputFormField();
	private ListBoxFormField authorList = new ListBoxFormField();
	private ListBoxFormField languageList = new ListBoxFormField();
	private NumberInputFormField pageCountInput = new NumberInputFormField();
	private CheckBoxFormField isPublicDomain = new CheckBoxFormField();
	private Button addButton = new Button();
	
	private final PratilipiType pratilipiType;
	private Long pratilipiId;
	private PratilipiView pratilipiView;

	private String pageUrl;
	private String coverImageUrl;
	private String authorPageUrl;

	
	public PratilipiDataInputViewImpl( PratilipiType pratilipiType ) {
		
		this.pratilipiType = pratilipiType;
		
	    titleInput.setPlaceholder( "Title" );
	    titleInput.setRequired( true );
	    authorList.addItem( "Select Author", "" );
	    authorList.setRequired( true );
	    languageList.addItem( "Select Language", "" );
	    languageList.setRequired( true );
	    pageCountInput.setPlaceholder( "Page Count" );
		isPublicDomain.setText( "Classics" );
		addButton.setText(  "Save " + pratilipiType.getName() );
		
	
		// Composing the widget
		panel.add( innerPanel );
	    
		innerPanel.add( titleInput );
		innerPanel.add( authorList );
		innerPanel.add( languageList );
		innerPanel.add( pageCountInput );
		innerPanel.add( isPublicDomain );
		innerPanel.add( addButton );

		
		// Setting required style classes
		panel.setStyleName( "row" );
		innerPanel.setStyleName( "col-sm-4" );
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
		validated = pageCountInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		titleInput.setEnabled( enabled );
		authorList.setEnabled( enabled );
		languageList.setEnabled( enabled );	
		pageCountInput.setEnabled( enabled );
		isPublicDomain.setEnabled( enabled );
	}

	@Override
	public PratilipiData getPratilipiData() {
		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setId( pratilipiId );
		pratilipiData.setType( pratilipiType );
		pratilipiData.setTitle( titleInput.getText() );
		pratilipiData.setAuthorId( Long.valueOf(authorList.getValue()) );
		pratilipiData.setLanguageId( Long.parseLong( languageList.getValue() ) );
		pratilipiData.setPageCount( pageCountInput.getValue() );
		pratilipiData.setPublicDomain( isPublicDomain.isChecked() );
		
		pratilipiData.setAuthorName( authorList.getValueText() );
		pratilipiData.setLanguageName( languageList.getValueText() );
		
		pratilipiData.setPageUrl( pageUrl );
		pratilipiData.setCoverImageUrl( coverImageUrl );
		pratilipiData.setAuthorPageUrl( authorPageUrl );
		
		return pratilipiData;
	}

	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		pratilipiId = pratilipiData.getId();
		
		titleInput.setText( pratilipiData.getTitle() );
		authorList.setValue( pratilipiData.getAuthorId().toString() );
		languageList.setValue( pratilipiData.getLanguageId().toString() );
		pageCountInput.setValue( pratilipiData.getPageCount() );
		isPublicDomain.setChecked( pratilipiData.isPublicDomain() );
		
		pageUrl = pratilipiData.getPageUrl();
		coverImageUrl = pratilipiData.getCoverImageUrl();
		authorPageUrl = pratilipiData.getAuthorPageUrl();
	}

	@Override
	public PratilipiView getPratilipiView() {
		return pratilipiView;
	}

	@Override
	public void setPratilipiView( PratilipiView pratilipiView ) {
		this.pratilipiView = pratilipiView;
	}

	@Override
	public void reset() {
		pratilipiId = null;
		
		titleInput.setText( null );
		authorList.setValue( null );
		languageList.setValue( null );
		pageCountInput.setValue( null );
		isPublicDomain.setChecked( false );
		
		titleInput.resetValidation();
		authorList.resetValidation();
		languageList.resetValidation();
		isPublicDomain.resetValidation();
		
		pratilipiView = null;
	}

}
