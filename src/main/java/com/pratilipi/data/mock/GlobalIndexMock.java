package com.pratilipi.data.mock;

import static com.pratilipi.data.mock.AuthorMock.*;
import static com.pratilipi.data.mock.PageMock.*;
import static com.pratilipi.data.mock.PratilipiMock.*;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.util.PratilipiDataUtil;

public class GlobalIndexMock {

	public static final List<Object> GLOBAL_INDEX = new LinkedList<>();
	
	
	static {
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( hiPratilipi_1, hiAuthor_1, hiPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( hiPratilipi_2, hiAuthor_1, hiPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( hiPratilipi_3, hiAuthor_1, hiPratilipi_3_Page ) );
		
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( guPratilipi_1, guAuthor_1, guPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( guPratilipi_2, guAuthor_1, guPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( guPratilipi_3, guAuthor_1, guPratilipi_3_Page ) );
		
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( taPratilipi_1, taAuthor_1, taPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( taPratilipi_2, taAuthor_1, taPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createPratilipiData( taPratilipi_3, taAuthor_1, taPratilipi_3_Page ) );
	}
	
}
