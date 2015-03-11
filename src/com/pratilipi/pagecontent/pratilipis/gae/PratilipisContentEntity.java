package com.pratilipi.pagecontent.pratilipis.gae;

import java.util.List;

import javax.jdo.annotations.NotPersistent;
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
	
	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private List<Long> pratilipiIdList;
	
	@NotPersistent
	private PratilipiType pratilipiType;

	@NotPersistent
	private Boolean publicDomain;
	
	@NotPersistent
	private Long languageId;
	
	@NotPersistent
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
	
	@Override
	public Boolean getPublicDomain() {
		return publicDomain;
	}

	@Override
	public void setPublicDomain( Boolean publicDomain ) {
		this.publicDomain = publicDomain;
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
	public PratilipiState getPratilipiState() {
		return pratilipiState;
	}

	@Override
	public void setPratilipiState( PratilipiState pratilipiState ) {
		this.pratilipiState = pratilipiState;
	}

	@Override
	public PratilipiFilter toFilter() {
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( pratilipiType );
		pratilipiFilter.setPublicDomain( publicDomain );
		pratilipiFilter.setLanguageId( languageId );
		pratilipiFilter.setState( pratilipiState );
		return pratilipiFilter;
	}
	
}
