RELEASE_COMMIT_ID="d096cd724026065a2307c0d2acfbd3081995e7b4"

if [ $(git rev-parse HEAD) != $(RELEASE_COMMIT_ID) ]
then
	git fetch
	git reset --hard RELEASE_COMMIT_ID
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/android-web.xml				src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/android-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/android
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=android
fi
