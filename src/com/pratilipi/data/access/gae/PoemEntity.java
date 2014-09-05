package com.pratilipi.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.pratilipi.data.transfer.Poem;

@PersistenceCapable
public class PoemEntity extends PratilipiEntity implements Poem { }
