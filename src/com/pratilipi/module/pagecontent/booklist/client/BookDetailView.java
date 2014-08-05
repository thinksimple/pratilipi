package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.BookData;

public class BookDetailView extends Composite {
		
	private Image closeButton;
	private PopupPanel popup = new PopupPanel();
	private BookData bookData = new BookData();
	private VerticalPanel vPanel = new VerticalPanel();
	
	public BookDetailView(PopupPanel popup, BookData bookData){
		this.popup = popup;
		this.bookData = bookData;
	}
	
	//This is for experimental purpose only. Function in below commented section is actual one.
	public VerticalPanel getPopup(){
		
		//Close Button
		HorizontalPanel closePanel = new HorizontalPanel();
		this.closeButton = new Image("/Images/closebutton.jpg");
		closeButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				popup.hide();
				
			}});
		closePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		closePanel.add(this.closeButton);
		this.vPanel.add(closePanel);
		
		//Book Details
		HorizontalPanel bookDetails = new HorizontalPanel();
		VerticalPanel bookCover = new VerticalPanel();
		//bookCover.add(bookData.getCoverPage());
		bookDetails.add(bookCover);
		VerticalPanel details = new VerticalPanel(); //holds book details
		Label title = new Label("Title  : "+bookData.getTitle());	//book title
		details.add(title);
		Label author = new Label("Author : "); //book author
		details.add(author);
		Label publisher = new Label("Publisher : ");  //Publisher
		details.add(publisher);
		bookDetails.add(details);
		vPanel.add(bookDetails);
		
		//book Reviews
		VerticalPanel reviews = new VerticalPanel();
		Label review = new Label("----- Book reviews goes heree  ----");
		reviews.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		reviews.add(review);
		vPanel.add(reviews);
		
		//Comments
		VerticalPanel comments = new VerticalPanel();
		Label comment = new Label("---------  COMMENTS GOES HERE   -----------");
		comments.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		comments.add(comment);
		vPanel.add(comments);
		
		return this.vPanel;
	}
}
