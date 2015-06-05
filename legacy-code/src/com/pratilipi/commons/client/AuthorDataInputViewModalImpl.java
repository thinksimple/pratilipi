package com.pratilipi.commons.client;

import java.util.LinkedList;
import java.util.List;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.user.client.ui.Button;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public class AuthorDataInputViewModalImpl extends AuthorDataInputView {
	
	private final Modal modal = new Modal();
	private final AuthorDataInputView authorDataInputView = new AuthorDataInputViewImpl();
	private final List<Button> addedButtonList = new LinkedList<>();
	
	
	public AuthorDataInputViewModalImpl() {
		
		modal.setTitle( "New Author" );
		modal.add( authorDataInputView );
				
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
		authorDataInputView.addLanguageListItem( languageData );
	}
		
	@Override
	public boolean validateInputs() {
		return authorDataInputView.validateInputs();
	}

	@Override
	public void setEnabled(boolean enabled) {
		authorDataInputView.setEnabled( enabled );
		for( Button button : addedButtonList )
			button.setEnabled( enabled );
	}

	@Override
	public AuthorData getAuthorData() {
		return authorDataInputView.getAuthorData();
	}

	@Override
	public void setAuthorData( AuthorData authorData ) {
		authorDataInputView.setAuthorData( authorData );
		modal.setTitle( "Edit Author Info" );
	}


	@Override
	public void setServerError(String error) { }


	@Override
	public void setVisibleServerError(boolean visible) { }

}
