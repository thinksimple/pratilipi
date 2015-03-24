package com.pratilipi.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.LanguageData;

@SuppressWarnings("serial")
public class PratilipiData implements Serializable {

	private Long id;
	
	private PratilipiType type;
	private boolean hasType;

	private String pageUrl;
	private String pageUrlAlias;
	private String coverImageUrl;
	private String readerPageUrl;
	private String writerPageUrl;
	
	private String title;
	private boolean hasTitle;
	
	private String titleEn;
	private boolean hasTitleEn;
	
	private Long languageId;
	private boolean hasLanguageId;

	private LanguageData languageData;

	
	private Long authorId;
	private boolean hasAuthorId;
	
	private AuthorData authorData;

	
	private Long publicationYear;
	private boolean hasPublicationYear;
	
	private Date listingDate;
	private Date LastUpdated;

	
	private String summary;
	private boolean hasSummary;
	
	private String index;
	private boolean hasIndex;

	private Long wordCount;
	private boolean hasWordCount;
	
	private Integer pageCount;
	private boolean hasPageCount;

	private PratilipiContentType contentType;
	private boolean hasContentType;
	
	private PratilipiState state;
	private boolean hasState;
	
	private Double relevance;

	
	public PratilipiData() {}
	
	public PratilipiData( Long id ) {
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public PratilipiType getType(){
		return this.type;
	}
	
	public void setType( PratilipiType type ) {
		this.type = type;
		this.hasType = true;
	}
	
	public boolean hasType() {
		return hasType;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getPageUrlAlias() {
		return pageUrlAlias;
	}

	public void setPageUrlAlias( String pageUrlAlias ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl( String coverImageUrl ) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getReaderPageUrl() {
		return readerPageUrl;
	}

	public void setReaderPageUrl( String readerPageUrl ) {
		this.readerPageUrl = readerPageUrl;
	}
	
	public String getWriterPageUrl(){
		return this.writerPageUrl;
	}
	
	public void setWriterPageUrl( String writerPageUrl ){
		this.writerPageUrl = writerPageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	}
	
	public boolean hasTitle() {
		return hasTitle;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
		this.hasTitleEn = true;
	}
	
	public boolean hasTitleEn() {
		return hasTitleEn;
	}

	
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}

	public boolean hasLanguageId() {
		return hasLanguageId;
	}
	
	public LanguageData getLanguageData() {
		return languageData;
	}

	public void setLanguageData( LanguageData languageData ) {
		this.languageData = languageData;
	}
	

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
		this.hasAuthorId = true;
	}

	public boolean hasAuthorId() {
		return hasAuthorId;
	}
	
	public AuthorData getAuthorData() {
		return authorData;
	}

	public void setAuthorData( AuthorData authorData ) {
		this.authorData = authorData;
	}
	
	
	public Long getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear( Long publicationYear ) {
		this.publicationYear = publicationYear;
		this.hasPublicationYear = true;
	}

	public boolean hasPublicationYear() {
		return hasPublicationYear;
	}
	
	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}

	public Date getLastUpdated(){
		return this.LastUpdated;
	}
	
	public void setLastUpdated( Date lastUpdated ){
		this.LastUpdated = lastUpdated;
	}

	public String getSummary() {
		return summary;
	}
	
	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}

	public boolean hasSummary() {
		return hasSummary;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex( String index ) {
		this.index = index;
		this.hasIndex = true;
	}

	public boolean hasIndex() {
		return hasIndex;
	}

	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
		this.hasWordCount = true;
	}
	
	public boolean hasWordCount() {
		return hasWordCount;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount( Integer pageCount ) {
		this.pageCount = pageCount;
		this.hasPageCount = true;
	}
	
	public boolean hasPageCount() {
		return hasPageCount;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public void setContentType( PratilipiContentType contentType ){
		this.contentType = contentType;
		this.hasContentType = true;
	}
	
	public boolean hasContentType() {
		return hasContentType;
	}

	public PratilipiState getState() {
		return state;
	}
	
	public void setState( PratilipiState state ) {
		this.state = state;
		this.hasState = true;
	}
	
	public boolean hasState() {
		return hasState;
	}
	
	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance( Double relevance ) {
		this.relevance = relevance;
	}
	
}
