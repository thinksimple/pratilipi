package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class GetBookListResponse implements IsSerializable {

	private List<BookData> bookDataList;
	
	
	@SuppressWarnings("unused")
	private GetBookListResponse() {}
	
	public GetBookListResponse( List<BookData> bookDataList ) {
		this.bookDataList = bookDataList;
	}
	
	
	public List<BookData> getBookList() {
		return this.bookDataList;
	}
	
}
