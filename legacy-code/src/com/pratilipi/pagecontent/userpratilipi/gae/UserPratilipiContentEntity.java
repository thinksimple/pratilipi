package com.pratilipi.pagecontent.userpratilipi.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.userpratilipi.UserPratilipiContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class UserPratilipiContentEntity extends PageContentEntity implements UserPratilipiContent { }
