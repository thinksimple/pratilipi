<div class="parent-container">
	<div class="container">
		<div class="pratilipi-shadow secondary-500 box" style="margin: 12px auto;">
			<div class="text-center" style="padding: 10px;">
				<img style="width: 50px; height: 50px;" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png">
				<h3 class="text-center pratilipi-red" style="margin-top: 10px;"> <#-- ${ _strings.email_settings } --> Settings </h3>
			</div>

			<form id="notification_settings_form" style="text-align: left;">

				<div class="form-group">
					<label for="frequency"><#-- ${ _strings.email_frequency } -->Email Frequncy:</label>
					<select class="form-control" id="email_frequency">
						<option value="IMMEDIATELY"><#-- ${ _strings.email_frequency_immediate } -->Immediately</option>
						<option value="DAILY"><#-- ${ _strings.email_frequency_daily } -->Daily</option>
						<option value="WEEKLY"><#-- ${ _strings.email_frequency_weekly } -->Weekly</option>
						<option value="MONTHLY"><#-- ${ _strings.email_frequency_monthly } -->Monthly</option>
						<option value="NEVER"><#-- ${ _strings.email_frequency_never } -->Never</option>
					</select>
				</div>

				<div class="form-group">
					<label for="content">Content<#-- ${ _strings.email_group_content } --></label>		
					<div class="checkbox">
						<label>
							<input name="content" value="USER_PRATILIPI_REVIEW" type="checkbox"> New Review <#-- ${ _strings.option_new_review } -->
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input name="content" value="COMMENT_REVIEW_REVIEWER" type="checkbox"> New Comment <#-- ${ _strings.option_new_comment } -->
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input name="content" value="VOTE_REVIEW_REVIEWER" type="checkbox"> Like on review <#-- ${ _strings.option_like_review } -->
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input name="content" value="VOTE_COMMENT_REVIEW_COMMENTOR" type="checkbox"> New Like on Comment <#-- ${ _strings.option_like_comment } -->
						</label>
					</div>
				</div>

				<div class="form-group">
					<label for="network">Network <#-- ${ _strings.email_group_network } --></label>	 
					<div class="checkbox">
						<label>
							<input name="network" value="AUTHOR_FOLLOW" type="checkbox"> New Follower <#-- ${ _strings.option_new_follower } -->
						</label>
					</div>	
					<div class="checkbox">
						<label>
							<input name="network" value="PRATILIPI_PUBLISHED_FOLLOWER" type="checkbox"> New Content by people you follow <#-- ${ _strings.option_pratilipi_published_follower } -->
						</label>
					</div>	
					<div class="checkbox">
						<label>
							<input name="network" value="GENERIC" type="checkbox"> Pratilipi Updates and Offers <#-- ${ _strings.option_pratilipi_updates } -->
						</label>
					</div>							
				</div>

				<button class="pratilipi-dark-blue-button" type="submit">Submit <#-- ${ _strings.email_settings_save_changes } --></button>

			</form>
		</div>
	</div>
</div>
<script>
	$( "#notification_settings_form" ).on( "submit" , function( e ) {

		e.preventDefault();

		var notification_preferences = {};
		notification_preferences[ "emailFrequency" ] = $( "#email_frequency" ).val();
		notification_preferences[ "notificationSubscriptions" ] = {};

		$( "#notification_settings_form input:checkbox" ).each( function() {
			notification_preferences[ "notificationSubscriptions" ][ $( this ).val() ] = $( this ).is( ':checked' );
		});

		setNotificationPreferences( notification_preferences );

	});

	$( document ).ready( function() {
		getNotificationPreferences( getNotifPreferencesCallback );
	});

	function getNotifPreferencesCallback( notification_preferences ) {

		var notification_subscriptions = notification_preferences[ "notificationSubscriptions" ];

		$( "#email_frequency" ).val( notification_preferences[ "emailFrequency" ] != null ? 
				notification_preferences[ "emailFrequency" ] : "IMMEDIATELY" );

		$( "input:checkbox" ).each( function() {
			$( this ).prop( 'checked', notification_subscriptions != null && notification_subscriptions[ $( this ).val() ] != null ?
				notification_subscriptions[ $( this ).val() ] : true );
		});
	}
</script>