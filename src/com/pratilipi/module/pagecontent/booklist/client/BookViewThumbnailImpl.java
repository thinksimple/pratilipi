package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.service.shared.data.BookData;

public class BookViewThumbnailImpl extends BookView {

	private Label isbnLabel = new Label();
	private Label titleLabel = new Label();
	
	
	public BookViewThumbnailImpl() {
		Panel panel = new HorizontalPanel();
		panel.add( new Label( "Thumbnail View : " ) );
		panel.add( isbnLabel );
		panel.add( titleLabel );
		
		initWidget( panel );
	}

	
	@Override
	public void setBookData( BookData bookData ) {
		this.isbnLabel.setText( bookData.getIsbn() );
		this.titleLabel.setText( bookData.getTitle() );
	}

}
