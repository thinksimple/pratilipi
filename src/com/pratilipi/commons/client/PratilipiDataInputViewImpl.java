package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.CheckBoxFormField;
import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewImpl extends PratilipiDataInputView {
	
	private Panel panel = new FlowPanel();
	
	private TextInputFormField titleInput = new TextInputFormField();
	private TextInputFormField titleEnInput = new TextInputFormField();
	private ListBoxFormField typeList = new ListBoxFormField();
	private ListBoxFormField languageList = new ListBoxFormField();
	private CheckBoxFormField isPublicDomain = new CheckBoxFormField();
	
	private PratilipiData pratilipiData = null;

	
	public PratilipiDataInputViewImpl() {
		
	    titleInput.setPlaceholder( "Title" );
	    titleInput.setRequired( true );
	    titleEnInput.setPlaceholder( "Title (English)" );
	    typeList.setPlaceholder( "Select Type" );
	    typeList.setRequired( true );
	    languageList.setPlaceholder( "Select Language" );
	    languageList.setRequired( true );
		isPublicDomain.setText( "Classics" );
		isPublicDomain.setVisible( false );
		
		for( PratilipiType pratilipiType : PratilipiType.values() )
			typeList.addItem( pratilipiType.getName(), pratilipiType.toString() );
		
	
		// Composing the widget
		panel.add( titleInput );
		panel.add( titleEnInput );
		panel.add( typeList );
		panel.add( languageList );
		panel.add( isPublicDomain );

		
		// Setting required style classes
		panel.getElement().setAttribute( "style", "margin:15px" );

		
		initWidget( panel );
	}
	
	
	@Override
	public void add( Button button ) {
		panel.add( button );
	}

	@Override
	public void addLanguageListItem( LanguageData languageData ) {
		languageList.addItem(
				languageData.getName() + " (" + languageData.getNameEn() + ")",
				languageData.getId().toString() );

		if( pratilipiData != null && pratilipiData.getLanguageId().equals( languageData.getId() ) )
			languageList.setValue( languageData.getId().toString() );
	}
		
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = titleInput.validate() && validated;
		validated = titleEnInput.validate() && validated;
		validated = typeList.validate() && validated;
		validated = languageList.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		titleInput.setEnabled( enabled );
		titleEnInput.setEnabled( enabled );
		typeList.setEnabled( enabled );	
		languageList.setEnabled( enabled );	
		isPublicDomain.setEnabled( enabled );
	}

	@Override
	public PratilipiData getPratilipiData() {
		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setId( this.pratilipiData == null ? null : this.pratilipiData.getId() );
		
		pratilipiData.setType( PratilipiType.valueOf( typeList.getValue() ) );
		pratilipiData.setTitle( titleInput.getText() );
		pratilipiData.setTitleEn( titleEnInput.getText() );
		pratilipiData.setLanguageId( Long.parseLong( languageList.getValue() ) );
		pratilipiData.setPublicDomain( isPublicDomain.isChecked() );
		
		return pratilipiData;
	}

	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
		
		typeList.setValue( pratilipiData.getType().toString() );
		titleInput.setText( pratilipiData.getTitle() );
		titleEnInput.setText( pratilipiData.getTitleEn() );
		languageList.setValue( pratilipiData.getLanguageId().toString() );
		if( pratilipiData.hasPublicDomain() ) {
			isPublicDomain.setChecked( pratilipiData.isPublicDomain() );
			isPublicDomain.setVisible( true );
		}
	}

}
