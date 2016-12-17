export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
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
	
	
	cp src/main/webapp/WEB-INF/worker-web.xml             src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml   src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/worker
	mvn gcloud:deploy -Dgcloud.gcloud_project=prod-pratilipi -Dgcloud.service=worker

	
	cp src/main/webapp/WEB-INF/worker-fe-appengine-web.xml src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/worker-fe
	mvn gcloud:deploy -Dgcloud.gcloud_project=prod-pratilipi -Dgcloud.service=worker-fe
	
	
	cp src/main/webapp/WEB-INF/worker-be-appengine-web.xml src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/worker-be
	mvn gcloud:deploy -Dgcloud.gcloud_project=prod-pratilipi -Dgcloud.service=worker-be
	
	
	# Cleaning up .git directory
	git gc
fi
