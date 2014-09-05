package com.pratilipi.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.pratilipi.data.transfer.Story;

@PersistenceCapable
public class StoryEntity extends PratilipiEntity implements Story { }
