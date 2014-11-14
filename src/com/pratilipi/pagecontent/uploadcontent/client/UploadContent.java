package com.pratilipi.pagecontent.uploadcontent.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetReaderContentRequest;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.PratilipiData;


public class UploadContent implements EntryPoint {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private FileUpload imageContentUpload = new FileUpload();
	private FileUpload wordContentUpload = new FileUpload();
	
	private PratilipiData pratilipiData = new PratilipiData();
	
	private String uploadUrl;
	
	private Button doneButton = new Button( "Done" );
	private Button convertWordButton = new Button( "Convert Word to HTML" );
	private InlineLabel loadingMsg = new InlineLabel( "Updating Info..." );
	
	public void onModuleLoad() {
		
		//Encoding pratilipiData.
		RootPanel encodedDataPanel = RootPanel.get( "PageContent-UploadContent-EncodedData" );
		String pratilipiDataEncodedStr = encodedDataPanel.getElement().getInnerText();
		try{
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( pratilipiDataEncodedStr );
			pratilipiData = ( PratilipiData ) streamReader.readObject();
		} catch( SerializationException e ) {
			Window.alert( e.getMessage() );
		}
		
		//Enabling multiple fileupload property
		imageContentUpload.getElement().setPropertyString( "multiple", "multiple" );
		
		//Setting upload url and pratilipi's Content type if it is null.
		if( pratilipiData.getContentType() != null ){
			if( pratilipiData.getContentType().equals( PratilipiContentType.IMAGE ))
				uploadUrl = pratilipiData.getImageContentUploadUrl();
			else if( pratilipiData.getContentType().equals( PratilipiContentType.PRATILIPI ))
				uploadUrl = pratilipiData.getWordContentUploadUrl();
		}
		else{
			if( Window.Location.getParameter( "type" ).equals( "image" ) ){
				uploadUrl = pratilipiData.getImageContentUploadUrl();
				pratilipiData.setContentType( PratilipiContentType.IMAGE );
			}
			else if( Window.Location.getParameter( "type" ).equals( "word" ) ){
				uploadUrl = pratilipiData.getWordContentUploadUrl();
				pratilipiData.setContentType( PratilipiContentType.PRATILIPI );
			}
		}
		
		//Image Content upload panel
		RootPanel imageUpload = RootPanel.get( "PageContent-UploadContent-Image" );
		if( imageUpload != null ){
			imageUpload.add( imageContentUpload );
			
			//Initializing jquery fileupload script
			initializeImageContentUploader( imageContentUpload.getElement(), 
					uploadUrl, 
					pratilipiData.getPageCount() == null ? 0 : ( float ) pratilipiData.getPageCount(), 
					loadingMsg.getElement() );
			setFileUploadOption( imageContentUpload.getElement(), "limitConcurrentUploads", "5" );
		}
			
		
		//Word content upload panel
		RootPanel wordUpload = RootPanel.get( "PageContent-UploadContent-Word" );
		if( wordUpload != null )
			wordUpload.add( wordContentUpload );
		
		//Adding button which redirects on the reader's page.
		loadingMsg.getElement().getStyle().setColor( "red" );
		loadingMsg.setVisible( false );
		
		doneButton.addStyleName( "btn btn-info" );
		doneButton.setVisible( false );
		doneButton.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign( pratilipiData.getReaderPageUrl() );
			}} );
		
		convertWordButton.addStyleName( "btn btn-info" );
		convertWordButton.setVisible( false );
		convertWordButton.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				wordToHtml();
			}} );
		
		RootPanel buttonPanel = RootPanel.get( "PageContent-UploadContent-button" );
		if( buttonPanel != null ){
			buttonPanel.add( loadingMsg );
			buttonPanel.add( doneButton );
		}
		
	}
	
	private native void initializeImageContentUploader( Element fileUpload, 
								String url,
								float pageCount,
								Element loadingMsg  ) /*-{

		var that = this;
		var statusDiv = $wnd.jQuery('#status-div');
		var newPagesCount = 0;
		
		$wnd.jQuery( fileUpload ).fileupload({
			dataType: 'html',
			replaceFileInput: false,
			add: function( e, data ){
				$wnd.$( this ).prop( 'disabled', true );
				var filename =  data.files[0].name;
				data.url = url + filename.split('.')[0];
				$wnd.$( statusDiv ).append('<div class="col-sm-6" ><i>' + filename + '</i></div>' +
							'<div class="progress col-sm-2" style="padding-left: 0px; padding-right: 0px;">' + 
							'<div id="progressbar" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
							'</div>' + 
							'<div id="statusDiv" class="status col-sm-3" >Pending</div>' );
				$wnd.$('#progressbar').attr('id', filename.split('.')[0] );
				$wnd.$('#statusDiv').attr('id', filename.split('.')[0] + "Div" );
				
				data.submit();
			},
			progress: function( e, data ) {
				var filename = data.files[0].name.split('.')[0];
				var progress = parseInt( data.loaded / data.total * 100, 10 );
				$wnd.$( "#" + filename ).css( 'width', progress + '%' );
				$wnd.$( "#" + filename ).attr( "aria-valuenow", progress );
				$wnd.$( "#" + filename + "Div" ).html( "Uploading" );
		    },
		    done: function( e, data ) {
		    	var filename = data.files[0].name.split('.')[0];
		    	if( pageCount < parseFloat( filename ) )
		    		newPagesCount = newPagesCount + 1;
				$wnd.$( "#" + filename + "Div" ).css( 'color', 'Green' );
				$wnd.$( "#" + filename + "Div" ).html( 'Done' );
		    },
		    fail: function( e, data ){
		    	var filename = data.files[0].name.split('.')[0];
		    	if( pageCount < parseFloat( filename ) )
		    		newPagesCount = newPagesCount + 1;
		    	$wnd.$( "#" + filename ).css( 'width', '0%'  );
				$wnd.$( "#" + filename + "Div" ).css( 'color', 'red' );
				$wnd.$( "#" + filename + "Div" ).html( 'Failed' );
		    },
		    stop: function( e ){
		    	$wnd.$( loadingMsg ).css( 'display', 'block' );
		    	$wnd.$( this ).prop( 'disabled', false );
		    	that.@com.pratilipi.pagecontent.uploadcontent.client.UploadContent::updatePratilipi(Ljava/lang/String;)( newPagesCount );
		    }
		});
		
	}-*/;
	
	private native void initializeWordContentUploader( Element fileUpload, String url )/*-{
		$wnd.jQuery( fileUpload ).fileupload({
			dataType: 'html',
			replaceFileInput: false,
			add: function( e, data ){
				$wnd.$( this ).prop( 'disabled', true );
				var filename =  data.files[0].name;
				data.url = url;
				$wnd.$( statusDiv ).append('<div class="col-sm-6" ><i>' + filename + '</i></div>' +
							'<div class="progress col-sm-2" style="padding-left: 0px; padding-right: 0px;">' + 
							'<div id="progressbar" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
							'</div>' + 
							'<div id="statusDiv" class="status col-sm-3" >Pending</div>' );
				data.submit();
			},
			progress: function( e, data ) {
				var filename = data.files[0].name.split('.')[0];
				var progress = parseInt( data.loaded / data.total * 100, 10 );
				$wnd.$( '.progressbar' ).css( 'width', progress + '%' );
				$wnd.$( '.progressbar' ).attr( "aria-valuenow", progress );
				$wnd.$( '.status' ).html( "Uploading" );
		    },
		    done: function( e, data ) {
		    	$wnd.$( '.progressbar' ).css( 'width', '0%'  );
				$wnd.$( '.status' ).css( 'color', 'red' );
				$wnd.$( '.status' ).html( 'Failed' );
		    },
		    fail: function( e, data ){
		    	$wnd.$( '.progressbar' ).css( 'width', '0%'  );
				$wnd.$( '.status' ).css( 'color', 'red' );
				$wnd.$( '.status' ).html( 'Failed' );
		    },
		    stop: function( e ){
		    	$wnd.$( this ).prop( 'disabled', false );
		    	that.@com.pratilipi.pagecontent.uploadcontent.client.UploadContent::wordToHtml();
		    }
		});
	}-*/;
	
	private native void setFileUploadOption( Element fileUpload, String option, String value ) /*-{
		$wnd.jQuery( fileUpload ).fileupload( 'option', option, value );
	}-*/;
	
	//Update pageCount and contentType in pratilipiData.
	private void updatePratilipi( String pageCount ){
		Long pageUploaded = Long.parseLong( String.valueOf( pageCount ));
		pratilipiData.setPageCount(( pratilipiData.getPageCount() == null ? 0 : pratilipiData.getPageCount() )
										+ pageUploaded );
	
		pratilipiService.savePratilipi( new SavePratilipiRequest( pratilipiData ), new AsyncCallback<SavePratilipiResponse> (){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert( caught.getMessage() );
				
			}

			@Override
			public void onSuccess(SavePratilipiResponse result) {
				loadingMsg.setVisible( false );
				doneButton.setVisible( true );
				pratilipiData = result.getPratilipiData();
			}} );
	}
	
	//Convert word to html
	private void wordToHtml(){
		loadingMsg.setText( "Converting word to HTML. This might take some time. Please Wait..." );
		loadingMsg.setVisible( true );
		
		pratilipiService.ConvertWordToHtml( new GetReaderContentRequest( pratilipiData.getId(), 0 ), 
						new AsyncCallback<Void> (){

							@Override
							public void onFailure(Throwable caught) {
								Window.alert( caught.getMessage() );
								loadingMsg.setText( "Word to HTML conversion <b>FAILED</b>. Please Try again" );
								convertWordButton.setVisible( true );
								
							}

							@Override
							public void onSuccess(Void result) {
								loadingMsg.setVisible( false );
								doneButton.setVisible( true );
							}} );
	}
	
}
