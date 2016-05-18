package com.pratilipi.data.mock;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.gae.CategoryEntity;

public class CategoryMock {
	
	public static final List<Category> CATEGORY_TABLE = new LinkedList<>();

	public static final Category category_1 = new CategoryEntity( "A Category", new PratilipiFilter() );

	static {
		CATEGORY_TABLE.add( category_1 );
	}
	
}
