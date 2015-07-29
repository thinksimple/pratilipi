RELEASE_COMMIT_ID="3cb876fed323972ec33c7fae880fefe9fc3d7198"

export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

if [ $(git rev-parse HEAD) != $RELEASE_COMMIT_ID ]
then
	git fetch
	git reset --hard $RELEASE_COMMIT_ID
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/android-web.xml				src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/android-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/android
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=android
fi
