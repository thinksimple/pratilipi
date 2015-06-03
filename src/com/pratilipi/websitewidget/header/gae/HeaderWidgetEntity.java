package com.pratilipi.websitewidget.header.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.pratilipi.websitewidget.header.HeaderWidget;

@SuppressWarnings("serial")
@PersistenceCapable
public class HeaderWidgetEntity extends WebsiteWidgetEntity implements HeaderWidget { }
