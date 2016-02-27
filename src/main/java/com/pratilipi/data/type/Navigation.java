package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.List;

public interface Navigation extends Serializable {

	class Link {
		
		String name;
		String url;
		
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