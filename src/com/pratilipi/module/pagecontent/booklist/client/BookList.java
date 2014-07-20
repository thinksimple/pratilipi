package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetBookListRequest;
import com.pratilipi.service.shared.GetBookListResponse;
import com.pratilipi.service.shared.data.BookData;

public class BookList implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		
		pratilipiService.getBookList( new GetBookListRequest(), new AsyncCallback<GetBookListResponse>() {
			
			@Override
			public void onSuccess( GetBookListResponse response ) {

				for( BookData bookData : response.getBookList() ) {
					BookView bookView = new BookViewThumbnailImpl();
					bookView.setBookData( bookData );
					RootPanel
							.get( "Pratilipi-BookList" )
							.add( bookView );
				}
				
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub
				Window.alert( caught.getMessage() );
			}
			
		} );
		
	}
	
}
