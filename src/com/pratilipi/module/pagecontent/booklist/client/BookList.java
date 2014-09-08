package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.PratilipiData;

public class BookList implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		
		pratilipiService.getPratilipiList(
				new GetPratilipiListRequest( PratilipiType.BOOK, null, 100 ),
				new AsyncCallback<GetPratilipiListResponse>() {
			
			@Override
			public void onSuccess( GetPratilipiListResponse response ) {

				for( PratilipiData pratilipiData : response.getPratilipiDataList() ) {
					BookView bookView = new BookViewThumbnailImpl();
					bookView.setBookData( (BookData) pratilipiData );
					RootPanel
							.get( "PageContent-BookList" )
							.add( bookView.getThumbnail() );
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
