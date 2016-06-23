package com.pratilipi.data.type.gae;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.Navigation;

@SuppressWarnings("serial")
public class NavigationEntity implements Navigation {

	private String TITLE;
	
	private List<Link> LINKS;
	
	
	public NavigationEntity() {
		this.LINKS = new LinkedList<Link>();
	}

	
	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void setTitle( String title ) {
		this.TITLE = title;
	}
	
	@Override
	public List<Link> getLinkList() {
		return LINKS;
	}
	
	@Override
	public void addLink( Link link ) {
		this.LINKS.add( link );
	}

}
