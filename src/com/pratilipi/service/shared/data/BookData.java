package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BookData implements IsSerializable {

	private String isbn;
	
	private String title;
	
	private String language;

	
	private Long authorId;
	
	private Long publisherId;

	private Date publicationDate;

	private Date listingDate;

	
	private Long wordCount;

	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn( String isbn ) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage( String language ) {
		this.language = language;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate( Date publicationDate ) {
		this.publicationDate = publicationDate;
	}

	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}

	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
	}
	
}
