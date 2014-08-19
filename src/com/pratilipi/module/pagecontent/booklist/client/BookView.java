package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.pratilipi.service.shared.data.BookData;

public abstract class BookView extends Composite {

	public abstract void setBookData( BookData book );
	
	public abstract FlowPanel getThumbnail();

}
