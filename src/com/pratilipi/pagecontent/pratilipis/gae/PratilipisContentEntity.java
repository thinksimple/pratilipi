package com.pratilipi.pagecontent.pratilipis.gae;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class PratilipisContentEntity extends PageContentEntity
		implements PratilipisContent {
	
	@Persistent( column = "X_STR_0" )
	private String title;

	@Persistent( column = "X_LIST_LONG_0" )
	private List<Long> pratilipiIdList;
	
	@Persistent( column = "X_ENUM_0" )
	private PratilipiType pratilipiType;

	@Persistent( column = "X_LONG_0" )
	private Long languageId;
	
	@Persistent( column = "X_LONG_1" )
	private Long authorId;
	
	@Persistent( column = "X_ENUM_1" )
	private PratilipiState pratilipiState;


	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}
	
	@Override
	public List<Long> getPratilipiIdList() {
		return pratilipiIdList;
	}

	@Override
	public void setPratilipiIdList( List<Long> pratilipiIdList ) {
		this.pratilipiIdList = pratilipiIdList;
	}
	
	@Override
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	@Override
	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}
	
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
	public PratilipiState getPratilipiState() {
		return pratilipiState;
	}

	@Override
	public void setPratilipiState( PratilipiState pratilipiState ) {
		this.pratilipiState = pratilipiState;
	}

	@Deprecated
	@Override
	public PratilipiFilter toFilter() {
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( pratilipiType );
		pratilipiFilter.setLanguageId( languageId );
		pratilipiFilter.setState( pratilipiState );
		return pratilipiFilter;
	}
	
}
