package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewModalImpl extends PratilipiDataInputView {
	
	private final FocusPanel focusPanel = new FocusPanel();
	private final PratilipiType pratilipiType;
	private final Modal modal = new Modal();
	private final PratilipiDataInputView pratilipiDataInputView;

	
	public PratilipiDataInputViewModalImpl( PratilipiType pratilipiType ) {
		
		this.pratilipiType = pratilipiType;
	
		modal.setTitle( "Add " + pratilipiType.getName() );
		pratilipiDataInputView = new PratilipiDataInputViewImpl( pratilipiType );
		pratilipiDataInputView.getElement().getStyle().setPadding( 15, Unit.PX );
		modal.add( pratilipiDataInputView );
		
		initWidget( modal );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		if( visible )
			modal.show();
		else
			modal.hide();
	}
	
	@Override
	public HandlerRegistration addAddButtonClickHandler(ClickHandler clickHandler) {
		return pratilipiDataInputView.addAddButtonClickHandler( clickHandler );
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
		modal.setTitle( "Edit " + pratilipiType.getName() );
		modal.show();
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
		modal.hide();
		modal.setTitle( "Add " + pratilipiType.getName() );
		pratilipiDataInputView.reset();
	}

	
}
