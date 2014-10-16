package com.pratilipi.pagecontent.author.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;

public class AuthorContent implements EntryPoint {
	
	private PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private final FileUpload authorImageUpload = new FileUpload();
	
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
			authorImageUpload.getElement().setAttribute( "id", "Upload-image");
			authorImageUpload.getElement().getStyle().setDisplay( Display.NONE );
			loadFileUploader( authorImageUpload.getElement() );

			rootPanel.add( authorImageUpload );
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
