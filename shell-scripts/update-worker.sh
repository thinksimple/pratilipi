RELEASE_COMMIT_ID="d096cd724026065a2307c0d2acfbd3081995e7b4"

if [ $(git rev-parse HEAD) != $(RELEASE_COMMIT_ID) ]
then
	git fetch
	git reset --hard RELEASE_COMMIT_ID
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/worker-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	cp src/main/webapp/WEB-INF/worker-queue.xml			src/main/webapp/WEB-INF/queue.xml
	cp src/main/webapp/WEB-INF/worker-cron.xml			src/main/webapp/WEB-INF/cron.xml

	# Update prod-pratilipi/worker
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=worker
fi
