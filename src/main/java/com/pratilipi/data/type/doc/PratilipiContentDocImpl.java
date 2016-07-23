package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.PratilipiContentDoc;


public class PratilipiContentDocImpl implements PratilipiContentDoc {
	
	public static class PageletImpl implements PratilipiContentDoc.Pagelet {
		
		private PageletType type;
		
		private Object data;
		

		public PageletImpl( PageletType type, Object data ) {
			this.type = type;
			this.data = data;
		}

		
		@Override
		public PageletType getType() {
			return type;
		}

		@Override
		public Object getData() {
			return data;
		}

	}

	public static class PageImpl implements PratilipiContentDoc.Page {

		private List<Pagelet> pagelets;
		
		
		public PageImpl() {}
		
		public PageImpl( List<Pagelet> pageletList ) {
			this.pagelets = pageletList;
		}

		
		@Override
		public List<Pagelet> getPageletList() {
			return pagelets == null ? new ArrayList<Pagelet>( 1 ) : pagelets;
		}

		@Override
		public void addPagelet( PageletType type, Object data ) {
			if( this.pagelets == null )
				this.pagelets = new LinkedList<>();
			this.pagelets.add( new PageletImpl( type, data ) );
		}
		
	}
	
	public static class ChapterImpl implements PratilipiContentDoc.Chapter {
		
		private String title;

		private List<Page> pages;

		private Integer nesting;

		
		public ChapterImpl( String title ) {
			this.title = title;
		}

		public ChapterImpl( String title, Integer nesting ) {
			this.title = title;
			this.nesting = nesting;
		}
		
		
		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public int getPageCount() {
			return pages == null ? 0 : pages.size();
		}

		@Override
		public Page getPage( int pageNo ) {
			return pages == null || pages.size() < pageNo ? null : pages.get( pageNo - 1 );
		}
		
		@Override
		public Page addPage( PageletType type, Object data ) {
			Page page = new PageImpl();
			page.addPagelet( type, data );
			if( this.pages == null )
				this.pages = new LinkedList<>();
			this.pages.add( page );
			return page;
		}
		
		@Override
		public List<Page> getPageList() {
			return pages;
		}
		
		@Override
		public int getNesting() {
			return nesting == null ? 0 : nesting;
		}

	}
	

	
	public List<Chapter> chapterList;

	
	public PratilipiContentDocImpl() {}
	
	public PratilipiContentDocImpl( List<Chapter> chapterList ) {
		this.chapterList = chapterList;
	}
	

	@Override
	public int getChapterCount() {
		return chapterList == null ? 0 : chapterList.size();
	}

	@Override
	public Chapter getChapter( int chapterNo ) {
		return chapterList == null || chapterList.size() < chapterNo ? null : chapterList.get( chapterNo - 1 );
	}
	
	@Override
	public List<Chapter> getChapterList() {
		return chapterList == null ? new ArrayList<Chapter>( 0 ) : chapterList;
	}
	
	@Override
	public Chapter addChapter( String title ) {
		Chapter chapter = new ChapterImpl( title );
		if( this.chapterList == null )
			this.chapterList = new LinkedList<>();
		this.chapterList.add( chapter );
		return chapter;
	}

	@Override
	public Chapter addChapter( String title, int nesting ) {
		Chapter chapter = new ChapterImpl( title, nesting );
		if( this.chapterList == null )
			this.chapterList = new LinkedList<>();
		this.chapterList.add( chapter );
		return chapter;
	}
	
}
