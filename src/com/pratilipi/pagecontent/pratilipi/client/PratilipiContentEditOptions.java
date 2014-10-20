package com.pratilipi.pagecontent.pratilipi.client;

import java.util.Date;

import com.claymus.commons.client.ui.Dropdown;
import com.claymus.commons.client.ui.FileUploadWithProgressBar;
import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewModalImpl;
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
	
	
	// Cover image edit options widgets
	private final FileUploadWithProgressBar coverImageUpload = new FileUploadWithProgressBar();
	
	
	// Summary edit options widgets
	private final Label savingSummaryLabel = new Label( "Saving Summary ..." );
	private Button editPratilipiInfo;
	
	
	private PratilipiData pratilipiData;


	
	
	
	// Publish message
	RootPanel publishRootPanel;
	private Button publishButton;

	// Un-publish message
	RootPanel unPublishRootPanel;
	private Button unPublishButton;
	
	
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
			Window.alert( e.getMessage() );
		}
		
		
		titePanel.getElement().setInnerHTML( "" );
		titePanel.add( dropdown );

		
		
		
		//Edit Book info.
		RootPanel editInfoRootPanel = RootPanel.get( "PageContent-Pratilipi-Info-EditOption" );
		if( editInfoRootPanel != null ) {
			editPratilipiInfo = new Button( "Edit " + pratilipiData.getType().getName() + " Info" );
			editPratilipiInfo.addStyleName( "btn btn-primary hidden-xs" );
			editPratilipiInfo.addClickHandler( this );
			editInfoRootPanel.add( editPratilipiInfo );
			
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
						pratilipiDataInputView.addLanguageListItem( languageData );
				}
				
		    });
			
		}
		
		publishRootPanel = RootPanel.get( "PageContent-Pratilipi-Publish" );
		if( publishRootPanel != null ) {
			
			publishButton = new Button( "Publish This " + pratilipiData.getType().getName() );
			publishButton.setStyleName( "btn btn-success" );
			publishButton.addClickHandler( this );
			publishButton.getElement().setAttribute( "style", "margin: 0px 0px 4px 15px");
			
			if( pratilipiData.getState() != null && pratilipiData.getState().equals( PratilipiState.PUBLISHED )) {
				publishRootPanel.setVisible( false );
			}
			
			publishRootPanel.add( publishButton );
		}
		
		unPublishRootPanel = RootPanel.get( "PageContent-Pratilipi-Unpublish" );
		if( unPublishRootPanel != null ) {
			
			unPublishButton = new Button( "Unpublish This " + pratilipiData.getType().getName() );
			unPublishButton.setStyleName( "btn btn-danger" );
			unPublishButton.addClickHandler( this );
			unPublishButton.getElement().setAttribute( "style", "margin: 0px 0px 4px 15px");
			
			if( pratilipiData.getState() != null && pratilipiData.getState().equals( PratilipiState.DRAFTED )) {
				unPublishRootPanel.setVisible( false );
			}
			
			unPublishRootPanel.add( unPublishButton );
		}

	}

	
	private void setPratilipiData( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
		dropdown.setTitle( pratilipiData.getTitle() );
		authorNamePanel.getElement().setInnerText( pratilipiData.getAuthorData().getFullName() );
		pratilipiDataInputView.setPratilipiData( pratilipiData );
		summaryPanel.getElement().setInnerHTML( pratilipiData.getSummary() );
		summaryInput.setHtml( pratilipiData.getSummary() );
		genreList.set( pratilipiData.getGenreNameList() );
		addRemoveGenre.setPratilipiData( pratilipiData );
		coverImageUpload.setUploadUrl( pratilipiData.getCoverImageUploadUrl() );
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
			
			
			
			
			
		} else if( event.getSource() == editPratilipiInfo ) {
			pratilipiDataInputView.setPratilipiData( pratilipiData );
			pratilipiDataInputView.setVisible( true );
			RootPanel.get().add( pratilipiDataInputView );
		} else if( event.getSource() == publishButton ) {
			
			//This is used to make a pratilipi entity published
			publishButton.setEnabled( false );
			PratilipiData updatedState = new PratilipiData();
			updatedState.setId( pratilipiData.getId() );
			updatedState.setState( PratilipiState.PUBLISHED );
			updateStateRPC(publishButton, updatedState );
		} else if( event.getSource() == unPublishButton ) {
			
			//This is used to make a pratilipi entity unpublished
			unPublishButton.setEnabled( false );
			PratilipiData updatedState = new PratilipiData();
			updatedState.setId( pratilipiData.getId() );
			updatedState.setState( PratilipiState.DRAFTED );
			updateStateRPC(unPublishButton, updatedState );
		} else {
			
			//This is used when save button is clicked.
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
	
	private void updateStateRPC( final Button buttonClicked, final PratilipiData updatedState ) {
		pratilipiService.savePratilipi(
				new SavePratilipiRequest( updatedState ),
				new AsyncCallback<SavePratilipiResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				buttonClicked.setEnabled( true );
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess( SavePratilipiResponse response ) {
				pratilipiData.setState( updatedState.getState() );
				if( pratilipiData.getState().equals( PratilipiState.PUBLISHED) ) {
					publishRootPanel.setVisible( false );
					unPublishButton.setEnabled( true );
					unPublishRootPanel.setVisible( true );
				}
				
				if( pratilipiData.getState().equals( PratilipiState.DRAFTED) ) {
					unPublishRootPanel.setVisible( false );
					publishButton.setEnabled( true );
					publishRootPanel.setVisible( true );
				}
			}
			
		});
	}
	
	private native void loadFileUploader( Element element ) /*-{
		$wnd.jQuery( element ).fileupload({
			add: function(e, data) {
                var uploadErrors = [];
                var acceptFileTypes = /^image\/(jpe?g|png|bmp)$/i;
                if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                    uploadErrors.push('Not an accepted file type. Please upload ".jpg" file');
                }
                if( data.originalFiles[0]['size'] > 10000000 ) {
                    uploadErrors.push('File size should be less than ');
                }
                if(uploadErrors.length > 0) {
                    alert(uploadErrors.join("\n"));
                } else {
                    data.submit();
                }
	        },
			dataType: 'html',
			progressall: function (e, data) {
				$wnd.jQuery('#cover-image').css( 'opacity', '0.3' );
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $wnd.jQuery('#progress').css( 'display', 'block' );
	            $wnd.jQuery('#progress .bar').css( 'width', progress + '%' );
	            $wnd.jQuery('#percent').html( progress + '%' );
			},
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
