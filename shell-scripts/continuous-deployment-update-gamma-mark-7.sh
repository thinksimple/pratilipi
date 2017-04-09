export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
	git fetch
	git reset --hard origin/master

	# pom-mark-7.xml
	cp pom-mark-7.xml pom.xml

	mvn clean
	mvn install
	ant


	cp src/main/webapp/WEB-INF/gamma-web.xml           src/main/webapp/WEB-INF/web.xml
	cp src/main/webapp/WEB-INF/gamma-appengine-web.xml src/main/webapp/WEB-INF/appengine-web.xml

	# Update prod-pratilipi/gamma
	mvn gcloud:deploy -Dapp.id=prod-pratilipi -Dapp.module=gamma


	# Cleaning up .git directory
	git gc
fi
