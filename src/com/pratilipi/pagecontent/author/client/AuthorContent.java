package com.pratilipi.pagecontent.author.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.data.AuthorData;

public class AuthorContent implements EntryPoint, ClickHandler {
	
	private PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private final FileUpload authorImageUpload = new FileUpload();
	
	// Summary edit options widgets
	private final Anchor editSummaryAnchor = new Anchor( "Edit Summary" );
	private final Anchor saveSummaryAnchor = new Anchor( "Save Summary" );
	private final Label savingSummaryLabel = new Label( "Saving Summary ..." );

	// Content edit options widgets
	private final Anchor editContentAnchor = new Anchor( "Edit Content" );
	private final Anchor saveContentAnchor = new Anchor( "Save Content" );
	private final Label savingContentLabel = new Label( "Saving Content ..." );
		
	private String url = Window.Location.getPath();
	
	public void onModuleLoad() {
		
		String authorIdStr = url.substring( PratilipiHelper.URL_AUTHOR_PAGE.length() );
		
		// Cover image edit options
		RootPanel rootPanel = RootPanel.get( "PageContent-Author-Image-EditOptions" );
		if( rootPanel != null ) {
			String uploadUrl = PratilipiHelper.URL_AUTHOR_IMAGE + authorIdStr;
			authorImageUpload.getElement().setAttribute( "data-url", uploadUrl );
			loadFileUploader( authorImageUpload.getElement() );

			rootPanel.add( authorImageUpload );
		}
		
		// Summary edit options
		rootPanel = RootPanel.get( "PageContent-Author-Summary-EditOptions" );
		if( rootPanel != null ) {
			editSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.setVisible( false );
			savingSummaryLabel.setVisible( false );
		
			rootPanel.add( editSummaryAnchor );
			rootPanel.add( saveSummaryAnchor );
			rootPanel.add( savingSummaryLabel );
		}
				
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if( event.getSource() == editSummaryAnchor ) {
			editSummaryAnchor.setVisible( false );
			saveSummaryAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Author-Summary" ).getElement() );
			
		} else if( event.getSource() == saveSummaryAnchor ) {
			saveSummaryAnchor.setVisible( false );
			savingSummaryLabel.setVisible( true );
			
			String authorIdStr = url.substring( PratilipiHelper.URL_AUTHOR_PAGE.length() );
			Long authorId = Long.parseLong( authorIdStr );

			AuthorData authorData = new AuthorData();
			authorData.setId( authorId );
			authorData.setSummary( getHtmlFromEditor( "PageContent-Author-Summary" ) );
			
			pratilipiService.saveAuthor(
					new SaveAuthorRequest( authorData ),
					new AsyncCallback<SaveAuthorResponse>() {
				
				@Override
				public void onSuccess( SaveAuthorResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					savingSummaryLabel.setVisible( false );
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
