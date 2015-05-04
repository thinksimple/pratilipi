package com.pratilipi.pagecontent.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InvalidArgumentException;
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
			HttpServletRequest request ) throws InvalidArgumentException, UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Event event = dataAccessor.getEvent( eventContent.getId() );

		if( event == null )
			throw new InvalidArgumentException( "Invalid Event Id" );
		
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
			pratilipiIdList.add( 5682850309341184L );
			pratilipiIdList.add( 5687883339923456L );
			pratilipiIdList.add( 5087344503816192L );
			pratilipiIdList.add( 5631703288643584L );
			pratilipiIdList.add( 5163046389415936L );
			pratilipiIdList.add( 5149836277972992L );
			pratilipiIdList.add( 6244999922450432L );
			pratilipiIdList.add( 5183355276492800L );
			pratilipiIdList.add( 5657082955038720L );
			pratilipiIdList.add( 5684170609131520L );
			pratilipiIdList.add( 5650099170443264L );
			pratilipiIdList.add( 5722142951866368L );
			pratilipiIdList.add( 5641478634209280L );
			pratilipiIdList.add( 5631232721289216L );
			pratilipiIdList.add( 5068282767867904L );
			pratilipiIdList.add( 5071513120145408L );
			pratilipiIdList.add( 5683247728033792L );
			pratilipiIdList.add( 5707548384559104L );
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
			pratilipiIdList.add( 5739568976363520L );
			pratilipiIdList.add( 5687698589220864L );
			pratilipiIdList.add( 5728394545201152L );
			pratilipiIdList.add( 5639526772899840L );
			pratilipiIdList.add( 5664266656940032L );
			pratilipiIdList.add( 5079493639143424L );
			pratilipiIdList.add( 5194758548881408L );
			pratilipiIdList.add( 5644807166754816L );
			pratilipiIdList.add( 5637257553772544L );
			pratilipiIdList.add( 5748696788500480L );
			pratilipiIdList.add( 5682882454487040L );
			pratilipiIdList.add( 5760316151431168L );
			pratilipiIdList.add( 5106158675165184L );
			pratilipiIdList.add( 5765097389555712L );
			pratilipiIdList.add( 5111780653137920L );
			pratilipiIdList.add( 5079044412407808L );
			pratilipiIdList.add( 5680257289945088L );
			pratilipiIdList.add( 6221531482947584L );
			pratilipiIdList.add( 4863588183310336L );
			pratilipiIdList.add( 5152489426911232L );
			pratilipiIdList.add( 5660809678028800L );
		} else if( event.getId() == 5085337277693952L ) {
			pratilipiIdList.add( 4816254556700672L );
			pratilipiIdList.add( 4878783374950400L );
			pratilipiIdList.add( 6222892316491776L );
			pratilipiIdList.add( 5445862402555904L );
			pratilipiIdList.add( 6271305724723200L );
			pratilipiIdList.add( 5409772597673984L );
			pratilipiIdList.add( 6234502418399232L );
			pratilipiIdList.add( 5663330890940416L );
			pratilipiIdList.add( 5759681167360000L );
			pratilipiIdList.add( 5109750878437376L );
			pratilipiIdList.add( 6235650785280000L );
			pratilipiIdList.add( 5110667585519616L );
			pratilipiIdList.add( 5081271218733056L );
			pratilipiIdList.add( 5631845760761856L );
			pratilipiIdList.add( 5096266023305216L );
			pratilipiIdList.add( 5663172715347968L );
			pratilipiIdList.add( 5680127098748928L );
			pratilipiIdList.add( 5102224753557504L );
			pratilipiIdList.add( 5180118280437760L );
			pratilipiIdList.add( 5188186644938752L );
			pratilipiIdList.add( 5651396451893248L );
			pratilipiIdList.add( 5149188945870848L );
			pratilipiIdList.add( 5723885634846720L );
			pratilipiIdList.add( 5668122396721152L );
			pratilipiIdList.add( 5693617926569984L );
			pratilipiIdList.add( 5753434137427968L );
			pratilipiIdList.add( 5069181959536640L );
			pratilipiIdList.add( 5724919044243456L );
			pratilipiIdList.add( 5074346993254400L );
			pratilipiIdList.add( 6296120871354368L );
			pratilipiIdList.add( 5101789351247872L );
			pratilipiIdList.add( 5661878118252544L );
			pratilipiIdList.add( 5763956001996800L );
			pratilipiIdList.add( 5722522989363200L );
			pratilipiIdList.add( 5762909909024768L );
			pratilipiIdList.add( 5731880649359360L );
			pratilipiIdList.add( 5682803131809792L );
			pratilipiIdList.add( 5719042186805248L );
			pratilipiIdList.add( 5740735865290752L );
			pratilipiIdList.add( 5069624676712448L );
			pratilipiIdList.add( 5737932862259200L );
			pratilipiIdList.add( 5186886612025344L );
			pratilipiIdList.add( 5675192449761280L );
			pratilipiIdList.add( 5187971359703040L );
			pratilipiIdList.add( 5702443480383488L );
			pratilipiIdList.add( 5735104290750464L );
			pratilipiIdList.add( 5661698467823616L );
			pratilipiIdList.add( 5753938796085248L );
			pratilipiIdList.add( 6212424139014144L );
			pratilipiIdList.add( 5099266863267840L );
			pratilipiIdList.add( 5697030479413248L );
			pratilipiIdList.add( 5766140261302272L );
			pratilipiIdList.add( 5650472497053696L );
			pratilipiIdList.add( 5653514407641088L );
			pratilipiIdList.add( 5763040100220928L );
			pratilipiIdList.add( 5760251861139456L );
			pratilipiIdList.add( 5699320770723840L );
			pratilipiIdList.add( 5638435045900288L );
			pratilipiIdList.add( 5155035268775936L );
			pratilipiIdList.add( 5698949524488192L );
			pratilipiIdList.add( 5656396062261248L );
			pratilipiIdList.add( 5766270452498432L );
			pratilipiIdList.add( 5664994049916928L );
			pratilipiIdList.add( 5743893538668544L );
			pratilipiIdList.add( 5663401019703296L );
			pratilipiIdList.add( 5652217059082240L );
			pratilipiIdList.add( 5642286020952064L );
			pratilipiIdList.add( 5756790922805248L );
			pratilipiIdList.add( 5156445226008576L );
			pratilipiIdList.add( 5093417788899328L );
		} else if( event.getId() == 5133264616423424L ){
			pratilipiIdList.add( 5640809676275712L );
			pratilipiIdList.add( 5082859467440128L );
			pratilipiIdList.add( 6317636392583168L );
			pratilipiIdList.add( 5634383868329984L );
			pratilipiIdList.add( 5727762631360512L );
			pratilipiIdList.add( 5686637379977216L );
			pratilipiIdList.add( 5753208165105664L );
			pratilipiIdList.add( 4881800908242944L );
			pratilipiIdList.add( 4861957085593600L );
			pratilipiIdList.add( 5760159569674240L );
			pratilipiIdList.add( 4814703318532096L );
			pratilipiIdList.add( 6324359492796416L );
			pratilipiIdList.add( 6244664361353216L );
			pratilipiIdList.add( 5943610507788288L );
			pratilipiIdList.add( 5147971171647488L );
			pratilipiIdList.add( 5657456701079552L );
			pratilipiIdList.add( 5631952916840448L );
			pratilipiIdList.add( 5681433892880384L );
			pratilipiIdList.add( 4859069525393408L );
			pratilipiIdList.add( 6597633095237632L );
			pratilipiIdList.add( 6203503273836544L );
			pratilipiIdList.add( 5760892398469120L );
			pratilipiIdList.add( 5716141255163904L );
			pratilipiIdList.add( 5671839405703168L );
			pratilipiIdList.add( 5643569461198848L );
		} else if( event.getId() == 5142286312669184L ){
			pratilipiIdList.add( 4507527593066496L );
			pratilipiIdList.add( 5469550472593408L );
			pratilipiIdList.add( 5705040979296256L );
			pratilipiIdList.add( 5754443572183040L );
			pratilipiIdList.add( 5142091025874944L );
			pratilipiIdList.add( 4765863030816768L );
			pratilipiIdList.add( 5717860382932992L );
			pratilipiIdList.add( 5644004762845184L );
			pratilipiIdList.add( 6280810336354304L );
			pratilipiIdList.add( 5143913702621184L );
			pratilipiIdList.add( 6291265897365504L );
			pratilipiIdList.add( 5865999978987520L );
			pratilipiIdList.add( 5731544786272256L );
			pratilipiIdList.add( 5303050025566208L );
			pratilipiIdList.add( 5669744468295680L );
			pratilipiIdList.add( 4888175512125440L );
			pratilipiIdList.add( 4917152851165184L );
			pratilipiIdList.add( 4802840954404864L );
			pratilipiIdList.add( 6006654084579328L );
			pratilipiIdList.add( 5663521362673664L );
			pratilipiIdList.add( 5478490447020032L );
			pratilipiIdList.add( 4792359355154432L );
			pratilipiIdList.add( 4774803005243392L );
			pratilipiIdList.add( 6011292682813440L );
			pratilipiIdList.add( 5728633066881024L );
			pratilipiIdList.add( 5707098268631040L );
			pratilipiIdList.add( 5089650112724992L );
			pratilipiIdList.add( 5669228568903680L );
			pratilipiIdList.add( 4858857931145216L );
			pratilipiIdList.add( 5695308147195904L );
			pratilipiIdList.add( 5168442898382848L );
			pratilipiIdList.add( 5636860353183744L );
			pratilipiIdList.add( 5670086220185600L );
			pratilipiIdList.add( 5638054622527488L );
			pratilipiIdList.add( 5667340125470720L );
			pratilipiIdList.add( 6236747964874752L );
			pratilipiIdList.add( 5707975213711360L );
			pratilipiIdList.add( 5754800155131904L );
			pratilipiIdList.add( 5692884846116864L );
			pratilipiIdList.add( 5678418456739840L );
			pratilipiIdList.add( 5163118564999168L );
			pratilipiIdList.add( 5168830116528128L );
			pratilipiIdList.add( 4800534389194752L );
			pratilipiIdList.add( 6289471909658624L );
			pratilipiIdList.add( 6298760447524864L );
			pratilipiIdList.add( 6208039463944192L );
			pratilipiIdList.add( 4910375224999936L );
			pratilipiIdList.add( 6269577084272640L );
			pratilipiIdList.add( 5101612938821632L );
			pratilipiIdList.add( 5172644047486976L );
			pratilipiIdList.add( 6220897790722048L );
			pratilipiIdList.add( 4891169070776320L );
			pratilipiIdList.add( 5639243523162112L );
			pratilipiIdList.add( 6017068977618944L );
			pratilipiIdList.add( 5975435041046528L );
			pratilipiIdList.add( 5766184234385408L );
			pratilipiIdList.add( 5154496334266368L );
			pratilipiIdList.add( 6305192429486080L );
			pratilipiIdList.add( 5756336679682048L );
			pratilipiIdList.add( 4815506645188608L );
			pratilipiIdList.add( 5672485227855872L );
			pratilipiIdList.add( 5122889535717376L );
			pratilipiIdList.add( 5131461350916096L );
			pratilipiIdList.add( 4909908013088768L );
			pratilipiIdList.add( 6240159242649600L );
			pratilipiIdList.add( 5112590741012480L );
			pratilipiIdList.add( 4935399717732352L );
			pratilipiIdList.add( 6011292682813440L );
			pratilipiIdList.add( 5733593619890176L );
			pratilipiIdList.add( 5730057788063744L );
			pratilipiIdList.add( 5483600350806016L );
			pratilipiIdList.add( 5762665179774976L );
			pratilipiIdList.add( 5202340659331072L );
			pratilipiIdList.add( 4828877683687424L );
			pratilipiIdList.add( 6207307708891136L );
			pratilipiIdList.add( 6577280218300416L );
			pratilipiIdList.add( 5448582811353088L );
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
