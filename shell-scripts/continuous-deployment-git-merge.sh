git remote update

if [ $(git rev-parse HEAD) != $(git rev-parse origin) ]
then
	git fetch
	git reset --hard origin/master
	
	# Cleaning up .git directory
	git gc
fi


export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

if [ $hour -eq 0 -a $minute -eq 45 ]
then
	
	git remote set-url origin https://antshpra:$1@github.com/Pratilipi/pratilipi.git
	
	branch=$(date +%d-%b-%H-%M-Release)


	echo "Creating new branch -> $branch ..."
	git checkout -b $branch origin/master
	git push origin $branch
	
	
	update_module () {
		echo "Syncing $1 from $2 ..."
		git branch -D $1
		git push origin --delete --force $1
		git checkout -b $1 $2
		git push origin $1
	}
	
	update_module release-prod $branch
	update_module release-api $branch
	update_module release-worker $branch
	update_module release-android $branch

fi