package com.pratilipi.pagecontent.books.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.BookView;
import com.pratilipi.commons.client.BookViewDetailImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.data.BookData;

public class BooksContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {

		RootPanel.get( "PageContent-Books" ).add( new InfiniteScrollPanel() {

			@Override
			protected void loadItems() {
				
				pratilipiService.getBookList( new GetBookListRequest(), new AsyncCallback<GetBookListResponse>() {
					
					@Override
					public void onSuccess( GetBookListResponse response ) {

						for( BookData bookData : response.getBookList() ) {
							BookView bookView = new BookViewDetailImpl();
							bookView.setBookData( bookData );
							add( bookView );
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
}
