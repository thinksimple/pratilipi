export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

cd ~/pratilipi

git fetch
git reset --hard origin/master

mvn install
ant
mvn appengine:update -Dapp.id=prod-pratilipi