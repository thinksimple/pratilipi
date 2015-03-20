package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Language;

@SuppressWarnings("serial")
@PersistenceCapable( table = "LANGUAGE" )
public class LanguageEntity implements Language {

	@PrimaryKey
	@Persistent( column = "LANGUAGE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "NAME_EN" )
	private String nameEn;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "HIDDEN" )
	private Boolean hidden;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}
	
	@Override
	public String getNameEn() {
		return nameEn;
	}

	@Override
	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}
	
	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	@Override
	public Boolean getHidden() {
		return hidden;
	}

	@Override
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
