package com.pratilipi.commons.client;


import com.claymus.commons.client.ui.StarRating;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class RatingPanel extends Composite {
	private StarRating stars;
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	private Long bookId;
	private UserPratilipiData userPratilipi = new UserPratilipiData();
	private Boolean resetRating = false;
	
	private InlineLabel saveRatingError = new InlineLabel( "Error Occured While Saving. Please Try Again Later" );
	
	public RatingPanel( Long pratilipiId, Integer rating, boolean isReadOnly ){
		stars  = new StarRating( rating, isReadOnly );
		FlowPanel flowPanel = new FlowPanel();
		
		bookId = pratilipiId;
		

		saveRatingError.getElement().getStyle().setColor( "red" );
		saveRatingError.getElement().getStyle().setDisplay( Display.NONE );
		
		eventHandler();
		flowPanel.add( stars );
		flowPanel.add( saveRatingError );
		initWidget( flowPanel );
	}
	
	public void eventHandler(){
		stars.addStarsClickHandler( addClickHandler() );
		stars.addStarsMouseOverHandler( addMouseOverHandler() );
		stars.addStarsMouseOutHandler( addMouseOutHandler() );
	}
	
	public ClickHandler addClickHandler(){
		ClickHandler starClickHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if (!stars.isReadOnly()) {
		            final Image image = (Image)event.getSource();
		            if( resetRating && stars.getHoverIndex() == stars.getRating() ){
		            	stars.setValue(0, true);
		            }
		            else {
		            	stars.setValue( Integer.parseInt( image.getTitle() ), true);
		            	resetRating = true;
		            }
	                userPratilipi.setPratilipiId( bookId );
	        		userPratilipi.setRating( stars.getRating() );
	                pratilipiService.addUserPratilipi(new AddUserPratilipiRequest( userPratilipi ), new AsyncCallback<AddUserPratilipiResponse>(){

						@Override
						public void onFailure(Throwable caught) {
							saveRatingError.getElement().getStyle().setDisplay( Display.BLOCK );
						}

						@Override
						public void onSuccess(AddUserPratilipiResponse result) {
							
						}});
		        }
			}
		};
		return starClickHandler;
	}
	
	public MouseOverHandler addMouseOverHandler(){
		MouseOverHandler starMouseOverHandler = new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (!stars.isReadOnly()) {
		            Image image = (Image)event.getSource();    
		            stars.setHoverIndex(Integer.parseInt(image.getTitle()));                
		            stars.setStarImages();
		        }
				
			}
		};
		return starMouseOverHandler;
	}
	
	public MouseOutHandler addMouseOutHandler(){
		MouseOutHandler starMouseOutHandler = new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				stars.setHoverIndex(0);
				stars.setStarImages();
			}
		};
		return starMouseOutHandler;
	}

}

