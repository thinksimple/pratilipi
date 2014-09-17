package com.pratilipi.pagecontent.languages.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.languages.LanguagesContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class LanguagesContentEntity extends PageContentEntity
		implements LanguagesContent { }
