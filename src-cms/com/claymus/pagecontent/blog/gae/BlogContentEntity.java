package com.claymus.pagecontent.blog.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.blog.BlogContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class BlogContentEntity extends PageContentEntity implements BlogContent {

	private String cursor;
	
	private Integer postCount;
	
	
	@Override
	public String getCursor() {
		return cursor;
	}

	@Override
	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}

	@Override
	public Integer getPostCount() {
		return postCount;
	}

	@Override
	public void setPostCount( Integer postCount ) {
		this.postCount = postCount;
	}

}
