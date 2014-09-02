package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;

public class BookDataInput implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	private ValidateInputImpl validator;
	private BookDataInputView bookDataInputView = new BookDataInputViewImpl();
	
	public void onModuleLoad() {
		
		String path = Window.Location.getPath();
		
		//Add new book page.
		if(path.equals("/book/new")){
			final Button button = new Button( "Save" );
			button.addClickHandler( new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					validator = new ValidateInputImpl(bookDataInputView);
					boolean matchFound = validator.validateBook();
					if(matchFound){
						pratilipiService.addBook( new AddBookRequest( bookDataInputView.getBook() ), new AsyncCallback<AddBookResponse>() {
							
							@Override
							public void onSuccess( AddBookResponse result ) {
								Long bookId = result.getBookId();
								Window.alert( "Book added successfully !" );
								Window.Location.assign( PratilipiHelper.BOOK_PAGE_URL + bookId);
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
			
			RootPanel.get( "PageContent-HomeBook-New" ).add( bookDataInputView );
			RootPanel.get( "PageContent-HomeBook-New" ).add( button );
		}
		
		//Update book page.
		else{
			String id = Window.Location.getParameter("bookid");
			Window.alert("Update book " + id);
			final Long bookId = Long.valueOf(id);
			
			Button updateButton = new Button("Update");
			updateButton.addClickHandler( new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					bookDataInputView.setBookId(bookId);
					validator = new ValidateInputImpl(bookDataInputView);
					boolean matchFound = validator.validateBook();
					if(matchFound){
						pratilipiService.addBook( new AddBookRequest( bookDataInputView.getBook() ), new AsyncCallback<AddBookResponse>() {
							
							@Override
							public void onSuccess( AddBookResponse result ) {
								Window.alert( "Book updated successfully !" );
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
			
			RootPanel.get( "PageContent-HomeBook-New" ).add( bookDataInputView );
			RootPanel.get( "PageContent-HomeBook-New" ).add( updateButton );
			RootPanel.get( "PageContent-HomeBook-New" ).add( new BookDataUpdateViewImpl( bookId) );
		}
	}
}
