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
	private String DESCRIPTION;
	private List<Long> PRATILIPI_IDS;
	
	@Deprecated
	private Date START_DATE;
	@Deprecated
	private Date END_DATE;
	
	private Date CREATION_DATE;
	private Date LAST_UPDATED;

	
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
		if( LANGUAGE == null ) {
			if( LANGUAGE_ID == 5130467284090880L || LANGUAGE_ID == 5750790484393984L )
				LANGUAGE = Language.HINDI;
			else if( LANGUAGE_ID == 5965057007550464L || LANGUAGE_ID == 5746055551385600L )
				LANGUAGE = Language.GUJARATI;
			else if( LANGUAGE_ID == 6319546696728576L || LANGUAGE_ID == 5719238044024832L )
				LANGUAGE = Language.TAMIL;
			else if( LANGUAGE_ID == 5173513199550464L )
				LANGUAGE = Language.MARATHI;
			else if( LANGUAGE_ID == 5752669171875840L )
				LANGUAGE = Language.MALAYALAM;
			else if( LANGUAGE_ID == 6235363433512960L )
				LANGUAGE = Language.BENGALI;
			else if( LANGUAGE_ID == 6213615354904576L || LANGUAGE_ID == 5688424874901504L )
				LANGUAGE = Language.ENGLISH;
		}
		return LANGUAGE;
	}

	@Override
	public void setLanguage( Language language ) {
		this.LANGUAGE = language;
	}


	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public void setDescription( String description ) {
		this.DESCRIPTION = description;
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

	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED == null ? CREATION_DATE : LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
