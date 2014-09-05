package com.pratilipi.pagecontent.pratilipi.client;

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
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.UpdatePratilipiRequest;
import com.pratilipi.service.shared.UpdatePratilipiResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentEditOptions implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	// Cover image edit options widgets
	private final FileUpload coverImageUpload = new FileUpload();
	
	
	// Summary edit options widgets
	private final Anchor editSummaryAnchor = new Anchor( "Edit Summary" );
	private final Anchor saveSummaryAnchor = new Anchor( "Save Summary" );
	private final Label savingLabel = new Label( "Saving ..." );

	
	private String url = Window.Location.getPath();
	private PratilipiType pratilipiType;

	
	public void onModuleLoad() {
		for( PratilipiType pratilipiType : PratilipiType.values() )
			if( url.startsWith( pratilipiType.getPageUrl() ) )
				this.pratilipiType = pratilipiType;

		if( pratilipiType == null )
			return;
	
		
		String pratilipiIdStr = url.substring( pratilipiType.getPageUrl().length() );
		
		
		// Cover image edit options
		RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-CoverImage-EditOptions" );
		if( rootPanel != null ) {
			String uploadUrl = pratilipiType.getCoverImageUrl() + pratilipiIdStr;
			coverImageUpload.getElement().setAttribute( "data-url", uploadUrl );
			loadFileUploader( coverImageUpload.getElement() );

			rootPanel.add( coverImageUpload );
		}
		
		
		// Summary edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-Summary-EditOptions" );
		if( rootPanel != null ) {
			editSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.setVisible( false );
			savingLabel.setVisible( false );
	
			rootPanel.add( editSummaryAnchor );
			rootPanel.add( saveSummaryAnchor );
			rootPanel.add( savingLabel );
		}
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == editSummaryAnchor ) {
			editSummaryAnchor.setVisible( false );
			saveSummaryAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Pratilipi-Summary" ).getElement() );
			
		} else if( event.getSource() == saveSummaryAnchor ) {
			saveSummaryAnchor.setVisible( false );
			savingLabel.setVisible( true );
			
			String pratilipiIdStr = url.substring( pratilipiType.getPageUrl().length() );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );

			PratilipiData pratilipiData = pratilipiType.newPratilipiData();
			pratilipiData.setId( pratilipiId );
			pratilipiData.setSummary( getHtmlFromEditor( "PageContent-Pratilipi-Summary" ) );
			
			pratilipiService.updatePratilipi(
					new UpdatePratilipiRequest( pratilipiData ),
					new AsyncCallback<UpdatePratilipiResponse>() {
				
				@Override
				public void onSuccess( UpdatePratilipiResponse result ) {
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
