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
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.UserBookData;

public class HomeBookContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	private UserBookData userBookData = new UserBookData();
	private final Anchor AddReviewAnchor = new Anchor( "Review this book" );
	private final Anchor saveReviewAnchor = new Anchor( "Save Review" );
	private final Label savingLabel = new Label( "Saving ..." );
	
	public void onModuleLoad() {
		
		Window.alert( "Hello Book Page !" );
		
		AddReviewAnchor.addClickHandler( this );
		saveReviewAnchor.addClickHandler( this );
		
		RootPanel rootPanel = RootPanel.get( "PageContent-HomeBook-Review-AddOptions" );
		rootPanel.add( AddReviewAnchor );
		rootPanel.add( saveReviewAnchor );
		rootPanel.add( savingLabel );
		saveReviewAnchor.setVisible( false );
		savingLabel.setVisible( false );
		
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == AddReviewAnchor ) {
			AddReviewAnchor.setVisible( false );
			saveReviewAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-HomeBook-Summary" ).getElement() );
			
		} else if( event.getSource() == saveReviewAnchor ) {
			saveReviewAnchor.setVisible( false );
			savingLabel.setVisible( true );
			
			BookData bookData = new BookData();
			bookData.setId( Long.parseLong( Window.Location.getPath().substring( 6 ) ));
			bookData.setSummary( getHtmlFromEditor( "PageContent-HomeBook-Summary" ) );
			
			pratilipiService.updateBook(
					new UpdateBookRequest( bookData ),
					new AsyncCallback<UpdateBookResponse>() {
				
				@Override
				public void onSuccess(UpdateBookResponse result) {
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
