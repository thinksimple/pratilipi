package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.PratilipiContentDoc;


public class PratilipiContentDocImpl implements PratilipiContentDoc {
	
	public static class PageletImpl implements PratilipiContentDoc.Pagelet {
		
		private PageletType type;
		
		private Object data;
		

		@SuppressWarnings("unused")
		private PageletImpl() {}
		
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

		private List<PageletImpl> pagelets;
		
		
		public PageImpl() {}
		
		
		@Override
		public List<Pagelet> getPageletList() {
			return pagelets == null
					? new ArrayList<Pagelet>( 1 )
					: new ArrayList<Pagelet>( pagelets );
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

		private List<PageImpl> pages;

		private Integer nesting;

		
		@SuppressWarnings("unused")
		private ChapterImpl() {}
		
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
			PageImpl page = new PageImpl();
			page.addPagelet( type, data );
			if( this.pages == null )
				this.pages = new LinkedList<>();
			this.pages.add( page );
			return page;
		}
		
		@Override
		public List<Page> getPageList() {
			return pages == null
					? new ArrayList<Page>( 0 )
					: new ArrayList<Page>( pages );
		}
		
		@Override
		public int getNesting() {
			return nesting == null ? 0 : nesting;
		}

	}
	

	
	public List<ChapterImpl> chapters;

	
	public PratilipiContentDocImpl() {}
	

	@Override
	public int getChapterCount() {
		return chapters == null ? 0 : chapters.size();
	}

	@Override
	public Chapter getChapter( int chapterNo ) {
		return chapters == null || chapters.size() < chapterNo ? null : chapters.get( chapterNo - 1 );
	}
	
	@Override
	public List<Chapter> getChapterList() {
		return chapters == null
				? new ArrayList<Chapter>( 0 )
				: new ArrayList<Chapter>( chapters );
	}
	
	@Override
	public Chapter addChapter( String title ) {
		ChapterImpl chapter = new ChapterImpl( title );
		if( this.chapters == null )
			this.chapters = new LinkedList<>();
		this.chapters.add( chapter );
		return chapter;
	}

	@Override
	public Chapter addChapter( String title, int nesting ) {
		ChapterImpl chapter = new ChapterImpl( title, nesting );
		if( this.chapters == null )
			this.chapters = new LinkedList<>();
		this.chapters.add( chapter );
		return chapter;
	}
	
}
