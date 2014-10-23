package com.pratilipi.pagecontent.reader.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetReaderContentRequest;
import com.pratilipi.service.shared.GetReaderContentResponse;
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.data.PratilipiContentData;
import com.pratilipi.service.shared.data.PratilipiData;

public class ReaderContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	// Content edit options widgets
	private final Anchor editContentAnchor = new Anchor( "Edit Content" );
	private final Anchor saveContentAnchor = new Anchor( "Save Content" );
	private final Label savingContentLabel = new Label( "Saving Content ..." );
	
	//Full Screen button
	private Button fullScreenButton = new Button();
	private Button fullScreenExitButton = new Button();
	
	//Reader Div
	private RootPanel readerRootPanel;
	private Label readerContent = new Label();
	
	
	//Previous and next buttons
	private Button previousPageButton = new Button();
	private Button nextPageButton = new Button();
	
	private String url = Window.Location.getPath();
	private String pageNumber = Window.Location.getParameter( "page" );
	
	private PratilipiData pratilipiData = new PratilipiData();
	private String pageContent;
	
	public void onModuleLoad() {
		
		readerRootPanel = RootPanel.get( "PageContent-Pratilipi-Content" );
		
		//FullScreen Logic
		RootPanel fullScreenRootPanel = RootPanel.get( "PageContent-Pratilipi-Content-Fullscreen" );
		fullScreenRootPanel.add( fullScreenButton );
		fullScreenRootPanel.add( fullScreenExitButton );
		
		fullScreenButton.setHTML( "<span class=\"glyphicon glyphicon-resize-full\"></span>" );
		fullScreenButton.setTitle( "Full Screen" );
		fullScreenButton.addStyleName( "pull-right" );
		fullScreenButton.getElement().getStyle().setMarginBottom( 5, Unit.PX );
		fullScreenButton.addClickHandler( this );
		
		fullScreenExitButton.setHTML( "<span class=\"glyphicon glyphicon-resize-small\"></span>" );
		fullScreenExitButton.setTitle( "Exit Full Screen" );
		fullScreenExitButton.addClickHandler( this );
		fullScreenExitButton.getElement().setAttribute( "style", 
							" position : fixed; right: 10%; top: 10%; "
							+ " display: none; z-index: 3; " );
		
		// Decoding PratilipiData
		RootPanel rootPanel = RootPanel.get( "PageContent-Reader-EncodedData" );
		String pratilipiDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( pratilipiDataEncodedStr );
			pratilipiData = (PratilipiData) streamReader.readObject();
		} catch( SerializationException e ) {
			Window.alert( e.getMessage() );
		}

		// Content edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-Content-EditOptions" );
		if( rootPanel != null ) {
			editContentAnchor.addClickHandler( this );
			saveContentAnchor.addClickHandler( this );
			saveContentAnchor.setVisible( false );
			savingContentLabel.setVisible( false );
	
			rootPanel.add( editContentAnchor );
			rootPanel.add( saveContentAnchor );
			rootPanel.add( savingContentLabel );
		}
		
		RootPanel buttonRootPanel = RootPanel.get( "PageContent-Pratilipi-Content-Buttons" );
		if( buttonRootPanel != null ) {
			previousPageButton.setHTML( "<span class=\"glyphicon glyphicon-chevron-left\"></span>&nbsp;&nbsp;Previous" );
			previousPageButton.addStyleName( "btn btn-default" );
			previousPageButton.addClickHandler( this );
			previousPageButton.getElement().setAttribute( "style", 
					"position: fixed; left: 10%; top: 25%;");
			//Hide previous page button for first page.
			if( pageNumber == null || pageNumber.equals( "1" ) )
				previousPageButton.setVisible( false );
			
			nextPageButton.setHTML( "Next&nbsp;&nbsp;<span class=\"glyphicon glyphicon-chevron-right\"  ></span>" );
			nextPageButton.addStyleName( "btn btn-default pull-right" );
			nextPageButton.addClickHandler( this );
			nextPageButton.getElement().setAttribute( "style", 
					"position: fixed; right: 10%; top: 25%;");
			
			buttonRootPanel.add( previousPageButton );
			buttonRootPanel.add( nextPageButton );
		}
		
		//Attaching key down handler to body.
		RootPanel.get().addDomHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if( event.isRightArrow()) {
					onClickNextPageButton();
				}
				
				if( event.isLeftArrow() ) {
					onClickPreviousPageButton();
				}
				
				if( event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE ){
					RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-Content" );
					rootPanel.removeStyleName( "fullscreen-reader" );
					fullScreenExitButton.setVisible( false );
					fullScreenButton.setVisible( true );
				}
			}}, KeyDownEvent.getType() );

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
		} else if( event.getSource() == previousPageButton ) {
			
			onClickPreviousPageButton();
			
		} else if( event.getSource() == nextPageButton ) {
			//This is used to throw message when right arrow key is pushed on last page.
			if( !nextPageButton.isVisible() )
				Window.alert( "You have reached end of this " + pratilipiData.getType().getName() );
			else
				onClickNextPageButton();
			
		} else if( event.getSource() == fullScreenButton ) {
			RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-Content" );
			rootPanel.addStyleName( "fullscreen-reader" );
			fullScreenExitButton.setVisible( true );
			fullScreenButton.setVisible( false );
		} else if( event.getSource() == fullScreenExitButton ) {
			RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-Content" );
			rootPanel.removeStyleName( "fullscreen-reader" );
			fullScreenExitButton.setVisible( false );
			fullScreenButton.setVisible( true );
		}
		
	}
	
	private void loadReaderContent( int pageNo ) {
		if( pratilipiData.getPageCount() != null && pratilipiData.getPageCount() > 0 ) {
			//TODO : REMOVE STATIC URL ASAP
			String urlPrefix = "/resource." + pratilipiData.getType().getName().toLowerCase() + "-content/image/" + pratilipiData.getId();
			pageContent = "<img style=\"width:100%;\" src=\"" + urlPrefix + "/" + pageNo + "\">";
			readerRootPanel.getElement().setInnerHTML( pageContent );

		} else {
			pratilipiService.getReaderContent( 
					new GetReaderContentRequest( pratilipiData.getId(), pageNo ), 
					new AsyncCallback<GetReaderContentResponse>() {
		
						@Override
						public void onFailure(Throwable caught) {
							Window.alert( caught.getMessage() );
							
							nextPageButton.setEnabled( true );
							previousPageButton.setEnabled( true );
						}
		
						@Override
						public void onSuccess(
								GetReaderContentResponse result) {
							readerRootPanel.getElement().setInnerHTML( result.getPageContent() );
							if( result.isLastPage() )
								nextPageButton.setVisible( false );
							
							nextPageButton.setEnabled( true );
							previousPageButton.setEnabled( true );
						}
					});
		}
			
		if( pageNo == 1 )
			previousPageButton.setVisible( false );

		if( pratilipiData.getPageCount() != null && pageNo == pratilipiData.getPageCount() )
			nextPageButton.setVisible( false );
	}

	private void onClickPreviousPageButton() {

		previousPageButton.setEnabled( false );
		nextPageButton.setEnabled( false );
		String pageNoStr = Window.Location.getParameter( "page" );
		Integer pageNo = pageNoStr == null ? 1 : Integer.parseInt( pageNoStr );
		
		if( pageNo == 1 ) {
			Window.alert( "This is starting of this " + pratilipiData.getType().getName() );
		}
		else {
			String currentUrl = Window.Location.getHref();
			String newUrl;
			Integer previousPage = pageNo - 1;
			loadReaderContent( previousPage );
			newUrl = currentUrl.substring( 0, currentUrl.indexOf( "=" )+1 ) + previousPage ;
			pushState( newUrl );
		}

		if( !nextPageButton.isVisible() )
			nextPageButton.setVisible( true );
		
		readerRootPanel.add( readerContent );
	}
	
	private void onClickNextPageButton() {
		
		nextPageButton.setEnabled( false );
		previousPageButton.setEnabled( false );
		String pageNoStr = Window.Location.getParameter( "page" );
		Integer pageNo = pageNoStr == null ? 1 : Integer.parseInt( pageNoStr );
		Integer nextPage = pageNo + 1;
		
		loadReaderContent( nextPage );
		
		//TODO : CHANGE THIS ASAP
		//URL of next page.
		String currentUrl = Window.Location.getHref();
		String newUrl;
		if( pageNoStr == null && currentUrl.indexOf( "?") == -1 ) {
			newUrl = currentUrl + "?page=" + nextPage;
		} else {
			newUrl = currentUrl.substring( 0, currentUrl.indexOf( "?" ) ) + "?page=" + nextPage;
		}
		
		pushState( newUrl );
		
		if( !previousPageButton.isVisible() )
			previousPageButton.setVisible( true );
		
		readerRootPanel.add( readerContent );
	}
	
	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;
	
	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

	private native void pushState( String newUrl ) /*-{
		$wnd.history.pushState( {}, '', newUrl );
	}-*/;


}
