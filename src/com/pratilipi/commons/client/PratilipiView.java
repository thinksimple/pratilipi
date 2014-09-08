package com.pratilipi.commons.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.PratilipiData;

public abstract class PratilipiView extends Composite {

	public abstract HandlerRegistration addEditHyperlinkClickHandler(
			ClickHandler clickHandler);

	public abstract PratilipiData getPratilipiData();
	
	public abstract void setPratilipiData( PratilipiData pratilipiData );

	public abstract void focus();

}
