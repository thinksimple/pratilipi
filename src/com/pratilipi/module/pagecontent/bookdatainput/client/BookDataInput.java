package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.BookDataInputView;
import com.pratilipi.commons.client.BookDataInputViewImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class BookDataInput implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private final Accordion accordion = new Accordion();
	
	private BookDataInputView bookDataInputView = new BookDataInputViewImpl();
	
	public void onModuleLoad() {
		
		if( RootPanel.get( "PageContent-Authors-DataInput" ) == null )
			return;
		else {
				//Get list of authors.
				pratilipiService.getAuthorList(new GetAuthorListRequest( null , 20 ), new AsyncCallback<GetAuthorListResponse>(){
	
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
	
					@Override
					public void onSuccess(GetAuthorListResponse response) {
						for(AuthorData authorData : response.getAuthorList() )
							//authorOracle.add( authorData.getFirstName()+ " " + authorData.getLastName());
							bookDataInputView.setAuthorList( authorData );
					}});
				
				//getting list of languages for suggestion box
			    pratilipiService.getLanguageList(new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						
					}

					@Override
					public void onSuccess(GetLanguageListResponse response) {
						for( LanguageData languageData : response.getLanguageList())
							bookDataInputView.setLanguageList(languageData);
					}});
			    
			    //getting list of publishers
			    /*pratilipiService.getPublisherList(new GetPublisherListRequest(), new AsyncCallback<GetPublisherListResponse>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert( caught.getMessage() );
					}

					@Override
					public void onSuccess(GetPublisherListResponse response) {
						for(PublisherData publisherData : response.getPublisherList())
							bookDataInputView.setPublisherList( publisherData );
					}});*/
		}
		
		accordion.setTitle( "Add Book" );
		bookDataInputView.addAddButtonClickHandler( this );

		accordion.add( bookDataInputView );
		RootPanel.get( "PageContent-Books-DataInput" ).add( accordion );
		
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( !bookDataInputView.validateInputs() )
			return;
		
		bookDataInputView.setEnabled( false );
		PratilipiData pratilipiData = bookDataInputView.getPratilipiData();
		AddBookRequest request = new AddBookRequest( pratilipiData );
		pratilipiService.addBook( request, new AsyncCallback<AddBookResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				bookDataInputView.setEnabled( true );
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess(AddBookResponse result) {
				Window.Location.reload();				
			}});
		
		/*
		String path = Window.Location.getPath();
		
		//Add new book page.
		if(path.equals("/book/new")){
			final Button button = new Button( "Save" );
			button.addClickHandler( new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
				
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
			
				}
			} );
			
			RootPanel.get( "PageContent-HomeBook-New" ).add( bookDataInputView );
			RootPanel.get( "PageContent-HomeBook-New" ).add( updateButton );
			RootPanel.get( "PageContent-HomeBook-New" ).add( new BookDataUpdateViewImpl( bookId) );
		}*/
	} 
}
