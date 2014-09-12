package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewAccordionImpl extends PratilipiDataInputView {
	
	private final FocusPanel focusPanel = new FocusPanel();
	private final PratilipiType pratilipiType;
	private final Accordion accordion = new Accordion();
	private final PratilipiDataInputView pratilipiDataInputView;

	
	public PratilipiDataInputViewAccordionImpl( PratilipiType pratilipiType ) {
		
		this.pratilipiType = pratilipiType;
	
		focusPanel.add( accordion );
		accordion.setTitle( "Add " + pratilipiType.getName() );
		pratilipiDataInputView = new PratilipiDataInputViewImpl( pratilipiType );
		
		
		accordion.add( pratilipiDataInputView );

		
		initWidget( focusPanel );
	}
	
	
	@Override
	public HandlerRegistration addAddButtonClickHandler(ClickHandler clickHandler) {
		return pratilipiDataInputView.addAddButtonClickHandler( clickHandler );
	}

	@Override
	public void addAuthorListItem( String item, String value ) {
		pratilipiDataInputView.addAuthorListItem( item, value );
	}

	@Override
	public void addLanguageListItem( String item, String value ) {
		pratilipiDataInputView.addLanguageListItem( item, value );
	}
		
	@Override
	public boolean validateInputs() {
		return pratilipiDataInputView.validateInputs();
	}

	@Override
	public void setEnabled(boolean enabled) {
		pratilipiDataInputView.setEnabled( enabled );
	}

	@Override
	public PratilipiData getPratilipiData() {
		return pratilipiDataInputView.getPratilipiData();
	}

	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		focusPanel.setFocus( true );
		accordion.setTitle( "Edit " + pratilipiType.getName() );
		accordion.show();
		pratilipiDataInputView.setPratilipiData( pratilipiData );
	}

	@Override
	public PratilipiView getPratilipiView() {
		return pratilipiDataInputView.getPratilipiView();
	}

	@Override
	public void setPratilipiView( PratilipiView pratilipiView ) {
		pratilipiDataInputView.setPratilipiView( pratilipiView );
	}

	@Override
	public void reset() {
		accordion.hide();
		accordion.setTitle( "Add " + pratilipiType.getName() );
		pratilipiDataInputView.reset();
	}

}
