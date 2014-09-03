package com.pratilipi.pagecontent.book.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.UpdateBookRequest;
import com.pratilipi.service.shared.UpdateBookResponse;
import com.pratilipi.service.shared.data.BookData;

public class BookContentEditOptions implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	// Cover image edit options widgets
	private final FileUpload coverImageUpload = new FileUpload();
	
	
	// Summary edit options widgets
	private final Anchor editSummaryAnchor = new Anchor( "Edit Summary" );
	private final Anchor saveSummaryAnchor = new Anchor( "Save Summary" );
	private final Label savingLabel = new Label( "Saving ..." );
	

	public void onModuleLoad() {

		String uri = Window.Location.getPath();
		String bookIdStr = uri.substring( PratilipiHelper.URL_BOOK_PAGE.length() );
		
		
		// Cover image edit options
		coverImageUpload.getElement().setAttribute(
				"data-url", PratilipiHelper.URL_BOOK_COVER + bookIdStr );

		RootPanel coverImageEditOptions =
				RootPanel.get( "PageContent-Book-CoverImage-EditOptions" );
		coverImageEditOptions.add( coverImageUpload );
		
		loadFileUploader( coverImageUpload.getElement() );

		
		// Summary edit options
		editSummaryAnchor.addClickHandler( this );
		saveSummaryAnchor.addClickHandler( this );

		RootPanel rootPanel = RootPanel.get( "PageContent-Book-Summary-EditOptions" );
		rootPanel.add( editSummaryAnchor );
		rootPanel.add( saveSummaryAnchor );
		rootPanel.add( savingLabel );

		saveSummaryAnchor.setVisible( false );
		savingLabel.setVisible( false );
		
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == editSummaryAnchor ) {
			editSummaryAnchor.setVisible( false );
			saveSummaryAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Book-Summary" ).getElement() );
			
		} else if( event.getSource() == saveSummaryAnchor ) {
			saveSummaryAnchor.setVisible( false );
			savingLabel.setVisible( true );
			
			BookData bookData = new BookData();
			bookData.setId( Long.parseLong( Window.Location.getPath().substring( 6 ) ));
			bookData.setSummary( getHtmlFromEditor( "PageContent-Book-Summary" ) );
			
			pratilipiService.updateBook(
					new UpdateBookRequest( bookData ),
					new AsyncCallback<UpdateBookResponse>() {
				
				@Override
				public void onSuccess( UpdateBookResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					savingLabel.setVisible( false );
					saveSummaryAnchor.setVisible( true );
				}
				
			});
		}
		
	}
	
	private native void loadFileUploader( Element element ) /*-{
		$wnd.jQuery( element ).fileupload({
			dataType: 'html',
			done: function( e, data ) {
				$wnd.document.location.reload();
			}
		});
	}-*/;

	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;
	
	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

}
