package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.BookData;

public abstract class BookDataInputView extends Composite {
	
	public abstract void setBook( BookData book );
	
	public abstract BookData getBook();
	
	public abstract String getIsbn();
	
	public abstract String getTitle();
	
	public abstract String getAuthor();
	
	public abstract String getPublisher();
	
	public abstract String getLanguage();
	
	public abstract void setIsbnErrorStyle();
	
	public abstract void setTitleErrorStyle();
	
	public abstract void setAuthorErrorStyle();
	
	public abstract void setPublisherErrorStyle();
	
	public abstract void setLanguageErrorStyle();
}
