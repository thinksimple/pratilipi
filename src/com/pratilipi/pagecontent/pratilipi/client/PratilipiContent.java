package com.pratilipi.pagecontent.pratilipi.client;

import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.InlineLabel;
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
	private final Button updateReviewButton = new Button ( "Save" );
	private final Button cancelReviewEditButton = new Button( "Cancel" );
	private final RootPanel encodedPratilipiDataPanel = RootPanel.get( "PageContent-Pratilipi-EncodedData" );
	private final Anchor editReviewAnchor = new Anchor( "Edit" );
	private final RichTextInputFormField reviewInput = new RichTextInputFormField();
	private Element reviewPara;
	
	private PratilipiData pratilipiData;
	private UserPratilipiData userPratilipiData;
	
	private RatingPanel ratingPanel;
	
	private String url = Window.Location.getPath();

	private RootPanel reviewPanel = RootPanel.get( "PageContent-Pratilipi-Review" );
	private RootPanel submitButtonPanel = RootPanel.get( "PageContent-Pratilipi-Review-AddOptions" );
	private RootPanel reviewEditAnchorPanel = RootPanel.get( "PageContent-Pratilipi-ReviewEditAnchor" );
	private RootPanel reviewEditPanel = RootPanel.get( "PageContent-Pratilipi-ReviewEdit" );
	private RootPanel errorMessagePanel = RootPanel.get( "PageContent-Pratilipi-Review-SaveErrorMessage" );
	private RootPanel rootRatingPanel = RootPanel.get( "PageContent-Pratilipi-Rating" );
	private final RichTextInputFormField summaryInput = new RichTextInputFormField();
	
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

		saveReviewButton.getElement().setAttribute( "class", "btn btn-danger" );
		saveReviewButton.addClickHandler( this );
		updateReviewButton.getElement().setAttribute( "class", "btn btn-success" );
		updateReviewButton.addClickHandler( this );
		cancelReviewEditButton.getElement().setAttribute( "class", "btn btn-danger" );
		cancelReviewEditButton.getElement().getStyle().setMarginLeft( 5, Unit.PX );
		cancelReviewEditButton.addClickHandler( this );
		editReviewAnchor.getElement().addClassName( "editAnchor" );
		editReviewAnchor.addClickHandler( this );
		
		if( reviewPanel != null && submitButtonPanel != null ) {
			loadBasicEditor( RootPanel.get( "PageContent-Pratilipi-Review" ).getElement() );
			submitButtonPanel.add( saveReviewButton );
		}
		
		if( reviewEditAnchorPanel != null ){
			reviewEditAnchorPanel.add( editReviewAnchor );
		}
		
		if( reviewEditPanel != null && submitButtonPanel != null ){
			reviewEditPanel.add( summaryInput );
			submitButtonPanel.add( updateReviewButton );
			submitButtonPanel.add( cancelReviewEditButton );
		}
		
		if( rootRatingPanel != null ){
			@SuppressWarnings( "unused" )
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
		} else if( event.getSource() == editReviewAnchor ) {
			Element parent = editReviewAnchor.getParent().getElement();
			Element sibling = parent.getPreviousSiblingElement();
			while( sibling != null ){
				if( sibling.getTagName() == "P" ){
					reviewPara = sibling;
					sibling = sibling.getPreviousSiblingElement();
				} else {
					sibling = sibling.getPreviousSiblingElement();
					continue;
				}
				if( reviewPara.getInnerHTML() != null && !reviewPara.getInnerHTML().trim().isEmpty() ){
					reviewPara.getStyle().setDisplay( Display.NONE );
					reviewEditAnchorPanel.getElement().getStyle().setDisplay( Display.NONE );
					summaryInput.setHtml( reviewPara.getInnerHTML() );
					reviewEditPanel.getElement().getStyle().setDisplay( Display.BLOCK );
					submitButtonPanel.getElement().getStyle().setDisplay( Display.BLOCK );
					break;
				}
			}
			
		} else if( event.getSource() == updateReviewButton ){
			errorMessagePanel.getElement().getStyle().setDisplay( Display.NONE );
			if( summaryInput.getHtml().trim().isEmpty() )
				return;
			
			updateReviewButton.setEnabled( false );
			summaryInput.setEnabled( false );
			cancelReviewEditButton.setEnabled( false );
			
			UserPratilipiData userPratilipiData = new UserPratilipiData();
			userPratilipiData.setPratilipiId( pratilipiData.getId() );
			userPratilipiData.setReview( summaryInput.getHtml() );
			
			pratilipiService.addUserPratilipi(
					new AddUserPratilipiRequest( userPratilipiData ),
					new AsyncCallback<AddUserPratilipiResponse>() {
				
				@Override
				public void onSuccess( AddUserPratilipiResponse result ) {
					reviewEditPanel.getElement().getStyle().setDisplay( Display.NONE );
					submitButtonPanel.getElement().getStyle().setDisplay( Display.NONE );
					reviewPara.setInnerHTML( summaryInput.getHtml() );
					reviewPara.getStyle().setDisplay( Display.BLOCK );
					reviewEditAnchorPanel.getElement().getStyle().setDisplay( Display.BLOCK );
					updateReviewButton.setEnabled( true );
					summaryInput.setEnabled( true );
					cancelReviewEditButton.setEnabled( true );
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					InlineLabel error = new InlineLabel( caught.getMessage() );
					errorMessagePanel.add( error );
					errorMessagePanel.getElement().getStyle().setDisplay( Display.BLOCK );
					updateReviewButton.setEnabled( true );
					summaryInput.setEnabled( true );
					cancelReviewEditButton.setEnabled( true );
				}
				
			});
		} else if( event.getSource() == cancelReviewEditButton ){
			reviewEditPanel.getElement().getStyle().setDisplay( Display.NONE );
			submitButtonPanel.getElement().getStyle().setDisplay( Display.NONE );
			errorMessagePanel.getElement().getStyle().setDisplay( Display.NONE );
			reviewPara.getStyle().setDisplay( Display.BLOCK );
			reviewEditAnchorPanel.getElement().getStyle().setDisplay( Display.BLOCK );
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
