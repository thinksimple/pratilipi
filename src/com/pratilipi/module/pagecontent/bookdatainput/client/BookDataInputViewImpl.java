package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PublisherData;

public class BookDataInputViewImpl extends BookDataInputView {
	
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	VerticalPanel panel;
	private Label formHeader = new Label( "Enter Book Details" );
	private Label isbnLabel = new Label("ISBN ");
	private Label titleLabel = new Label("Title ");
	private Label authorLabel = new Label("Author ");
	private Label publisherLabel = new Label("Publisher ");
	private Label languageLabel = new Label("Language ");
	
	private TextBox isbnInput = new TextBox(); 
	private TextBox titleInput = new TextBox();
	private ListBox authorInput = new ListBox();
	private ListBox publisherInput = new ListBox();;
	private ListBox languageInput = new ListBox();;

	//Book id
	private Long bookId;
	
	public BookDataInputViewImpl() {
		panel = new VerticalPanel();
		HorizontalPanel isbn = new HorizontalPanel();
		HorizontalPanel title = new HorizontalPanel();
		HorizontalPanel author = new HorizontalPanel();
		HorizontalPanel publisher = new HorizontalPanel();
		HorizontalPanel language = new HorizontalPanel();
		
		
		//setting width to elements
		isbnLabel.setWidth("100px");
		isbnInput.setWidth("100px");
		titleLabel.setWidth("100px");
		titleInput.setWidth("100px");
		authorLabel.setWidth("100px");
		authorInput.setWidth("105px");
		publisherLabel.setWidth("100px");
		publisherInput.setWidth("105px");
		languageLabel.setWidth("100px");
		languageInput.setWidth("105px");
		
		//Get list of authors.
		pratilipiService.getAuthorList(new GetAuthorListRequest(), new AsyncCallback<GetAuthorListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(GetAuthorListResponse response) {
				for(AuthorData authorData : response.getAuthorList() )
					//authorOracle.add( authorData.getFirstName()+ " " + authorData.getLastName());
					authorInput.addItem(authorData.getFirstName()+ " " + authorData.getLastName(), authorData.getId().toString());
				
			}});
	
	    //getting list of publishers
	    pratilipiService.getPublisherList(new GetPublisherListRequest(), new AsyncCallback<GetPublisherListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess(GetPublisherListResponse response) {
				for(PublisherData publisherData : response.getPublisherList())
					publisherInput.addItem(publisherData.getName(), publisherData.getId().toString());
			}});
	    
	    //getting list of languages for suggestion box
	    pratilipiService.getLanguageList(new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(GetLanguageListResponse response) {
				for( LanguageData languageData : response.getLanguageList())
					languageInput.addItem(languageData.getName(), languageData.getId().toString());
				
			}});
	   
	    //adding label and input boxes to horizontal panels.
	    isbn.add(isbnLabel);
	    isbn.add(isbnInput);
	    title.add(titleLabel);
	    title.add(titleInput);
	    author.add(authorLabel);
	    author.add(authorInput);
	    publisher.add(publisherLabel);
	    publisher.add(publisherInput);
	    language.add(languageLabel);
	    language.add(languageInput);
	    
	    //adding horizontal panels to vertical panel
	    panel.add(formHeader);
		panel.add( isbn );
		panel.add( title );
		panel.add( author );
		panel.add( publisher );
		panel.add( language );
		
		
		panel.setSpacing(10);
		
		//form.add(panel);
		
		initWidget( panel );
	}
	
	public void setBook( BookData book ) {
		// TODO: Implementation
	}
	
	public BookData getBook() {
		BookData book = new BookData();
//		book.setIsbn( isbnInput.getText() );
		book.setId(bookId);
		book.setTitle( titleInput.getText() );
		book.setAuthorName( authorInput.getItemText(authorInput.getSelectedIndex()));  //need to find a way to get AUTHOR_ID of required author from database
		book.setPublisherName(publisherInput.getItemText(publisherInput.getSelectedIndex()));  //need to find a way to get PUBLISHER_ID of required author from database
		book.setLanguageName(languageInput.getItemText(languageInput.getSelectedIndex()));
		book.setAuthorId(Long.valueOf(authorInput.getValue(authorInput.getSelectedIndex())));
		book.setPublisherId(Long.valueOf(publisherInput.getValue(publisherInput.getSelectedIndex())));
		book.setLanguageId(Long.valueOf(languageInput.getValue(languageInput.getSelectedIndex())));
		
		return book;
	}
	
	public Long getBookId(){
		return bookId;
	}
	
	public void setBookId(Long bookId){
		this.bookId = bookId;
	}
	
	public String getIsbn(){
		return isbnInput.getText();
	}
	
	@Override
	public void setIsbn(String isbn) {
		this.isbnInput.setText(isbn);
	}
	
	public String getTitle(){
		return titleInput.getText();
	}
	
	@Override
	public void setTtile(String title) {
		this.titleInput.setText(title);
	}
	
	public String getAuthor(){
		return authorInput.getItemText(authorInput.getSelectedIndex());
	}
	
	@Override
	public void setAuthor(String author) {
		//Todo
	}
	
	public String getPublisher(){
		return publisherInput.getItemText(publisherInput.getSelectedIndex());
	}

	@Override
	public void setPublisher(String publisher) {
		//TODO
	}
	public String getLanguage(){
		return languageInput.getItemText(languageInput.getSelectedIndex());
	}

	@Override
	public void setLanguage(String lang) {
		//TODO
	}
	
	//Error styling.
	public void setIsbnErrorStyle(){
		isbnInput.getElement().setAttribute("style", "border:1px solid #FF0000");
	}
	
	public void setTitleErrorStyle(){
		titleInput.getElement().setAttribute("style", "border:1px solid #FF0000");
	}
	
	public void setAuthorErrorStyle(){
		authorInput.getElement().setAttribute("style", "border:1px solid #FF0000");
	}
	
	public void setPublisherErrorStyle(){
		publisherInput.getElement().setAttribute("style", "border:1px solid #FF0000");
	}
	
	public void setLanguageErrorStyle(){
		languageInput.getElement().setAttribute("style", "border:1px solid #FF0000");
	}
	
	//Accept Styling
	public void setIsbnAcceptStyle(){
		isbnInput.getElement().setAttribute("style", "border:1px solid #000000");
	}
	
	public void setTitleAcceptStyle(){
		titleInput.getElement().setAttribute("style", "border:1px solid #000000");
	}
	
	public void setAuthorAcceptStyle(){
		authorInput.getElement().setAttribute("style", "border:1px solid #000000");
	}
	
	public void setPublisherAcceptStyle(){
		publisherInput.getElement().setAttribute("style", "border:1px solid #000000");
	}
	
	public void setLanguageAcceptStyle(){
		languageInput.getElement().setAttribute("style", "border:1px solid #000000");
	}

	@Override
	public void setForm() {
		// TODO Auto-generated method stub
		
	}
	
}
