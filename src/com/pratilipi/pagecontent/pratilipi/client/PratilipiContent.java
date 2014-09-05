package com.pratilipi.pagecontent.pratilipi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class PratilipiContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	private final Anchor addReviewAnchor = new Anchor( "Review this" );
	private final Anchor saveReviewAnchor = new Anchor( "Save Review" );
	private final Label savingLabel = new Label( "Saving ..." );
	
	private String url = Window.Location.getPath();
	private PratilipiType pratilipiType;

	
	public void onModuleLoad() {
		for( PratilipiType pratilipiType : PratilipiType.values() )
			if( url.startsWith( pratilipiType.getPageUrl() ) )
				this.pratilipiType = pratilipiType;

		if( pratilipiType == null )
			return;
		
		
		RootPanel rootPanel = RootPanel.get( "PageContent-Pratilipi-Review-AddOptions" );
		if( rootPanel != null ) {
			addReviewAnchor.addClickHandler( this );
			saveReviewAnchor.addClickHandler( this );
			saveReviewAnchor.setVisible( false );
			savingLabel.setVisible( false );
		
			rootPanel.add( addReviewAnchor );
			rootPanel.add( saveReviewAnchor );
			rootPanel.add( savingLabel );
		}
	}

	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == addReviewAnchor ) {
			addReviewAnchor.setVisible( false );
			saveReviewAnchor.setVisible( true );
			loadEditor( RootPanel.get( "PageContent-Pratilipi-Review" ).getElement() );
			
		} else if( event.getSource() == saveReviewAnchor ) {
			saveReviewAnchor.setVisible( false );
			savingLabel.setVisible( true );
			
			String pratilipiIdStr = url.substring( pratilipiType.getPageUrl().length() );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			
			UserPratilipiData userPratilipiData = new UserPratilipiData();
			userPratilipiData.setPratilipiId( pratilipiId );
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
					savingLabel.setVisible( false );
					saveReviewAnchor.setVisible( true );
				}
				
			});
		}
		
	}
	
	private native void loadEditor( Element element ) /*-{
		$wnd.CKEDITOR.replace( element );
	}-*/;
	
	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

}
