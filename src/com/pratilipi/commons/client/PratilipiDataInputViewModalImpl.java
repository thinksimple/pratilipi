package com.pratilipi.commons.client;

import java.util.LinkedList;
import java.util.List;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.user.client.ui.Button;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiDataInputViewModalImpl extends PratilipiDataInputView {
	
	private final Modal modal = new Modal();
	private final PratilipiDataInputView pratilipiDataInputView = new PratilipiDataInputViewImpl();
	private final List<Button> addedButtonList = new LinkedList<>();
	
	
	public PratilipiDataInputViewModalImpl() {
		
		modal.setTitle( "New Pratilipi" );
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
	public void add( Button button ) {
		modal.add( button );
		addedButtonList.add( button );
	}

	@Override
	public void addLanguageListItem( LanguageData languageData ) {
		pratilipiDataInputView.addLanguageListItem( languageData );
	}
		
	@Override
	public boolean validateInputs() {
		return pratilipiDataInputView.validateInputs();
	}

	@Override
	public void setEnabled(boolean enabled) {
		pratilipiDataInputView.setEnabled( enabled );
		for( Button button : addedButtonList )
			button.setEnabled( enabled );
	}

	@Override
	public PratilipiData getPratilipiData() {
		return pratilipiDataInputView.getPratilipiData();
	}

	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		pratilipiDataInputView.setPratilipiData( pratilipiData );
		modal.setTitle( "Edit " + pratilipiData.getType().getName() );
	}

}
