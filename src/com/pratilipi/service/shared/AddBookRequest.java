package com.pratilipi.service.shared;

import java.io.Serializable;

import com.pratilipi.service.shared.data.BookData;

@SuppressWarnings("serial")
public class AddBookRequest implements Serializable {

	private BookData book;


	@SuppressWarnings("unused")
	private AddBookRequest() {}
	
	public AddBookRequest( BookData book ) {
		this.book = book;
	}
	
	
	public BookData getBook() {
		return this.book;
	}

}
