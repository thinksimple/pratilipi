package com.pratilipi.commons.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.service.shared.data.BookData;

public class BookViewDetailImpl extends BookView {

	private final Panel panel = new FlowPanel();
	private final Panel thumbnailPanel = new FlowPanel();
	private final Panel detailPanel = new FlowPanel();

	private final Anchor bookImageAnchor = new Anchor();
	private final Image bookImage = new Image();
	
	private final HeadingElement titleElement = Document.get().createHElement( 3 );
	private final Anchor titleAnchor = new Anchor();
	
	private final HeadingElement authorElement = Document.get().createHElement( 4 );
	private final Anchor authorAnchor = new Anchor();
	
	private final HTML summaryHtml = new HTML();
	
	
	public BookViewDetailImpl() {
		panel.add( thumbnailPanel );
		panel.add( detailPanel );
		
		thumbnailPanel.add( bookImageAnchor );
		bookImageAnchor.getElement().appendChild( bookImage.getElement() );

		detailPanel.getElement().appendChild( titleElement );
		titleElement.appendChild( titleAnchor.getElement() );
		
		detailPanel.getElement().appendChild( authorElement );
		authorElement.appendChild( authorAnchor.getElement() );
		
		detailPanel.add( summaryHtml );

		
		panel.setStyleName( "row" );
		thumbnailPanel.setStyleName( "col-sm-2" );
		detailPanel.setStyleName( "col-sm-10" );
		
		bookImage.setStyleName( "img-responsive img-thumbnail" );
		
		
		initWidget( panel );
	}

	
	@Override
	public void setBookData( BookData bookData ) {
		bookImageAnchor.setHref( PratilipiHelper.URL_BOOK_PAGE + bookData.getId() );
		bookImage.setUrl( PratilipiHelper.URL_BOOK_COVER + bookData.getId() );

		titleAnchor.setText( bookData.getTitle() );
		titleAnchor.setHref( PratilipiHelper.URL_BOOK_PAGE + bookData.getId() );

		authorAnchor.setText( bookData.getAuthorName() );
		authorAnchor.setHref( PratilipiHelper.URL_AUTHOR_PAGE + bookData.getAuthorId() );
		
		summaryHtml.setHTML( bookData.getSummary() );
	}

}
