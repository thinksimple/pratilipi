##################################################################################################################

#GAMMA - UPDATE EVERYTHING EVERY ONE MINUTE
while true
do
git remote update
if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
git fetch
git reset --hard origin/master
echo Yes
fi
sleep 60
done

##################################################################################################################

# PROD MODULE - UPDATE ACCORDING TO TAGS - EVERY 5 MINUTES
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
date +"Last Updated on - %m-%d-%Y %r"
fi
sleep 300
done

##################################################################################################################


# original - do not disturb - for testing purpose
var="$(git show-ref refs/tags/release-prod)"
echo $var
echo ${var:0:40}

#working
var="$(git show-ref refs/tags/release-prod)"
var="${var:0:40}"
echo $var

#working
TAG="release-prod"
var="$(git show-ref refs/tags/$TAG)"
var="${var:0:40}"
echo $var


##################################################################################################################

#Some useful commands

#LIST OF TAGE IN LOCAL
git tag --list

#LIST OF TAGS IN REMOTE
git ls-remote -t

#SETTING THE LOCAL HEAD
LOCAL_HEAD="$(git rev-parse HEAD)"

#SETTING THE REMOTE HEAD
REMOTE_HEAD="$(git rev-parse origin)"

#SETTING THE LOCAL TAG.
TAG="release-prod"
# Getting the commit ID of the tag
#SIZE=${#TAG}
#LOCAL_TAG="$(git show-ref refs/tags/$TAG^{})"
#LOCAL_TAG="${LOCAL_TAG:52+$SIZE:40}"
#The same can be done in one step :-)
LOCAL_TAG="$(git rev-parse refs/tags/$TAG^{})"
echo $LOCAL_TAG

#SETTING THE REMOTE TAG
TAG="release-prod"
REMOTE_TAG="$(git ls-remote https://github.com/Pratilipi/pratilipi.git rev-parse refs/tags/$TAG^{})"
REMOTE_TAG="${REMOTE_TAG:0:40}"
echo $REMOTE_TAG


##################################################################################################################
