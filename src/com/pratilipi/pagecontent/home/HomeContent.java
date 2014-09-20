package com.pratilipi.pagecontent.home;

import java.util.List;

import com.claymus.data.transfer.PageContent;

public interface HomeContent extends PageContent {

	List<Long> getBookIdList();

	void setBookIdList( List<Long> bookIdList );

	List<Long> getPoemIdList();

	void setPoemIdList( List<Long> poemIdList );

	List<Long> getStoryIdList();

	void setStoryIdList( List<Long> storyIdList );

}
