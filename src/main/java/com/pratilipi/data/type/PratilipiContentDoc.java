package com.pratilipi.data.type;

import java.util.List;

import com.google.gson.JsonArray;

public interface PratilipiContentDoc {

	enum PageletType {
		HEAD_1, HEAD_2, TEXT, HTML, BLOCK_QUOTE, IMAGE
	}

	enum AlignmentType {
		LEFT, CENTER, RIGHT, JUSTIFY
	}


	interface Pagelet {
		PageletType getType();
		void setType( PageletType type );
		Object getData();
		void setData( Object data );
		AlignmentType getAlignment();
	}

	interface Page {
		List<Pagelet> getPageletList();
		void addPagelet( PageletType type, Object data );
		void addPagelet( PageletType type, Object data, AlignmentType alignmentType );
		void deleteAllPagelets();
		void setHtml( String html );
	}

	interface Chapter {
		String getTitle();
		void setTitle( String title );
		int getPageCount();
		Page getPage( int pageNo );
		List<Page> getPageList();
		Page addPage( int pageNo );
		Page addPage( PageletType type, Object data );
		void deletePage( int pageNo );
		int getNesting();
	}

	
	int getChapterCount();
	Chapter getChapter( int chapterNo );
	List<Chapter> getChapterList();
	Chapter addChapter( String title );
	Chapter addChapter( Integer chapterNo, String title );
	Chapter addChapter( Integer chapterNo, String title, Integer nesting );
	void deleteChapter( int chapterNo );
	JsonArray getIndex();

}
