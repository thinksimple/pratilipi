package com.pratilipi.data.type.gae;

import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.Category;

@SuppressWarnings("serial")
public class CategoryEntity implements Category {

	private String NAME;
	
	private PratilipiFilter PRATILIPI_FILTER;

	
	public CategoryEntity() {}
	
	public CategoryEntity( String name, PratilipiFilter pratilipiFilter ) {
		this.NAME = name;
		this.PRATILIPI_FILTER = pratilipiFilter;
	}

	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public PratilipiFilter getPratilipiFilter() {
		return PRATILIPI_FILTER;
	}
	
}
