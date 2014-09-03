package com.pratilipi.pagecontent.authors.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;

public class AuthorsContent implements EntryPoint, ClickHandler {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	private final Accordion accordion = new Accordion();
	
	private AuthorsDataInputView authorsDataInputView = new AuthorsDataInputViewImpl();
	private Button addAuthorButton = new Button( "Add Author" );

	
	public void onModuleLoad() {
		
		if( RootPanel.get( "PageContent-Authors-DataInput" ) != null ) {
			accordion.setTitle( "Add Author" );
			addAuthorButton.addClickHandler( this );

			accordion.add( authorsDataInputView );
			accordion.add( addAuthorButton );
			
			RootPanel.get( "PageContent-Authors-DataInput" ).add( accordion );
		}
		
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == addAuthorButton ) {
		
			ValidateAuthorData validator = new ValidateAuthorData( authorsDataInputView );
			if( validator.validateAuthor() ) {
				pratilipiService.addAuthor( new AddAuthorRequest( authorsDataInputView.getAuthor()), new AsyncCallback<AddAuthorResponse>(){
	
					@Override
					public void onFailure(Throwable caught) {
						Window.alert( caught.getMessage() );
					}
	
					@Override
					public void onSuccess(AddAuthorResponse result) {
						Window.alert( "Author added successfully !" );
						Window.Location.reload();
					}});
			
			} else {
				Window.alert("Error in form");
			}		
		}
		
	}
	
}
