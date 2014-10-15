package com.pratilipi.pagecontent.author.client;

import com.claymus.commons.client.ui.Dropdown;
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
	
	
	// Author data edit options widgets
	private Anchor editAuthorDataAnchor = new Anchor( "Edit Info" );
	private Button saveAuthorDataButton = new Button( "Save" );
	
	private AuthorData authorData;
	private AuthorDataInputView authorDataInputView = new AuthorDataInputViewModalImpl();


	@Override
	public void onModuleLoad() {
		
		// Decoding AuthorData
		RootPanel rootPanel = RootPanel.get( "PageContent-Author-EncodedData" );
		String authorDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( authorDataEncodedStr );
			authorData = (AuthorData) streamReader.readObject();
		} catch( SerializationException e ) {
			Window.Location.reload();
		}


		// Author data edit options widgets
		editAuthorDataAnchor.addClickHandler( this );
		saveAuthorDataButton.addClickHandler( this );
		
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
		authorDataInputView.add( saveAuthorDataButton );
		
		saveAuthorDataButton.setStyleName( "btn btn-success" );
		RootPanel.get().add( authorDataInputView );
		
		
		
		
		Dropdown dropdown = new Dropdown( authorData.getName() );
		dropdown.add( editAuthorDataAnchor );

		rootPanel = RootPanel.get( "PageContent-Author-Title" );
		rootPanel.getElement().setInnerHTML( "" );
		rootPanel.add( dropdown );

		
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
							Window.Location.reload();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							authorDataInputView.setEnabled( true );
						}
						
					});
			
		}
		
	}

}
