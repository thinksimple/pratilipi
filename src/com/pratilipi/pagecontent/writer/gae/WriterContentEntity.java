package com.pratilipi.pagecontent.writer.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.writer.WriterContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class WriterContentEntity extends PageContentEntity
		implements WriterContent { }
