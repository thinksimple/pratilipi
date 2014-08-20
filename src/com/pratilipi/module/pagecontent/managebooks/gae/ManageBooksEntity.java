package com.pratilipi.module.pagecontent.managebooks.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.module.pagecontent.managebooks.ManageBooks;

@PersistenceCapable
public class ManageBooksEntity extends PageContentEntity implements ManageBooks { }
