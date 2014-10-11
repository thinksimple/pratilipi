package com.pratilipi.pagecontent.pratilipi.client;

import com.claymus.commons.client.ui.Dropdown;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewModalImpl;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.PratilipiUtil;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentEditOptions implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	// Genre list edit options widgets
	private GenreList genreList;
	private Anchor genreAnchor;
	private AddRemoveGenre addRemoveGenre;
	
	private PratilipiData pratilipiData;

	
	
	// Cover image edit options widgets
	private final FileUpload coverImageUpload = new FileUpload();
	
	
	// Word content edit options widgets
	private final FileUpload wordContentUpload = new FileUpload();

	
	// Html content edit options widgets
	private final FileUpload htmlContentUpload = new FileUpload();

	
	// Summary edit options widgets
	private final Anchor editSummaryAnchor = new Anchor( "Edit Summary" );
	private final Anchor saveSummaryAnchor = new Anchor( "Save Summary" );
	private final Label savingSummaryLabel = new Label( "Saving Summary ..." );
	private Button editPratilipiInfo;
	
	private PratilipiDataInputView pratilipiDataInputView;
	
	private String url = Window.Location.getPath();
	private PratilipiType pratilipiType;

	
	public void onModuleLoad() {

		// Decoding PratilipiData
		RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-EncodedData" );
		String pratilipiDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			System.out.println( "Encoder : " + pratilipiDataEncodedStr );
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( pratilipiDataEncodedStr );
			pratilipiData = (PratilipiData) streamReader.readObject();
			System.out.println( "After RPC" + pratilipiData.getTitleEn() );
		} catch( SerializationException e ) {
			Window.alert( e.getMessage() );
		}
		
		
		// Genre list and edit options
		genreList = new GenreList( pratilipiData.getGenreNameList() );
		genreAnchor = new Anchor( "Add/Remove Genre" );
		genreAnchor.addClickHandler( this );
		addRemoveGenre = new AddRemoveGenre( pratilipiData );
		addRemoveGenre.addValueChangeHandler( new ValueChangeHandler<PratilipiData>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<PratilipiData> event) {
				genreList.set( event.getValue().getGenreNameList() );
			}
			
		});
				
		Dropdown dropdown = new Dropdown( pratilipiData.getTitle() );
		dropdown.add( genreAnchor );

		
		rootPanel = RootPanel.get( "PageContent-Pratilipi-Title" );
		rootPanel.getElement().setInnerHTML( "" );
		rootPanel.add( dropdown );

		rootPanel = RootPanel.get( "PageContent-Pratilipi-GenreList" );
		rootPanel.getElement().setInnerHTML( "" );
		rootPanel.add( genreList );
		
		RootPanel.get().add( addRemoveGenre );
		
		
		// Cover image edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-CoverImage-EditOptions" );
		if( rootPanel != null ) {
			String uploadUrl = rootPanel.getElement().getAttribute( "upload-url" );
			coverImageUpload.getElement().setAttribute( "data-url", uploadUrl );
			loadFileUploader( coverImageUpload.getElement() );

			rootPanel.add( coverImageUpload );
		}
		
		
		// Html content edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-HtmlContent-EditOptions" );
		if( rootPanel != null ) {
			String uploadUrl = rootPanel.getElement().getAttribute( "upload-url" );
			htmlContentUpload.getElement().setAttribute( "data-url", uploadUrl );
			loadFileUploader( htmlContentUpload.getElement() );

			rootPanel.add( htmlContentUpload );
		}
		
		
		// Word content edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-WordContent-EditOptions" );
		if( rootPanel != null ) {
			String uploadUrl = rootPanel.getElement().getAttribute( "upload-url" );
			wordContentUpload.getElement().setAttribute( "data-url", uploadUrl );
			loadFileUploader( wordContentUpload.getElement() );

			rootPanel.add( wordContentUpload );
		}
		
		
		// Summary edit options
		rootPanel = RootPanel.get( "PageContent-Pratilipi-Summary-EditOptions" );
		if( rootPanel != null ) {
			editSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.addClickHandler( this );
			saveSummaryAnchor.setVisible( false );
			savingSummaryLabel.setVisible( false );
	
			rootPanel.add( editSummaryAnchor );
			rootPanel.add( saveSummaryAnchor );
			rootPanel.add( savingSummaryLabel );
		}
		
		//Edit Book info.
		RootPanel editInfoRootPanel = RootPanel.get( "PageContent-Pratilipi-Info-EditOption" );
		if( editInfoRootPanel != null ) {
			editPratilipiInfo = new Button( "Edit " + pratilipiData.getType().getName() + " Info" );
			editPratilipiInfo.addStyleName( "btn btn-primary hidden-xs" );
			editPratilipiInfo.addClickHandler( this );
			editInfoRootPanel.add( editPratilipiInfo );
			
			pratilipiDataInputView = new PratilipiDataInputViewModalImpl( pratilipiData.getType() );
			
			//Add language list to book info edit modal
			pratilipiService.getLanguageList(
		    		new GetLanguageListRequest(),
		    		new AsyncCallback<GetLanguageListResponse>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess( GetLanguageListResponse response ) {
					for( LanguageData languageData : response.getLanguageList() )
						pratilipiDataInputView.addLanguageListItem(
								PratilipiUtil.createLanguageName( languageData ),
								languageData.getId().toString() );
				}
				
		    });
			
			pratilipiDataInputView.addAddButtonClickHandler( this );
		}
		

	}

	
	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == genreAnchor ) {
			addRemoveGenre.setVisible( true );
			
		} else if( event.getSource() == editSummaryAnchor ) {
			editSummaryAnchor.setVisible( false );
			saveSummaryAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Pratilipi-Summary" ).getElement() );
			
		} else if( event.getSource() == saveSummaryAnchor ) {
			
			if( getHtmlFromEditor( "PageContent-Pratilipi-Summary" ).trim().isEmpty() )
				return;

			
			saveSummaryAnchor.setVisible( false );
			savingSummaryLabel.setVisible( true );
			
			String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );

			PratilipiData pratilipiData = new PratilipiData();
			pratilipiData.setId( pratilipiId );
			pratilipiData.setSummary( getHtmlFromEditor( "PageContent-Pratilipi-Summary" ) );
			
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>() {
				
				@Override
				public void onSuccess( SavePratilipiResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					savingSummaryLabel.setVisible( false );
					saveSummaryAnchor.setVisible( true );
				}
				
			});
		
		} else if( event.getSource() == editPratilipiInfo ) {
			System.out.println( "In onClick function : " + pratilipiData.getTitleEn() );
			pratilipiDataInputView.setPratilipiData( pratilipiData );
			pratilipiDataInputView.setVisible( true );
			RootPanel.get().add( pratilipiDataInputView );
		} 
		else {
			
			//
			if( ! pratilipiDataInputView.validateInputs() )
				return;
			
			PratilipiData editedBook = pratilipiDataInputView.getPratilipiData();
			pratilipiDataInputView.setEnabled( false );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( editedBook ),
					new AsyncCallback<SavePratilipiResponse>(){
	
				@Override
				public void onFailure(Throwable caught) {
					pratilipiDataInputView.setEnabled( true );
					Window.alert( caught.getMessage() );
				}
	
				@Override
				public void onSuccess( SavePratilipiResponse response ) {
					Window.Location.reload();
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
