package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.BookData;

public class BookDataInputViewImpl extends BookDataInputView {
	
	private TextBox isbnInput = new TextBox(); 
	private TextBox titleInput = new TextBox();
	private TextBox authorInput = new TextBox();
	private TextBox publisherInput = new TextBox();
	private TextBox languageInput = new TextBox();
	private FileUpload bookCover = new FileUpload();

	public BookDataInputViewImpl() {
		FormPanel form = new FormPanel();
		VerticalPanel panel = new VerticalPanel();
		isbnInput.getElement().setPropertyString("placeholder","Enter ISBN");
		titleInput.getElement().setPropertyString("placeholder","Enter Title");
		authorInput.getElement().setPropertyString("placeholder","Enter Author Name");
		publisherInput.getElement().setPropertyString("placeholder","Enter Publisher Name");
		languageInput.getElement().setPropertyString("placeholder","Enter Book's Language");
		bookCover.getElement().setPropertyString("placeholder", "Select Book Cover Image");
		
		panel.add( isbnInput );
		panel.add( titleInput );
		panel.add( authorInput );
		panel.add( publisherInput );
		panel.add( languageInput );
		panel.add(bookCover);
		panel.setSpacing(10);
		
		form.add(panel);
		
		initWidget( form );
	}
	
	public void setBook( BookData book ) {
		// TODO: Implementation
	}
	
	public BookData getBook() {
		BookData book = new BookData();
		book.setIsbn( isbnInput.getText() );
		book.setTitle( titleInput.getText() );
		book.setTitle( authorInput.getText() );  //need to find a way to get AUTHOR_ID of required author from database
		book.setTitle( publisherInput.getText() );  //need to find a way to get PUBLISHER_ID of required author from database
		book.setTitle( languageInput.getText() );
		return book;
	}
	
	public String getIsbn(){
		return isbnInput.getText();
	}
	
	public String getTitle(){
		return titleInput.getText();
	}
	
	public String getAuthor(){
		return authorInput.getText();
	}
	
	public String getPublisher(){
		return publisherInput.getText();
	}
	
	public String getLanguage(){
		return languageInput.getText();
	}
	
	//Error styling is not working for some reason.
	public void setIsbnErrorStyle(){
		isbnInput.getElement().setPropertyString("border", "1px solid #FF0000");
	}
	
	public void setTitleErrorStyle(){
		titleInput.getElement().setPropertyString("border", "1px solid #FF0000");
	}
	
	public void setAuthorErrorStyle(){
		authorInput.getElement().setPropertyString("border", "1px solid #FF0000");
	}
	
	public void setPublisherErrorStyle(){
		publisherInput.getElement().setPropertyString("border", "1px solid #FF0000");
	}
	
	public void setLanguageErrorStyle(){
		languageInput.getElement().setPropertyString("border", "1px solid #FF0000");
	}
}
