package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.List;

import com.google.gson.JsonObject;

public interface Navigation extends Serializable {

	@SuppressWarnings("serial")
	class Link implements Serializable {
		
		private String name;
		private String url;
		private String apiName;
		private Object apiRequest;
		private String imageName;
		
		public Link( String name, String url, String apiName, String apiRequest, String imageName ) {
			this.name = name;
			this.url = url;
			this.apiName = apiName;
			this.apiRequest = apiRequest;
			this.imageName = imageName;
		}
		
		public String getName() {
			return name;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getApiName() {
			return apiName;
		}
		
		public Object getApiRequest() {
			return apiRequest;
		}
		
		public void setApiRequest( JsonObject apiRequest ) {
			this.apiRequest = apiRequest;
		}
		
		public String getImageName() {
			return imageName;
		}
		
	}
	
	
	String getTitle();
	
	void setTitle( String title );
	
	List<Link> getLinkList();
	
	void addLink( Link link );
	
}