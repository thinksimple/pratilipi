package com.pratilipi.site.element.data;


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
		private Item[] items;

		public String getTitle() {
			return title;
		}

		public Item[] getItems() {
			return items;
		}
		
	}

	private Section[] sections;

	public Section[] getSections() {
		return sections;
	}

}