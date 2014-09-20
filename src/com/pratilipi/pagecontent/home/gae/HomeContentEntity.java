package com.pratilipi.pagecontent.home.gae;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.home.HomeContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class HomeContentEntity extends PageContentEntity
		implements HomeContent {
	
	@Persistent( column = "X_COL_0" )
	private List<Long> bookIdList;

	@Persistent( column = "X_COL_1" )
	private List<Long> poemIdList;

	@Persistent( column = "X_COL_2" )
	private List<Long> storyIdList;
	

	@Override
	public List<Long> getBookIdList() {
		return bookIdList;
	}

	@Override
	public void setBookIdList( List<Long> bookIdList ) {
		this.bookIdList = bookIdList;
	} 
	
	@Override
	public List<Long> getPoemIdList() {
		return poemIdList;
	}

	@Override
	public void setPoemIdList( List<Long> poemIdList ) {
		this.poemIdList = poemIdList;
	} 
	
	@Override
	public List<Long> getStoryIdList() {
		return storyIdList;
	}

	@Override
	public void setStoryIdList( List<Long> storyIdList ) {
		this.storyIdList = storyIdList;
	} 
	
}
