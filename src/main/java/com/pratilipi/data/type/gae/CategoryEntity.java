package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.common.type.CategoryType;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.Category;

@SuppressWarnings("serial")
@PersistenceCapable( table = "CATEGORY" )
public class CategoryEntity implements Category {

	@Deprecated
	@PrimaryKey
	@Persistent( column = "CATEGORY_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Deprecated
	@Persistent( column = "SERIAL_NUMBER" )
	private int serialNumber;
	
	@Persistent( column = "CATEGORY_NAME")
	private String name;
	
	@Deprecated
	@Persistent( column = "CATEGORY_PLURAL" )
	private String plural;
	
	@Deprecated
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;
	
	@Deprecated
	@Persistent( column = "CATEGORY_TYPE" )
	private CategoryType type;
	
	@Deprecated
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Deprecated
	@Persistent( column = "HIDDEN" )
	private Boolean hidden;
	
	@NotPersistent
	private PratilipiFilter pratilipiFilter;

	
	public CategoryEntity() {}
	
	public CategoryEntity( Long id ) {
		this.id = id;
	}
	
	public CategoryEntity( String name, PratilipiFilter pratilipiFilter ) {
		this.name = name;
		this.pratilipiFilter = pratilipiFilter;
	}

	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public int getSerialNumber(){
		return serialNumber;
	}
	
	@Override
	public void setSerialNumber( int serialNumber ){
		this.serialNumber = serialNumber;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName( String categoryName ) {
		this.name = categoryName;
	}
	
	@Override
	public String getPlural() {
		return plural;
	}

	@Override
	public void setPlural( String plural ) {
		this.plural = plural;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLangugeId(Long languageId) {
		this.languageId = languageId;
	}

	@Override
	public CategoryType getType() {
		return type;
	}

	@Override
	public void setType(CategoryType type) {
		this.type = type;
	}

	@Override
	public Date getCreationData() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public Boolean isHidden(){
		return hidden;
	}
	
	@Override
	public void setHidden( Boolean hidden ){
		this.hidden = hidden;
	}

	@Override
	public PratilipiFilter getPratilipiFilter() {
		return pratilipiFilter;
	}
}
