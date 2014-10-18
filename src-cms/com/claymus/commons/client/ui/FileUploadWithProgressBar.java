package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadWithProgressBar extends Composite {

	private final Panel panel = new FlowPanel();
	
	private final Panel progress = new SimplePanel();
	private final Panel progressBar = new SimplePanel();
	private final FileUpload fileUpload = new FileUpload();
	private final Anchor uploadAnchor = new Anchor( "Select File" );

	
	public FileUploadWithProgressBar() {

		progress.setStyleName( "progress" );
		progress.setVisible( false );

		progressBar.setStyleName( "progress-bar progress-bar-success progress-bar-striped active" );
		progressBar.getElement().setAttribute( "role", "progressbar" );
		progressBar.getElement().setAttribute( "aria-valuenow", "0" );
		progressBar.getElement().setAttribute( "aria-valuemin", "0" );
		progressBar.getElement().setAttribute( "aria-valuemax", "100" );
		progressBar.getElement().setAttribute( "style", "width:0%" );
		
		fileUpload.getElement().setAttribute( "data-sequential-uploads", "true" );
		fileUpload.addChangeHandler( new ChangeHandler() {
			
			@Override
			public void onChange( ChangeEvent event ) {
				progress.setVisible( true );
				uploadAnchor.setVisible( false );
			}
			
		});
		fileUpload.setVisible( false );
		
		uploadAnchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				fileUpload.getElement().<InputElement>cast().click();
			}
			
		});
		
		
		initializeFileUploader(
				fileUpload.getElement(),
				uploadAnchor.getElement(),
				progress.getElement() );

		
		progress.add( progressBar );
		panel.add( progress );
		panel.add( fileUpload );
		panel.add( uploadAnchor );
		
		initWidget( panel );
	}
	
	public void setTitle( String title ) {
		uploadAnchor.setText( title );
	}
	
	public void setUploadUrl( String uploadUrl ) {
		setFileUploadUrl( fileUpload.getElement(), uploadUrl );
	}
	
	public Widget getProgressBar() {
		return progress;
	}
	
	private native void initializeFileUploader(
			Element fileUpload, Element anchor, Element progress ) /*-{
		
		$wnd.jQuery( fileUpload ).fileupload({
			dataType: 'html',
			progressall: function( e, data ) {
				var completed = parseInt( data.loaded / data.total * 100, 10 );
				$wnd.jQuery( progress ).children().first().css( 'width', completed + '%' );
				$wnd.jQuery( progress ).children().first().attr( "aria-valuenow", completed );
		    },
		    done: function( e, data ) {
	            $wnd.jQuery( progress ).hide();
	            $wnd.jQuery( anchor ).show();
	        }
		});
	}-*/;

	private native void setFileUploadUrl( Element fileUpload, String uploadUrl ) /*-{
		$wnd.jQuery( fileUpload ).fileupload( 'option', 'url', uploadUrl );
	}-*/;
	
}
