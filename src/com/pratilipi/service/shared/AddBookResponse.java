package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class AddBookResponse implements IsSerializable {

	private Long bookId;

	@Deprecated
	private BookData book;
	
	
	@SuppressWarnings("unused")
	private AddBookResponse() {}
	
	public AddBookResponse( Long bookId ) {
		this.bookId = bookId;
	}
	
	@Deprecated
	public AddBookResponse( BookData book ) {
		this.book = book;
	}

	
	public Long getBookId() {
		return this.bookId;
	}
	
	@Deprecated
	public BookData getBook() {
		return this.book;
	}
	
}
