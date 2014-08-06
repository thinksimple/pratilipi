package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class ValidateInputImpl{
	
	private BookDataInputView book;
	//Regular expression for numbers.
	private String numPattern = new String("^[0-9]+$");
	private RegExp numExp = RegExp.compile(numPattern);

	//Regular expression for string containing characters and whitespace.
	private String charPattern = new String("^[a-bA-B\\s]+$");
	private RegExp charExp = RegExp.compile(charPattern);

	//Regular expression for string containing number, characters and whitespace.
	private String strPattern = new String("^[0-9a-bA-B\\s]+$");
	private RegExp strExp = RegExp.compile(strPattern);

	//container of validation result.
	private Boolean validated = true;

	private MatchResult matcher;

	public ValidateInputImpl(){}

	//overload constructor to validate objects of other classes.
	public ValidateInputImpl(BookDataInputView book){
		this.book = book;
	}
	
	//create similar functions corresponding to object passed in the constructor.
	public boolean validateBook() {
		//validating ISBN textbox
		this.matcher = numExp.exec(book.getIsbn());
		Boolean matchFound = (matcher!=null);
		if(!matchFound){
			book.setIsbnErrorStyle();
			validated = false;
		}
		
		//validating title textbox
		this.matcher = strExp.exec(book.getTitle());
		matchFound = (matcher!=null);
		if(!matchFound){
			book.setTitleErrorStyle();
			validated = false;
		}
		
		//validating author textbox
		this.matcher = charExp.exec(book.getAuthor());
		matchFound = (matcher!=null);
		if(!matchFound){
			book.setAuthorErrorStyle();
			validated = false;
		}
		
		//validating publisher textbox
		this.matcher = charExp.exec(book.getPublisher());
		matchFound = (matcher!=null);
		if(!matchFound){
			book.setPublisherErrorStyle();
			validated = false;
		}
		
		//validating language textbox
		this.matcher = charExp.exec(book.getLanguage());
		matchFound = (matcher!=null);
		if(!matchFound){
			book.setLanguageErrorStyle();
			validated = false;
		}
		return validated;
	}
}

