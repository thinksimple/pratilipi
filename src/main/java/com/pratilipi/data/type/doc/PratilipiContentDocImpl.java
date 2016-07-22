package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.PratilipiContentDoc;


public class PratilipiContentDocImpl implements PratilipiContentDoc {
	
	public static class Pagelet {
		
		private Object data;

		private PageletType type;
		

		public Pagelet( Object data, PageletType type ) {
			this.data = data;
			this.type = type;
		}

		
		public Object getData() {
			return data;
		}

		public PageletType getType() {
			return type;
		}

	}

	public static class Page {

		private List<Pagelet> pagelets;
		
		
		public Page() {}
		
		public Page( List<Pagelet> pageletList ) {
			this.pagelets = pageletList;
		}

		
		public int getPageletCount() {
			return pagelets == null ? 0 : pagelets.size();
		}

		public Pagelet getPagelet( int pageletNo ) {
			return pagelets == null || pagelets.size() < pageletNo ? null : pagelets.get( pageletNo - 1 );
		}

		public List<Pagelet> getPageletList() {
			return pagelets == null ? new ArrayList<Pagelet>( 1 ) : pagelets;
		}

		public void addPagelet( Pagelet pagelet ) {
			if( this.pagelets == null )
				this.pagelets = new LinkedList<>();
			this.pagelets.add( pagelet );
		}
		
	}
	
	public static class Chapter {

		private String title;

		private List<Page> pages;

		
		public Chapter( String title ) {
			this.title = title;
		}

		public Chapter( String title, List<Page> pageList ) {
			this.title = title;
			this.pages = pageList;
		}
		
		
		public String getTitle() {
			return title;
		}

		public int getPageCount() {
			return pages == null ? 0 : pages.size();
		}

		public Page getPage( int pageNo ) {
			return pages == null || pages.size() < pageNo ? null : pages.get( pageNo - 1 );
		}
		
		public void addPage( Page page ) {
			if( this.pages == null )
				this.pages = new LinkedList<>();
			this.pages.add( page );
		}

	}
	

	
	public List<Chapter> chapterList;

	
	public PratilipiContentDocImpl() {}
	
	public PratilipiContentDocImpl( List<Chapter> chapterList ) {
		this.chapterList = chapterList;
	}
	

	public int getChapterCount() {
		return chapterList == null ? 0 : chapterList.size();
	}

	public Chapter getChapter( int chapterNo ) {
		return chapterList == null || chapterList.size() < chapterNo ? null : chapterList.get( chapterNo - 1 );
	}
	
	public void addChapter( Chapter chapter ) {
		if( this.chapterList == null )
			this.chapterList = new LinkedList<>();
		this.chapterList.add( chapter );
	}
	
}
