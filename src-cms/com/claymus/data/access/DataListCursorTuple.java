package com.claymus.data.access;

import java.util.List;

public class DataListCursorTuple<T> {

	private List<T> dataList;
	private String cursor;
	
	
	public DataListCursorTuple( List<T> dataList, String cursor ) {
		this.dataList = dataList;
		this.cursor = cursor;
	}

	
	public List<T> getDataList() {
		return dataList;
	}
	
	public String getCursor() {
		return cursor;
	}
	
}
