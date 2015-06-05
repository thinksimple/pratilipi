package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EventData implements IsSerializable {
	
	private Long id;
	
	private String eventBannerUrl;
	private String eventBannerUploadUrl;

	private String name;
	private boolean hasName;

	private String nameEn;
	private boolean hasNameEn;

	private Date startDate;
	private Date endDate;
	private Date creationDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getEventBannerUrl() {
		return eventBannerUrl;
	}

	public void setEventBannerUrl( String eventBannerUrl ) {
		this.eventBannerUrl = eventBannerUrl;
	}

	public String getEventBannerUploadUrl() {
		return eventBannerUploadUrl;
	}

	public void setEventBannerUploadUrl( String eventBannerUploadUrl ) {
		this.eventBannerUploadUrl = eventBannerUploadUrl;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
		this.hasName = true;
	}

	public boolean hasName() {
		return hasName;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
		this.hasNameEn = true;
	}

	public boolean hasNameEn() {
		return hasNameEn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate( Date endDate ) {
		this.endDate = endDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}
