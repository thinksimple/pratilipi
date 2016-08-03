package com.pratilipi.data.mock;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.gae.CategoryEntity;

public class CategoryMock {
	
	public static final List<Category> CATEGORY_TABLE = new LinkedList<>();

	public static final Category category_1 = new CategoryEntity( "Stories", new PratilipiFilter() );
	public static final Category category_2 = new CategoryEntity( "Poems", new PratilipiFilter() );
	public static final Category category_3 = new CategoryEntity( "Magazines", new PratilipiFilter() );
	public static final Category category_4 = new CategoryEntity( "Books", new PratilipiFilter() );

	static {
		CATEGORY_TABLE.add( category_1 );
		CATEGORY_TABLE.add( category_2 );
		CATEGORY_TABLE.add( category_3 );
		CATEGORY_TABLE.add( category_4 );
	}
	
}