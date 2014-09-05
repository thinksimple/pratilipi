package com.pratilipi.commons.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.PoemData;

public abstract class PoemView extends Composite {

	public abstract void setPoemData( PoemData poemData );

}
