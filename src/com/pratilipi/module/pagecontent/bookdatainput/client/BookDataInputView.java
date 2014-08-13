package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.BookData;

public abstract class BookDataInputView extends Composite {
	
	//getters and setter for all form elements.
	public abstract void setBook( BookData book );
	
	public abstract BookData getBook();
	
	public abstract Long getBookId();
	
	public abstract void setBookId(Long bookId);
	
	public abstract String getIsbn();
	
	public abstract void setIsbn(String isbn);
	
	public abstract String getTitle();
	
	public abstract void setTtile(String title);
	
	public abstract String getAuthor();
	
	public abstract void setAuthor(String author);
	
	public abstract String getPublisher();
	
	public abstract void setPublisher(String publisher);
	
	public abstract String getLanguage();
	
	public abstract void setLanguage(String lang);
	
	//Error styling functions
	public abstract void setIsbnErrorStyle();
	
	public abstract void setTitleErrorStyle();
	
	public abstract void setAuthorErrorStyle();
	
	public abstract void setPublisherErrorStyle();
	
	public abstract void setLanguageErrorStyle();
	
	//Accept styling function
	public abstract void setIsbnAcceptStyle();
	
	public abstract void setTitleAcceptStyle();
	
	public abstract void setAuthorAcceptStyle();
	
	public abstract void setPublisherAcceptStyle();
	
	public abstract void setLanguageAcceptStyle();
	
	//Set Form
	public abstract void setForm();
}
