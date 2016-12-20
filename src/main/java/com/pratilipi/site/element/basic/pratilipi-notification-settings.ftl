  <div class="parent-container" style="">
    <div class="container">
      <div class="pratilipi-shadow secondary-500 box" style="text-align: center;">
        <img style="width: 50px;height: 50px;" src="http://0.ptlp.co/resource-hi/logo/pratilipi_logo.png">
        <h3 class="text-center pratilipi-red" style="margin-top: 10px;"> <!-- ${ _strings.edit_author_heading } --> Notifications </h3>
      </div>
      <div class="pratilipi-shadow secondary-500 box">
      <form id="notification_settings_form">

        <div class="form-group">
          <label for="frequency"><!-- ${ _strings.edit_author_language } -->Email Frequncy:</label>
          <select class="form-control" id="email_frequency">
            <option value="IMMEDIATELY"><!-- ${ _strings.language_hi } -->Immediately</option>
            <option value="DIALY"><!-- ${ _strings.language_ta } -->Daily</option>
            <option value="WEEKLY"><!-- ${ _strings.language_kn } -->Weekly</option>
            <option value="MONTHLY"><!-- ${ _strings.language_bn } -->Monthly</option>
            <option value="NEVER"><!-- ${ _strings.language_bn } -->Never</option>
          </select>
        </div>

        <div class="form-group">
          <label for="content">Content</label><br>    
          <div class="checkbox">
            <label>
              <input name="content" value="USER_PRATILIPI_REVIEW" type="checkbox"> New Review
            </label>
          </div>  
          <div class="checkbox">
            <label>
              <input name="content" value="COMMENT_REVIEW_REVIEWER" type="checkbox"> New Comment
            </label>
          </div>  
          <div class="checkbox">
            <label>
              <input name="content" value="VOTE_REVIEW_REVIEWER" type="checkbox"> Like on review
            </label>
          </div>  
          <div class="checkbox">
            <label>
              <input name="content" value="VOTE_COMMENT_REVIEW_COMMENTOR" type="checkbox"> New Like on Comment
            </label>
          </div>            
        </div> 

        <div class="form-group">
          <label for="network">Network</label><br>   
          <div class="checkbox">
            <label>
              <input name="network" value="AUTHOR_FOLLOW" type="checkbox"> New Follower
            </label>
          </div>  
          <div class="checkbox">
            <label>
              <input name="network" value="PRATILIPI_PUBLISHED_FOLLOWER" type="checkbox"> New Content by people you follow
            </label>
          </div>  
          <div class="checkbox">
            <label>
              <input name="network" value="GENERIC  " type="checkbox"> Pratilipi Updates and Offers
            </label>
          </div>              
        </div>
        <button type="submit">Submit</button> 
      </form>
      </div>
    </div>
  </div>
  <script>
    $( "#notification_settings_form" ).on( "submit", function( e ) {
      e.preventDefault();
      var notification_preferences = {};
      notification_preferences[ "email_frequency" ] = $( "#email_frequency" ).val();
      notification_preferences[ "notification_subscriptions" ] = {};

      $( "input:checkbox" ).each( function(){
        notification_preferences[ "notification_subscriptions" ][ $( this ).val() ] = $( this ).is( ':checked' ) ;
      } ); 
      console.log( notification_preferences );   
      setNotificationPreferences( notification_preferences );
    });
    
    $( document ).ready(function() {
      var notification_preferences = getNotificationPreferences();
      var notification_subscriptions = notification_preferences[ "notification_subscriptions" ];
      $( "#email_frequency" ).val( notification_preferences[ "email_frequency" ] );

      $( "input:checkbox" ).each( function() {
        $( this ).prop('checked', notification_subscriptions[ $( this ).val() ]) ;
      });         

    });     
  </script>