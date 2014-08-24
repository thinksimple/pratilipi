package com.pratilipi.module.pagecontent.homebook.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddUserBookRequest;
import com.pratilipi.service.shared.AddUserBookResponse;
import com.pratilipi.service.shared.data.UserBookData;

public class HomeBookContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	private final Anchor addReviewAnchor = new Anchor( "Review this book." );
	private final Anchor saveReviewAnchor = new Anchor( "Save Review" );
	private final Label savingLabel = new Label( "Saving ..." );
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get( "PageContent-HomeBook-Review-AddOptions" );
		if( rootPanel != null ) {
			addReviewAnchor.addClickHandler( this );
			saveReviewAnchor.addClickHandler( this );
		
			rootPanel.add( addReviewAnchor );
			rootPanel.add( saveReviewAnchor );
			rootPanel.add( savingLabel );
			
			saveReviewAnchor.setVisible( false );
			savingLabel.setVisible( false );
		}
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == addReviewAnchor ) {
			addReviewAnchor.setVisible( false );
			saveReviewAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-HomeBook-Review" ).getElement() );
			
		} else if( event.getSource() == saveReviewAnchor ) {
			saveReviewAnchor.setVisible( false );
			savingLabel.setVisible( true );
			
			UserBookData userBookData = new UserBookData();
			userBookData.setBookId( Long.parseLong( Window.Location.getPath().substring( 6 ) ));
			userBookData.setReview( getHtmlFromEditor( "PageContent-HomeBook-Review" ) );
			
			pratilipiService.addUserBook(
					new AddUserBookRequest( userBookData ),
					new AsyncCallback<AddUserBookResponse>() {
				
				@Override
				public void onSuccess( AddUserBookResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					savingLabel.setVisible( false );
					saveReviewAnchor.setVisible( true );
				}
				
			});
		}
		
	}
	
	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;
	
	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;
		

}
