<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-pratilipi-page">
		<#include "meta/Head.ftl">
		<link rel='import' href='/elements.${lang}/pratilipi-pratilipi-page.html?2016060807'>
		<script>
			function convertDate( date ) {
				var d = new Date( date );
				function day(d) { return (d < 10) ? '0' + d : d; }
				function month(m) { var months = ['${ _strings.month_jan }','${ _strings.month_feb }','${ _strings.month_mar }',
				                                  '${ _strings.month_apr }','${ _strings.month_may }','${ _strings.month_jun }',
				                                  '${ _strings.month_jul }','${ _strings.month_aug }','${ _strings.month_sep }',
				                                  '${ _strings.month_oct }','${ _strings.month_nov }','${ _strings.month_dec }']; 
				                                  return months[m]; }
				return [ day(d.getDate()), month(d.getMonth()), d.getFullYear() ].join(' ');
			}
		</script>
	</head>
	
	<body>
		<pratilipi-pratilipi-page 
				user-data='${ userJson }'
				pratilipi-id='${ pratilipi.getId()?c }'
				pratilipi-data='${ pratilipiJson }'
				userpratilipi-data='${ userpratilipiJson! }'
				
				review-list='${ reviewListJson }'
				cursor='${ reviewListCursor! }'
				pratilipi-types='${ pratilipiTypesJson }'
				navigation-list='${ navigationList }'></pratilipi-pratilipi-page>
	</body>
	
</html>
