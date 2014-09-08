package com.pratilipi.commons.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiViewDetailImpl extends PratilipiView {

	private final FocusPanel focusPanel = new FocusPanel();
	private final Panel panel = new FlowPanel();
	private final Panel thumbnailPanel = new FlowPanel();
	private final Panel detailPanel = new FlowPanel();

	private final Anchor coverImageAnchor = new Anchor();
	private final Image coverImage = new Image();
	
	private final HeadingElement titleElement = Document.get().createHElement( 3 );
	private final Anchor titleAnchor = new Anchor();
	
	private final HeadingElement authorElement = Document.get().createHElement( 4 );
	private final Anchor authorAnchor = new Anchor();
	
	private final SpanElement classicsLabel = Document.get().createSpanElement();
	private final Anchor editAnchor = new Anchor();
	
	private final HTML summaryHtml = new HTML();
	
	
	private PratilipiData pratilipiData;

	
	public PratilipiViewDetailImpl() {
		classicsLabel.setInnerText( "CLASSICS" );
		classicsLabel.setAttribute( "class", "label label-default" );
		editAnchor.setText( "Edit" );
		editAnchor.setVisible( false );

		
		// Composing the widget
		focusPanel.add( panel );
		
		panel.add( thumbnailPanel );
		panel.add( detailPanel );
		
		thumbnailPanel.add( coverImageAnchor );
		coverImageAnchor.getElement().appendChild( coverImage.getElement() );

		detailPanel.getElement().appendChild( titleElement );
		titleElement.appendChild( titleAnchor.getElement() );
		
		detailPanel.getElement().appendChild( authorElement );
		authorElement.appendChild( authorAnchor.getElement() );
		
		detailPanel.getElement().appendChild( classicsLabel );

		detailPanel.add( editAnchor );
		
		detailPanel.add( summaryHtml );

		
		// Setting required style classes
		panel.setStyleName( "row" );
		thumbnailPanel.setStyleName( "col-sm-2" );
		detailPanel.setStyleName( "col-sm-10" );
		
		coverImage.setStyleName( "img-responsive img-thumbnail" );
		
		
		initWidget( focusPanel );
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
		
		PratilipiType pratilipiType = pratilipiData.getType();
		
		coverImageAnchor.setHref( pratilipiType.getPageUrl() + pratilipiData.getId() );
		coverImage.setUrl( pratilipiType.getCoverImageUrl() + pratilipiData.getId() );

		titleAnchor.setText( pratilipiData.getTitle() );
		titleAnchor.setHref( pratilipiType.getPageUrl() + pratilipiData.getId() );

		authorAnchor.setText( pratilipiData.getAuthorName() );
		authorAnchor.setHref( PratilipiHelper.URL_AUTHOR_PAGE + pratilipiData.getAuthorId() );
		
		if( pratilipiData.isPublicDomain() )
			classicsLabel.removeAttribute( "style" );
		else
			classicsLabel.setAttribute( "style", "display:none" );
		
		summaryHtml.setHTML( pratilipiData.getSummary() );
	}

	@Override
	public void focus() {
		focusPanel.setFocus( true );
	}
	
}
