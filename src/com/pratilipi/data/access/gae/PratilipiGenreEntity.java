package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.PratilipiGenre;

@PersistenceCapable( table = "PRATILIPI_GENRE" )
public class PratilipiGenreEntity implements PratilipiGenre {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_GENRE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "PRATILIPI_TYPE" )
	private PratilipiType pratilipiType;
	
	@Persistent( column = "GENRE_ID" )
	private Long genreId;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	@Override
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}
	
	@Override
	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}

	public Long getGenreId() {
		return genreId;
	}
	
	public void setGenreId( Long genreId ) {
		this.genreId = genreId;
	}
	
}
