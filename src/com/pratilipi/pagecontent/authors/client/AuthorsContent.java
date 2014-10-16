package com.pratilipi.pagecontent.authors.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.AuthorDataInputView;
import com.pratilipi.commons.client.AuthorDataInputViewImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public class AuthorsContent implements EntryPoint, ClickHandler {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	private final Accordion accordion = new Accordion();
	private final Button addButton = new Button( "Add" );
	
	private final AuthorDataInputView authorsDataInputView =
			new AuthorDataInputViewImpl();

	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get( "PageContent-Authors-DataInput" );
		if( rootPanel != null ) {
			accordion.setTitle( "Add Author" );
			addButton.addClickHandler( this );

			accordion.add( authorsDataInputView );
			accordion.add( addButton );
			rootPanel.add( accordion );

			pratilipiService.getLanguageList(
					new GetLanguageListRequest(),
					new AsyncCallback<GetLanguageListResponse>() {

				@Override
				public void onSuccess(GetLanguageListResponse response) {
					for( LanguageData languageData : response.getLanguageList())
						authorsDataInputView.addLanguageListItem( languageData );
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert(caught.getMessage());
					
				}

			});
			
			addButton.setStyleName( "btn btn-success" );
		}
		
	}
	
	
	@Override
	public void onClick( ClickEvent event ) {
		if( ! authorsDataInputView.validateInputs() )
			return;
		
		authorsDataInputView.setEnabled( false );
		addButton.setEnabled( false );
		AuthorData authorData = authorsDataInputView.getAuthorData();
		SaveAuthorRequest request = new SaveAuthorRequest( authorData );
		pratilipiService.saveAuthor( request, new AsyncCallback<SaveAuthorResponse>(){

			@Override
			public void onFailure( Throwable caught ) {
				authorsDataInputView.setEnabled( true );
				addButton.setEnabled( true );
			}

			@Override
			public void onSuccess( SaveAuthorResponse result) {
				Window.Location.reload();				
			}
			
		});
	}
	
}
