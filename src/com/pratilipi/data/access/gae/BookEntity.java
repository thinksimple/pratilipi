package com.pratilipi.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.pratilipi.data.transfer.Book;

@SuppressWarnings("serial")
@PersistenceCapable
public class BookEntity extends PratilipiEntity implements Book {

	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;


	@Override
	public Long getPublisherId() {
		return publisherId;
	}

	@Override
	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}

}
