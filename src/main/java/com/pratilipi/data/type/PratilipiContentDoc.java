package com.pratilipi.data.type;

import java.util.List;

import com.google.gson.JsonObject;

public interface PratilipiContentDoc {
	
	enum PageletType {
		HEAD_1, HEAD_2, HTML, TEXT, IMAGE, BLOCKQUOTE
	}
	
	interface Pagelet {
		PageletType getType();
		Object getData();
	}
	
	interface Page {
		List<Pagelet> getPageletList();
		void addPagelet( PageletType type, Object data );
	}
	
	interface Chapter {
		String getTitle();
		void setTitle( String title );
		int getPageCount();
		Page getPage( int pageNo );
		List<Page> getPageList();
		Page addPage( PageletType type, Object data );
		int getNesting();
	}

	int getChapterCount();
	Chapter getChapter( int chapterNo );
	List<Chapter> getChapterList();
	Chapter addChapter( String title );
	Chapter addChapter( String title, Integer chapterNo );
	Chapter addChapter( String title, Integer chapterNo, Integer nesting );
	void removeChapter( int chapterNo );
	List<JsonObject> getIndex();

}
