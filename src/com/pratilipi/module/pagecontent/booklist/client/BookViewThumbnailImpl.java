package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.service.shared.data.BookData;

public class BookViewThumbnailImpl extends BookView {

	private BookData bookData = new BookData();
	private Image image = new Image();
	
	
	public BookViewThumbnailImpl() {
		
	}

	
	@Override
	public void setBookData( BookData bookData ) {
		this.bookData = bookData;
	}


	@Override
	public FlowPanel getThumbnail() {
		
		FlowPanel bookThumbnail = new FlowPanel();
		Anchor anchor = new Anchor();
		
		//set book cover
		image.setUrl( PratilipiHelper.BOOK_COVER_URL + bookData.getId() );
		image.addStyleName("img-responsive");
		anchor.getElement().appendChild(image.getElement());   //add image to the anchor tag
		image.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				final PopupPanel popup = new PopupPanel();
				BookDetailView bookDetail = new BookDetailView(popup, bookData);
				VerticalPanel vPanel = bookDetail.getPopup();
				
				//Close Book detail window using ESCAPE keypress event
				popup.addDomHandler(new KeyDownHandler(){

					@Override
					public void onKeyDown(KeyDownEvent event) {
						if(event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE)
							popup.hide();
					}} , KeyDownEvent.getType());
				
				//set focus on popup panel on mouseOver event. 
				popup.addDomHandler(new MouseOverHandler(){

					@Override
					public void onMouseOver(MouseOverEvent event) {
						// TODO Auto-generated method stub
						popup.getElement().focus();
					}}, MouseOverEvent.getType());
				
				popup.clear();
				popup.setAnimationEnabled(true);
				popup.setGlassEnabled(true);
				popup.setAutoHideEnabled(false);
				popup.setWidth(.7*Window.getClientWidth()+"px");
				popup.setHeight(Window.getClientHeight()+"px");
				popup.add(vPanel);
				popup.center();
				
			}});
		anchor.setStyleName("thumnail");
		anchor.getElement().setAttribute("href", "/book/" + bookData.getId());
		
		Label title = new Label();
		String bookTitle = bookData.getTitle();
		String bookAuthor = bookData.getAuthorName();
		title.getElement().setInnerHTML("<a href=#>"
				+ bookTitle 
				+ "</a> </br> <a href=#>-"
				+ bookAuthor + "</a>");
		title.setStyleName("p-title");
		
		Label tags = new Label();
		String lang = bookData.getLanguageName();
		tags.getElement().setInnerHTML("<a href=#>" 
				+ lang
				//TODO - ADD TAGS ONCE BOOKDATA CLASS MODIFIED.
				+ "</a>, <a href=#>Poem</a>");
		tags.addStyleName("p-tag");
		
		bookThumbnail.addStyleName("col-lg-2");
		bookThumbnail.addStyleName("col-sm-4");
		bookThumbnail.addStyleName("col-xs-6");
		bookThumbnail.addStyleName("p-bookcover");
		
		bookThumbnail.add(anchor);
		bookThumbnail.add(tags);
		bookThumbnail.add(title);
		
		return bookThumbnail;
	}
	
	
}
