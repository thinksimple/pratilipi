package com.pratilipi.data.mock;

import static com.pratilipi.data.mock.PratilipiMock.*;
import static com.pratilipi.data.mock.CategoryMock.*;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.PratilipiCategory;
import com.pratilipi.data.type.gae.PratilipiCategoryEntity;

public class PratilipiCategoryMock {
	
	public static final List<PratilipiCategory> PRATILIPI_CATEGORY_TABLE = new LinkedList<>();

	public static final PratilipiCategory pratilipiCategory_1 = new PratilipiCategoryEntity( category_1.getId(), hiPratilipi_1.getId() );

	static {
		PRATILIPI_CATEGORY_TABLE.add( pratilipiCategory_1 );
		
		pratilipiCategory_1.setCategoryId( 1L );
		pratilipiCategory_1.setPratilipiId( 1L );
	}
	
}
