package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Book;

@PersistenceCapable( table = "BOOK" )
public class BookEntity implements Book {

	@PrimaryKey
	@Persistent( column = "BOOK_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	
	@Persistent( column = "AUTHOR_ID" )
	private Long authorId;
	
	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Persistent( column = "PUBLICATION_DATE" )
	private Date publicationDate;

	@Persistent( column = "LISTING_DATE" )
	private Date listingDate;

	
	@Persistent( column = "WORD_COUNT" )
	private Long wordCount;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	@Override
	public Long getAuthorId() {
		return authorId;
	}

	@Override
	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	@Override
	public Long getPublisherId() {
		return publisherId;
	}

	@Override
	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}

	@Override
	public Date getPublicationDate() {
		return publicationDate;
	}

	@Override
	public void setPublicationDate( Date publicationDate ) {
		this.publicationDate = publicationDate;
	}

	@Override
	public Date getListingDate() {
		return listingDate;
	}

	@Override
	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}

	@Override
	public Long getWordCount() {
		return wordCount;
	}

	@Override
	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
	}
	
}
