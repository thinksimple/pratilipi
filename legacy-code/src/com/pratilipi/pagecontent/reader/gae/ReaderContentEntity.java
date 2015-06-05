package com.pratilipi.pagecontent.reader.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.reader.ReaderContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class ReaderContentEntity extends PageContentEntity
		implements ReaderContent { }
