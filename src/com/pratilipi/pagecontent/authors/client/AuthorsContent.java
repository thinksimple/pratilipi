package com.pratilipi.pagecontent.authors.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.AuthorsDataInputView;
import com.pratilipi.commons.client.AuthorsDataInputViewImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.data.AuthorData;

public class AuthorsContent implements EntryPoint, ClickHandler {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	private final Accordion accordion = new Accordion();
	
	private AuthorsDataInputView authorsDataInputView = new AuthorsDataInputViewImpl();

	
	public void onModuleLoad() {
				
		if( RootPanel.get( "PageContent-Authors-DataInput" ) == null )
			return;
		
		accordion.setTitle( "Add Author" );
		authorsDataInputView.addAddButtonClickHandler( this );

		accordion.add( authorsDataInputView );
		RootPanel.get( "PageContent-Authors-DataInput" ).add( accordion );
		
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( !authorsDataInputView.validateInputs() )
			return;
		
		authorsDataInputView.setEnabled( false );
		AuthorData authorData = authorsDataInputView.getAuthorData();
		AddAuthorRequest request = new AddAuthorRequest( authorData );
		pratilipiService.addAuthor( request, new AsyncCallback<AddAuthorResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				authorsDataInputView.setEnabled( true );
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess(AddAuthorResponse result) {
				Window.Location.reload();				
			}});
	}
	
}
