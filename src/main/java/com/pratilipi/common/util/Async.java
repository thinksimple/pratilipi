package com.pratilipi.common.util;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.pratilipi.data.DataAccessorFactory;

public abstract class Async {

	public final void exec() {
		Closeable closeable = ObjectifyService.begin();
		DataAccessorFactory.getL1CacheAccessor().flush();
		doSome();
		closeable.close();
	}

	public abstract void doSome();

}