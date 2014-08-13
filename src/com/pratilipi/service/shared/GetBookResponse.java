package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.BookData;

public class GetBookResponse implements IsSerializable {
	
	private BookData bookData;
	
	@SuppressWarnings("unused")
	private GetBookResponse(){}
	
	public GetBookResponse(BookData bookData){
		this.bookData = bookData;
	}

	public BookData getBook(){
		return this.bookData;
	}
}
