package com.pratilipi.pagecontent.pratilipi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.RatingPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class PratilipiContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	
	private final Button saveReviewButton = new Button( "Submit Review" );
	private final RootPanel encodedPratilipiDataPanel = RootPanel.get( "PageContent-Pratilipi-EncodedData" );
	
	private PratilipiData pratilipiData;
	private UserPratilipiData userPratilipiData;
	
	private RatingPanel ratingPanel;
	
	private String url = Window.Location.getPath();

	
	public void onModuleLoad() {
		if( encodedPratilipiDataPanel != null ){
			String pratilipiDataEncodedStr = encodedPratilipiDataPanel.getElement().getInnerText();
			SerializationStreamReader streamReader;
			try {
				streamReader = ( (SerializationStreamFactory) pratilipiService )
					.createStreamReader( pratilipiDataEncodedStr );
				pratilipiData = ( PratilipiData ) streamReader.readObject();
			} catch (SerializationException e) {
				Window.Location.reload();
			}
		}
		
		RootPanel reviewPanel = RootPanel.get( "PageContent-Pratilipi-Review" );
		RootPanel submitButtonPanel = RootPanel.get( "PageContent-Pratilipi-Review-AddOptions" );
		if( reviewPanel != null && submitButtonPanel != null ) {
			saveReviewButton.getElement().setAttribute( "class", "btn btn-danger" );
			saveReviewButton.addClickHandler( this );
			
			loadBasicEditor( RootPanel.get( "PageContent-Pratilipi-Review" ).getElement() );
			submitButtonPanel.add( saveReviewButton );
		}
		
		final RootPanel rootRatingPanel = RootPanel.get( "PageContent-Pratilipi-Rating" );
		if( rootRatingPanel != null ){
			final int rating;
			if( pratilipiData.getRatingCount() > 0L ){
				rating = ( int ) ( (double) pratilipiData.getStarCount()/pratilipiData.getRatingCount() );
			}
			else
				rating = 0;
			
			pratilipiService.getUserPratilipi( 
						new GetUserPratilipiRequest( null, pratilipiData.getId() ),
						new AsyncCallback<GetUserPratilipiResponse>() {
							
							@Override
							public void onSuccess( GetUserPratilipiResponse result ){
								userPratilipiData = result.getUserPratilipi();
								int userRating = userPratilipiData.getRating() == null ? 0 : userPratilipiData.getRating();
								ratingPanel = new RatingPanel( pratilipiData.getId(), userRating, false );
								rootRatingPanel.add( ratingPanel );
							}
							
							@Override
							public void onFailure( Throwable caught ){
								ratingPanel = new RatingPanel( pratilipiData.getId(), null, false );
								rootRatingPanel.add( ratingPanel );
							}
						});

			
		}
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == saveReviewButton ) {
			
			if( getHtmlFromEditor( "PageContent-Pratilipi-Review" ).trim().isEmpty() )
				return;
			
			
			saveReviewButton.setEnabled( false );
			saveReviewButton.setText( "Submitting ..." );
			
			UserPratilipiData userPratilipiData = new UserPratilipiData();
			userPratilipiData.setPratilipiId( pratilipiData.getId() );
			userPratilipiData.setReview( getHtmlFromEditor( "PageContent-Pratilipi-Review" ) );
			
			pratilipiService.addUserPratilipi(
					new AddUserPratilipiRequest( userPratilipiData ),
					new AsyncCallback<AddUserPratilipiResponse>() {
				
				@Override
				public void onSuccess( AddUserPratilipiResponse result ) {
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					Window.alert( caught.getMessage() );
					saveReviewButton.setEnabled( true );
					saveReviewButton.setText( "Submit Review" );
				}
				
			});
		}
		
	}
	
	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;

	private native void loadBasicEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element, {
				toolbar : 'BASIC',
				filebrowserImageBrowseUrl : "/filebrowser",
		} );
		$wnd.CKEDITOR.on( 'dialogDefinition', function(e){
			var dialogName = e.data.name;
			var dialogDefinition = e.data.definition;
			
			if( dialogName == 'image' ){
				dialogDefinition.removeContents( 'Link' );
				dialogDefinition.removeContents( 'advanced' );
			}
		});
	}-*/;

	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

}
