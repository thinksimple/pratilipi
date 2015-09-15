package com.pratilipi.data.client;

import com.pratilipi.common.util.PratilipiFilter;

public class CategoryData {

	private String name;
	
	private PratilipiFilter pratilipiFilter;

	
	@SuppressWarnings("unused")
	private CategoryData() {}
	
	public CategoryData( String name, PratilipiFilter pratilipiFilter ) {
		this.name = name;
		this.pratilipiFilter = pratilipiFilter;
	}
	
	
	public String getName() {
		return name;
	}

	public PratilipiFilter getPratilipiFilter() {
		return pratilipiFilter;
	}

}