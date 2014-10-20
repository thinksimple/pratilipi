package com.pratilipi.pagecontent.author.client;

import java.util.Date;

import com.claymus.commons.client.ui.Dropdown;
import com.claymus.commons.client.ui.FileUploadWithProgressBar;
import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
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
import com.pratilipi.commons.client.AuthorDataInputView;
import com.pratilipi.commons.client.AuthorDataInputViewModalImpl;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewModalImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class AuthorContentEditOptions implements EntryPoint, ClickHandler {
	
	private PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	private final RootPanel authorNamePanel =
			RootPanel.get( "PageContent-Author-Name" );
	private final RootPanel authorNameEnPanel =
			RootPanel.get( "PageContent-Author-NameEn" );
	private final RootPanel authorSummaryPanel =
			RootPanel.get( "PageContent-Author-Summary" );
	private final RootPanel authorSummaryEditOptionsPanel =
			RootPanel.get( "PageContent-Author-Summary-EditOptions" );
	private final RootPanel authorImagePanel =
			RootPanel.get( "PageContent-Author-Image" );
	private final RootPanel authorImageEditOptionsPanel =
			RootPanel.get( "PageContent-Author-Image-EditOptions" );
	
	
	private final Dropdown dropdown = new Dropdown();

	
	// Author data edit options widgets
	private final Anchor editAuthorDataAnchor = new Anchor( "Edit Info" );
	private final Button saveAuthorDataButton = new Button( "Save" );
	private final AuthorDataInputView authorDataInputView = new AuthorDataInputViewModalImpl();
	
	private final Anchor editAuthorSummaryAnchor = new Anchor( "Edit Summary" );
	private final Button saveAuthorSummaryButton = new Button( "Save" );
	private final RichTextInputFormField authorSummaryInput = new RichTextInputFormField();


	// Author image edit options widgets
	private final FileUploadWithProgressBar authorImageUpload = new FileUploadWithProgressBar();
	
	
	private AuthorData authorData = null;

	
	// New Pratilipi option widgets
	private final Anchor newPratilipiAnchor = new Anchor( "New Pratilipi" );
	private final Button savePratilipiDataButton = new Button( "Save" );
	private final PratilipiDataInputView pratilipiDataInputView =
			new PratilipiDataInputViewModalImpl();
	
	
	@Override
	public void onModuleLoad() {
		
		// Author data edit options widgets
		editAuthorDataAnchor.addClickHandler( this );
		saveAuthorDataButton.addClickHandler( this );
		
		editAuthorSummaryAnchor.addClickHandler( this );
		saveAuthorSummaryButton.addClickHandler( this );
		saveAuthorSummaryButton.setVisible( false );
		
		dropdown.add( editAuthorDataAnchor );
		authorDataInputView.add( saveAuthorDataButton );
		RootPanel.get().add( authorDataInputView );
		
		dropdown.add( editAuthorSummaryAnchor );
		authorSummaryEditOptionsPanel.add( saveAuthorSummaryButton );
		
		saveAuthorDataButton.setStyleName( "btn btn-success" );
		saveAuthorSummaryButton.setStyleName( "btn btn-success" );
		

		// Author image edit options widgets
		authorImageUpload.setTitle( "<h1 class='bg-translucent' style='margin-top:-41px; text-align:right;'><span class='glyphicon glyphicon-camera' style='margin-right:10px;'></span></h1>" );
		authorImageUpload.setAcceptedFileTypes( "image/jpg, image/jpeg, image/png, image/bmp" );
		authorImageUpload.addValueChangeHandler( new ValueChangeHandler<String>( ) {
			
			@Override
			public void onValueChange( ValueChangeEvent<String> event ) {
				Element imgElement = authorImagePanel.getElement();
				String src = imgElement.getAttribute( "src" );
				if( src.indexOf( '?' ) != -1 )
					src = src.substring( 0, src.indexOf( '?' ) );
				src = src + "?" + new Date().getTime();
				imgElement.setAttribute( "src", src );
			}
			
		});
		authorImageUpload.getProgressBar().getElement().setAttribute(
				"style",
				authorImageUpload.getProgressBar().getElement().getAttribute( "style" )
				+ "margin-top:10px; margin-bottom:10px;" );
		
		authorImageEditOptionsPanel.add( authorImageUpload );
		authorImageEditOptionsPanel.add( authorImageUpload.getProgressBar() );
		
		
		// Decoding AuthorData
		RootPanel rootPanel = RootPanel.get( "PageContent-Author-EncodedData" );
		String authorDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( authorDataEncodedStr );
			authorData = (AuthorData) streamReader.readObject();
			setAuthorData( authorData );
		} catch( SerializationException e ) {
			Window.Location.reload();
		}

		
		// New Pratilipi option widgets
		newPratilipiAnchor.addClickHandler( this );
		savePratilipiDataButton.addClickHandler( this );

		dropdown.add( newPratilipiAnchor );
		pratilipiDataInputView.add( savePratilipiDataButton );
		RootPanel.get().add( pratilipiDataInputView );
		
		savePratilipiDataButton.setStyleName( "btn btn-success" );

		
		authorNamePanel.getElement().setInnerHTML( "" );
		authorNamePanel.add( dropdown );
		
		
		pratilipiService.getLanguageList(
				new GetLanguageListRequest(),
				new AsyncCallback<GetLanguageListResponse>() {

			@Override
			public void onSuccess( GetLanguageListResponse response ) {
				for( LanguageData languageData : response.getLanguageList() ) {
					authorDataInputView.addLanguageListItem( languageData );
					pratilipiDataInputView.addLanguageListItem( languageData );
				}
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.Location.reload();
			}

		});

	}
	
	private void setAuthorData( AuthorData authorData ) {
		this.authorData = authorData;
		dropdown.setTitle( authorData.getFullName() );
		authorNameEnPanel.getElement().setInnerText( authorData.getFullNameEn() );
		authorSummaryPanel.getElement().setInnerHTML( authorData.getSummary() );
		authorSummaryInput.setHtml( authorData.getSummary() );
		authorImageUpload.setUploadUrl( authorData.getAuthorImageUrl() );
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == editAuthorDataAnchor ) {
			authorDataInputView.setAuthorData( authorData );
			authorDataInputView.setVisible( true );
		
		} else if( event.getSource() == saveAuthorDataButton ) {
			if( !authorDataInputView.validateInputs() )
				return;
			authorDataInputView.setEnabled( false );
			pratilipiService.saveAuthor(
					new SaveAuthorRequest( authorDataInputView.getAuthorData() ),
					new AsyncCallback<SaveAuthorResponse>() {
						
						@Override
						public void onSuccess( SaveAuthorResponse response ) {
							setAuthorData( response.getAuthorData() );
							authorDataInputView.setVisible( false );
							authorDataInputView.setEnabled( true );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							authorDataInputView.setEnabled( true );
						}
						
					});
			
		} else if( event.getSource() == editAuthorSummaryAnchor ) {
			authorSummaryPanel.getElement().setInnerHTML( "" );
			authorSummaryPanel.add( authorSummaryInput );
			saveAuthorSummaryButton.setVisible( true );
		
		} else if( event.getSource() == saveAuthorSummaryButton ) {
			authorSummaryInput.setEnabled( false );
			saveAuthorSummaryButton.setEnabled( false );
			
			AuthorData authorData = new AuthorData();
			authorData.setId( this.authorData.getId() );
			authorData.setSummary( authorSummaryInput.getHtml() );
			
			pratilipiService.saveAuthor(
					new SaveAuthorRequest( authorData ),
					new AsyncCallback<SaveAuthorResponse>() {
						
						@Override
						public void onSuccess( SaveAuthorResponse response ) {
							authorSummaryPanel.remove( authorSummaryInput );
							saveAuthorSummaryButton.setVisible( false );
							setAuthorData( response.getAuthorData() );

							authorSummaryInput.setEnabled( true );
							saveAuthorSummaryButton.setEnabled( true );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							authorSummaryInput.setEnabled( true );
							saveAuthorSummaryButton.setEnabled( true );
						}
						
					});
		
		} else if( event.getSource() == newPratilipiAnchor ) {
			pratilipiDataInputView.setVisible( true );
		
		} else if( event.getSource() == savePratilipiDataButton ) {
			if( !pratilipiDataInputView.validateInputs() )
				return;
			pratilipiDataInputView.setEnabled( false );
			savePratilipiDataButton.setEnabled( false );
			
			PratilipiData pratilipiData = pratilipiDataInputView.getPratilipiData();
			pratilipiData.setAuthorId( authorData.getId() );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>() {
						
						@Override
						public void onSuccess( SavePratilipiResponse response ) {
							Window.Location.assign( response.getPratilipiData().getPageUrl() );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							pratilipiDataInputView.setEnabled( true );
							savePratilipiDataButton.setEnabled( true );
						}
						
					});
		}
		
	}
	
}
