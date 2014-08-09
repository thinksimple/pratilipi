package com.pratilipi.module.pagecontent.bookdatainput.client;

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
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;

public class BookDataInput implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	private ValidateInputImpl validator;
	//private FormPanel form = new FormPanel();
	
	public void onModuleLoad() {
		
		final BookDataInputView bookDataInputView = new BookDataInputViewImpl();
		
		Button button = new Button( "Save" );
		button.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				validator = new ValidateInputImpl(bookDataInputView);
				boolean matchFound = validator.validateBook();
				if(matchFound){
					pratilipiService.addBook( new AddBookRequest( bookDataInputView.getBook() ), new AsyncCallback<AddBookResponse>() {
						
						@Override
						public void onSuccess( AddBookResponse result ) {
							Window.alert( "Book added successfully !" );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
						}
						
					} );
				}
				else {
					Window.alert("Error in Form");
				}
			}
			
		} );
		
		//form.add(bookDataInputView);
		//form.add(button);
		
		RootPanel.get( "PageContent-BookDataInput" ).add( bookDataInputView );
		RootPanel.get( "PageContent-BookDataInput" ).add( button );
		//RootPanel.get( "PageContent-BookDataInput" ).add(form);
		
	}
	
}
