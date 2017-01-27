var gulp = require('gulp');
// Requires the gulp-sass plugin
var sass = require('gulp-sass');
var cleanCSS = require('gulp-clean-css');
var scss_source_dir = 'src/main/webapp/pwa-stylesheets/scss/**/*.scss';
var scss_dest_dir = 'src/main/webapp/pwa-stylesheets/css';
var css_minification_source_dir = 'src/main/webapp/pwa-stylesheets/css/**/*.css';
var css_minification_dest_dir = 'src/main/webapp/pwa-stylesheets/css-minified';

gulp.task('sass', function() {
  return gulp.src( scss_source_dir ) // Gets all files ending with .scss in app/scss and children dirs
    .pipe(sass())
    .pipe(gulp.dest( scss_dest_dir ))
})
 
gulp.task('minify-css', function() {
  return gulp.src(css_minification_source_dir)
    .pipe(cleanCSS({compatibility: 'ie8'}))
    .pipe(gulp.dest( css_minification_dest_dir ));
});

gulp.task('watch', function(){
  gulp.watch( scss_source_dir, ['sass']); 
  gulp.watch( css_minification_source_dir, ['minify-css']); 
  // Other watchers
})