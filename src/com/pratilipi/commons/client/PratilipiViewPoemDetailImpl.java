package com.pratilipi.commons.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiViewPoemDetailImpl extends PratilipiView {

	private final Panel panel = new FlowPanel();
	private final Panel thumbnailPanel = new FlowPanel();
	private final Panel detailPanel = new FlowPanel();

	private final Anchor poemImageAnchor = new Anchor();
	private final Image poemImage = new Image();
	
	private final HeadingElement titleElement = Document.get().createHElement( 3 );
	private final Anchor titleAnchor = new Anchor();
	
	private final HeadingElement authorElement = Document.get().createHElement( 4 );
	private final Anchor authorAnchor = new Anchor();
	
	private final HTML summaryHtml = new HTML();
	
	
	public PratilipiViewPoemDetailImpl() {
		panel.add( thumbnailPanel );
		panel.add( detailPanel );
		
		thumbnailPanel.add( poemImageAnchor );
		poemImageAnchor.getElement().appendChild( poemImage.getElement() );

		detailPanel.getElement().appendChild( titleElement );
		titleElement.appendChild( titleAnchor.getElement() );
		
		detailPanel.getElement().appendChild( authorElement );
		authorElement.appendChild( authorAnchor.getElement() );
		
		detailPanel.add( summaryHtml );

		
		panel.setStyleName( "row" );
		thumbnailPanel.setStyleName( "col-sm-2" );
		detailPanel.setStyleName( "col-sm-10" );
		
		poemImage.setStyleName( "img-responsive img-thumbnail" );
		
		
		initWidget( panel );
	}

	
	@Override
	public void setPratilipiData( PratilipiData poemData ) {
		poemImageAnchor.setHref( PratilipiHelper.URL_POEM_PAGE + poemData.getId() );
		poemImage.setUrl( PratilipiHelper.URL_POEM_COVER + poemData.getId() );

		titleAnchor.setText( poemData.getTitle() );
		titleAnchor.setHref( PratilipiHelper.URL_POEM_PAGE + poemData.getId() );

		authorAnchor.setText( poemData.getAuthorName() );
		authorAnchor.setHref( PratilipiHelper.URL_AUTHOR_PAGE + poemData.getAuthorId() );
		
		summaryHtml.setHTML( poemData.getSummary() );
	}

}
