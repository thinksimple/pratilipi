package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
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
	
	private Label formHeader = new Label( "Enter Book Details" );
	private Label isbnLabel = new Label("ISBN ");
	private Label titleLabel = new Label("Title ");
	private Label authorLabel = new Label("Author ");
	private Label publisherLabel = new Label("Publisher ");
	private Label languageLabel = new Label("Language ");
	
	private Label edit = new Label("edit");
	
	private TextBox isbnInput = new TextBox(); 
	private TextBox titleInput = new TextBox();
	private SuggestBox authorInput;
	private SuggestBox publisherInput;
	private SuggestBox languageInput;
	
	private Label coverLabel = new Label("Upload Book Cover");
	private Label contentLabel = new Label("Upload Book");
	private FileUpload bookCover = new FileUpload();
	private FileUpload bookContent = new FileUpload();
	private FormPanel form;
	
	private Button save = new Button();

	//Book id
	private Long bookId;
	
	public BookDataInputViewImpl() {
		form = new FormPanel();
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel isbn = new HorizontalPanel();
		HorizontalPanel title = new HorizontalPanel();
		HorizontalPanel author = new HorizontalPanel();
		HorizontalPanel publisher = new HorizontalPanel();
		HorizontalPanel language = new HorizontalPanel();
		
		//panels to hold uploading elements
		VerticalPanel uploads = new VerticalPanel();
		HorizontalPanel cover = new HorizontalPanel();
		HorizontalPanel content = new HorizontalPanel();
		
		//setting width to labels
		isbnLabel.setWidth("100px");
		titleLabel.setWidth("100px");
		authorLabel.setWidth("100px");
		publisherLabel.setWidth("100px");
		languageLabel.setWidth("100px");
		coverLabel.setWidth("150px");
		contentLabel.setWidth("150px");
		
		//Suggestion container for author suggestion box
		final MultiWordSuggestOracle authorOracle = new MultiWordSuggestOracle();  
		
		//Get list of authors.
		pratilipiService.getAuthorList(new GetAuthorListRequest(), new AsyncCallback<GetAuthorListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(GetAuthorListResponse response) {
				for(AuthorData authorData : response.getAuthorList() )
					authorOracle.add( authorData.getFirstName()+ " " + authorData.getLastName() 
							+ "( " + authorData.getPenName() + " )");
			}});
	      
	    authorInput = new SuggestBox( authorOracle );
	    
	    //suggestion container for publisher suggestion box
	    final MultiWordSuggestOracle publisherOracle = new MultiWordSuggestOracle();  
	    
	    //getting list of publishers
	    pratilipiService.getPublisherList(new GetPublisherListRequest(), new AsyncCallback<GetPublisherListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess(GetPublisherListResponse response) {
				for(PublisherData publisherData : response.getPublisherList())
					publisherOracle.add(publisherData.getName());
			}});
	    
	    //Adding suggestion values to publisher suggestion box
	    publisherInput = new SuggestBox( publisherOracle );
	    
	    //suggestion values for language suggestion Box
	    final MultiWordSuggestOracle langOracle = new MultiWordSuggestOracle();
	    
	    //getting list of languages for suggestion box
	    pratilipiService.getLanguageList(new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(GetLanguageListResponse response) {
				for( LanguageData languageData : response.getLanguageList())
					langOracle.add(languageData.getName());
				
			}});
	    
	    //adding suggestion values to language suggestion box
	    languageInput = new SuggestBox( langOracle );
		
	    //Book Edit link
	    edit.setVisible(false);
	    edit.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				save.setVisible(true);
				edit.setVisible(false);
				form.setVisible(false);
				setWritable();
				
			}});
	    
	    //adding upload form elements.
	    cover.add(coverLabel);
	    cover.add(bookCover);
	    content.add(contentLabel);
	    content.add(bookContent);
	    uploads.add(cover);
	    uploads.add(content);
	    form.add(uploads);
	    form.setVisible(false);
	    
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
		panel.add(edit);
		panel.add(form);
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
		book.setTitle( titleInput.getText() );
		book.setTitle( authorInput.getText() );  //need to find a way to get AUTHOR_ID of required author from database
		book.setTitle( publisherInput.getText() );  //need to find a way to get PUBLISHER_ID of required author from database
		book.setTitle( languageInput.getText() );
		return book;
	}
	
	public Long getBookId(){
		return bookId;
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
	public void setReabableOnly() {

		isbnInput.setEnabled(false);
		titleInput.setEnabled(false);
		authorInput.setEnabled(false);
		publisherInput.setEnabled(false);
		languageInput.setEnabled(false);
	}
	
	@Override
	public void setWritable(){
		isbnInput.setEnabled(true);
		titleInput.setEnabled(true);
		authorInput.setEnabled(true);
		publisherInput.setEnabled(true);
		languageInput.setEnabled(true);
	}
	
	public void setEditLink(Button save, Long bookId){
		this.save = save;
		this.bookId = bookId;
		edit.setVisible(true);
		form.setVisible(true);
	}
	
}
