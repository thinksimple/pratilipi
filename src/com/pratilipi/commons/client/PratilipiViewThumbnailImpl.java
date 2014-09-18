package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.commons.server.PratilipiHelper;
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
		colPanel.setStyleName( "col-lg-2 col-md-2 col-sm-3 col-xs-4" );
		panel.getElement().setAttribute( "style", "width:100px; padding-top: 10px; padding-bottom:10px;" );
		thumbnailPanel.getElement().setAttribute( "style", "width:100px; height:160px; overflow: hidden;" );
		infoPanel.getElement().setAttribute( "style",
				"position:absolute; bottom:10px;"
				+ "width:100px; height:60px; overflow: hidden; font-size:12px" );
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
		
		coverImageAnchor.setHref( pratilipiData.getPageUrl() );
		coverImage.setUrl( pratilipiData.getCoverImageUrl() );

		titleAnchor.setText( pratilipiData.getTitle() );
		titleAnchor.setHref( pratilipiData.getPageUrl() );

		authorAnchor.setText( "(" + pratilipiData.getAuthorName() + ")" );
		authorAnchor.setHref( PratilipiHelper.URL_AUTHOR_PAGE + pratilipiData.getAuthorId() );
	}

	@Override
	public void focus() {
		focusPanel.setFocus( true );
	}
	
}
