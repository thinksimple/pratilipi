export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin/raghu) ]
then
	git fetch
	git reset --hard origin/raghu
	
	mvn clean
	mvn install
	ant
	
	cp src/main/webapp/WEB-INF/gamma-web.xml			src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/gamma-appengine-web.xml	src/main/webapp/WEB-INF/appengine-web.xml
	
	# Update devo-pratilipi/raghu
	mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=raghu
fi
