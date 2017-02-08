<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pratilipi Language Translations</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-red.min.css" />
    <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.6/dialog-polyfill.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.6/dialog-polyfill.css" />
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <style>
    html {
        font-family: 'Noto Sans', sans-serif;
    }
    
    .demo-list-control {
        width: 300px;
    }
    
    .demo-list-radio {
        display: inline;
    }
    
    .mdl-dialog {
        width: 100%;
        height: 90%;
    }
    
    .demo-card-wide.mdl-card {
        width: 512px;
    }
    
    .demo-card-wide > .mdl-card__title {
        color: #fff;
        height: 256px;
        box-shadow: inset 0 0 0 1000px rgba(0, 0, 0, 0.2);
    }
    
    .demo-card-wide > .mdl-card__menu {
        color: #fff;
    }
    
    .email {
        background: url('http://i.imgur.com/Igzq3y0.jpg') center / cover;
    }
    
    .notif {
        background: url('http://i.imgur.com/ivt2BdL.jpg') center / cover;
    }
    
    .mdl-grid {
        /*padding-top: 5%;*/
        margin-left: 5%;
        width: 100%;
    }
    
    .pratilipi-logo {
        width: 70%;
    }
    
    .full-width {
        width: 80%;
    }
    
    .mdl-textfield {
        width: 100%!important;
        max-width: 100%!important;
    }
    
    .mdl-data-table {
        table-layout: fixed;
        width: 100%;
    }
    
    #my-table td,
    th {
        width: auto;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        -o-text-overflow: ellipsis;
    }
    
    td {
        border-top: 0px!important;
        border-bottom: 0px!important;
    }

    th {
        text-align: center!important;
    }
    /* unrelated to responsive table css */
    
    #my-table {
        margin-top: 24px;
    }
    
    .material-icons {
        vertical-align: -25%;
    }
    </style>
</head>

<body>
    <div class="mdl-layout mdl-js-layout">
        <header class="mdl-layout__header mdl-layout__header--scroll">
            <div class="mdl-layout__header-row">
                <span class="mdl-layout-title">
                    <img src="http://public.pratilipi.com/pratilipi-logo/png/Logo-2C-RGB-80px.png" class="pratilipi-logo"/>
                </span>
            </div>
        </header>
        <main>
            <div class="mdl-grid option-selector">
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="demo-card-wide mdl-card mdl-shadow--2dp">
                        <div class="mdl-card__title  email">
                            <h2 class="mdl-card__title-text">Email Translations</h2>
                        </div>
                        <div class="mdl-card__supporting-text">
                            All language translations related to content sent in email communications - both transactional and promotional in nature.
                        </div>
                        <div class="mdl-card__actions mdl-card--border">
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent continue-over" value="EMAIL">
                                Continue
                            </button>
                        </div>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="demo-card-wide mdl-card mdl-shadow--2dp">
                        <div class="mdl-card__title  notif">
                            <h2 class="mdl-card__title-text">Notification Translations</h2>
                        </div>
                        <div class="mdl-card__supporting-text">
                            All language translations related to content shown as notifications on web and mobile products.
                        </div>
                        <div class="mdl-card__actions mdl-card--border">
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent continue-over" value="NOTIFICATION">
                                Continue
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-table">
                <table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp action-form full-width">
                </table>
            </div>
    </div>
    </main>
    </div>
</body>
<script>
$('.continue-over').click(function() {
    $('.option-selector').html('<img src="http://www.downgraf.com/wp-content/uploads/2014/09/01-progress.gif"/>');
    //http://hindi.gamma.pratilipi.com/api?requests={%22req1%22:%22/i18n?group=EMAIL%26language=TAMIL%22,%22req2%22:%22/i18n?group=EMAIL%26language=ENGLISH%22}
    var group = $(this).val();
    var language = window.location.hostname.split(".")[0].toUpperCase();
    $.ajax({
        type: 'GET',
        // url: '/api/i18n?language=' + language + '&group=' + group,
        url: '/api?requests={%22req1%22:%22/i18n?group=' + group + '%26language=' + language + '%22,%22req2%22:%22/i18n?group=' + group + '%26language=ENGLISH%22}',
        success: function(resp) {
            data = JSON.parse(resp);
            englishData = data["req2"]["response"]["keyValues"];
            langData = data["req1"]["response"]["keyValues"];
            console.log(englishData);
            console.log(langData);
            var formInit = "<form action='#'>";
            var formEnd = "</form>";
            var tHead = '<thead><tr><th class="mdl-data-table__cell--non-numeric">English</th><th>' + language + '</th></tr></thead>';
            var tBodyInit = "<tbody>";
            var tBodyEnd = "</tbody>";
            var textFieldInit = '<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">';
            var textFieldEnd = '</div>';
            var inputField = '<textarea class="mdl-textfield__input" type="text" id="sample';
            var labelField = '<label class="mdl-textfield__label" for="sample';
            var i = 0;
            var inLoop = "";
            for (var e in englishData){
                for (var l in langData){
                    if (e==l){
                        inLoop = inLoop + '<tr>' + '<td class=mdl-data-table__cell--non-numeric">' + englishData[e] + '</td>' + '<td>' + textFieldInit + inputField + i + '"></textarea>' + labelField + i + '">' + langData[l] + '</label>' + textFieldEnd + '</td>' + '</tr>';
                        i = i + 1; 
                    }
                }
            }
            var out = tHead + tBodyInit + formInit + inLoop + formEnd + tBodyEnd;
            $('.action-form').html(out);
            $('.option-selector').hide();
            componentHandler.upgradeDom();
            $('.action-form').show();        
        }
    })
}); 
</script>
</html>
