<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<link rel="shortcut icon" type="image/png" href="/favicon.png">
		<title>${ title }</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
	</head>

	<body>
		pratilipi-data='${ pratilipiJson }'
		<br>
		addedToLib=${ addedToLib?c }
		<br>
		review-list='${ reviewListJson }'
		<br>
		cursor='${ reviewListCursor! }'
    </body>


</html>
