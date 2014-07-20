package com.pratilipi.service.shared;

import java.io.Serializable;
import java.util.List;

import com.pratilipi.service.shared.data.BookData;

@SuppressWarnings("serial")
public class GetBookListResponse implements Serializable {

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
