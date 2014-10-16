package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiViewThumbnailImpl extends PratilipiView {

	private final Panel colPanel = new SimplePanel();
	private final FocusPanel focusPanel = new FocusPanel();
	private final Panel panel = new FlowPanel();
	private final Panel thumbnailPanel = new FlowPanel();
	private final Panel infoPanel = new FlowPanel();

	private final Anchor coverImageAnchor = new Anchor();
	private final Image coverImage = new Image();
	
	private final Anchor titleAnchor = new Anchor();
	private final Anchor authorAnchor = new Anchor();
	private final Anchor editAnchor = new Anchor();
	
	
	private PratilipiData pratilipiData;
	
	
	public PratilipiViewThumbnailImpl() {
		editAnchor.setText( "Edit" );
		editAnchor.setVisible( false );

		
		// Composing the widget
		colPanel.add( panel );
		
		panel.add( thumbnailPanel );
		panel.add( infoPanel );

		thumbnailPanel.add( coverImageAnchor );
		coverImageAnchor.getElement().appendChild( coverImage.getElement() );

		infoPanel.add( titleAnchor );
		infoPanel.add( authorAnchor );
		
		
		// Setting required style classes
		colPanel.setStyleName( "col-lg-2 col-md-3 col-sm-3 col-xs-6" );
		colPanel.getElement().setAttribute( "style", "margin-bottom:30px;" );
		panel.getElement().setAttribute( "style", "width:150px; height:240px;" );
		thumbnailPanel.setStyleName( "bg-gray" );
		thumbnailPanel.getElement().setAttribute( "style", "width:150px; height:240px; overflow:hidden;" );
		infoPanel.setStyleName( "bg-gray bg-translucent" );
		infoPanel.getElement().setAttribute( "style",
				"position:absolute; bottom:0px; "
				+ "width:150px; padding:5px 10px 5px 10px;" );
		coverImage.setStyleName( "img-responsive" );
		titleAnchor.getElement().setAttribute( "style", "display:block;");
		authorAnchor.getElement().setAttribute( "style", "display:block;");
		
		initWidget( colPanel );
	}
	
	@Override
	public HandlerRegistration addEditHyperlinkClickHandler( ClickHandler clickHandler ) {
		editAnchor.setVisible( true );
		return editAnchor.addClickHandler( clickHandler );
	}
	
	@Override
	public PratilipiData getPratilipiData() {
		return pratilipiData;
	}
	
	@Override
	public void setPratilipiData( PratilipiData pratilipiData ) {
		this.pratilipiData = pratilipiData;
		AuthorData authorData = pratilipiData.getAuthorData();
		
		coverImageAnchor.setHref( pratilipiData.getPageUrl() );
		coverImage.setUrl( pratilipiData.getCoverImageUrl() );
		coverImage.setTitle( pratilipiData.getTitle() );
		coverImage.setAltText( pratilipiData.getTitle() );
		
		titleAnchor.setHTML( "<strong style='color:black !important;'>" + pratilipiData.getTitle() + "</strong>" );
		titleAnchor.setHref( pratilipiData.getPageUrl() );

		authorAnchor.setHTML( "<i><small style='color:black !important;'>-" + authorData.getName() + "</small></i>" );
		authorAnchor.setHref( authorData.getPageUrl() );
	}

	@Override
	public void focus() {
		focusPanel.setFocus( true );
	}
	
}
