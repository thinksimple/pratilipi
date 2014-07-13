package com.claymus.data.access.gae;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.PageWidget;

@PersistenceCapable( table = "PAGE_WIDGET" )
@Discriminator( column = "TYPE", strategy = DiscriminatorStrategy.CLASS_NAME )
public abstract class PageWidgetEntity implements PageWidget {

	@PrimaryKey
	@Persistent( column = "PAGE_WIDGET_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "POSITION" )
	private String position;
	
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getPosition() {
		return position;
	}

	@Override
	public void setPosition( String position ) {
		this.position = position;
	}

}
