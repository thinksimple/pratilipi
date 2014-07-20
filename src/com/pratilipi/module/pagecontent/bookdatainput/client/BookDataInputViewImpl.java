package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.pratilipi.service.shared.data.BookData;

public class BookDataInputViewImpl extends BookDataInputView {
	
	private TextBox isbnInput = new TextBox();
	private TextBox titleInput = new TextBox();

	public BookDataInputViewImpl() {
		Panel panel = new HorizontalPanel();
		panel.add( isbnInput );
		panel.add( titleInput );
		
		initWidget( panel );
	}
	
	public void setBook( BookData book ) {
		// TODO: Implementation
	}
	
	public BookData getBook() {
		BookData book = new BookData();
		book.setIsbn( isbnInput.getText() );
		book.setTitle( titleInput.getText() );
		return book;
	}
	
}
