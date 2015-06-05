package com.pratilipi.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.PratilipiGenre;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PRATILIPI_GENRE" )
public class PratilipiGenreEntity implements PratilipiGenre {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_GENRE_ID" )
	private String id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "GENRE_ID" )
	private Long genreId;
	
	
	public void setId( String id ) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	@Override
	public Long getGenreId() {
		return genreId;
	}
	
	@Override
	public void setGenreId( Long genreId ) {
		this.genreId = genreId;
	}
	
}
