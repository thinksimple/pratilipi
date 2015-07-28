export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

cd ~/pratilipi

while true
do

	date
	bash update-test.sh 2>&1 | logger
	sleep 60
	
done
