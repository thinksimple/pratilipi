while true
do
git remote update
if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
git fetch
git reset --hard origin/master
echo Yes
fi
sleep 5
done


# original - do not disturb
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



#program

# Setting the tag name. Change only here if you wanted to change the tag name
TAG="release-prod"
SIZE=${#TAG}

while true
do
# Setting the Local head to the head
LOCAL_HEAD="$(git rev-parse HEAD)"
git remote update
REMOTE_TAG="$(git show-ref refs/tags/$TAG -d)"
REMOTE_TAG="${REMOTE_TAG:52+$SIZE:40}"
if [ $LOCAL_HEAD != $REMOTE_TAG ]
then
git fetch
git reset --hard $REMOTE_TAG
echo Updated
fi
echo $LOCAL_HEAD
echo $REMOTE_TAG
sleep 5
done




#test - WORKED YAAY! :-D
TAG="release-prod"
SIZE=${#TAG}
REMOTE_TAG="$(git show-ref refs/tags/$TAG -d)"
REMOTE_TAG="${REMOTE_TAG:52+$SIZE:40}"
echo $REMOTE_TAG
















