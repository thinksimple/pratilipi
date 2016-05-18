package com.pratilipi.data.type;

import java.io.Serializable;

import com.pratilipi.common.util.PratilipiFilter;

public interface Category extends Serializable {

	String getName();
	
	PratilipiFilter getPratilipiFilter();
	
}
