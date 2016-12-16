<!DOCTYPE html>
<html class="js no-touchevents" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sky-High Airport Arrivals</title>
    <meta name="theme-color" content="#29BDBB">
</head>

<body>
    <header>
        <div class="content">
            <h3>Arrivals</h3>
        </div>
    </header>
    <div class="container">
        <div id="main" class="content">
            <ul class="arrivals-list" data-bind="foreach: arrivals">
                <li class="item">
                    <span class="title" data-bind="html: title"></span>
                    <span class="status" data-bind="html: averageRating"></span>
                    <span class="time" data-bind="html: time"></span>
                </li>
            </ul>
        </div>
    </div>
    <script src="http://0.ptlp.co/resource-all/jquery.knockout.boostrap.js"></script>
    <script src="resources/js/pwa-poc/arrivals.js?1"></script>
    <script src="resources/js/pwa-poc/page.js?1"></script>    
    <script src="resources/js/pwa-poc/main.js?1"></script>
</body>

</html>
