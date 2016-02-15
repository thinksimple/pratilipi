package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Event;

@PersistenceCapable( table = "EVENT" )
public class EventEntity implements Event {

	private static final long serialVersionUID = 5281080286713072853L;

	
	@PrimaryKey
	@Persistent( column = "EVENT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "NAME_EN" )
	private String nameEn;


	@Persistent( column = "LANGUAGE" )
	private Language language;

	@Deprecated
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	
	@Persistent( column = "SUMMARY" )
	private Text summary;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;


	
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
	public Language getLanguage() {
		return language;
	}

	@Override
	public void setLanguage( Language language ) {
		this.language = language;
	}


	@Override
	public String getSummary() {
		return summary == null ? null : summary.getValue();
	}

	@Override
	public void setSummary( String summary ) {
		this.summary = summary == null ? null : new Text( summary );
	}
	
	@Override
	public Date getCreationDate() {
		return creationDate;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}
