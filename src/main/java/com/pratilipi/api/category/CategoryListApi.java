package com.pratilipi.api.category;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.category.shared.GetCategoryListRequest;
import com.pratilipi.api.category.shared.GetCategoryListResponse;
import com.pratilipi.data.client.CategoryData;
import com.pratilipi.data.util.CategoryDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/category/list" )
public class CategoryListApi extends GenericApi {
	
	@Get
	public GetCategoryListResponse getPratilipiCategory( GetCategoryListRequest request ) {
		
		List<CategoryData> categoryList = CategoryDataUtil.getCategoryList( request.getLanguage() );
		return new GetCategoryListResponse( categoryList );
		
	}

}
