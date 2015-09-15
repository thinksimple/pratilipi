package com.pratilipi.api.category.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.CategoryData;

@SuppressWarnings("unused")
public class GetCategoryListResponse extends GenericResponse { 
	
	private List<CategoryData> pratilipiCategoryList;

	
	private GetCategoryListResponse() {}
	
	public GetCategoryListResponse( List<CategoryData> pratilipiCategoryList ) {
		this.pratilipiCategoryList = pratilipiCategoryList;
	}
	
}
