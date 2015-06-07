package com.pratilipi.site.element.data;

import java.util.List;

public class PratilipiNavigation {

	public static class Section {

		public static class Item {

			private String url;
			private String text;

			public String getUrl() {
				return url;
			}
			public String getText() {
				return text;
			}
			
		}

		private String title;
		private List<Item> items;

		public String getTitle() {
			return title;
		}

		public List<Item> getItems() {
			return items;
		}
		
	}

	private List<Section> sections;

	public List<Section> getSections() {
		return sections;
	}

}