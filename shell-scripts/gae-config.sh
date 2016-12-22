export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.9
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

cp src/main/webapp/WEB-INF/config-queue.xml             src/main/webapp/WEB-INF/queue.xml
cp src/main/webapp/WEB-INF/config-cron.xml              src/main/webapp/WEB-INF/cron.xml
cp src/main/webapp/WEB-INF/config-datastore-indexes.xml src/main/webapp/WEB-INF/datastore-indexes.xml
cp src/main/webapp/WEB-INF/config-dispatch.xml          src/main/webapp/WEB-INF/dispatch.xml

mvn appengine:update_cron     -Dapp.id=prod-pratilipi -Dapp.module=gamma
mvn appengine:update_queues   -Dapp.id=prod-pratilipi -Dapp.module=gamma
mvn appengine:update_indexes  -Dapp.id=prod-pratilipi -Dapp.module=gamma
mvn appengine:vaccum_indexes  -Dapp.id=prod-pratilipi -Dapp.module=gamma
mvn appengine:update_dispatch -Dapp.id=prod-pratilipi -Dapp.module=gamma
