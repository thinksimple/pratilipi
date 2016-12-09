export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/release-prod) ]
then
	git fetch
	git reset --hard origin/release-prod
	
	mvn clean
	mvn install
	ant
	
	
	cp src/main/webapp/WEB-INF/prod-web.xml				src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/prod-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/prod
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=default

	
	cp src/main/webapp/WEB-INF/prod-dispatch.xml		src/main/webapp/WEB-INF/dispatch.xml

	# Update Routing
	mvn appengine:update_dispatch -Dapp.id=prod-pratilipi -Dapp.module=default
	
	
	# Cleaning up .git directory
	git gc
fi
