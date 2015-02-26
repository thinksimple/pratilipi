package com.pratilipi.pagecontent.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.service.shared.data.EventData;
import com.pratilipi.service.shared.data.PratilipiData;

public class EventContentProcessor extends PageContentProcessor<EventContent> {

	@Override
	public String generateTitle( EventContent eventContent, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Event event = dataAccessor.getEvent( eventContent.getId() );
		return event.getName() + " (" + event.getNameEn() + ")";
	}
	
	@Override
	public String generateHtml(
			EventContent eventContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Event event = dataAccessor.getEvent( eventContent.getId() );
		EventData eventData = pratilipiHelper.createEventData( event );
		

		List<Long> pratilipiIdList = new LinkedList<>();
		if( event.getId() == 5724293958729728L ) {
			pratilipiIdList.add( 5714860599934976L );
			pratilipiIdList.add( 5188359081164800L );
			pratilipiIdList.add( 5748975256731648L );
			pratilipiIdList.add( 5142066044600320L );
			pratilipiIdList.add( 5645869466517504L );
			pratilipiIdList.add( 4906884104454144L );
			pratilipiIdList.add( 6286849043595264L );
			pratilipiIdList.add( 5158596132208640L );
			pratilipiIdList.add( 5123970642739200L );
			pratilipiIdList.add( 5755063473537024L );
			pratilipiIdList.add( 5660007156678656L );
			pratilipiIdList.add( 5751678938644480L );
			pratilipiIdList.add( 5764984579555328L );
			pratilipiIdList.add( 5731142116311040L );
			pratilipiIdList.add( 5071033459539968L );
			pratilipiIdList.add( 5203730769117184L );
			pratilipiIdList.add( 5675862665986048L );
			pratilipiIdList.add( 5112912712564736L );
			pratilipiIdList.add( 6247049829810176L );
			pratilipiIdList.add( 5700641305395200L );
			pratilipiIdList.add( 5150712619073536L );
			pratilipiIdList.add( 5673942714941440L );
			pratilipiIdList.add( 5764224101908480L );
			pratilipiIdList.add( 5116432874471424L );
			pratilipiIdList.add( 5093206228205568L );
			pratilipiIdList.add( 5679510703833088L );
			pratilipiIdList.add( 5670554354843648L );
			pratilipiIdList.add( 6207213370605568L );
			pratilipiIdList.add( 5712633491619840L );
			pratilipiIdList.add( 6275126836330496L );
			pratilipiIdList.add( 6265634824388608L );
			pratilipiIdList.add( 5167516376629248L );
			pratilipiIdList.add( 5694163790069760L );
			pratilipiIdList.add( 5922826238296064L );
			pratilipiIdList.add( 5757923519102976L );
			pratilipiIdList.add( 5724332043010048L );
			pratilipiIdList.add( 5200628292780032L );
			pratilipiIdList.add( 5727873780416512L );
			pratilipiIdList.add( 5646384292167680L );
		} else if( event.getId() == 5641434644348928L ) {
			pratilipiIdList.add( 5705015998021632L );
			pratilipiIdList.add( 5653401597640704L );
			pratilipiIdList.add( 5688274483937280L );
			pratilipiIdList.add( 5717073850269696L );
			pratilipiIdList.add( 5651817190916096L );
			pratilipiIdList.add( 5723456272334848L );
			pratilipiIdList.add( 5695064290361344L );
			pratilipiIdList.add( 5647744857276416L );
			pratilipiIdList.add( 5722400918339584L );
		}
		List<PratilipiData> pratilipiDataList =
				pratilipiHelper.createPratilipiDataListFromIdList(
						pratilipiIdList, false, false, false );
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "eventData", eventData );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
