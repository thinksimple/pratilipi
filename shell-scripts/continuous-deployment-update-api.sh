export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/release-api) ]
then
	git fetch
	git reset --hard origin/release-api
	
	mvn clean
	mvn install
	ant
	
	
	cp src/main/webapp/WEB-INF/api-web.xml             src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/api-appengine-web.xml   src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/api
	mvn gcloud:deploy -Dgcloud.gcloud_project=prod-pratilipi -Dgcloud.service=api

	
	# Cleaning up .git directory
	git gc
fi
