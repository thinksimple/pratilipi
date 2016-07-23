package com.pratilipi.data.type;

import java.util.List;

import com.pratilipi.data.type.doc.PratilipiContentDocImpl.Chapter;


public interface PratilipiContentDoc {
	
	enum PageletType {
		HEAD_1, HEAD_2, HTML, TEXT, IMAGE,
	}
	
	int getChapterCount();
	Chapter getChapter( int chapterNo );
	List<Chapter> getChapterList();
	void addChapter( Chapter chapter );
	
}
