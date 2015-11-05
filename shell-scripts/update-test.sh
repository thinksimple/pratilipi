export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
	git fetch
	git reset --hard origin/master 1> /dev/null
	
	mvn clean 1> /dev/null
	mvn install 1> /dev/null
	ant 1> /dev/null
	
	cp src/main/webapp/WEB-INF/gamma-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/gamma-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update prod-pratilipi/gamma
	mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma 1> /dev/null
	logger "prod-pratilipi/gamma deployment completed."
	
	
	# Update devo-pratilipi/default
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=default 1> /dev/null
	logger "prod-pratilipi/default deployment completed."
	
	cp src/main/webapp/WEB-INF/worker-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/worker-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	cp src/main/webapp/WEB-INF/worker-queue.xml			src/main/webapp/WEB-INF/queue.xml
	
	# Update devo-pratilipi/worker
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=worker 1> /dev/null
	logger "prod-pratilipi/worker deployment completed."
fi
