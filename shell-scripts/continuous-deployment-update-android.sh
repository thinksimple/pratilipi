export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/release-android) ]
then
	git fetch
	git reset --hard origin/release-android
	
	mvn clean
	mvn install
	ant
	
	
	cp src/main/webapp/WEB-INF/android-web.xml             src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/android-appengine-web.xml   src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/android
	mvn gcloud:deploy -Dapp.id=prod-pratilipi -Dapp.module=android

	
	# Cleaning up .git directory
	git gc
fi
