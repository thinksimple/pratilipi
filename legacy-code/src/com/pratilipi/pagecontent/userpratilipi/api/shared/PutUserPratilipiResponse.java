package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings( "serial" )
public class PutUserPratilipiResponse extends GenericResponse {
	
	private String bookmarks;
	
	public void setBookmarks( String bookmarks ) {
		this.bookmarks = bookmarks;
	}
	
	public String getBookmark(){
		return bookmarks;
	}
	
}
