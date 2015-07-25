export M2_HOME=/usr/local/apache-maven/apache-maven-3.3.3
export M2=$M2_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
export PATH=$M2:$PATH

cd ~/pratilipi

# Setting the tag name. Change here only if you wanted to change the tag name
TAG="release-prod"

while true
do
    git remote update

    #Setting the local head
    LOCAL_HEAD="$(git rev-parse HEAD)"

    #Setting the remote tag
    REMOTE_TAG="$(git ls-remote https://github.com/Pratilipi/pratilipi.git rev-parse refs/tags/$TAG^{})"
    REMOTE_TAG="${REMOTE_TAG:0:40}"

        #Check whether local head and remote tag are not same.
        if [ $LOCAL_HEAD != $REMOTE_TAG ]
        then
            git fetch
            git reset --hard $REMOTE_TAG

            #maven update
            mvn clean
            mvn install
            ant

            # Update prod-pratilipi/default
            mvn appengine:update -Dapp.id=prod-pratilipi -Dapp.module=default

            cp src/main/webapp/WEB-INF/prod-web.xml src/main/webapp/WEB-INF/web.xml
            cp src/main/webapp/WEB-INF/prod-appengine-web.xml src/main/webapp/WEB-INF/appengine-web.xml

            date +"Last Updated on - %m-%d-%Y %r"
        fi

    sleep 300
done
