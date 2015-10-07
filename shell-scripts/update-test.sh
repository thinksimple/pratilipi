export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/raghu-email) ]
then
	git fetch
	git reset --hard origin/raghu-email
	
	mvn clean
	mvn install
	ant
	
	# Update prod-pratilipi/gamma
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma
	
	# Update devo-pratilipi/default
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=default
	
	cp src/main/webapp/WEB-INF/worker-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	cp src/main/webapp/WEB-INF/worker-queue.xml			src/main/webapp/WEB-INF/queue.xml
	
	# Update devo-pratilipi/worker
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=worker
fi
