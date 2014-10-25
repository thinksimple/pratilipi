package com.pratilipi.pagecontent.pratilipis.gae;

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
	private PratilipiType pratilipiType;

	@Persistent( column = "X_COL_1" )
	private Boolean publicDomain;
	
	@Persistent( column = "X_COL_2" )
	private Long languageId;

	@Persistent( column = "X_COL_3" )
	private PratilipiState pratilipiState;

	
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
