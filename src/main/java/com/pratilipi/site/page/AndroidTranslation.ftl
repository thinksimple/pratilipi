<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pratilipi Language Translations</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
     <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-red.min.css" /> 
    <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="dialog-polyfill/dialog-polyfill.js"></script>
    <link rel="stylesheet" type="text/css" href="dialog-polyfill/dialog-polyfill.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <style>
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
        background: url('card_bkg/email.jpg') center / cover;
    }
    
    .notif {
        background: url('card_bkg/notif.jpg') center / cover;
    }
    
    .mdl-grid {
        padding-top: 10%;
        padding-left: 10%;
        width: 70%;
    }
    .pratilipi-logo{
    	width:70%;
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
            <dialog class="mdl-dialog">
                <div class="mdl-dialog__content">
                    <div class="mdl-dialog__title">
                        Select a language
                    </div>
                    <ul class="demo-list-control mdl-list">
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                	      			Hindi
                	    		</span>
                            <span class="mdl-list__item-secondary-action">
                	     			<label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-1">
                	        			<input type="radio" id="list-option-1" class="mdl-radio__button" name="options" value="HINDI"/>
                	        		</label>
                	    		</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                	      			Marathi
                	    		</span>
                            <span class="mdl-list__item-secondary-action">
                	      			<label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-2">
                	        			<input type="radio" id="list-option-2" class="mdl-radio__button" name="options" value="MARATHI"/>
                	      			</label>
                	    		</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                	      			Gujarati
                	    		</span>
                            <span class="mdl-list__item-secondary-action">
                	      			<label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-3">
                	      				<input type="radio" id="list-option-3" class="mdl-radio__button" name="options" value="GUJARATI"/>
                	        		</label>
                	    		</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            	    Bengali
                            	</span>
                            <span class="mdl-list__item-secondary-action">
                            	    <label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-4">
                            	      	<input type="radio" id="list-option-4" class="mdl-radio__button" name="options" value="BENGALI"/>
                            	    </label>
                            	</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            	    Tamil
                            	</span>
                            <span class="mdl-list__item-secondary-action">
                            	    <label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-5">
                            	      	<input type="radio" id="list-option-5" class="mdl-radio__button" name="options" value="TAMIL"/>
                            	    </label>
                            	</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            	    Telugu
                            	</span>
                            <span class="mdl-list__item-secondary-action">
                            	    <label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-6">
                            	      	<input type="radio" id="list-option-6" class="mdl-radio__button" name="options" value="TELUGU"/>
                            	    </label>
                            	</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            	    Kannada
                            	</span>
                            <span class="mdl-list__item-secondary-action">
                            	    <label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-7">
                            	      	<input type="radio" id="list-option-7" class="mdl-radio__button" name="options" value="KANNADA"/>
                            	    </label>
                            	</span>
                        </li>
                        <li class="mdl-list__item">
                            <span class="mdl-list__item-primary-content">
                            	    Malayalam
                            	</span>
                            <span class="mdl-list__item-secondary-action">
                            	    <label class="demo-list-radio mdl-radio mdl-js-radio mdl-js-ripple-effect" for="list-option-8">
                            	      	<input type="radio" id="list-option-8" class="mdl-radio__button" name="options" value="MALAYALAM"/>
                            	    </label>
                            	</span>
                        </li>
                    </ul>
                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent continue">
                        Continue
                    </button>
                </div>
            </dialog>
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
        </main>
    </div>
</body>
<script>
var dialog = document.querySelector('dialog');
$('.option-selector').hide();
dialogPolyfill.registerDialog(dialog);
dialog.showModal();
dialog.querySelector('.continue').addEventListener('click', function() {
    var language = $('input[name=options]:checked').val();
    console.log(language);
    dialog.close();
    $('.option-selector').show();
    $('.continue-over').click(function() {
        var group = $(this).val();
        console.log(group);
        $.ajax({
            type: 'GET',
            url: 'http://' + language.toLowerCase() + '.gamma.pratilipi.com/api/i18n?language=' + language + '&group=' + group,
            data: {
                get_param: 'value'
            },
            // dataType: 'jsonp',
            success: function(data) {
                console.log(data)
            }
        });
    });
});
</script>

</html>
