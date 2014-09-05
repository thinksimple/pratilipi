package com.pratilipi.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.pratilipi.data.transfer.Article;

@PersistenceCapable
public class ArticleEntity extends PratilipiEntity implements Article { }
