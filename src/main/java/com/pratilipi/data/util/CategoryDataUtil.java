package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.CategoryData;
import com.pratilipi.data.type.Category;

public class CategoryDataUtil {
	
	private static final Logger logger = 
			Logger.getLogger( CategoryDataUtil.class.getName() );

	public static List<CategoryData> getCategoryList( Language language ) throws UnexpectedServerException {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Category> categoryList = dataAccessor.getCategoryList( language );
		List<CategoryData> categoryDataList = new ArrayList<>( categoryList.size() );
		for( Category category : categoryList )
			categoryDataList.add( new CategoryData( category.getName(), category.getPratilipiFilter() ) );
		return categoryDataList;
	}

}