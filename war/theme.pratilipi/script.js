/* For Google Analytics tracking */
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-53742841-2', 'auto');
ga('require', 'displayfeatures');
ga('send', 'pageview');


/* SCRIPT IS USED TO CHANGE HEIGHT OF LOGIN/SIGHNUP PANEL TO ACCOMODATE IN SMALL SCREENS */
$(document).ready(function() {
// Optimalisation: Store the references outside the event handler:
var $window = $(window);
function checkWidth() {
var windowsize = $window.width();
if (windowsize > 768) {
$('#Pratilipi-Search-UserAccess').height( '50px' );
}
else
$('#Pratilipi-Search-UserAccess').height( '80px' );
}
// Execute on load
checkWidth();
// Bind event listener
$(window).resize(checkWidth);
});