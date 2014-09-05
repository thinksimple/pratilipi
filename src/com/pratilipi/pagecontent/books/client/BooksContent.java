package com.pratilipi.pagecontent.books.client;

import com.claymus.commons.client.ui.Accordion;
import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewBookDetailImpl;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewImpl;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddBookRequest;
import com.pratilipi.service.shared.AddBookResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.LanguageData;

public class BooksContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private final Accordion accordion = new Accordion();
	private final PratilipiDataInputView<BookData> bookDataInputView =
			new PratilipiDataInputViewImpl<BookData>( PratilipiType.BOOK );

	
	public void onModuleLoad() {
		
		if( RootPanel.get( "PageContent-Books-DataInput" ) != null ) {
		
			accordion.setTitle( "Add Book" );
			bookDataInputView.addAddButtonClickHandler( this );

			accordion.add( bookDataInputView );
			RootPanel.get( "PageContent-Books-DataInput" ).add( accordion );
			
			
			// Load list of authors.
			pratilipiService.getAuthorList( new GetAuthorListRequest( null , 100 ), new AsyncCallback<GetAuthorListResponse>() {

				@Override
				public void onFailure( Throwable caught ) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess( GetAuthorListResponse response ) {
					for( AuthorData authorData : response.getAuthorList() )
						bookDataInputView.setAuthorList( authorData );
				}
				
			});
			
			// Load list of languages
		    pratilipiService.getLanguageList( new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess( GetLanguageListResponse response ) {
					for( LanguageData languageData : response.getLanguageList())
						bookDataInputView.setLanguageList( languageData );
				}
				
		    });
		
		}

		RootPanel.get( "PageContent-Books" ).add( new InfiniteScrollPanel() {

			@Override
			protected void loadItems() {
				
				pratilipiService.getBookList( new GetBookListRequest(), new AsyncCallback<GetBookListResponse>() {
					
					@Override
					public void onSuccess( GetBookListResponse response ) {

						for( BookData bookData : response.getBookList() ) {
							PratilipiView pratilipiView = new PratilipiViewBookDetailImpl();
							pratilipiView.setPratilipiData( bookData );
							add( pratilipiView );
						}
						
						loadSuccessful();
					}
					
					@Override
					public void onFailure( Throwable caught ) {
						loadFailed();
						// TODO Auto-generated method stub
						Window.alert( caught.getMessage() );
					}
					
				} );
				
			}
			
		} );
	
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( !bookDataInputView.validateInputs() )
			return;
		
		bookDataInputView.setEnabled( false );
		BookData bookData = bookDataInputView.getPratilipiData();
		AddBookRequest request = new AddBookRequest( bookData );
		pratilipiService.addBook( request, new AsyncCallback<AddBookResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				bookDataInputView.setEnabled( true );
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess(AddBookResponse result) {
				Window.Location.reload();				
			}
			
		});

	}
	
}
