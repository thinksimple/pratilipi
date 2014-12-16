package com.pratilipi.pagecontent.pratilipi.client;

import java.util.Date;

import com.claymus.commons.client.ui.Dropdown;
import com.claymus.commons.client.ui.FileUploadWithProgressBar;
import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewModalImpl;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
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
	
	
	private final RootPanel titePanel =
			RootPanel.get( "PageContent-Pratilipi-Title" );
	private final RootPanel authorNamePanel =
			RootPanel.get( "PageContent-Pratilipi-AuthorName" );
	private final RootPanel summaryPanel =
			RootPanel.get( "PageContent-Pratilipi-Summary" );
	private final RootPanel summaryEditOptionsPanel =
			RootPanel.get( "PageContent-Pratilipi-Summary-EditOptions" );
	private final RootPanel genreListPanel =
			RootPanel.get( "PageContent-Pratilipi-GenreList" );
	private final RootPanel coverImagePanel =
			RootPanel.get( "PageContent-Pratilipi-CoverImage" );
	private final RootPanel coverImageEditOptionsPanel =
			RootPanel.get( "PageContent-Pratilipi-CoverImage-EditOptions" );
	private final RootPanel publishPanel =
			RootPanel.get( "PageContent-Pratilipi-Publish" );


	private final Dropdown dropdown = new Dropdown();


	// Pratilipi data edit options widgets
	private final Anchor editPratilipiDataAnchor = new Anchor( "Edit Info" );
	private final Button savePratilipiDataButton = new Button( "Save" );
	private final PratilipiDataInputView pratilipiDataInputView = new PratilipiDataInputViewModalImpl();
	
	private final Anchor editSummaryAnchor = new Anchor( "Edit Summary" );
	private final Button saveSummaryButton = new Button( "Save" );
	private final RichTextInputFormField summaryInput = new RichTextInputFormField();

	
	// Genre list edit options widgets
	private final GenreList genreList = new GenreList();
	private final Anchor genreAnchor = new Anchor( "Add/Remove Genre" );
	private final AddRemoveGenre addRemoveGenre = new AddRemoveGenre();
	
	
	//Upload content
	private final Anchor uploadImageContentAnchor = new Anchor( "Upload Image Content" );
	private final Anchor uploadWordContentAnchor = new Anchor( "Upload Word Content" );
	private final Anchor uploadPDFContentAnchor = new Anchor( "Upload PDF Content" );
	
	
	// Publish-Unpublish options widgets
	private final Anchor publishAnchor = new Anchor( "here" );
	private final Anchor unpublishAnchor = new Anchor( "Un-publish" );
	
	
	// Cover image edit options widgets
	private final FileUploadWithProgressBar coverImageUpload = new FileUploadWithProgressBar();
	
	
	private PratilipiData pratilipiData;

	
	public void onModuleLoad() {
		
		// Pratilipi data edit options widgets
		editPratilipiDataAnchor.addClickHandler( this );
		savePratilipiDataButton.addClickHandler( this );
		
		editSummaryAnchor.addClickHandler( this );
		saveSummaryButton.addClickHandler( this );
		saveSummaryButton.setVisible( false );
		
		dropdown.add( editPratilipiDataAnchor );
		pratilipiDataInputView.add( savePratilipiDataButton );
		RootPanel.get().add( pratilipiDataInputView );
		
		dropdown.add( editSummaryAnchor );
		summaryEditOptionsPanel.add( saveSummaryButton );
		
		savePratilipiDataButton.setStyleName( "btn btn-success" );
		saveSummaryButton.setStyleName( "btn btn-success" );
		
		
		// Genre list and edit options
		genreAnchor.addClickHandler( this );
		addRemoveGenre.addValueChangeHandler( new ValueChangeHandler<PratilipiData>() {
			
			@Override
			public void onValueChange( ValueChangeEvent<PratilipiData> event ) {
				genreList.set( event.getValue().getGenreNameList() );
			}
			
		});
		
		genreListPanel.getElement().setInnerHTML( "" );
		genreListPanel.add( genreList );

		dropdown.add( genreAnchor );
		RootPanel.get().add( addRemoveGenre );
		

		//Upload Content links
		dropdown.add( uploadImageContentAnchor );
		dropdown.add( uploadWordContentAnchor );
		dropdown.add( uploadPDFContentAnchor );

		
		// Un-publish option widgets
		publishAnchor.addClickHandler( this );
		unpublishAnchor.addClickHandler( this );
		dropdown.add( unpublishAnchor );

		
		// Cover image edit options
		coverImageUpload.setTitle( "<h1 class='bg-translucent' style='margin-top:-41px; text-align:right;'><span class='glyphicon glyphicon-camera' style='margin-right:10px;'></span></h1>" );
		coverImageUpload.setAcceptedFileTypes( "image/jpg, image/jpeg, image/png, image/bmp" );
		coverImageUpload.addValueChangeHandler( new ValueChangeHandler<String>( ) {
			
			@Override
			public void onValueChange( ValueChangeEvent<String> event ) {
				Element imgElement = coverImagePanel.getElement();
				String src = imgElement.getAttribute( "src" );
				if( src.indexOf( '?' ) != -1 )
					src = src.substring( 0, src.indexOf( '?' ) );
				src = src + "?" + new Date().getTime();
				imgElement.setAttribute( "src", src );
			}
			
		});
		coverImageUpload.getProgressBar().getElement().setAttribute(
				"style",
				coverImageUpload.getProgressBar().getElement().getAttribute( "style" )
				+ "margin-top:10px; margin-bottom:10px;" );
		
		coverImageEditOptionsPanel.add( coverImageUpload );
		coverImageEditOptionsPanel.add( coverImageUpload.getProgressBar() );

		
		// Decoding PratilipiData
		RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-EncodedData" );
		String pratilipiDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( pratilipiDataEncodedStr );
			pratilipiData = (PratilipiData) streamReader.readObject();
			setPratilipiData( pratilipiData );
		} catch( SerializationException e ) {
			Window.Location.reload();
		}


		titePanel.getElement().setInnerHTML( "" );
		titePanel.add( dropdown );

		
		pratilipiService.getLanguageList(
				new GetLanguageListRequest(),
				new AsyncCallback<GetLanguageListResponse>() {

			@Override
			public void onSuccess( GetLanguageListResponse response ) {
				for( LanguageData languageData : response.getLanguageList() )
					pratilipiDataInputView.addLanguageListItem( languageData );
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.Location.reload();
			}

		});

	}

	
	private void setPratilipiData( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
		dropdown.setTitle( pratilipiData.getTitle() );
		if( authorNamePanel != null )
			authorNamePanel.getElement().setInnerText( pratilipiData.getAuthorData().getFullName() );
		pratilipiDataInputView.setPratilipiData( pratilipiData );
		summaryPanel.getElement().setInnerHTML( pratilipiData.getSummary() );
		summaryInput.setHtml( pratilipiData.getSummary() );
		genreList.set( pratilipiData.getGenreNameList() );
		addRemoveGenre.setPratilipiData( pratilipiData );
		uploadImageContentAnchor.setHref( "/upload?id=" + pratilipiData.getId() + "&type=image" );
		uploadWordContentAnchor.setHref( "/upload?id=" + pratilipiData.getId() + "&type=pratilipi" );
		uploadPDFContentAnchor.setHref( "/upload?id=" + pratilipiData.getId() + "&type=pdf" );
		
		if( pratilipiData.getContentType() != null && pratilipiData.getContentType() == PratilipiContentType.IMAGE ){
			uploadWordContentAnchor.setVisible( false );
			uploadPDFContentAnchor.setVisible( false );
		}
		else if( pratilipiData.getContentType() != null && pratilipiData.getContentType() == PratilipiContentType.PRATILIPI ){
			uploadImageContentAnchor.setVisible( false );
			uploadPDFContentAnchor.setVisible( false );
		}
		else if( pratilipiData.getContentType() != null && pratilipiData.getContentType() == PratilipiContentType.PDF ){
			uploadImageContentAnchor.setVisible( false );
			uploadWordContentAnchor.setVisible( false );
		}
		
		if( pratilipiData.getState() == PratilipiState.PUBLISHED ) {
			publishPanel.setVisible( false );
			unpublishAnchor.setVisible( true );
			unpublishAnchor.setText( "Un-Publish this " + pratilipiData.getType().getName() );
		} else {
			Element el1 = Document.get().createSpanElement();
			Element el2 = Document.get().createSpanElement();
			el1.setInnerText( "This " + pratilipiData.getType().getName() + " is not yet published ! Click " );
			el2.setInnerText( " to publish it. " );
			
			publishPanel.setVisible( true );
			publishPanel.clear();
			publishPanel.getElement().removeAllChildren();
			publishPanel.getElement().appendChild( el1 );
			publishPanel.add( publishAnchor );
			publishPanel.getElement().appendChild( el2 );
			unpublishAnchor.setVisible( false );
		}
		coverImageUpload.setUploadUrl( pratilipiData.getCoverImageUploadUrl() );
		if( ! pratilipiData.getPageUrlAlias().equals( Window.Location.getPath() ) )
			Window.Location.assign( pratilipiData.getPageUrlAlias() );
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == editPratilipiDataAnchor ) {
			pratilipiDataInputView.setVisible( true );
		
			
		} else if( event.getSource() == savePratilipiDataButton ) {
			if( !pratilipiDataInputView.validateInputs() )
				return;
			pratilipiDataInputView.setEnabled( false );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiDataInputView.getPratilipiData() ),
					new AsyncCallback<SavePratilipiResponse>() {
						
						@Override
						public void onSuccess( SavePratilipiResponse response ) {
							setPratilipiData( response.getPratilipiData() );
							pratilipiDataInputView.setVisible( false );
							pratilipiDataInputView.setEnabled( true );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							pratilipiDataInputView.setEnabled( true );
						}
						
					});
			
			
		} else if( event.getSource() == editSummaryAnchor ) {
			summaryPanel.getElement().setInnerHTML( "" );
			summaryPanel.add( summaryInput );
			saveSummaryButton.setVisible( true );
		
			
		} else if( event.getSource() == saveSummaryButton ) {
			summaryInput.setEnabled( false );
			saveSummaryButton.setEnabled( false );
			
			PratilipiData pratilipiData = new PratilipiData();
			pratilipiData.setId( this.pratilipiData.getId() );
			pratilipiData.setSummary( summaryInput.getHtml() );
			
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>() {
						
						@Override
						public void onSuccess( SavePratilipiResponse response ) {
							summaryPanel.remove( summaryInput );
							saveSummaryButton.setVisible( false );
							setPratilipiData( response.getPratilipiData() );

							summaryInput.setEnabled( true );
							saveSummaryButton.setEnabled( true );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							summaryInput.setEnabled( true );
							saveSummaryButton.setEnabled( true );
						}
						
					});
		
			
		} else if( event.getSource() == genreAnchor ) {
			addRemoveGenre.setVisible( true );

			
		} else if( event.getSource() == publishAnchor ) {
			publishAnchor.setEnabled( false );
			PratilipiData pratilipiData = new PratilipiData( this.pratilipiData.getId() );
			pratilipiData.setState( PratilipiState.PUBLISHED );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>() {

						@Override
						public void onSuccess( SavePratilipiResponse response ) {
							publishAnchor.setEnabled( true );
							setPratilipiData( response.getPratilipiData() );
						}
			
						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
							publishAnchor.setEnabled( true );
						}

					});
			
		
		} else if( event.getSource() == unpublishAnchor ) {
			PratilipiData pratilipiData = new PratilipiData( this.pratilipiData.getId() );
			pratilipiData.setState( PratilipiState.DRAFTED );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>() {

						@Override
						public void onSuccess( SavePratilipiResponse response ) {
							setPratilipiData( response.getPratilipiData() );
						}

						@Override
						public void onFailure( Throwable caught ) {
							// Do nothing
						}

					});
		
		}
		
	}
	
}
