package com.pratilipi.pagecontent.author.client;

import com.claymus.commons.client.ui.Dropdown;
import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

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
	
	
	private final Dropdown dropdown = new Dropdown();

	
	// Author data edit options widgets
	private final Anchor editAuthorDataAnchor = new Anchor( "Edit Info" );
	private final Button saveAuthorDataButton = new Button( "Save" );
	private final AuthorDataInputView authorDataInputView = new AuthorDataInputViewModalImpl();
	
	private final Anchor editAuthorSummaryAnchor = new Anchor( "Edit Summary" );
	private final Button saveAuthorSummaryButton = new Button( "Save" );
	private final RichTextInputFormField authorSummaryInput = new RichTextInputFormField();


	private AuthorData authorData = null;
	
	
	@Override
	public void onModuleLoad() {
		
		// Author data edit options widgets
		editAuthorDataAnchor.addClickHandler( this );
		saveAuthorDataButton.addClickHandler( this );
		
		editAuthorSummaryAnchor.addClickHandler( this );
		saveAuthorSummaryButton.addClickHandler( this );
		saveAuthorSummaryButton.setVisible( false );
		
		pratilipiService.getLanguageList(
				new GetLanguageListRequest(),
				new AsyncCallback<GetLanguageListResponse>() {

			@Override
			public void onSuccess( GetLanguageListResponse response ) {
				for( LanguageData languageData : response.getLanguageList() )
					authorDataInputView.addLanguageListItem( languageData );
			}

			@Override
			public void onFailure( Throwable caught ) {
				Window.Location.reload();
			}

		});
		
		dropdown.add( editAuthorDataAnchor );
		authorDataInputView.add( saveAuthorDataButton );
		RootPanel.get().add( authorDataInputView );
		
		dropdown.add( editAuthorSummaryAnchor );
		authorSummaryEditOptionsPanel.add( saveAuthorSummaryButton );
		
		saveAuthorDataButton.setStyleName( "btn btn-success" );
		saveAuthorSummaryButton.setStyleName( "btn btn-success" );
		

		
		authorNamePanel.getElement().setInnerHTML( "" );
		authorNamePanel.add( dropdown );
		

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

	}
	
	private void setAuthorData( AuthorData authorData ) {
		this.authorData = authorData;
		dropdown.setTitle( authorData.getName() );
		authorNameEnPanel.getElement().setInnerText( authorData.getNameEn() );
		authorSummaryPanel.getElement().setInnerHTML( authorData.getSummary() );
		authorSummaryInput.setHtml( authorData.getSummary() );
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
						public void onFailure(Throwable caught) {
							authorSummaryInput.setEnabled( true );
							saveAuthorSummaryButton.setEnabled( true );
						}
						
					});
		}
		
	}
	
}
