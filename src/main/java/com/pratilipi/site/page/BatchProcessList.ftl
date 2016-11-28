<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		<title>${ title }</title>
		<script src="http://0.ptlp.co/resource-all/jquery.knockout.boostrap.js" type="text/javascript"></script>
		<link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>
		<style>
			html, body {
				padding: 0;
				margin: 0;
				width: 100%;
			}
			.header {
				height: 60px;
				min-height: 60px;
				width: 100%;
				position: fixed;
				background: #f5f5f5;
				border-bottom: 1px solid #d3d3d3;
				z-index: 1;
			}
			.pratilipi-icon {
				width: 48px;
				height: 48px;
				margin-left: 4px;
				margin-top: 8px;
				float: left;
			}
			.container {
				width: 100%;
				padding-top: 68px;
				margin-bottom: 10px;
				padding-left: 8px;
				padding-right: 8px;
			}
			.navigation-bar {
				margin-right: 12px;
				background: #f5f5f5;
				padding: 20px 32px;
				margin-bottom: 12px;
				border: 1px solid #d3d3d3;
			}
			.navigation-title {
				color: #d0021b;
				font-weight: 700;
				font-size: 18px;
			}
			.navigation-link {
				display: block;
				color: #333;
				line-height: 24px;
				text-decoration: none;
			}
			.main-content {
				overflow:hidden;
				padding: 0 24px;
				background: #f5f5f5;
				border: 1px solid #d3d3d3;
			}
			.footer {
				clear: both;
				width: 100%;
				background: #f5f5f5;
				padding: 20px 0;
				border-top: 1px solid #d3d3d3;
			}
			
			table {
				font-size: 14px;
				width: 100%;
				text-align: left;
				border-collapse: collapse;
				margin: 12px 0;
				background: #fff;
			}
			th {
				font-size: 14px;
				font-weight: Bold;
				color: #D0021b;
				padding: 8px;
				border: 1px solid #333;
			}
			td {
				font-size: 13px;
				font-weight: 400;
				color: #333;
				padding: 8px;
				border: 1px solid #d3d3d3;
			}
		</style>
	</head>
	
	<body>
		<div class="header">
			<a href="/">
				<img class="pratilipi-icon" title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-ta/logo/pratilipi_logo.png" />
			</a>
		</div>
		<div class="container">
			<div data-bind="foreach: { data: navigationList, as: 'navigation' }" class="navigation-bar pull-left hidden-xs hidden-sm">
				<div class="navigation-title" data-bind="text: title"></div>
				<div data-bind="foreach: {data: linkList, as: 'link'}">
					<a class="navigation-link" data-bind="attr: {href: url,title: name}, text: name"></a>
				</div>
				<br/>
			</div>
			<div class="main-content">
				<table width="100%">
					<tr>
						<th>
							Language
						</th>
						<th>
							Created On
						</th>
						<th>
							Message
						</th>
						<th>
							Url
						</th>
						<th>
							State
						</th>
					</tr>
					<tbody data-bind="foreach: {data: batchProcessList, as: 'batchProcess'}">
						<tr>
							<td data-bind="text: getLanguage( batchProcess.INIT_DOC )">
							</td>
							<td data-bind="text: batchProcess.CREATION_DATE">
							</td>
							<td data-bind="text: getMessage( batchProcess.EXEC_DOC )">
							</td>
							<td>
								<a data-bind="attr: { href: getSourceLink( batchProcess.INIT_DOC, batchProcess.EXEC_DOC ), target: '_blank' }, text: getSourceLink( batchProcess.INIT_DOC, batchProcess.EXEC_DOC )"></a>
							</td>
							<td data-bind="text: batchProcess.STATE_COMPLETED">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="footer">
			&nbsp;
		</div>
		<script type="application/javascript">
			function getLanguage( INIT_DOC ) {
				return JSON.parse( INIT_DOC ).authorFilter.language;
			}
			function getSourceLink( INIT_DOC, EXEC_DOC ) {
				var sourceId = JSON.parse( EXEC_DOC ).sourceId;
				var type = JSON.parse( EXEC_DOC ).type;
				if( type == "PRATILIPI" )
					return "http://" + getLanguage( INIT_DOC ).toLowerCase() + ".pratilipi.com/pratilipi/" + sourceId;
			}
			function getMessage( EXEC_DOC ) {
				return JSON.parse( EXEC_DOC ).message;
			}
			function transformList( batchProcessList ) {
				return {
					language: null,
					creationDate: null,
					message: null,
					pratilipi: null,
					state: null
				};
			}
			$(function(){
				var ViewModel = {
					user: ${ userJson },
					batchProcessList: ko.observableArray(${ batchProcessListJson }),
					navigationList: ${ navigationListJson }
				};

				ko.applyBindings( ViewModel );

			});
	</script>

	</body>

</html>