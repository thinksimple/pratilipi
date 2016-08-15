package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.List;

public interface Navigation extends Serializable {

	@SuppressWarnings("serial")
	class Link implements Serializable {
		
		private String name;
		private String url;
		private String apiRequest;
		
		public Link( String name, String url, String apiRequest ) {
			this.name = name;
			this.url = url;
			this.apiRequest = apiRequest;
		}
		
		public String getName() {
			return name;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getApiRequest() {
			return apiRequest;
		}
		
	}
	
	
	String getTitle();
	
	void setTitle( String title );
	
	List<Link> getLinkList();
	
	void addLink( Link link );
	
}