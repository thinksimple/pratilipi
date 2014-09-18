package com.pratilipi.pagecontent.reader.client;

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
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.data.PratilipiContentData;

public class ReaderContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	// Content edit options widgets
	private final Anchor editContentAnchor = new Anchor( "Edit Content" );
	private final Anchor saveContentAnchor = new Anchor( "Save Content" );
	private final Label savingContentLabel = new Label( "Saving Content ..." );

	
	private String url = Window.Location.getPath();
	
	public void onModuleLoad() {

		// Content edit options
		RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-Content-EditOptions" );
		if( rootPanel != null ) {
			editContentAnchor.addClickHandler( this );
			saveContentAnchor.addClickHandler( this );
			saveContentAnchor.setVisible( false );
			savingContentLabel.setVisible( false );
	
			rootPanel.add( editContentAnchor );
			rootPanel.add( saveContentAnchor );
			rootPanel.add( savingContentLabel );
		}

	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == editContentAnchor ) {
			editContentAnchor.setVisible( false );
			saveContentAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Pratilipi-Content" ).getElement() );
			
		} else if( event.getSource() == saveContentAnchor ) {
			saveContentAnchor.setVisible( false );
			savingContentLabel.setVisible( true );
			
			String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
			String pageNoStr = Window.Location.getParameter( "page" );
			
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			Integer pageNo = pageNoStr == null ? 1 : Integer.parseInt( pageNoStr );

			PratilipiContentData pratilipiContentData = new PratilipiContentData();
			pratilipiContentData.setPratilipiId( pratilipiId );
			pratilipiContentData.setPageNo( pageNo );
			pratilipiContentData.setContent( getHtmlFromEditor( "PageContent-Pratilipi-Content" ) );
			
			pratilipiService.savePratilipiContent(
					new SavePratilipiContentRequest( pratilipiContentData ),
					new AsyncCallback<SavePratilipiContentResponse>() {
				
				@Override
				public void onSuccess( SavePratilipiContentResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					savingContentLabel.setVisible( false );
					saveContentAnchor.setVisible( true );
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
