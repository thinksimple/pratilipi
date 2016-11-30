export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/release-worker) ]
then
	git fetch
	git reset --hard origin/release-worker
	
	mvn clean
	mvn install
	ant
	
	
	cp src/main/webapp/WEB-INF/worker-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	cp src/main/webapp/WEB-INF/worker-queue.xml			src/main/webapp/WEB-INF/queue.xml
	cp src/main/webapp/WEB-INF/worker-cron.xml			src/main/webapp/WEB-INF/cron.xml

	# Update prod-pratilipi/worker
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=worker
	
	
	cp src/main/webapp/WEB-INF/worker-fe-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/worker-fe
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=worker-fe
	
	
	cp src/main/webapp/WEB-INF/worker-be-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/worker-be
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=worker-be
	
	
	# Cleaning up .git directory
	git gc
fi
