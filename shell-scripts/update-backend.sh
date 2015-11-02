export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/release-backend) ]
then
	git fetch
	git reset --hard origin/release-backend
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/backend-web.xml				src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/backend-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/backend
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=backend
fi
