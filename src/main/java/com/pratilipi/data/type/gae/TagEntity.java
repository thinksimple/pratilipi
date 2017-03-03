package com.pratilipi.data.type.gae;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Tag;

@Cache
@Entity( name = "TAG" )
public class TagEntity implements Tag {

	@Id
	private Long TAG_ID;

	
	@Index
	private String HINDI;
	
	@Index
	private String GUJARATI;
	
	@Index
	private String TAMIL;
	
	@Index
	private String MARATHI;
	
	@Index
	private String MALAYALAM;
	
	@Index
	private String BENGALI;
	
	@Index
	private String TELUGU;
	
	@Index
	private String KANNADA;
	
	@Index
	private String ENGLISH;
	
	
	public TagEntity() {}
	
	public TagEntity( Long tagId ) {
		this.TAG_ID = tagId;
	}
	
	
	@Override
	public Long getId() {
		return TAG_ID;
	}

	public void setId( Long id ) {
		this.TAG_ID = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.TAG_ID = key.getId();
	}

	
	@Override
	public String getI18nName( Language language ) {
		switch( language ) {
			case HINDI:
				return HINDI == null ? ENGLISH : HINDI;
			case GUJARATI:
				return GUJARATI == null ? ENGLISH : GUJARATI;
			case TAMIL:
				return TAMIL == null ? ENGLISH : TAMIL;
			case MARATHI:
				return MARATHI == null ? ENGLISH : MARATHI;
			case MALAYALAM:
				return MALAYALAM == null ? ENGLISH : MALAYALAM;
			case BENGALI:
				return BENGALI == null ? ENGLISH : BENGALI;
			case TELUGU:
				return TELUGU == null ? ENGLISH : TELUGU;
			case KANNADA:
				return KANNADA == null ? ENGLISH : KANNADA;
			default: return ENGLISH;
		}
	}
	
	@Override
	public void setI18nName( Language language, String i18nName ) {
		switch( language ) {
			case HINDI:
				HINDI = i18nName; return;
			case GUJARATI:
				GUJARATI = i18nName; return;
			case TAMIL:
				TAMIL = i18nName; return;
			case MARATHI:
				MARATHI = i18nName; return;
			case MALAYALAM:
				MALAYALAM = i18nName; return;
			case BENGALI:
				BENGALI = i18nName; return;
			case TELUGU:
				TELUGU = i18nName; return;
			case KANNADA:
				KANNADA = i18nName; return;
			case ENGLISH:
				ENGLISH = i18nName; return;
		}
	}
	
}
