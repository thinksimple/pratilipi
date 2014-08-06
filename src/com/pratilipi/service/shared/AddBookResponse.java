package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class AddBookResponse implements IsSerializable {

	private BookData book;
	
	
	@SuppressWarnings("unused")
	private AddBookResponse() {}
	
	public AddBookResponse( BookData book ) {
		this.book = book;
	}

	
	public BookData getBook() {
		return this.book;
	}
	
}
