package com.pratilipi.commons.client;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.PratilipiData;

public abstract class PratilipiView extends Composite {

	public abstract void setPratilipiData( PratilipiData pratilipiData );

}
