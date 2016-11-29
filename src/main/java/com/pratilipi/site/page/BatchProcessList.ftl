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
			.add-process {
				background: white;
				border: 1px solid #333;
				margin-top: 20px;
				padding: 20px;
				text-align: center;
				color: #d0021b;
				font-weight: 700;
				font-size: 16px;
				cursor: pointer;
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
			.select-language {
				padding: 16px 0;
			}
			.select-language span {
				font-size: 16px; 
				margin-right: 12px;
			}
			.select-language select {
				padding: 8px;
				margin: 0;
				-webkit-border-radius:4px;
				-moz-border-radius:4px;
				border-radius:4px;
				background: #f8f8f8;
				color:#888;
				border:none;
				outline:none;
				display: inline-block;
				-webkit-appearance:none;
				-moz-appearance:none;
				appearance:none;
				cursor:pointer;
			}
			.select-language select:focus {
				outline: none;
			}
			.input-message {
				padding: 4px 0;
			}
			.input-message textarea {
				width: 100%;
				height: 200px;
				border: 1px solid #cccccc;
				outline: none;
				padding: 5px;
			}
			.input-url {
				padding: 16px 0;
			}
			.input-url input {
				width: 100%;
				border: 1px solid #cccccc;
				padding: 4px;
			}
			.progress-message {
				padding: 12px 0;
				text-align: center;
				line-height: 24px;
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
				<div class="add-process" data-toggle="modal" data-target="#batchProcessModal">
					Add New Task
				</div>
				<table width="100%">
					<tr>
						<th>Language</th>
						<th>Created On</th>
						<th>Message</th>
						<th>Url</th>
						<th>State</th>
					</tr>
					<tbody data-bind="foreach: {data: batchProcessList, as: 'batchProcess'}">
						<tr>
							<td data-bind="text: getLanguage( batchProcess.INIT_DOC )"></td>
							<td data-bind="text: batchProcess.CREATION_DATE"></td>
							<td data-bind="text: getMessage( batchProcess.EXEC_DOC )"></td>
							<td>
								<a data-bind="attr: { href: getSourceLink( batchProcess.INIT_DOC, batchProcess.EXEC_DOC ), target: '_blank' }, text: getSourceLink( batchProcess.INIT_DOC, batchProcess.EXEC_DOC )"></a>
							</td>
							<td data-bind="text: batchProcess.STATE_COMPLETED"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="footer">
			&nbsp;
		</div>

		<div class="modal fade" id="batchProcessModal" tabindex="-1" role="dialog" aria-labelledby="batchProcessModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="batchProcessModalLabel">Create New Notification</h4>
					</div>
					<div class="modal-body">
						<div class="select-language">
							<span>Please select the language: </span>
							<select data-bind="
								options: languageList,
								optionsText: 'languageVernacular',
								optionsValue: 'language',
								optionsCaption: 'Choose Language',
								value: selectedLanguage">
							</select>
						</div>
						<div class="input-url">
							<input placeholder="Paste the URL here..." data-bind="value: inputUrl" />
						</div>
						<div class="input-message">
							<textarea data-bind="value: inputMessage"></textarea>
						</div>
						<div class="progress-message" data-bind="text: progressMessage">&nbsp;</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" data-bind="click: makePostCall, disable: requestOnFlight">Save changes</button>
					</div>
				</div>
			</div>
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
			function processLanguageMap( languageMap ) {
				var languageArray = [];
				for( var key in languageMap )
					if( languageMap.hasOwnProperty( key ) )
						languageArray.push( { "language": key, "languageVernacular": languageMap[key] } );
				return languageArray;
			}
			function getUriFromUrl( url ) {
				var patt = new RegExp( /http?:\/\/.*.pratilipi.com\b([-a-zA-Z0-9@:%_\+.~#&\/\/=]*)/g );
				var match = patt.exec( url );
				if( match ) {
					return match[1];
				}
				return null;
			}
			
			
			$(function(){
				var ViewModel = {
					user: ${ userJson },
					batchProcessList: ko.observableArray(${ batchProcessListJson }),
					navigationList: ${ navigationListJson },
					languageList: processLanguageMap( ${ languageMap } ),
					selectedLanguage: ko.observable(),
					inputUrl: ko.observable(),
					inputMessage: ko.observable(),
					progressMessage: ko.observable(),
					requestOnFlight: ko.observable( false ),
					makePostCall: function() {
						if( ! this.selectedLanguage() ) {
							this.progressMessage( "Please Select the language." );
							return;
						}
						if( this.inputUrl() == null || this.inputUrl().trim().length == 0 ) {
							this.progressMessage( "Please enter a url." );
							return;
						}
						if( getUriFromUrl( this.inputUrl() ) == null ) {
							this.progressMessage( "Please enter a valid url." );
							return;
						}
						if( this.inputMessage() == null || this.inputMessage().trim().length == 0 ) {
							this.progressMessage( "Please enter a vaild message." );
							return;
						}
						this.progressMessage( "Processing ..." );
						this.requestOnFlight( true );
						$.ajax({
							type: 'post',
							url: '/api/batch-process',
							data: { 
								'language': this.selectedLanguage(), 
								'message': this.inputMessage(),
								'sourceUri': getUriFromUrl( this.inputUrl() ),
								'type': "ANDROID_NOTIFACTION_BY_AUTHOR_FILTER",
								'state': "INIT"
							},
							success: function( response ) {
								ViewModel.progressMessage( "Success!!" );
								ViewModel.refreshPage();
								setTimeout( function() {
									ViewModel.requestOnFlight( false ); 
									$( "#batchProcessModal" ).modal( "hide" );
								}, 2000 ); 
							},
							error: function( response ) {
								ViewModel.requestOnFlight( false );
								ViewModel.progressMessage( "Some Exception occurred at server! Please try again!" );
							}
						});
					},
					refreshPage: function() {
						$.ajax({
							type: 'get',
							url: '/api/batch-process/list',
							success: function( response ) {
								var batchProcessList = JSON.parse( response ).batchProcessList;
								ViewModel.batchProcessList( batchProcessList );
							},
							error: function( response ) {
								console.log( response );
							}
						});
					}
				};

				ko.applyBindings( ViewModel );
				setInterval( function () { ViewModel.refreshPage(); }, 30*1000 );

			});
	</script>

	</body>

</html>