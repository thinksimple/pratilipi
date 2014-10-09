package com.pratilipi.pagecontent.pratilipi.client;

import java.util.List;

import com.claymus.commons.client.ui.Modal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddPratilipiGenreRequest;
import com.pratilipi.service.shared.AddPratilipiGenreResponse;
import com.pratilipi.service.shared.DeletePratilipiGenreRequest;
import com.pratilipi.service.shared.DeletePratilipiGenreResponse;
import com.pratilipi.service.shared.GetGenreListRequest;
import com.pratilipi.service.shared.GetGenreListResponse;
import com.pratilipi.service.shared.data.GenreData;
import com.pratilipi.service.shared.data.PratilipiData;

public class AddRemoveGenre extends Composite implements HasValueChangeHandlers<PratilipiData> {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	
	private Modal modal = new Modal();
	
	
	public AddRemoveGenre( final PratilipiData pratilipiData ) {
		
		modal.setTitle( "Add/Remove Genre" );
		
		
		pratilipiService.getGenreList( new GetGenreListRequest(), new AsyncCallback<GetGenreListResponse>() {
			
			@Override
			public void onSuccess( GetGenreListResponse response ) {
				List<GenreData> genreDataList = response.getGenreDataList();
				int genreCount = genreDataList.size();
				
				Grid grid = new Grid( genreCount, 1 );
				
				for( int i = 0; i < genreCount; i++ ) {
					final GenreData genreData = genreDataList.get( i );
					
					FlowPanel panel = new FlowPanel();
					Label label = new Label( genreData.getName() );
					final Button addButton = new Button( "Add" );
					final Button removeButton = new Button( "Remove" );

					if( pratilipiData.getGenreIdList().contains( genreData.getId() ) ) {
						addButton.setVisible( false );
						addButton.setEnabled( false );
					} else {
						removeButton.setVisible( false );
						removeButton.setEnabled( false );
					}
					
					addButton.addClickHandler( new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							addButton.setEnabled( false );
							
							pratilipiService.addPratilipiGenre(
									new AddPratilipiGenreRequest( pratilipiData.getId(), genreData.getId() ),
									new AsyncCallback<AddPratilipiGenreResponse>() {
										
										@Override
										public void onSuccess( AddPratilipiGenreResponse result) {
											addButton.setVisible( false );
											removeButton.setVisible( true );
											removeButton.setEnabled( true );
											pratilipiData.getGenreIdList().add( genreData.getId() );
											pratilipiData.getGenreNameList().add( genreData.getName() );
											ValueChangeEvent.fire( AddRemoveGenre.this, pratilipiData );
										}
										
										@Override
										public void onFailure(Throwable caught) {
											addButton.setEnabled( true );
										}
										
									});
						}
						
					});
					
					removeButton.addClickHandler( new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							removeButton.setEnabled( false );
							
							pratilipiService.deletePratilipiGenre(
									new DeletePratilipiGenreRequest( pratilipiData.getId(), genreData.getId() ),
									new AsyncCallback<DeletePratilipiGenreResponse>() {
										
										@Override
										public void onSuccess( DeletePratilipiGenreResponse result) {
											removeButton.setVisible( false );
											addButton.setVisible( true );
											addButton.setEnabled( true );
											pratilipiData.getGenreIdList().remove( genreData.getId() );
											pratilipiData.getGenreNameList().remove( genreData.getName() );
											ValueChangeEvent.fire( AddRemoveGenre.this, pratilipiData );
										}
										
										@Override
										public void onFailure(Throwable caught) {
											removeButton.setEnabled( true );
										}
										
									});
						}
						
					});

					panel.add( label );
					panel.add( addButton );
					panel.add( removeButton );
					
					grid.setWidget( i, 0, panel );
					
					label.getElement().setAttribute( "style", "display:inline;" );
					addButton.setStyleName( "btn btn-info btn-xs pull-right" );
					removeButton.setStyleName( "btn btn-warning btn-xs pull-right" );
				}
				
				modal.add( grid );
				
				grid.setStyleName( "table table-hover" );
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				Window.Location.reload();
			}
			
		});
		
		
		initWidget( modal );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		if( visible )
			modal.show();
		else
			modal.hide();
	}

	@Override
	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<PratilipiData> handler ) {
		return addHandler( handler, ValueChangeEvent.getType() );
	}
	
}