package com.pratilipi.data.type.gae;

import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.Category;

@SuppressWarnings("serial")
public class CategoryEntity implements Category {

	private String name;
	
	private PratilipiFilter pratilipiFilter;

	
	public CategoryEntity() {}
	
	public CategoryEntity( String name, PratilipiFilter pratilipiFilter ) {
		this.name = name;
		this.pratilipiFilter = pratilipiFilter;
	}

	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public PratilipiFilter getPratilipiFilter() {
		return pratilipiFilter;
	}
	
}
