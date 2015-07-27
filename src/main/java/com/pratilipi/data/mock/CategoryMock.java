package com.pratilipi.data.mock;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.CategoryType;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.gae.CategoryEntity;

public class CategoryMock {
	
	public static final List<Category> CATEGORY_TABLE = new LinkedList<>();

	public static final Category category_1 = new CategoryEntity( 1L );

	static {
		CATEGORY_TABLE.add( category_1 );
		
		category_1.setName( "Book" );
		category_1.setPlural( "Books" );
		category_1.setSerialNumber( 1 );
		category_1.setType( CategoryType.GENRE );
		category_1.setHidden( false );
	}
}
