package com.pratilipi.pagecontent.pratilipis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewAccordionImpl;
import com.pratilipi.commons.client.PratilipiDataInputViewModalImpl;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
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

public class PratilipisContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private PratilipiFilter pratilipiFilter;
	private PratilipiDataInputView pratilipiDataInputView;
	private Anchor addBook = new Anchor( "Add" );
	private Anchor addStory = new Anchor( "Add" );
	private Anchor addPoem = new Anchor( "Add" );
	private Anchor addArticle = new Anchor( "Add" );
	
	
	public void onModuleLoad() {
		
		RootPanel rootPanel =
				RootPanel.get( "PageContent-Pratilipi-List-Preloaded" );
		
		if( rootPanel != null ) {
			String filterStr =
					rootPanel.getElement().getAttribute( "pratilipi-filters" );
			pratilipiFilter = filterStr == null ?
					null : PratilipiFilter.fromString( filterStr );
		}
		
		rootPanel = RootPanel.get( "PageContent-Pratilipi-DataInput" );
		if( rootPanel != null ) {
			pratilipiDataInputView = new PratilipiDataInputViewAccordionImpl(
					pratilipiFilter.getType() );
			pratilipiDataInputView.addAddButtonClickHandler( this );

			rootPanel.add( pratilipiDataInputView );
			
			// Load list of languages
		   languageListRPC( pratilipiDataInputView );
		
		}
		
		rootPanel = RootPanel.get( "PageContent-Pratilipi-List" );
		if( rootPanel != null )
			rootPanel.add(
					new PratilipiList(
							RootPanel.get( "PageContent-Pratilipi-List-Preloaded" ),
							pratilipiFilter, pratilipiDataInputView ) );
	
		//Links on author page to add books,stories, poems and articles.
		RootPanel addBookPanel = RootPanel.get( "addBook" );
		if( addBookPanel != null )
			addBookPanel.add( addBook );
		addBook.setStyleName( "BOOK" );
		addBook.addClickHandler( this );
		
		RootPanel addStoryPanel = RootPanel.get( "addStory" );
		if( addStoryPanel != null )
			addStoryPanel.add( addStory );
		addStory.setStyleName( "STORY" );
		addStory.addClickHandler( this );
		
		RootPanel addPoemPanel = RootPanel.get( "addPoem" );
		if( addPoemPanel != null )
			addPoemPanel.add( addPoem );
		addPoem.setStyleName( "POEM" );
		addPoem.addClickHandler( this );
		
		RootPanel addArticlePanel = RootPanel.get( "addArticle" );
		if( addArticlePanel != null )
			addArticlePanel.add( addArticle );
		addArticle.setStyleName( "ARTICLE" );
		addArticle.addClickHandler( this );
		
	}
	
	
	@Override
	public void onClick( ClickEvent event ) {
		event.stopPropagation();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();

		String eventSource = event.getSource().getClass().toString();
		String gwtObject = eventSource.substring( eventSource.lastIndexOf( "." )+1 );
		
		//When add link on author page is clicked.
		if( gwtObject.equals( "Anchor" ) ) {
			pratilipiFilter.setType( PratilipiType.valueOf( ( (Anchor) event.getSource() ).getStyleName() ) );
			pratilipiDataInputView = new PratilipiDataInputViewModalImpl( pratilipiFilter.getType() );
			languageListRPC( pratilipiDataInputView );
			pratilipiDataInputView.addAddButtonClickHandler( this );
			RootPanel.get().add( pratilipiDataInputView );
			
			pratilipiDataInputView.setVisible( true );
		}
		
		//When 'save Pratilipi' button clicked in the add pratilipi form. 
		if( gwtObject.equals( "Button" ) ) {
			
			if( ! pratilipiDataInputView.validateInputs() )
				return;
			
			String currentUrl = Window.Location.getHref();
			boolean hasParameter = currentUrl.indexOf( "?" ) != -1 ? true : false;
			Long authorId;
			if( hasParameter ) {
				authorId = Long.valueOf( currentUrl.substring( currentUrl.lastIndexOf( "/" ) + 1,
																currentUrl.indexOf( "?" )));
			}
			else
				authorId = Long.valueOf( currentUrl.substring( currentUrl.lastIndexOf( "/" ) + 1 ));
			
			final PratilipiData pratilipiData = pratilipiDataInputView.getPratilipiData();
			pratilipiData.setAuthorId( authorId );
			pratilipiData.setState( PratilipiState.DRAFTED );
			
			pratilipiDataInputView.setEnabled( false );
			pratilipiService.savePratilipi(
					new SavePratilipiRequest( pratilipiData ),
					new AsyncCallback<SavePratilipiResponse>(){
	
				@Override
				public void onFailure(Throwable caught) {
					pratilipiDataInputView.setEnabled( true );
					Window.alert( caught.getMessage() );
				}
	
				@Override
				public void onSuccess( SavePratilipiResponse response ) {
					final PratilipiView pratilipiView = pratilipiDataInputView.getPratilipiView();
					if( pratilipiView == null ) {
						Window.Location.replace( response.getPratilipiData().getPageUrl() );
	
					} else {
						pratilipiDataInputView.reset();
						pratilipiDataInputView.setEnabled( true );
						new Timer() { // Wait for the pratilipiDataInputView to collapse
							@Override
							public void run() {
								pratilipiView.focus();
								pratilipiView.setPratilipiData( pratilipiData );
							}
						}.schedule( 100 );
					}
				}
				
			});
		}
	}
	
	private void languageListRPC( PratilipiDataInputView PratilipiDataInputView ) {
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
	}
}
