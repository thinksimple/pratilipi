package com.pratilipi.data.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PratilipiContentData {
	
	public class Chapter {

		private String title;

		private List<Page> pageList;

		
		public Chapter( String chapter ) {
			
			this.pageList = new ArrayList<Page>();

			// Setting the first heading as title
			Document doc = Jsoup.parse( chapter );
			// Iterating through h1 to h6.
			for( Integer i = 1; i <= 6; i ++ ) {
				Elements elements = doc.getElementsByTag( "h" + i ); 
				if( elements == null || elements.isEmpty() )
					continue;
				for( Element element : elements ) {
					if( ! element.text().isEmpty() ) {
						this.title = element.text();
						i = 7;
						break;
					}
				}
			}
			
			this.pageList.add( new Page( chapter ) );
		}

		public Chapter( String title, List<Page> pageList ) {
			this.title = title;
			this.pageList = pageList;
		}

		public String getTitle() {
			return title;
		}

		public int getPageCount() {
			return pageList == null ? 0 : pageList.size();
		}

		public Page getPage( int pageNo ) {
			return pageList == null || pageList.size() < pageNo ? null : pageList.get( pageNo - 1 );
		}

	}
	
	public class Page {

		private List<Pagelet> pageletList;
		
		
		public Page( String content ) {
			this.pageletList = new ArrayList<Pagelet>();
	
			Document document = Jsoup.parse( content );
			Elements elements = document.getAllElements();
			
			for( Element element : elements ) {
				if( element.tag().toString().toUpperCase().equals( "img" ) ) 
					this.pageletList.add( new Pagelet( element.attr( "src" ), PageletType.IMAGE ) ); 
				else  if( ! element.text().isEmpty() && 
						! element.tag().equals( "#root" ) && 
						! element.tag().equals( "html" ) && 
						! element.tag().equals( "head" ) &&
						! element.tag().equals( "body" ) ) 
					this.pageletList.add( new Pagelet( element.text(), PageletType.TEXT ) );
				
			}
		}
		
		public Page( List<Pagelet> pageletList ) {
			this.pageletList = pageletList;
		}

		public int getPageletCount() {
			return pageletList == null ? 0 : pageletList.size();
		}

		public Pagelet getPagelet( int pageletNo ) {
			return pageletList == null || pageletList.size() < pageletNo ? null : pageletList.get( pageletNo - 1 );
		}

	}
	
	public class Pagelet {
		
		private String id;

		private String data;

		private PageletType type;
		

		public Pagelet( String data, PageletType type ) {
			this.id = UUID.randomUUID().toString();
			this.data = data;
			this.type = type;
		}

		public String getId() {
			return id;
		}

		public String getData() {
			return data;
		}

		public PageletType getType() {
			return type;
		}

	}
	
	public enum PageletType {
		TEXT, HTML, IMAGE
	}
	
	public List<Chapter> chapterList;

	public PratilipiContentData( List<Chapter> chapterList ) {
		this.chapterList = chapterList;
	}

	public int getChapterCount() {
		return chapterList == null ? 0 : chapterList.size();
	}

	public Chapter getChapter( int chapterNo ) {
		return chapterList == null || chapterList.size() < chapterNo ? null : chapterList.get( chapterNo - 1 );
	}
	
}
