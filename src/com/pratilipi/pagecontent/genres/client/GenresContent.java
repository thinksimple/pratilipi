package com.pratilipi.pagecontent.genres.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.GenreDataInputView;
import com.pratilipi.commons.client.GenreDataInputViewImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.data.GenreData;

public class GenresContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private final Accordion accordion = new Accordion();
	private final GenreDataInputView genreDataInputView =
			new GenreDataInputViewImpl();

	
	public void onModuleLoad() {
		if( RootPanel.get( "PageContent-Genres-DataInput" ) == null )
			return;
		
		accordion.setTitle( "Add Genre" );
		genreDataInputView.addAddButtonClickHandler( this );

		accordion.add( genreDataInputView );
		RootPanel.get( "PageContent-Genres-DataInput" ).add( accordion );
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( ! genreDataInputView.validateInputs() )
			return;
		
		genreDataInputView.setEnabled( false );
		GenreData languageData = genreDataInputView.getGenreData();
		AddGenreRequest request = new AddGenreRequest( languageData );
		pratilipiService.addGenre( request, new AsyncCallback<AddGenreResponse>() {

			@Override
			public void onSuccess( AddGenreResponse response ) {
				// TODO: Display success message
				Window.Location.reload();
			}
		
			@Override
			public void onFailure( Throwable caught ) {
				genreDataInputView.setEnabled( true );
				
				// TODO: Remove browser alert and show proper error message
				Window.alert( caught.getMessage() );
			}

		});
	}
	
}
