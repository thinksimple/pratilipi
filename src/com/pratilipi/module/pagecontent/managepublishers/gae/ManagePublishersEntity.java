package com.pratilipi.module.pagecontent.managepublishers.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.module.pagecontent.managepublishers.ManagePublishers;

@SuppressWarnings("serial")
@PersistenceCapable
public class ManagePublishersEntity extends PageContentEntity
		implements ManagePublishers { }
