package com.pratilipi.data.type.gae;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Event;

@Cache
@Entity( name = "EVENT" )
public class EventEntity implements Event {

	// Intentionally Leaving all fields UnIndexed
	
	@Id
	private Long EVENT_ID;

	private String NAME;
	private String NAME_EN;

	private Language LANGUAGE;
	@Deprecated
	private Long LANGUAGE_ID;
	private String SUMMARY;
	private List<Long> PRATILIPI_IDS;
	
	private Date CREATION_DATE;

	
	@Override
	public Long getId() {
		return EVENT_ID;
	}
	
	public void setId( Long id ) {
		this.EVENT_ID = id;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setName( String name ) {
		this.NAME = name;
	}

	@Override
	public String getNameEn() {
		return NAME_EN;
	}

	@Override
	public void setNameEn( String nameEn ) {
		this.NAME_EN = nameEn;
	}

	
	@Override
	public Language getLanguage() {
		return LANGUAGE;
	}

	@Override
	public void setLanguage( Language language ) {
		this.LANGUAGE = language;
	}


	@Override
	public String getSummary() {
		return SUMMARY;
	}

	@Override
	public void setSummary( String summary ) {
		this.SUMMARY = summary;
	}

	@Override
	public List<Long> getPratilipiIdList() {
		return PRATILIPI_IDS;
	}
	
	@Override
	public void setPratilipiIdList( List<Long> pratilipiIdList ) {
		this.PRATILIPI_IDS = pratilipiIdList;
	}
	
	
	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

}
