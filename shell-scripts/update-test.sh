export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

cd ~/pratilipi

while true
do
	git remote update

	if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
	then
		git fetch
		git reset --hard origin/master
		
		mvn clean
		mvn install
		ant
		
		# Update devo-pratilipi/default
		mvn appengine:update -Dapp.id=devo-pratilipi -Dapp.module=default
		
		# Update prod-pratilipi/gamma
		mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=gamma
	fi

	sleep 300
done
