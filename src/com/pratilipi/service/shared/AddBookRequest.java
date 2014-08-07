package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class AddBookRequest implements IsSerializable {

	private BookData bookData;


	@SuppressWarnings("unused")
	private AddBookRequest() {}
	
	public AddBookRequest( BookData bookData ) {
		this.bookData = bookData;
	}
	
	
	public BookData getBook() {
		return this.bookData;
	}

}
