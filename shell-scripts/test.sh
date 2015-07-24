while true
do
git remote update
if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
git pull
git reset --hard origin/master
echo Yes
fi
sleep 5
done