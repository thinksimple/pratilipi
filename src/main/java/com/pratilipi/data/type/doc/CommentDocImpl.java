package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.data.type.CommentDoc;

public class CommentDocImpl implements CommentDoc {
	
	private Long userId;
	
	private String content;
	private Long dateMillis;
	
	private List<Long> likedByUserIds;
	
	
	@Override
	public Long getUserId() {
		return userId;
	}
	
	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	
	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent( String content ) {
		this.content = content;
	}
	
	@Override
	public Date getDate() {
		return dateMillis == null ? null : new Date( dateMillis );
	}

	@Override
	public void setDate( Date date ) {
		this.dateMillis = date == null ? null : date.getTime();
	}

	
	@Override
	public List<Long> getLikedByUserIds() {
		return likedByUserIds == null ? new ArrayList<Long>( 0 ) : likedByUserIds;
	}
	
	@Override
	public void setLikedByUserIds( List<Long> likedByUserIds ) {
		this.likedByUserIds = likedByUserIds == null || likedByUserIds.size() == 0 ? null : likedByUserIds;
	}
	
}