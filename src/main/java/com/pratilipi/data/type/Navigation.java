package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.List;

public interface Navigation extends Serializable {

	@SuppressWarnings("serial")
	class Link implements Serializable {
		
		private String name;
		private String url;
		
		public Link( String name, String url ) {
			this.name = name;
			this.url = url;
		}
		
		public String getName() {
			return name;
		}
		
		public String getUrl() {
			return url;
		}
		
	}
	
	
	String getTitle();
	
	void setTitle( String title );
	
	List<Link> getLinkList();
	
	void addLink( Link link );
	
}