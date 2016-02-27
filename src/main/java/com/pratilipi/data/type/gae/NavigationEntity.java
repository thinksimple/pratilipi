package com.pratilipi.data.type.gae;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.Navigation;

public class NavigationEntity implements Navigation {

	private static final long serialVersionUID = -837436400887184239L;

	
	private String title;
	
	private List<Link> linkList;
	
	
	public NavigationEntity() {
		this.linkList = new LinkedList<Link>();
	}

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}
	
	@Override
	public List<Link> getLinkList() {
		return linkList;
	}
	
	@Override
	public void addLink( Link link ) {
		linkList.add( link );
	}

}
