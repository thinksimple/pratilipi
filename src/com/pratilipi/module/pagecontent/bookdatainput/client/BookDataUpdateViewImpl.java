package com.pratilipi.module.pagecontent.bookdatainput.client;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BookDataUpdateViewImpl extends BookDataUpdateView {
	
	BookDataInputView bookDataInput = new BookDataInputViewImpl();
	
	private Label coverLabel = new Label("Upload Book Cover");
	private Label contentLabel = new Label("Upload Book");
	private FileUpload bookCover = new FileUpload();
	private FileUpload bookContent = new FileUpload();
	private FormPanel form;

	//default constructor
	@SuppressWarnings("unused")
	private BookDataUpdateViewImpl(){	}
	
	public BookDataUpdateViewImpl(Long bookId){
		VerticalPanel vPanel = new VerticalPanel();
		form = new FormPanel();
		
		//panels to hold uploading elements
		VerticalPanel upload = new VerticalPanel();
		HorizontalPanel cover = new HorizontalPanel();
		HorizontalPanel content = new HorizontalPanel();
		
		//setting width to labels
		coverLabel.setWidth("150px");
		contentLabel.setWidth("150px");
		
			    
	    //adding upload form elements.
	    cover.add(coverLabel);
	    cover.add(bookCover);
	    content.add(contentLabel);
	    content.add(bookContent);
	    upload.add(cover);
	    upload.add(content);
	    form.add(upload);
	    
	    //adding horizontal panels to vertical panel
	    vPanel.add(form);
		vPanel.setSpacing(10);
		
		initWidget(vPanel);
		
	}

}
