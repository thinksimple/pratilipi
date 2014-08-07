package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.BookData;

public class BookViewThumbnailImpl extends BookView {

	private Label isbnLabel = new Label();
	private Label titleLabel = new Label();
	private BookData bookData = new BookData();
	
	
	public BookViewThumbnailImpl() {
		Panel panel = new HorizontalPanel();
		panel.add( new Label( "Thumbnail View : " ) );
		titleLabel.getElement().setPropertyString("cursor", "pointer");
		panel.add( isbnLabel );
		panel.add( titleLabel );
		
		FocusPanel wrapper = new FocusPanel();
		wrapper.add(panel);
		wrapper.addClickHandler(new ClickHandler(){

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
		
		initWidget( wrapper );
	}

	
	@Override
	public void setBookData( BookData bookData ) {
//		this.isbnLabel.setText( bookData.getIsbn() );
		this.titleLabel.setText( bookData.getTitle() );
		this.bookData = bookData;
	}
}
