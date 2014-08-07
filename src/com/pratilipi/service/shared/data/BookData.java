package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BookData implements IsSerializable {

	private Long id;
	
	private String title;
	
	private Long languageId;

	private String languageName;
	
	
	private Long authorId;
	
	private String authorName;
	
	private Long publisherId;

	private String publisherName;
	
	private Date publicationDate;

	private Date listingDate;

	
	private Long wordCount;

	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName( String languageName ) {
		this.languageName = languageName;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName( String authorName ) {
		this.authorName = authorName;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName( String publisherName ) {
		this.publisherName = publisherName;
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
