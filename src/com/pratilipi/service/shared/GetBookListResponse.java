package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class GetBookListResponse implements IsSerializable {

	private List<BookData> bookList;
	
	
	@SuppressWarnings("unused")
	private GetBookListResponse() {}
	
	public GetBookListResponse( List<BookData> bookList ) {
		this.bookList = bookList;
	}
	
	
	public List<BookData> getBookList() {
		return this.bookList;
	}
	
}
