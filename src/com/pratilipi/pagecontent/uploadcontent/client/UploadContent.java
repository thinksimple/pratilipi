package com.pratilipi.pagecontent.uploadcontent.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.RootPanel;


public class UploadContent implements EntryPoint {
	
	private FileUpload fileUpload = new FileUpload();
	
	public void onModuleLoad() {
		
		fileUpload.getElement().setPropertyString( "multiple", "multiple" );
		
		RootPanel imageUpload = RootPanel.get( "PageContent-UploadContent-Image" );
		if( imageUpload != null )
			imageUpload.add( fileUpload );
		
		//TODO : set upload url
		
		initializeMultipleFileUploader( fileUpload.getElement() );
		
	}
	
	private native void initializeMultipleFileUploader( Element fileUpload ) /*-{

		var statusDiv = $wnd.jQuery('#status-div');
		
		$wnd.jQuery( fileUpload ).fileupload({
			dataType: 'html',
			replaceFileInput: false,
			add: function( e, data ){
				var count = $wnd.$('input:file')[0].files.length;
				var filename =  data.files[0].name;
				$wnd.jQuery( statusDiv ).append('<div class="col-sm-6" ><i>' + filename + '</i></div>' +
							'<div class="progress col-sm-2" style="padding-left: 0px; padding-right: 0px;">' + 
							'<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
							'</div>' + 
							'<div class="status col-sm-3" >Pending</div>' );
							data.submit();
			},
			progressall: function( e, data ) {
				var progress = parseInt( data.loaded / data.total * 100, 10 );
				$wnd.$('.progress-bar').css( 'width', progress + '%' );
				$wnd.$('.progress-bar').attr( "aria-valuenow", progress );
				$wnd.$('.status').html( "Uploading" );
		    },
		    done: function( e, data ) {
				$wnd.$('.progress-bar').css( 'width', '0%' );
				$wnd.$('.status').css( 'font-color', 'Green' );
				$wnd.$('.status').html( 'Done' );
		    }
		});
		
	}-*/;
	
	private native void setFileUploadOption( Element fileUpload, String option, String value ) /*-{
		$wnd.jQuery( fileUpload ).fileupload( 'option', option, value );
	}-*/;
	
}
