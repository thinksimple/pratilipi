package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.BookData;

public abstract class BookDataInputView extends Composite {
	
	public abstract void setBook( BookData book );
	
	public abstract BookData getBook();
	
}
