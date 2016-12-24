<!DOCTYPE html>
<html lang="${lang}">
	<head>
		<title>${ title }</title>
		<script src="http://0.ptlp.co/resource-all/jquery.knockout.boostrap.js" type="text/javascript"></script>
		<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
	</head>
	<body>
		<table width="100%">
			<tr>
				<th>AuthorId</th>
				<th>Read Count</th>
				<th>FollowCount</th>
			</tr>
			<tbody data-bind="foreach: {data: authorList, as: 'author'}">
				<tr>
					<td data-bind="text: author.AUTHOR_ID"></td>
					<td data-bind="text: author.TOTAL_READ_COUNT"></td>
					<td data-bind="text: author.FOLLOW_COUNT"></td>
				</tr>
			</tbody>
		</table>
		<br/>
		<br/>
		<h1>delay1=${ delay1 }</h1>
		<h2>delay2=${ delay2 }</h2>
		<script type="application/javascript">
			$(function(){
				var ViewModel = {
					authorList: ${ authorList }
				};
				ko.applyBindings( ViewModel );
			});
		</script>
	</body>
</html>