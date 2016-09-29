package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonObject;
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
			if( pagelets == null )
				pagelets = new LinkedList<>();
			pagelets.add( new PageletImpl( type, data ) );
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
		public void setTitle( String title ) {
			this.title = title;
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
		public Page addPage( int pageNo ) {
			PageImpl page = new PageImpl();
			if( pages == null )
				pages = new LinkedList<>();
			pages.add( pageNo - 1, page );
			return page;
		}

		@Override
		public Page addPage( PageletType type, Object data ) {
			PageImpl page = new PageImpl();
			page.addPagelet( type, data );
			if( pages == null )
				pages = new LinkedList<>();
			pages.add( page );
			return page;
		}

		@Override
		public List<Page> getPageList() {
			return pages == null
					? new ArrayList<Page>( 0 )
					: new ArrayList<Page>( pages );
		}


		@Override
		public void deletePage( int pageNo ) {
			if( pages == null || pages.size() < pageNo )
				return;
			pages.remove( pageNo - 1 );
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
		if( chapters == null || chapters.size() < chapterNo )
			return null;
		int count = 0;
		for( Chapter chapter : chapters ) {
			count++;
			if( count == chapterNo )
				return chapter;
		}
		return null;
	}

	@Override
	public List<Chapter> getChapterList() {
		return chapters == null
				? new ArrayList<Chapter>( 0 )
				: new ArrayList<Chapter>( chapters );
	}

	@Override
	public Chapter addChapter( String title ) {
		return addChapter( title, null );
	}

	@Override
	public Chapter addChapter( String title, Integer chapterNo ) {
		return addChapter( title, null, null );
	}

	@Override
	public Chapter addChapter( String title, Integer chapterNo, Integer nesting ) {

		if( chapters == null )
			chapters = new LinkedList<>();

		ChapterImpl chapter = nesting != null ?
				new ChapterImpl( title, nesting ) : new ChapterImpl( title );

		if( chapterNo == null || chapters.size() < chapterNo )
			chapters.add( chapter );
		else
			chapters.add( chapterNo - 1, chapter );

		return chapter;

	}

	@Override
	public void deleteChapter( int chapterNo ) {

		if( chapters == null || chapters.size() < chapterNo )
			return;

		chapters.remove( chapterNo - 1 );

	}

	@Override
	public List<JsonObject> getIndex() {

		if( chapters == null )
			return null;

		List<JsonObject> index = new LinkedList<JsonObject>();

		int i = 0;
		for( Chapter chapter : chapters ) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty( "chapterNo", ++i );
			if( chapter.getTitle() != null )
				jsonObject.addProperty( "chapterTitle", chapter.getTitle() );
			index.add( jsonObject );
		}

		return index;

	}

}
