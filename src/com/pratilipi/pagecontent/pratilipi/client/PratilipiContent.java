package com.pratilipi.pagecontent.pratilipi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class PratilipiContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	
	private final Button saveReviewButton = new Button( "Submit Review" );
	
	private String url = Window.Location.getPath();

	
	public void onModuleLoad() {
		RootPanel reviewPanel = RootPanel.get( "PageContent-Pratilipi-Review" );
		RootPanel submitButtonPanel = RootPanel.get( "PageContent-Pratilipi-Review-AddOptions" );
		if( reviewPanel != null && submitButtonPanel != null ) {
			saveReviewButton.getElement().setAttribute( "class", "btn btn-danger" );
			saveReviewButton.addClickHandler( this );
			
			loadBasicEditor( RootPanel.get( "PageContent-Pratilipi-Review" ).getElement() );
			submitButtonPanel.add( saveReviewButton );
		}
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == saveReviewButton ) {
			
			if( getHtmlFromEditor( "PageContent-Pratilipi-Review" ).trim().isEmpty() )
				return;
			
			
			saveReviewButton.setEnabled( false );
			saveReviewButton.setText( "Submitting ..." );
			
			String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			
			UserPratilipiData userPratilipiData = new UserPratilipiData();
			userPratilipiData.setPratilipiId( pratilipiId );
			userPratilipiData.setReview( getHtmlFromEditor( "PageContent-Pratilipi-Review" ) );
			
			pratilipiService.addUserPratilipi(
					new AddUserPratilipiRequest( userPratilipiData ),
					new AsyncCallback<AddUserPratilipiResponse>() {
				
				@Override
				public void onSuccess( AddUserPratilipiResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					saveReviewButton.setEnabled( true );
					saveReviewButton.setText( "Submit Review" );
				}
				
			});
		}
		
	}
	
	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;

	private native void loadBasicEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element, {
				toolbar : 'BASIC'
		} );
	}-*/;

	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

}
